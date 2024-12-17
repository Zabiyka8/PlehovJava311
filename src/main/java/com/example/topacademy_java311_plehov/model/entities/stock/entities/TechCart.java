package com.example.topacademy_java311_plehov.model.entities.stock.entities;


import com.example.topacademy_java311_plehov.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@Table(name = "teachCart_t")
@AllArgsConstructor
public class TechCart extends BaseEntity {

    @ManyToOne
    private Pizza pizza;

    @Column(name = "amount")
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;




    public TechCart() {
    }


}
