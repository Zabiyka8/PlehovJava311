package com.example.topacademy_java311_plehov.model.shop;

import com.example.topacademy_java311_plehov.model.BaseEntity;
import com.example.topacademy_java311_plehov.model.secuirty.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@Table(name = "order_t")
@AllArgsConstructor
public class Order extends BaseEntity {
    @Column(name = "isPaid")
    private Boolean isPaid;
    @Column(name = "price")
    private Double price;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Set<OrderPosition> orderPositions;


    @ManyToOne
    private Shop shop;

    @ManyToOne
    private User user;

    public Order() {
        orderPositions = new HashSet<>();
    }
}
