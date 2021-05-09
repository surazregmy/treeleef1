package com.example.treeleef1.database;

import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class Database {

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/treeleaf", "root", "root");

        } catch (Exception e) {
            System.out.println("Error in initializing Database! Check the configuration");
            e.printStackTrace();
        }
        return connection;
    }

}
