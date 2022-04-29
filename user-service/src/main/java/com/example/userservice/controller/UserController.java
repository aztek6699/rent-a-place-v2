package com.example.userservice.controller;


import com.example.userservice.service.UserService;
import com.example.userservice.model.GenericResponse;
import com.example.userservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("")
    public ResponseEntity<GenericResponse> getAllUsers() {
        return ResponseEntity.ok(service.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getUserById(id));
    }

    @GetMapping("getByEmailMobileNumber")
    public ResponseEntity<GenericResponse> getUserByEmailMobileNumber(@RequestHeader("email") String email, @RequestHeader("mobileNumber") String mobileNumber) {
        return ResponseEntity.ok(service.getUserByEmailOrNumber(email, mobileNumber));
    }

    @PostMapping("")
    public ResponseEntity<GenericResponse> insertUser(@RequestBody User newUser) {
        return ResponseEntity.ok(service.insertUser(newUser));
    }
}
