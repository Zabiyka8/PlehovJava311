package com.example.topacademy_java311_plehov.model.entities.stock.entities;

import com.example.topacademy_java311_plehov.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@Table(name = "topping_t")
@AllArgsConstructor
public class Topping extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "amount")
    private Integer quantity;
    @Column(name = "price")
    private Double price;
    @Column(name = "inStock")
    private boolean inStock;

    @OneToMany
    @JoinColumn(name = "topping_id")
    private Set<Pizza> topping;

    public Topping() {
        topping = new HashSet<>();
    }
}
