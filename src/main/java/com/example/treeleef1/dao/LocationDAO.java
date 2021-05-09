package com.example.treeleef1.dao;

import com.example.treeleef1.database.Database;
import com.example.treeleef1.model.Camera;
import com.example.treeleef1.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class LocationDAO implements Dao {
    @Autowired
    Database database;


    @Override
    public Optional<Location> get(int id) throws SQLException {
        Connection connection = database.getConnection();
        String getQuery = "SELECT * FROM locations l LEFT JOIN cameras c " +
                "ON l.id = c.location_id where l.id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(getQuery);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Location location = null;
        List<Camera> cameras = new ArrayList<>();
        while (resultSet.next()) {
            location = new Location();
            location.setId(resultSet.getInt(1));
            location.setName(resultSet.getString(2));

            Camera camera = null;
            if (resultSet.getInt("c.id") != 0) {
                camera = new Camera();
                camera.setId(resultSet.getInt("c.id"));
                camera.setBrand(resultSet.getString("c.brand"));
                camera.setName(resultSet.getString("c.name"));
                cameras.add(camera);
            }
            location.setCameras(cameras);
        }
        return Optional.ofNullable(location);
    }

    @Override
    public List<Location> getAll() throws SQLException {
        Connection connection = database.getConnection();
        String getQuery = "SELECT * FROM locations l LEFT JOIN cameras c " +
                "ON l.id = c.location_id";
        PreparedStatement preparedStatement = connection.prepareStatement(getQuery);
        ResultSet resultSet = preparedStatement.executeQuery();

        Map<Location, List<Camera>> oneLocationToManyCamerasMap = new HashMap<>();

        while (resultSet.next()) {

            Location location = new Location();
            location.setId(resultSet.getInt("l.id"));
            location.setName(resultSet.getString("l.name"));
            Camera camera = null;
            if (resultSet.getInt("c.id") != 0) {
                camera = new Camera();
                camera.setId(resultSet.getInt("c.id"));
                camera.setBrand(resultSet.getString("c.brand"));
                camera.setName(resultSet.getString("c.name"));
            }

            if (!oneLocationToManyCamerasMap.containsKey(location)) {
                List<Camera> cameras = new ArrayList<>();
                if (camera != null)
                    cameras.add(camera);
                oneLocationToManyCamerasMap.put(location, cameras);
            } else {
                List<Camera> cameras = oneLocationToManyCamerasMap.get(location);
                if (camera != null)
                    cameras.add(camera);
                oneLocationToManyCamerasMap.put(location, cameras);
            }
        }

        List<Location> locations = new ArrayList<>();
        for (Map.Entry<Location, List<Camera>> mapElement : oneLocationToManyCamerasMap.entrySet()) {
            Location location = mapElement.getKey();
            List<Camera> cameras = mapElement.getValue();
            location.setCameras(cameras);
            locations.add(location);

        }
        return locations;
    }

    @Override
    public int save(Object o) throws SQLException {
        Location location = (Location) o;
        Connection connection = database.getConnection();
        String getQuery = "insert into locations(name) value (?)";
        PreparedStatement preparedStatement = connection.prepareStatement(getQuery);
        preparedStatement.setString(1, location.getName());
        int savedLocationId = preparedStatement.executeUpdate();
        return savedLocationId;
    }

    @Override
    public void update(Object o) throws SQLException {
        Location location = (Location) o;
        Connection connection = database.getConnection();
        String getQuery = "update locations l set l.name = ? where id =?";
        PreparedStatement preparedStatement = connection.prepareStatement(getQuery);
        preparedStatement.setString(1, location.getName());
        preparedStatement.setInt(2, location.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(Object o) throws SQLException {
        Location location = (Location) o;
        Connection connection = database.getConnection();
        String getQuery = "delete from locations where id =?";
        PreparedStatement preparedStatement = connection.prepareStatement(getQuery);
        preparedStatement.setInt(1, location.getId());
        preparedStatement.executeUpdate();
    }
}
