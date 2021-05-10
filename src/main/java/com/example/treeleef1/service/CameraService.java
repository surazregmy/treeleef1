package com.example.treeleef1.service;

import com.example.treeleef1.dao.CameraDAO;
import com.example.treeleef1.dao.LocationDAO;
import com.example.treeleef1.dao.VehicleDAO;
import com.example.treeleef1.exception.ResourceNotFoundException;
import com.example.treeleef1.model.Camera;
import com.example.treeleef1.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public class CameraService {

    private final static String NOT_FOUND_MSG = "Camera with ID %s not found";
    private final static String LOCATION_NOT_FOUND_MSG = "Location with ID %s not found";


    @Autowired
    CameraDAO cameraDAO;

    @Autowired
    LocationDAO locationDAO;

    public List<Camera> getAll() throws SQLException {
        return cameraDAO.getAll();
    }

    public Camera get(Integer id) throws SQLException, ResourceNotFoundException {
        return cameraDAO.get(id).orElseThrow(() -> new ResourceNotFoundException(String.format(NOT_FOUND_MSG, id)));
    }

    public int save(Camera camera) throws SQLException, ResourceNotFoundException {
        Location location = locationDAO.get(camera.getLocation().getId()).orElseThrow(() -> new ResourceNotFoundException(String.format(LOCATION_NOT_FOUND_MSG, camera.getLocation().getId())));
        ;
        return cameraDAO.save(camera);
    }


}
