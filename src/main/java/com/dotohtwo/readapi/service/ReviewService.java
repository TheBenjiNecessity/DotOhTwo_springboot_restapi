package com.dotohtwo.readapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.dotohtwo.readapi.model.Review;
import com.dotohtwo.readapi.repository.ReviewRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
       
    public List<Review> getAll() {
        return reviewRepository.findAll();// needs paging
    }

    public Optional<Review> get(Long id) {
        return reviewRepository.findById(id);
    }

    public void create(Review review) {
        reviewRepository.save(review);
    }

    public void update(Long id, Review reviewDTO) { // What kind of update? just one field?
        Review daoReview = reviewRepository.findById(id).map(Review -> {
            Review.comment = reviewDTO.comment;
            Review.rating = reviewDTO.rating;
            Review.content = reviewDTO.content;
            Review.info = reviewDTO.info;

            return Review;
        }).orElseThrow(() -> {
            return new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Review not found with given id: " + id
            );
        });

        reviewRepository.save(daoReview);
    }

    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }
}