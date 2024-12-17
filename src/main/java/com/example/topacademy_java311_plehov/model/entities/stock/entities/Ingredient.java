package com.example.topacademy_java311_plehov.model.entities.stock.entities;

import com.example.topacademy_java311_plehov.model.BaseEntity;
import com.example.topacademy_java311_plehov.model.shop.OrderPosition;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@Table(name = "ingredient_t")
@AllArgsConstructor
public class Ingredient extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "amount")
    private Integer amount;
    @Column(name = "price")
    private Double price;
    @Column(name = "inStock")
    private Integer inStock;
    @OneToMany
    @JoinColumn(name = "ingredient_id")
    private Set<Topping> toppings;

    public Ingredient() {
        toppings = new HashSet<>();
    }


    public boolean isInStock(Integer quantity){
        return this.amount >= quantity;
    }

}
