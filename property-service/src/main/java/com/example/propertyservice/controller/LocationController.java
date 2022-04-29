package com.example.propertyservice.controller;

import com.example.propertyservice.model.GenericResponse;
import com.example.propertyservice.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    @Autowired
    private LocationService service;

    @GetMapping("")
    public ResponseEntity<GenericResponse> getLocations() {
        return ResponseEntity.ok(service.getAllLocations());
    }

    @GetMapping("/address/{address}")
    public ResponseEntity<GenericResponse> getLocationByAddress(@PathVariable String address) {
        return ResponseEntity.ok(service.getLocationByAddress(address));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<GenericResponse> getLocationByCity(@PathVariable String city) {
        return ResponseEntity.ok(service.getLocationByCity(city));
    }

    @GetMapping("/country/{country}")
    public ResponseEntity<GenericResponse> getLocationByCountry(@PathVariable String country) {
        return ResponseEntity.ok(service.getLocationByCountry(country));
    }
}
