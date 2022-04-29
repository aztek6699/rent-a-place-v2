package com.example.propertyservice.repo;

import com.example.propertyservice.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepo extends JpaRepository<Property, Long> {

    List<Property> findAllByPropertyOwnerId(Long propertyOwnerId);
    Optional<Property> findByLocation_Address(String address);
    List<Property> findAllByLocation_City(String city);
    List<Property> findAllByLocation_Country(String country);
}
