package com.example.topacademy_java311_plehov.model.entities.stock.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemDTO {
    private int id;
    private String name;
}