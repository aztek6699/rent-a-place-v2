package com.example.bookingservice.contoller;

import com.example.bookingservice.service.BookingService;
import com.example.bookingservice.model.Booking;
import com.example.bookingservice.model.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/booking")
public class BookingController {

    @Autowired
    private BookingService service;

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getBookingById(id));
    }

    @GetMapping("/getAllBookingsByUserId/{id}")
    public ResponseEntity<GenericResponse> getAllBookingsByRenterId(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAllBookingsByRenterId(id));
    }

    @GetMapping("/getAllBookingsByPropertyId/{id}")
    public ResponseEntity<GenericResponse> getAllBookingsByPropertyId(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAllBookingsByPropertyId(id));
    }

    @PostMapping("")
    public ResponseEntity<GenericResponse> insertBooking(@RequestBody Booking booking) {
        return ResponseEntity.ok(service.insertBooking(booking));
    }

    @PutMapping("")
    public ResponseEntity<GenericResponse> updateBooking(@RequestBody Booking booking) {
        return ResponseEntity.ok(service.updateBooking(booking));
    }

    @PutMapping("/completeBooking")
    public ResponseEntity<GenericResponse> completeBooking(@RequestBody Booking booking) {
        return ResponseEntity.ok(service.completeBooking(booking));
    }
}
