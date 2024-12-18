package com.example.topacademy_java311_plehov.repositories;

import com.example.topacademy_java311_plehov.model.entities.stock.entities.TechCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechCartRepository extends JpaRepository<TechCart, Integer> {
}
