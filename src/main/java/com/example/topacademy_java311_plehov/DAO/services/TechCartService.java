package com.example.topacademy_java311_plehov.DAO.services;

import com.example.topacademy_java311_plehov.model.entities.stock.entities.TechCart;

import java.util.List;

public interface TechCartService extends DAO<TechCart>{
    List<TechCart> findTechCartByPizzaId(Long pizzaId);
}
