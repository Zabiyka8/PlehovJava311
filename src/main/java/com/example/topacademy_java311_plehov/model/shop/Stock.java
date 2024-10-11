package com.example.topacademy_java311_plehov.model.shop;

import com.example.topacademy_java311_plehov.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@Table(name = "stock_t")
@AllArgsConstructor
public class Stock extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "amount")
    private Integer amount;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "stock_id")
    private Set<Shop> stock;

    public Stock() {stock = new HashSet<>();}
}
