package com.example.treeleef1.controller;

import com.example.treeleef1.exception.ResourceNotFoundException;
import com.example.treeleef1.model.Camera;
import com.example.treeleef1.service.CameraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/cameras")
public class CameraController {
    @Autowired
    CameraService cameraService;

    @GetMapping()
    public ResponseEntity<?> getAll() throws SQLException {
        List<Camera> cameras = cameraService.getAll();
        return new ResponseEntity<>(cameras, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id) throws SQLException, ResourceNotFoundException {
        Camera camera = cameraService.get(id);
        return new ResponseEntity<>(camera, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid Camera camera) throws SQLException, ResourceNotFoundException {
        cameraService.save(camera);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
