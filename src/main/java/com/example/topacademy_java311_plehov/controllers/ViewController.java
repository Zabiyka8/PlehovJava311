package com.example.topacademy_java311_plehov.controllers;

import com.example.topacademy_java311_plehov.DAO.services.PizzaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class ViewController {

    private final PizzaService itemService;

    @GetMapping
    public String index(){
//        List<ItemDTO> items = itemService.findAll().stream()
//                .map(item -> ItemDTO.builder()
//                        .id(item.getId())
//                        .name(item.getName())
//                        .build())
//                .toList();
//
//        model.addAttribute("item", items);
        return "/ui/pages/index";
    }
}
