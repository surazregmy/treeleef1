package com.example.treeleef1.model;

import lombok.Data;

import java.util.List;

@Data
public class Location extends BaseModel {
    private String name;
    List<Camera> cameras;
}
