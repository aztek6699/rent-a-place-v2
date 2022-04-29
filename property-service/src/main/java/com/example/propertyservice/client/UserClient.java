package com.example.propertyservice.client;

import com.example.propertyservice.model.GenericResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "userService", url = "http://localhost:8081/api/v1/user")
public interface UserClient {

    @GetMapping("/{id}")
    GenericResponse getUserById(@PathVariable Long id);
}
