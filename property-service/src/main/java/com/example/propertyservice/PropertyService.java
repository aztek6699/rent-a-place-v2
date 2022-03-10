package com.example.propertyservice;

import com.example.propertyservice.model.Property;
import com.example.propertyservice.repo.LocationRepo;
import com.example.propertyservice.repo.PropertyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {

    private final PropertyRepo propertyRepo;
    private final LocationRepo locationRepo;

    @Autowired
    public PropertyService(PropertyRepo propertyRepo, LocationRepo locationRepo) {
        this.propertyRepo = propertyRepo;
        this.locationRepo = locationRepo;
    }

    public List<Property> getPropertysByOwnerId(Long propertyOwnerId) {
        return propertyRepo.findAllByPropertyOwnerId(propertyOwnerId);
    }

    public Boolean insertProperty(Long propertyOwnerId) {

    }
}
