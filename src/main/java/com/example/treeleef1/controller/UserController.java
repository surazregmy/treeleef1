package com.example.treeleef1.controller;

import com.example.treeleef1.exception.ResourceNotFoundException;
import com.example.treeleef1.exception.ServerException;
import com.example.treeleef1.model.User;
import com.example.treeleef1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/users")
public class UserController {

    private final static String NOT_FOUND_MSG = "User with ID %s not found";

    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable int id) throws ResourceNotFoundException, ServerException {
        User user = userService.getUser(id).orElseThrow(() -> new ResourceNotFoundException(String.format(NOT_FOUND_MSG, id)));
        return ResponseEntity.ok(user);
    }
}
