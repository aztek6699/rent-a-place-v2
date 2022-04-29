package com.example.bookingservice.client;

import com.example.bookingservice.model.GenericResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("property-service")
public interface PropertyClient {

    @GetMapping("property-service")
    public GenericResponse getPropertyById(Long id);
}
