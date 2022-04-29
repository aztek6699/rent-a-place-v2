package com.example.bookingservice.service;

import com.example.bookingservice.repo.BookingRepo;
import com.example.bookingservice.client.PropertyClient;
import com.example.bookingservice.client.UserClient;
import com.example.bookingservice.model.Booking;
import com.example.bookingservice.model.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private PropertyClient propertyClient;

    @Autowired
    private UserClient userClient;

    public GenericResponse getBookingById(Long bookingId) {
        Optional<Booking> booking = bookingRepo.findById(bookingId);
        if (booking.isEmpty()) return new GenericResponse(false, 01, "Booking does not exist", null);
        else return new GenericResponse(true, 00, "Booking found", List.of(booking));
    }

    public GenericResponse getAllBookingsByRenterId(Long renterId) {

        GenericResponse renter = userClient.getUserById(renterId);
        if (!renter.getIsSuccess()) return new GenericResponse(false, 01, "Renter does not exist", null);

        List<Booking> bookings = bookingRepo.findAllByRenterId(renterId);
        if (bookings.isEmpty()) return new GenericResponse(false, 01, "No bookings found for user", null);

        // get list of booking
        return new GenericResponse(true, 00, "Success", bookingRepo.findAllByRenterId(renterId));
    }

    public GenericResponse getAllBookingsByPropertyId(Long propertyId) {

        GenericResponse renter = userClient.getUserById(propertyId);
        if (!renter.getIsSuccess()) return new GenericResponse(false, 01, "Renter does not exist", null);

        List<Booking> list = bookingRepo.findAllByPropertyId(propertyId);
        if (list.isEmpty()) return new GenericResponse(false, 01, "No bookings for property", null);

        return new GenericResponse(true, 00, "Success", list);
    }

    public GenericResponse insertBooking(Booking newBooking) {

        // check if property exists
        GenericResponse property = propertyClient.getPropertyById(newBooking.getPropertyId());
        if (!property.getIsSuccess()) return new GenericResponse(false, 01, "Property does not exist", null);

        // check if renter exists
        GenericResponse renter = userClient.getUserById(newBooking.getRenterId());
        if (!renter.getIsSuccess()) return new GenericResponse(false, 01, "renter does not exist", null);

        // check if end date after start date
        if (!newBooking.getEndDate().after(newBooking.getStartDate()))
            return new GenericResponse(false, 01, "End date must be after start date", null);

        // check if dates already taken
        List<Booking> bookingList = bookingRepo.findAllByPropertyId(newBooking.getPropertyId());
        Date newStartDate = newBooking.getStartDate();
        for (Booking booking : bookingList) {
            if (newStartDate.after(booking.getStartDate()) && newStartDate.before(booking.getEndDate())) {
                return new GenericResponse(false, 01, "Dates already taken", null);
            }
        }

        // insert booking
        bookingRepo.save(newBooking);
        return new GenericResponse(true, 00, "Booking inserted", null);
    }

    public GenericResponse updateBooking(Booking updateBooking) {

        // check if booking exists
        if (!bookingRepo.existsById(updateBooking.getId()))
            return new GenericResponse(false, 01, "Booking does not exist", null);

        // check if dates already taken
        List<Booking> bookingList = bookingRepo.findAllByPropertyId(updateBooking.getPropertyId());
        Date newStartDate = updateBooking.getStartDate();
        for (Booking booking : bookingList) {
            // dont compare same booking dates
            if (!updateBooking.getId().equals(booking.getId()) && newStartDate.after(booking.getStartDate()) && newStartDate.before(booking.getEndDate()))
                return new GenericResponse(false, 01, "Dates already taken", null);
        }

        bookingRepo.save(updateBooking);
        return new GenericResponse(true, 00, "Booking updated", null);
    }

    public GenericResponse completeBooking(Booking completeBooking) {

        // check if booking exists
        if (!bookingRepo.existsById(completeBooking.getId()))
            return new GenericResponse(false, 01, "Booking does not exist", null);

        // check if end date has passed
        Date now = new Date(System.currentTimeMillis());
        if (completeBooking.getEndDate().before(now)) return new GenericResponse(false, 01, "End date has not passed", null);

        completeBooking.setComplete(true);
        return new GenericResponse(true, 00, "Booking completed", null);
    }


    private Boolean checkIfBookingDatesValid(Booking newBooking) {
        List<Booking> bookingList = bookingRepo.findAllByPropertyId(newBooking.getPropertyId());
        Date newStartDate = newBooking.getStartDate();
        for (Booking booking : bookingList) {
            if (newStartDate.after(booking.getStartDate()) && newStartDate.before(booking.getEndDate())) {
                return false;
            }
        }
        return true;
    }
}
