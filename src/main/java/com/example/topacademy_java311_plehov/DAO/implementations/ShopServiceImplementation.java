package com.example.topacademy_java311_plehov.DAO.implementations;

import com.example.topacademy_java311_plehov.DAO.services.ShopService;
import com.example.topacademy_java311_plehov.model.shop.Shop;
import com.example.topacademy_java311_plehov.repositories.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ShopServiceImplementation implements ShopService {
    private final ShopRepository repo;


    @Override
    public List<Shop> findAll() {
        return null;
    }

    @Override
    public Optional<Shop> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Shop save(Shop shop) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }
}
