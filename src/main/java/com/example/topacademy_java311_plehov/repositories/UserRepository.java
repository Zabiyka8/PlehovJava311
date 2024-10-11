package com.example.topacademy_java311_plehov.repositories;


import com.example.topacademy_java311_plehov.model.secuirty.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByProfileEmail(String email);
}
