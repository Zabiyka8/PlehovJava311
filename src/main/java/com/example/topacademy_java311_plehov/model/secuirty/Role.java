package com.example.topacademy_java311_plehov.model.secuirty;

import com.example.topacademy_java311_plehov.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@Table(name = "role_t")
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseEntity implements GrantedAuthority {
    @Column(name = "name_role")
    private String name;
    @Column(name = "add_order")
    private boolean add_order;
    @Column(name = "edits_site")
    private boolean edits_site;
    @Column(name = "change_user_status")
    private boolean change_user_status;


    @OneToMany
    private Set<User> users;

    @Override
    public String getAuthority() {
        return this.name;
    }
}
