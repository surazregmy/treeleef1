package com.example.treeleef1.service;

import com.example.treeleef1.dao.UserDAO;
import com.example.treeleef1.exception.ServerException;
import com.example.treeleef1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class UserService {


    @Autowired
    UserDAO userDAO;

    public Optional<User> getUser(int id) throws ServerException {
        try {
            return userDAO.get(id);
        } catch (Exception e) {
            throw new ServerException();
        }
    }
}
