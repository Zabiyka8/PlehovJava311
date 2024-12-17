package com.example.topacademy_java311_plehov.model.shop;

import com.example.topacademy_java311_plehov.model.BaseEntity;
import com.example.topacademy_java311_plehov.model.entities.stock.entities.Pizza;
import com.example.topacademy_java311_plehov.model.entities.stock.entities.Topping;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@Table(name = "order_position_t")
@AllArgsConstructor
public class OrderPosition extends BaseEntity {
    @Column(name = "amount")
    private Integer quantity;

    @ManyToOne
    private Order order;

    @OneToOne
    @JoinColumn(name = "pizza_id")
    private Pizza pizza;

    @OneToMany
    @JoinColumn(name = "order_position_id")
    private Set<Topping> toppings;

    @Column(name = "note")
    private String note;


    public OrderPosition() {
        toppings = new HashSet<>();
    }
}
