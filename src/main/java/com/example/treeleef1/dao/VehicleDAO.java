package com.example.treeleef1.dao;

import com.example.treeleef1.database.Database;
import com.example.treeleef1.model.Camera;
import com.example.treeleef1.model.Location;
import com.example.treeleef1.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.*;

@Component
public class VehicleDAO implements Dao {

    @Autowired
    Database database;

    @Override
    public List<Vehicle> getAll() throws SQLException {
        Connection connection = database.getConnection();
        String getQuery = "SELECT * FROM vehicles v LEFT JOIN vehicles_locations vl on v.id = vl.vehicles_id " +
                "LEFT JOIN locations l on vl.locations_id =l.id";
        PreparedStatement preparedStatement = connection.prepareStatement(getQuery);
        ResultSet resultSet = preparedStatement.executeQuery();

        Map<Vehicle, List<Location>> oneLocationToManyVehiclesMap = new HashMap<>();

        while (resultSet.next()) {

            Vehicle vehicle = new Vehicle();
            vehicle.setId(resultSet.getInt("v.id"));
            vehicle.setNumber(resultSet.getString("number"));
            Location location = null;
            if (resultSet.getInt("l.id") != 0) {
                location = new Location();
                location.setId(resultSet.getInt("l.id"));
                location.setName(resultSet.getString("name"));
            }

            if (!oneLocationToManyVehiclesMap.containsKey(vehicle)) {
                List<Location> locations = new ArrayList<>();
                if (location != null)
                    locations.add(location);
                oneLocationToManyVehiclesMap.put(vehicle, locations);
            } else {
                List<Location> locations = oneLocationToManyVehiclesMap.get(vehicle);
                if (location != null)
                    locations.add(location);
                oneLocationToManyVehiclesMap.put(vehicle, locations);
            }
        }

        List<Vehicle> vehicles = new ArrayList<>();
        for (Map.Entry<Vehicle, List<Location>> mapElement : oneLocationToManyVehiclesMap.entrySet()) {
            Vehicle vehicle = mapElement.getKey();
            List<Location> locations = mapElement.getValue();
            vehicle.setLocations(locations);
            vehicles.add(vehicle);

        }
        return vehicles;
    }

    @Override
    public int save(Object o) throws SQLException {
        Connection connection = database.getConnection();
        //Implemmenting Transactions
        connection.setAutoCommit(false);
        //Saving Vehicle
        Savepoint savepointVL = connection.setSavepoint("VehicleSaveVL");
        int savedVehicleId = 0;
        try {
            Vehicle vehicle = (Vehicle) o;
            String insertionQueryOnVehicle = "insert into vehicles(number) value (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertionQueryOnVehicle, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, vehicle.getNumber());
            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected == 1) {
                // get candidate id
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next())
                    savedVehicleId = rs.getInt(1);
            }

            //Saving vehicles_locations

            for (Location location : vehicle.getLocations()) {
                String insertionQueryOnVehicleLocation = "insert into vehicles_locations(vehicles_id,locations_id) value (?,?)";
                PreparedStatement preparedStatementForVL = connection.prepareStatement(insertionQueryOnVehicleLocation);
                preparedStatementForVL.setInt(1, savedVehicleId);
                preparedStatementForVL.setInt(2, location.getId());
                preparedStatementForVL.executeUpdate();
            }

            connection.commit();


        } catch (SQLException throwables) {
            connection.rollback(savepointVL);
            throwables.printStackTrace();
        }

        return savedVehicleId;
    }

    @Override
    public void update(Object o) throws SQLException {

    }

    @Override
    public void delete(Object o) throws SQLException {

    }

    @Override
    public Optional<Vehicle> get(int id) throws SQLException {
        Connection connection = database.getConnection();
        String getQuery = "SELECT * FROM vehicles v LEFT JOIN vehicles_locations vl on v.id = vl.vehicles_id " +
                "LEFT JOIN locations l on vl.locations_id =l.id LEFT JOIN cameras c on l.id = c.location_id" +
                " where v.id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(getQuery);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Vehicle vehicle = new Vehicle();
        List<Location> locations = new ArrayList<>();
        Map<Location, List<Camera>> oneLocationToManyCamerasMap = new HashMap<>();
        while (resultSet.next()) {

            vehicle.setId(resultSet.getInt("v.id"));
            vehicle.setNumber(resultSet.getString("number"));

            Location location = null;
            if (resultSet.getInt("l.id") != 0) {

                location = new Location();
                location.setId(resultSet.getInt("l.id"));
                location.setName(resultSet.getString("l.name"));
                locations.add(location);

                Camera camera = null;
                if (resultSet.getInt("c.id") != 0) {
                    camera = new Camera();
                    camera.setId(resultSet.getInt("c.id"));
                    camera.setBrand(resultSet.getString("c.brand"));
                    camera.setName(resultSet.getString("c.name"));


                    if (!oneLocationToManyCamerasMap.containsKey(location)) {
                        List<Camera> cameras = new ArrayList<>();
                        cameras.add(camera);
                        oneLocationToManyCamerasMap.put(location, cameras);
                    } else {
                        List<Camera> cameras = oneLocationToManyCamerasMap.get(location);
                        cameras.add(camera);
                        oneLocationToManyCamerasMap.put(location, cameras);
                    }
                }
            }
        }

        List<Location> locationsFinal = new ArrayList<>();
        for (Map.Entry<Location, List<Camera>> mapElement : oneLocationToManyCamerasMap.entrySet()) {
            Location location = mapElement.getKey();
            List<Camera> cameras = mapElement.getValue();
            location.setCameras(cameras);
            locationsFinal.add(location);

        }
        vehicle.setLocations(locationsFinal);
        return Optional.ofNullable(vehicle);
    }
}
