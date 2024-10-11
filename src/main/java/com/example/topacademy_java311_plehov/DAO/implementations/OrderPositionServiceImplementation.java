package com.example.topacademy_java311_plehov.DAO.implementations;

import com.example.topacademy_java311_plehov.DAO.services.OrderPositionService;
import com.example.topacademy_java311_plehov.model.shop.OrderPosition;
import com.example.topacademy_java311_plehov.repositories.OrderPositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderPositionServiceImplementation implements OrderPositionService {
    private final OrderPositionRepository repo;


    @Override
    public List<OrderPosition> findAll() {
        return null;
    }

    @Override
    public Optional<OrderPosition> findById(int id) {
        return Optional.empty();
    }

    @Override
    public OrderPosition save(OrderPosition orderPosition) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }
}
