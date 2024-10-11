package com.example.topacademy_java311_plehov.model.entities.stock.entities;



import com.example.topacademy_java311_plehov.model.BaseEntity;
import com.example.topacademy_java311_plehov.model.entities.itemAttributes.Size;
import com.example.topacademy_java311_plehov.model.entities.itemAttributes.Type;
import com.example.topacademy_java311_plehov.model.shop.OrderPosition;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@Table(name = "pizza_t")
@AllArgsConstructor
public class Pizza extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private Double price;
    @Column(name = "size")
    @Enumerated(EnumType.STRING)
    private Size size;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;
    @Column(name = "note")
    private String note;
//    @Column(name = "Set<ingredient>")
//    private Set<Ingredient> ingredient;



    @ManyToOne
    private Topping topping;

    @OneToMany
    @JoinColumn(name = "pizza_id")
    private Set<OrderPosition> pizza;

    @OneToMany
    @JoinColumn(name = "pizza_id")
    private Set<TechCart> pizzas;

    public Pizza() {
        pizza = new HashSet<>();
        pizzas = new HashSet<>();
    }
}

