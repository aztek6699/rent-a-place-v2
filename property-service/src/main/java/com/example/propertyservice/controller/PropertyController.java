package com.example.propertyservice.controller;

import com.example.propertyservice.model.GenericResponse;
import com.example.propertyservice.model.Property;
import com.example.propertyservice.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/property")
public class PropertyController {

    @Autowired
    private PropertyService service;

    @GetMapping("")
    public ResponseEntity<GenericResponse> getAllProperties() {
        return ResponseEntity.ok(service.getAllProperties());
    }

    @GetMapping("/getPropertyById/{id}")
    public ResponseEntity<GenericResponse> getPropertyById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getPropertyById(id));
    }

    @GetMapping("/getAllPropertyByUserId/{id}")
    public ResponseEntity<GenericResponse> getAllPropertyByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getAllPropertyByUserId(userId));
    }

    @PostMapping("")
    public ResponseEntity<GenericResponse> insertProperty(@RequestBody Property newProperty) {
        return ResponseEntity.ok(service.insertProperty(newProperty));
    }

    @GetMapping("/evictAllCacheValues")
    public String evictAllCacheValues() {
        return service.evictAllCacheValues();
    }
}
