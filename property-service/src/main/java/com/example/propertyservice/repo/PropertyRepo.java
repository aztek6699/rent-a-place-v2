package com.example.propertyservice.repo;

import com.example.propertyservice.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepo extends JpaRepository<Property, Long> {

    List<Property> findAllByPropertyOwnerId(Long propertyOwnerId);

    List<Property> findAllByLocation_City(String city);

    List<Property> findAllByLocation_Country(String country);
}
