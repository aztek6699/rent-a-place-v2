package com.example.bookingservice.client;

import com.example.bookingservice.model.GenericResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("user-service")
public interface UserClient {

    @GetMapping("/{id}")
    GenericResponse getUserById(@PathVariable Long id);
}
