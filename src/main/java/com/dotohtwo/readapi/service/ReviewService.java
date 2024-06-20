package com.dotohtwo.readapi.service;

import com.dotohtwo.readapi.repository.DAO.ReviewDAO;
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
        return reviewRepository
                .findAll()
                .stream()
                .map(Review::new)
                .toList();// needs paging
    }

    public Optional<Review> get(Long id) {
        return reviewRepository.findById(id).map(Review::new);
    }

    public Review create(ReviewDAO review) {
        return new Review(reviewRepository.save(review));
    }

    public Review update(Long id, ReviewDAO reviewDAO) { // What kind of update? just one field?
        ReviewDAO daoReview = reviewRepository.findById(id).map(review -> {
            review.comment = reviewDAO.comment;
            review.rating = reviewDAO.rating;
            review.content = reviewDAO.content;
            review.info = reviewDAO.info;

            return review;
        }).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Review not found with given id: " + id
        ));

        reviewRepository.save(daoReview);

        return new Review(daoReview);
    }

    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }
}