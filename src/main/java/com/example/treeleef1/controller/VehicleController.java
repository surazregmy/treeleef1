package com.example.treeleef1.controller;

import com.example.treeleef1.PdfUtils.VehicleMovementPdf;
import com.example.treeleef1.exception.ResourceNotFoundException;
import com.example.treeleef1.model.Location;
import com.example.treeleef1.model.Vehicle;
import com.example.treeleef1.service.VehicleService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/vehicles")
public class VehicleController {
    @Autowired
    VehicleService vehicleService;

    @GetMapping()
    public ResponseEntity<?> getAll() throws SQLException {
        List<Vehicle> vehicles = vehicleService.getAll();
        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id) throws SQLException, ResourceNotFoundException {
        Vehicle vehicle = vehicleService.get(id);
        return new ResponseEntity<>(vehicle, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Vehicle vehicle) throws SQLException, ResourceNotFoundException {
        vehicleService.save(vehicle);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping("/{id}/movementReport")
    public void exportMovementToPdf(@PathVariable Integer id, HttpServletResponse response) throws Exception {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        response.setContentType("application/octet-stream");
        String headerValue = "attachment; filename=vehicleMovement_" + id + "_" + currentDateTime + ".pdf";
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, headerValue);

        Vehicle vehicle = vehicleService.get(id);
        ByteArrayInputStream stream = new VehicleMovementPdf(vehicle).generate();
        IOUtils.copy(stream, response.getOutputStream());
    }

    @GetMapping("/{id}/detectMovement/{locationId}")
    public ResponseEntity<?> detectMovement(@PathVariable Integer id, @PathVariable Integer locationId) throws SQLException, ResourceNotFoundException {
        int detectionTimes = 0;
        String result = null;
        Vehicle vehicle = vehicleService.get(id);
        for (Location location : vehicle.getLocations()) {
            if (location.getId().equals(locationId)) {
                detectionTimes++;
                break;
            }
        }
        if (detectionTimes > 0) {
            result = "Vehicle " + id + " is Detected " + " in Location : " + locationId;
        } else {
            result = "Vehicle " + id + " is not Detected " + " in Location : " + locationId;

        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
