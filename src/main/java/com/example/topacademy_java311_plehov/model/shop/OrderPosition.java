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
@Table(name = "orderPosition_t")
@AllArgsConstructor
public class OrderPosition extends BaseEntity {
    @Column(name = "amount")
    private Integer quantity;



    @ManyToOne
    private Pizza pizza;

    public OrderPosition() {

    }
}
