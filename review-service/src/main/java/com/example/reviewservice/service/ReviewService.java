package com.example.reviewservice.service;

import com.example.reviewservice.client.BookingClient;
import com.example.reviewservice.client.PropertyClient;
import com.example.reviewservice.model.Booking;
import com.example.reviewservice.model.GenericResponse;
import com.example.reviewservice.model.Review;
import com.example.reviewservice.repo.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepo reviewRepo;

    @Autowired
    private BookingClient bookingClient;

    @Autowired
    private PropertyClient propertyClient;

    public GenericResponse insertReview(Review newReview) {

        // check if property exists
        GenericResponse property = propertyClient.getPropertyById(newReview.getPropertyId());
        if (!property.getIsSuccess()) return new GenericResponse(false, 01, "Property does not exist", null);

        // check if booking exists
        GenericResponse booking = bookingClient.getBookingById(newReview.getBookingId());
        if (!booking.getIsSuccess()) return new GenericResponse(false, 01, "Booking does not exist", null);
        if (booking.getRespData().get(0) == null) return new GenericResponse(false, 01, "Booking object null?", null);

        // check if booking complete
        Booking booking1 = (Booking) booking.getRespData().get(0);
        if (!booking1.getComplete()) return new GenericResponse(false, 01, "Booking not complete", null);

        // insert review
        reviewRepo.save(newReview);
        return new GenericResponse(true, 00, "Review inserted", null);
    }

    public GenericResponse getReviewsByPropertyId(Long propertyId) {
        // check if property exists, not sure if needed?
        GenericResponse property = propertyClient.getPropertyById(propertyId);
        if (!property.getIsSuccess()) return new GenericResponse(false, 01, "Property does not exist", null);

        List<Review> reviews = reviewRepo.findAllByPropertyId(propertyId);
        if (reviews.isEmpty()) return new GenericResponse(false, 01, "No reviews found", null);

        return new GenericResponse(true, 00, "Success", reviews);
    }
}
