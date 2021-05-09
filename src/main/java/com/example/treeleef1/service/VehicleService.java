package com.example.treeleef1.service;

import com.example.treeleef1.dao.LocationDAO;
import com.example.treeleef1.dao.VehicleDAO;
import com.example.treeleef1.exception.ResourceNotFoundException;
import com.example.treeleef1.model.Camera;
import com.example.treeleef1.model.Location;
import com.example.treeleef1.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class VehicleService {
    private final static String VEHICLE_NOT_FOUND_MSG = "Location with ID %s not found";
    private final static String LOCATION_NOT_FOUND_MSG = "Location with ID %s not found";
    @Autowired
    VehicleDAO vehicleDAO;

    @Autowired
    LocationDAO locationDAO;

    public List<Vehicle> getAll() throws SQLException {
        return vehicleDAO.getAll();
    }

    public Vehicle get(Integer id) throws SQLException, ResourceNotFoundException {
        return vehicleDAO.get(id).orElseThrow(() -> new ResourceNotFoundException(String.format(VEHICLE_NOT_FOUND_MSG, id)));
    }

    public int save(Vehicle vehicle) throws SQLException, ResourceNotFoundException {

        List<Location> dbLocations = new ArrayList<>();
        for (Location location : vehicle.getLocations()) {

            Location dbLocation = locationDAO.get(location.getId())
                    .orElseThrow(() -> new ResourceNotFoundException(String.format(LOCATION_NOT_FOUND_MSG, location.getId())));
            dbLocations.add(dbLocation);
        }

        vehicle.setLocations(new ArrayList<>());
        vehicle.setLocations(dbLocations);
        int savedID = vehicleDAO.save(vehicle);
        return savedID;
    }
}
