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
@Table(name = "shop_t")
@AllArgsConstructor
public class Shop extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "phone")
    private String phone;


    @OneToMany
    @JoinColumn(name = "shop_id")
    private Set<Order> orders;

    @OneToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    public Shop(){orders = new HashSet<>();}
}