package com.example.reviewservice.client;

import com.example.reviewservice.model.GenericResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("booking-service")
public interface BookingClient {

    @GetMapping("/{id}")
    public GenericResponse getBookingById(Long id);
}
