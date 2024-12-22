package com.example.topacademy_java311_plehov.DAO.implementations;

import com.example.topacademy_java311_plehov.DAO.services.*;
import com.example.topacademy_java311_plehov.model.entities.itemAttributes.Status;
import com.example.topacademy_java311_plehov.model.entities.stock.entities.Pizza;
import com.example.topacademy_java311_plehov.model.entities.stock.entities.TechCart;
import com.example.topacademy_java311_plehov.model.entities.stock.entities.Topping;
import com.example.topacademy_java311_plehov.model.shop.Order;
import com.example.topacademy_java311_plehov.model.shop.OrderPosition;
import com.example.topacademy_java311_plehov.model.shop.Profile;
import com.example.topacademy_java311_plehov.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImplementation implements OrderService {
    private final OrderRepository repo;
    private final ApplicationUserService applicationUserService;
    private final OrderPositionService orderPositionService;
    private final PizzaService pizzaService;
    private final ProfileService profileService;
    private final IngredientService ingredientService;

    @Override
    public List<Order> findAll() {
        return repo.findAll(); // Реализовать метод поиска всех заказов
    }

    @Override
    public Optional<Order> findById(int id) {
        return repo.findById((long) id);
    }

    @Override
    public Order save(Order order) {
        return repo.save(order);
    }

    @Override
    public void deleteById(int id) {
        repo.deleteById(Long.valueOf(id));
    }

    @Override
    public Optional<Order> findCartByUserId(Long profileId) {
        return Optional.ofNullable(repo.findCartByUserId(profileId));
    }

    @Override
    @Transactional
    public void addToCart(String email, int pizzaId) {
        Long userId = applicationUserService.loadUserByUsername(email).getId();
        Order cart = findCartByUserId(userId).orElseThrow(
                () -> new IllegalStateException("Корзина не найдена для пользователя")
        );
        Pizza pizza = pizzaService.findById(pizzaId).orElseThrow(
                () -> new IllegalArgumentException("Пицца не найдена")
        );
        Optional<OrderPosition> existingPositionOpt = cart.getOrderPositions().stream()
                .filter(position -> false)
                .findFirst();

        if (existingPositionOpt.isPresent()) {
            OrderPosition existingPosition = existingPositionOpt.get();
            existingPosition.setAmount(existingPosition.getAmount() + 1);
            orderPositionService.save(existingPosition);
        } else {
            OrderPosition newPosition = new OrderPosition();
            newPosition.setPizza(pizza);
            newPosition.setAmount(1);
            newPosition.setOrder(cart);

            cart.getOrderPositions().add(newPosition);
            orderPositionService.save(newPosition);
        }
        repo.save(cart);
    }

    private void addToCart(Order cart, OrderPosition positionToAdd) {
        if (isStockPositionPresent(cart, positionToAdd)) {
            OrderPosition positionToIncrementAmount = cart.getOrderPositions().stream()
                    .filter(orderPosition -> orderPosition.getPizza().getId() == positionToAdd.getPizza().getId())
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Позиция для увеличения не найдена"));
            positionToIncrementAmount.setAmount(positionToIncrementAmount.getAmount() + 1);
        } else {
            cart.getOrderPositions().add(positionToAdd);
        }
    }

    @Override
    public Order findByOrderPositionId(int orderPositionId) {
        return orderPositionService.findById(orderPositionId).orElseThrow(() ->
                new IllegalArgumentException("Позиция заказа не найдена")).getOrder();
    }

    @Override
    @Transactional
    public void pay(Order cart) {
        cart.setStatus(Status.IS_PAID); // Устанавливаем статус оплачен
        repo.save(cart); // Сохраняем корзину

        // Удаляем ингредиенты со склада
        removeFromStock(cart);

        Profile currentProfile = cart.getProfile();

        // Создание новой корзины для пользователя
        Order newCart = Order.builder()
                .status(Status.CART) // Статус новой корзины
                .profile(currentProfile) // Связываем с текущим пользователем
                .orderPositions(new HashSet<>()) // Инициализируем новую корзину
                .build();

        // Добавление новой корзины в профиль пользователя
        currentProfile.getOrders().add(newCart);
        profileService.save(currentProfile); // Сохраняем профиль
    }


    private void removeFromStock(Order cart) {
        Set<OrderPosition> orderPositionSet = cart.getOrderPositions();
        orderPositionSet.forEach(orderPosition -> {
            Set<Topping> toppings = orderPosition.getToppings();
            Set<TechCart> techCarts = orderPosition.getPizza().getTechCart();
            techCarts.forEach(techCart ->
                    ingredientService.remove(techCart.getIngredient(), techCart.getIngredient().getAmount() * orderPosition.getAmount())
            );
            toppings.forEach(topping ->
                    ingredientService.remove(topping.getIngredient(), topping.getIngredient().getAmount() * orderPosition.getAmount())
            );
        });
    }

    @Override
    public List<Order> ordersToDeliver() {
        return repo.findAll().stream()
                .filter(order -> order.getStatus().equals(Status.IS_PAID))
                .collect(Collectors.toList());
    }

    @Override
    public void deliver(int orderId) {
        // Реализовать логику для доставки заказа
    }


    private boolean isStockPositionPresent(Order cart, OrderPosition positionToAdd) {
        return cart.getOrderPositions().stream()
                .anyMatch(orderPosition -> orderPosition.getPizza().getId() == positionToAdd.getPizza().getId());
    }
}
