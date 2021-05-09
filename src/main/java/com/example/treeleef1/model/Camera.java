package com.example.treeleef1.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class Camera extends BaseModel {
    @NotEmpty(message = "Camera Name can not be empty")
    private String name;
    private String brand;
    @NotNull(message = "Please provide the location where the camera is Installed")
    Location location;
}
