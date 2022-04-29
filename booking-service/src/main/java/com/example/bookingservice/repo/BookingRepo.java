package com.example.bookingservice.repo;

import com.example.bookingservice.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Long> {

    List<Booking> findAllByPropertyId(Long propertyId);
    List<Booking> findAllByRenterId(Long renterId);
}
