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
public class CameraDAO implements Dao {
    @Autowired
    Database database;

    @Override
    public Optional<Camera> get(int id) throws SQLException {
        Connection connection = database.getConnection();
        String getQuery = "SELECT * FROM cameras c LEFT JOIN locations l " +
                "ON c.location_id = l.id where c.id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(getQuery);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Camera camera = new Camera();
        while (resultSet.next()) {

            camera.setId(resultSet.getInt("c.id"));
            camera.setBrand(resultSet.getString("c.brand"));
            camera.setName(resultSet.getString("c.name"));

            Location location = null;
            if (resultSet.getInt("l.id") != 0) {
                location = new Location();
                location.setId(resultSet.getInt(1));
                location.setName(resultSet.getString(2));
            }
            camera.setLocation(location);
        }
        return Optional.ofNullable(camera);
    }

    @Override
    public List<Camera> getAll() throws SQLException {
        Connection connection = database.getConnection();
        String getQuery = "SELECT * FROM cameras c LEFT JOIN locations l " +
                "ON c.location_id = l.id";
        PreparedStatement preparedStatement = connection.prepareStatement(getQuery);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Camera> cameras = new ArrayList<>();

        while (resultSet.next()) {

            Camera camera = new Camera();
            camera.setId(resultSet.getInt("c.id"));
            camera.setBrand(resultSet.getString("c.brand"));
            camera.setName(resultSet.getString("c.name"));

            Location location = new Location();
            location.setId(resultSet.getInt("l.id"));
            location.setName(resultSet.getString("l.name"));

            camera.setLocation(location);
            cameras.add(camera);

        }


        return cameras;

    }

    @Override
    public int save(Object o) throws SQLException {
        Camera camera = (Camera) o;
        Connection connection = database.getConnection();
        String getQuery = "insert into cameras(name,brand,location_id) value (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(getQuery);
        preparedStatement.setString(1, camera.getName());
        preparedStatement.setString(2, camera.getBrand());
        preparedStatement.setInt(3, camera.getLocation().getId());
        int savedCameraId = preparedStatement.executeUpdate();
        return savedCameraId;
    }

    @Override
    public void update(Object o) throws SQLException {

    }

    @Override
    public void delete(Object o) throws SQLException {

    }
}
