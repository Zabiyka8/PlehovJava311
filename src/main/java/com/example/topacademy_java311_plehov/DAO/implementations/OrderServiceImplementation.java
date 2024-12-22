package com.example.topacademy_java311_plehov.DAO.implementations;

import com.example.topacademy_java311_plehov.DAO.services.*;
import com.example.topacademy_java311_plehov.model.entities.itemAttributes.Status;
import com.example.topacademy_java311_plehov.model.entities.stock.entities.Pizza;
import com.example.topacademy_java311_plehov.model.entities.stock.entities.TechCart;
import com.example.topacademy_java311_plehov.model.entities.stock.entities.Topping;
import com.example.topacademy_java311_plehov.model.secuirty.ApplicationUser;
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
        return repo.findById(profileId);
    }

    @Transactional
    @Override
    public void addToCart(String email, int pizzaId) {
        ApplicationUser loggedUser = applicationUserService.loadUserByUsername(email);
        Optional<Order> optionalCart = findCartByUserId(loggedUser.getProfile().getId());

        if (optionalCart.isPresent()) {
            Order cart = optionalCart.get();
            Optional<Pizza> stockPositionToBuy = pizzaService.findById(pizzaId);

            if (stockPositionToBuy.isPresent()) {
                OrderPosition positionToAdd = OrderPosition.builder()
                        .amount(1)
                        .pizza(stockPositionToBuy.get())
                        .order(cart)
                        .build();
                addToCart(cart, positionToAdd);
                repo.save(cart);
            } else {
                throw new IllegalArgumentException("Такого товара нет в наличии");
            }
        } else {
            throw new IllegalStateException("Корзина не найдена для пользователя");
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
        if (cart != null) {
            cart.setStatus(Status.IS_PAID);
            repo.save(cart);
            removeFromStock(cart);
            Profile currentProfile = cart.getProfile();
            currentProfile.getOrders().add(
                    Order.builder()
                            .status(Status.CART)
                            .profile(currentProfile)
                            .orderPositions(new HashSet<>())
                            .build()
            );
            profileService.save(currentProfile);
        } else {
            throw new IllegalArgumentException("Корзина не может быть оплачена, так как не найдена");
        }
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

    private boolean isStockPositionPresent(Order cart, OrderPosition positionToAdd) {
        return cart.getOrderPositions().stream()
                .anyMatch(orderPosition -> orderPosition.getPizza().getId() == positionToAdd.getPizza().getId());
    }
}
