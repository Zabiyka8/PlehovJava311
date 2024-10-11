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
@Table(name = "ingredient_t")
@AllArgsConstructor
public class Ingredient extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "amount")
    private Integer quantity;
    @Column(name = "inStock")
    private boolean inStock;

    @OneToMany
    @JoinColumn(name = "ingredient_id")
    private Set<TechCart> techCarts;


//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "ingredient_id")
//    private Set<Pizza> ingredients;
//

    public Ingredient() {
        techCarts = new HashSet<>();
//        ingredients = new HashSet<>();

    }


}
