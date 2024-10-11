package com.example.topacademy_java311_plehov.DAO.implementations;

import com.example.topacademy_java311_plehov.DAO.services.StockService;
import com.example.topacademy_java311_plehov.model.shop.Stock;
import com.example.topacademy_java311_plehov.repositories.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockServiceImplementation implements StockService {
    private final StockRepository repo;


    @Override
    public List<Stock> findAll() {
        return null;
    }

    @Override
    public Optional<Stock> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Stock save(Stock stock) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }
}
