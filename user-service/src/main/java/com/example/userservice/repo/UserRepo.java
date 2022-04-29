package com.example.userservice.repo;

import com.example.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByEmailOrMobileNumber(String email, String mobileNumber);

    Boolean existsByEmailOrMobileNumber(String email, String mobileNumber);

}
