package com.example.topacademy_java311_plehov.controllers;

import com.example.topacademy_java311_plehov.DAO.services.PizzaService;
import com.example.topacademy_java311_plehov.DAO.services.TechCartService;
import com.example.topacademy_java311_plehov.model.entities.stock.DTO.PizzaDTO;
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
        List<PizzaDTO> pizzas = pizzaService.findAll().stream()
                .map(pizza -> PizzaDTO.builder()
                        .price(pizza.getPrice())
                        .id(pizza.getId())
                        .name(pizza.getName())
                        .techCart(listToString(techCartService.findTechCartByPizzaId(pizza.getId())))
                        .build())
                .toList();

        model.addAttribute("pizzas", pizzas);
        return "/ui/pages/index";
    }

    public String listToString(List<String> list){
        return String.join(", ", list);
    }
}
