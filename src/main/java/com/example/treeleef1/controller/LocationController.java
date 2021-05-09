package com.example.treeleef1.controller;

import com.example.treeleef1.exception.ResourceNotFoundException;
import com.example.treeleef1.model.Location;
import com.example.treeleef1.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    LocationService locationService;

    @GetMapping()
    public ResponseEntity<?> getAll() throws SQLException {
        List<Location> locations = locationService.getAll();
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id) throws SQLException, ResourceNotFoundException {
        Location location = locationService.get(id);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Location location) throws SQLException {
        locationService.save(location);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Location location) throws SQLException, ResourceNotFoundException {
        if (location.getId() == null)
            throw new IllegalArgumentException("Invalid Request for Update: Id should be passed");
        locationService.update(location);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) throws SQLException, ResourceNotFoundException {
        locationService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
