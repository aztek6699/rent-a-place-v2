package com.example.reviewservice.controller;

import com.example.reviewservice.model.GenericResponse;
import com.example.reviewservice.model.Review;
import com.example.reviewservice.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/review")
public class ReviewController {

    @Autowired
    private ReviewService service;

    @PostMapping("")
    public ResponseEntity<GenericResponse> insertReview(@RequestBody Review newReview) {
        return ResponseEntity.ok(service.insertReview(newReview));
    }

    @GetMapping("")
    public ResponseEntity<GenericResponse> getReviewsByPropertyId(@RequestParam Long propertyId) {
        return ResponseEntity.ok(service.getReviewsByPropertyId(propertyId));
    }
}
