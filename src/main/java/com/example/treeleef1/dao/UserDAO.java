package com.example.treeleef1.dao;

import com.example.treeleef1.database.Database;
import com.example.treeleef1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

@Component
public class UserDAO implements Dao {

    @Autowired
    Database database;

    @Override
    public Optional<User> get(int id) throws SQLException {
        Connection connection = database.getConnection();
        String getQuery = "SELECT * FROM user where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(getQuery);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        User user = null;
        while (resultSet.next()) {
            user = new User();
            user.setName(resultSet.getString(1));
            user.setPassword(resultSet.getString(2));
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Collection getAll() {
        return null;
    }

    @Override
    public int save(Object o) {
        return 0;
    }

    @Override
    public void update(Object o) {

    }

    @Override
    public void delete(Object o) {

    }
}
