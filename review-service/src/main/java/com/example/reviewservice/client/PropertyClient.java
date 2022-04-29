package com.example.reviewservice.client;

import com.example.reviewservice.model.GenericResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("property-service")
public interface PropertyClient {

    @GetMapping("/{id}")
    public GenericResponse getPropertyById(Long id);
}
