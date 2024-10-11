package com.example.topacademy_java311_plehov.repositories;


import com.example.topacademy_java311_plehov.model.entities.stock.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

}