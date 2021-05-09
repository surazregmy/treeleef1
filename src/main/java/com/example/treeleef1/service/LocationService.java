package com.example.treeleef1.service;

import com.example.treeleef1.dao.LocationDAO;
import com.example.treeleef1.exception.ResourceNotFoundException;
import com.example.treeleef1.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public class LocationService {
    private final static String NOT_FOUND_MSG = "Location with ID %s not found";
    @Autowired
    LocationDAO locationDAO;

    public List<Location> getAll() throws SQLException {
        return locationDAO.getAll();
    }

    public Location get(Integer id) throws SQLException, ResourceNotFoundException {
        return locationDAO.get(id).orElseThrow(() -> new ResourceNotFoundException(String.format(NOT_FOUND_MSG, id)));
    }

    public int save(Location location) throws SQLException {
        return locationDAO.save(location);
    }

    public void update(Location location) throws SQLException, ResourceNotFoundException {
        Location preLocation = locationDAO.get(location.getId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format(NOT_FOUND_MSG, location.getId())));
        locationDAO.update(location);
    }

    public void delete(Integer id) throws SQLException, ResourceNotFoundException {
        Location delLocation = locationDAO.get(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(NOT_FOUND_MSG, id)));
        System.out.println("delLocation.getId()");
        System.out.println(delLocation.getId());
        locationDAO.delete(delLocation);
    }
}
