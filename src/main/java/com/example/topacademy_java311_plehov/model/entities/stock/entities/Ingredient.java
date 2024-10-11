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
@NoArgsConstructor
public class Ingredient extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "amount")
    private Integer amount;
    @Column(name = "price")
    private Double price;

//    @ManyToOne
//    private OrderPosition orderPosition;



    public boolean isInStock(Integer quantity){
        return this.amount >= quantity;
    }

}
