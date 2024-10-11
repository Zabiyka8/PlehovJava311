package com.example.topacademy_java311_plehov.model.shop;

import com.example.topacademy_java311_plehov.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "shop_t")
@AllArgsConstructor
@NoArgsConstructor
public class Shop extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "phone")
    private String phone;


    @ManyToOne
    private Order order;

    @ManyToOne
    private Stock stock;
}