package com.example.propertyservice.service;

import com.example.propertyservice.model.GenericResponse;
import com.example.propertyservice.model.Property;
import com.example.propertyservice.model.PropertyLocation;
import com.example.propertyservice.repo.LocationRepo;
import com.example.propertyservice.repo.PropertyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    private final LocationRepo locationRepo;
    private final PropertyRepo propertyRepo;

    // HashOperations<KEY, ID, TYPE>
    private final HashOperations<String, Long, Property> propertyHashOperations;

    @Autowired
    public LocationService(LocationRepo locationRepo, PropertyRepo propertyRepo, RedisTemplate<String, Property> redisTemplate) {
        this.locationRepo = locationRepo;
        this.propertyRepo = propertyRepo;
        propertyHashOperations = redisTemplate.opsForHash();
    }

    public GenericResponse insertLocation(PropertyLocation newLocation) {
        Optional<PropertyLocation> location = locationRepo.findLocationByAddress(newLocation.getAddress());

        if (location.isPresent()) {
            return new GenericResponse(false, 01, "Address must be unique", null);
        } else {
            return new GenericResponse(true, 00, "Location inserted", null);
        }
    }

    public GenericResponse getAllLocations() {
        List<PropertyLocation> list = locationRepo.findAll();

        if (list.isEmpty()) {
            return new GenericResponse(true, 01, "No locations found", null);
        } else {
            return new GenericResponse(false, 00, "Success", list);
        }
    }

    public GenericResponse getLocationByAddress(String address) {
        Optional<PropertyLocation> location = locationRepo.findLocationByAddress(address);

        if (location.isPresent()) {
            return new GenericResponse(true, 00, "Location found", List.of(location));
        } else {
            return new GenericResponse(false, 01, "Location by address not found", null);
        }
    }

    @CachePut(value = "property")
    public GenericResponse getLocationByCity(String city) {
        List<Property> propertyList = propertyRepo.findAllByLocation_City(city);

        return new GenericResponse(true, 00, "Locations found", propertyList);
    }

    @CachePut(value = "property")
    public GenericResponse getLocationByCountry(String country) {
        List<Property> propertyList = propertyRepo.findAllByLocation_Country(country);

        return new GenericResponse(true, 00, "Locations found", propertyList);
    }

    public Boolean checkIfAddressExist(String address) {
        return locationRepo.findLocationByAddress(address).isPresent();
    }
}
