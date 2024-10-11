package com.example.topacademy_java311_plehov.repositories;


import com.example.topacademy_java311_plehov.model.shop.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}