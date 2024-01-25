package com.dotohtwo.readapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.dotohtwo.readapi.model.Review;
import com.dotohtwo.readapi.service.ReviewService;

@RestController
@RequestMapping("/review")
public class ReviewController {
   @Autowired
    private ReviewService reviewService;

    @GetMapping("/{id}")
    public Review get(@PathVariable("id") String id) {
        return reviewService.get(Long.parseLong(id)).orElseThrow(() -> {
            return new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Review not found with given id: " + id
            );
        });
    }

    @GetMapping("/list")
    public List<Review> list(@RequestParam(value = "time") String time) {
        return reviewService.getAll();
    }

    @PostMapping("/{id}")
    public Review post(@RequestBody Review review) {
        return reviewService.create(review);
    }

    // @PatchMapping("/{id}")
    // public String get(@PathVariable("id") String id) {
    //   return "review";
    // }

    @PutMapping("/{id}")
    public Review put(@PathVariable("id") String id, @RequestBody Review review) {
        return reviewService.update(Long.parseLong(id), review);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
      reviewService.delete(Long.parseLong(id));
    }
}

// TODO endpoint to get reviewable for review?