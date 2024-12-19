package com.example.topacademy_java311_plehov.controllers;

import com.example.topacademy_java311_plehov.DAO.services.PizzaService;
import com.example.topacademy_java311_plehov.DAO.services.TechCartService;
import com.example.topacademy_java311_plehov.model.entities.stock.DTO.PizzaDTO;
import com.example.topacademy_java311_plehov.model.entities.stock.entities.Ingredient;
import com.example.topacademy_java311_plehov.model.entities.stock.entities.Pizza;
import com.example.topacademy_java311_plehov.model.entities.stock.entities.TechCart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class ViewController {

    private final PizzaService pizzaService;
    private final TechCartService techCartService;

    @GetMapping
    public String index(Model model){
        List<TechCart> pizzas = techCartService.findAll().stream()
                .map(pizza -> TechCart.builder()
                        .ingredient(Ingredient.builder()
                                .name(pizza.getIngredient().getName())
                                .build())
                        .pizza(Pizza.builder()
                                .type(pizza.getPizza().getType())
                                .size(pizza.getPizza().getSize())
                                .price(pizza.getPizza().getPrice())
                                .name(pizza.getPizza().getName())
                                .build())
                        .build())
                .toList();

        model.addAttribute("pizzas", pizzas);
        return "/ui/pages/index";
    }
}
