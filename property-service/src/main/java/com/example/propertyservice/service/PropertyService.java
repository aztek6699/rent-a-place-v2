package com.example.propertyservice.service;

import com.example.propertyservice.client.UserClient;
import com.example.propertyservice.model.GenericResponse;
import com.example.propertyservice.model.Property;
import com.example.propertyservice.model.PropertyLocation;
import com.example.propertyservice.repo.LocationRepo;
import com.example.propertyservice.repo.PropertyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

    @Value("${redis.key.property}")
    private String propertyKey;

    private final PropertyRepo propertyRepo;
    private final LocationRepo locationRepo;
    private final UserClient userClient;

    // HashOperations<KEY, ID, TYPE>
    private final HashOperations<String, Long, Property> propertyHashOperations;

    @Autowired
    public PropertyService(PropertyRepo propertyRepo,
                           LocationRepo locationRepo,
                           UserClient userClient,
                           RedisTemplate<String, Property> redisTemplate) {

        this.propertyRepo = propertyRepo;
        this.locationRepo = locationRepo;
        this.userClient = userClient;
        propertyHashOperations = redisTemplate.opsForHash();
    }

    @CachePut(value = "property")
    public GenericResponse getAllProperties() {
        List<Property> list = propertyRepo.findAll();

        if (!list.isEmpty()) {
            return new GenericResponse(true, 00, "Success", list);
        } else {
            return new GenericResponse(false, 01, "No properties found", null);
        }
    }

    public GenericResponse getPropertyById(Long id) {
        Optional<Property> property;
        property = Optional.ofNullable(propertyHashOperations.get(propertyKey, id));

        if (property.isPresent()) return new GenericResponse(true, 00, "Property found", List.of(property));

        property = propertyRepo.findById(id);
        if (property.isPresent()) {
            propertyHashOperations.put(propertyKey, id, property.get());
            return new GenericResponse(true, 00, "Property found", List.of(property));
        } else {
            return new GenericResponse(false, 01, "Property not found", null);
        }
    }

    @CachePut(value = "property")
    public GenericResponse getAllPropertyByUserId(Long id) {
        // check if user exists
        GenericResponse user = userClient.getUserById(id);
        if (!user.getIsSuccess()) return new GenericResponse(false, 01, "User does not exist", null);

        List<Property> list = propertyRepo.findAllByPropertyOwnerId(id);
        if (!list.isEmpty()) return new GenericResponse(false, 01, "No properties for user", null);

        return new GenericResponse(true, 00, "Success", list);
    }

    public GenericResponse insertProperty(Property newProperty) {

        // check if property owner exists
        GenericResponse propertyOwner = userClient.getUserById(newProperty.getPropertyOwnerId());
        if (!propertyOwner.getIsSuccess()) return new GenericResponse(false, 01,"Owner must not be null", null);

        // check if check in time after check out time
        if (!newProperty.getCheckInTime().after(newProperty.getCheckOutTime()))
            return new GenericResponse(false, 01, "Check in time must be after check out time", null);

        // check if location is not null
        PropertyLocation location = newProperty.getLocation();
        if (location == null) return new GenericResponse(false, 01, "Location must not be null", null);

        // check if location with address exist
        if (locationRepo.existsByAddress(location.getAddress()))
            return new GenericResponse(false, 01, "Address already exists", null);

        // done to maintain the coherence of the object graph, id of location is the id of the property
        location.setProperty(newProperty);
        propertyRepo.save(newProperty);

        // get inserted property from db to get id then insert into cache
        Optional<Property> fetchProperty = propertyRepo.findByLocation_Address(newProperty.getLocation().getAddress());
        if (fetchProperty.isPresent()) {
            propertyHashOperations.put(propertyKey, newProperty.getId(), newProperty);
        } else {
            return new GenericResponse(true, 00, "Property added to db, but not cache", null);
        }

        return new GenericResponse(true, 00, "Property added", null);
    }

    @CacheEvict(value = "property", allEntries = true)
    public String evictAllCacheValues() {
        return "evict all";
    }
}
