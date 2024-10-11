package com.example.topacademy_java311_plehov.model.secuirty;


import com.example.topacademy_java311_plehov.model.BaseEntity;
import com.example.topacademy_java311_plehov.model.shop.Order;
import com.example.topacademy_java311_plehov.model.shop.Profile;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.mapping.ToOne;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Builder
@Entity
@Table(name = "user_t")
@AllArgsConstructor
public class User extends BaseEntity {
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;

    @ManyToOne
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToMany
    @JoinColumn(name = "user_id")
    private Set<Order> orders;
    public User(){orders = new HashSet<>();}

    public UserDetails securityUserFromEntity() {
        return new org.springframework.security.core.userdetails.User(
                this.profile.getEmail(),
                password,
                true,
                true,
                true,
                true,
                new ArrayList<>(){{add(role);}}
        );
    }
}
