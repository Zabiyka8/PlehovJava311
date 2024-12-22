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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        return null;
    }

    @Override
    public Optional<Order> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Order save(Order order) {
        return null;
    }

    @Override
    public void deleteById(int id) {
    }


    @Override
    public Optional<Order> findCartByUserId(Long profileId) {
        return repo.findById(profileId);
    }

    @Override
    public void addToCart(String email, int pizzaId) {

        ApplicationUser loggedUser = applicationUserService.loadUserByUsername(email);
        // по id юзера ищем корзину (единственный неоплаченный заказ)
        Order cart = findCartByUserId(loggedUser.getProfile().getId()).get();
        // получаем 1 единицу товара со склада
        Optional<Pizza> stockPositionToBuy = pizzaService.findById(pizzaId);
        // на основе позиции со склада, которую собрался приобретать покупатель, формируем запись в чеке (корзине)
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
    }

    @Override
    public Order findByOrderPositionId(int orderPositionId) {
        return orderPositionService.findById(orderPositionId).get().getOrder();
    }

    @Override
    public void pay(Order cart) {
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
    }
    private void removeFromStock(Order cart) {
        Set<OrderPosition> orderPositionSet = cart.getOrderPositions();
        for (OrderPosition orderPosition : orderPositionSet) {
            Set<Topping> toppings = orderPosition.getToppings();
            Set<TechCart> techCarts = orderPosition.getPizza().getTechCart();
            for (TechCart techCart : techCarts) {
                ingredientService.remove(techCart.getIngredient(), techCart.getIngredient().getAmount() * orderPosition.getAmount());
            }
            for (Topping topping : toppings) {
                ingredientService.remove(topping.getIngredient(), topping.getIngredient().getAmount() * orderPosition.getAmount());
            }
        }
    }

    @Override
    public List<Order> ordersToDeliver() {
        return null;
    }

    @Override
    public void deliver(int orderId) {
    }

    private void addToCart(Order cart, OrderPosition positionToAdd) {

        // Если товар присутствует в корзине, изменяем его количество, в противном случае, добавляем новую строку в корзину
        if (isStockPositionPresent(cart, positionToAdd)) {
            OrderPosition positionToIncrementAmount = cart.getOrderPositions().stream()
                    .filter(orderPosition -> orderPosition.getPizza().getId() == positionToAdd.getPizza().getId())
                    .findFirst().get();
                positionToIncrementAmount.setAmount(positionToIncrementAmount.getAmount() + 1);
        } else {
                // добавляем запись в чек (корзину)
                cart.getOrderPositions().add(positionToAdd);
        }
    }
    private boolean isStockPositionPresent(Order cart, OrderPosition positionToAdd) {
        return cart.getOrderPositions().stream()
                .anyMatch(orderPosition -> orderPosition.getId() == positionToAdd.getId());
    }
}
