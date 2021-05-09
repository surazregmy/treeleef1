package com.example.treeleef1.model;

import lombok.Data;

import java.util.List;

@Data
public class Vehicle extends BaseModel {
    private String number;
    List<Location> locations;


}
