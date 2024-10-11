package com.example.topacademy_java311_plehov.DAO.implementations;

import com.example.topacademy_java311_plehov.DAO.services.ToppingService;
import com.example.topacademy_java311_plehov.model.entities.stock.entities.Topping;
import com.example.topacademy_java311_plehov.repositories.ToppingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ToppingServiceImplementation implements ToppingService {
    private final ToppingRepository repo;

    @Override
    public List<Topping> findAll() {
        return null;
    }

    @Override
    public Optional<Topping> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Topping save(Topping additionalService) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }
}
