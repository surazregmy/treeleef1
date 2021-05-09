package com.example.treeleef1;

import com.example.treeleef1.dao.UserDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

@SpringBootTest
class Treeleef1ApplicationTests {

    @Autowired
    UserDAO userDAO;

    @Test
    void contextLoads() {
    }

//    @Test
//    void getAllUsers() throws SQLException {
//        userDAO.getAllUsers();
//    }

}
