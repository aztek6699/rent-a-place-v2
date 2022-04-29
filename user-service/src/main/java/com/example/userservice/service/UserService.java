package com.example.userservice.service;

import com.example.userservice.model.GenericResponse;
import com.example.userservice.model.User;
import com.example.userservice.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    public GenericResponse getAllUsers() {
        List<User> list = repo.findAll();
        if (list.isEmpty()) return new GenericResponse(false, 01, "No users", null);
        else return new GenericResponse(true, 00, "Success", list);
    }

    public GenericResponse getUserById(Long id) {
        Optional<User> user = repo.findById(id);
        if (user.isPresent()) return new GenericResponse(true, 00, "User found", List.of(user));
        else return new GenericResponse(false, 01, "User not found", null);
    }

    public GenericResponse getUserByEmailOrNumber(String email, String mobileNumber) {
        Optional<User> user = repo.findByEmailOrMobileNumber(email, mobileNumber);
        if (user.isPresent()) return new GenericResponse(true, 00, "User found", List.of(user));
        else return new GenericResponse(false, 01, "User not found", null);
    }

    public GenericResponse insertUser(User newUser) {
        if (repo.existsByEmailOrMobileNumber(newUser.getEmail(), newUser.getMobileNumber())) return new GenericResponse(false, 01, "Email/mobile taken", null);

        // use this as user id
        Long id = System.currentTimeMillis();
        newUser.setId(id);

        repo.save(newUser);
        return new GenericResponse(true, 00, "User saved", null);
    }
}
