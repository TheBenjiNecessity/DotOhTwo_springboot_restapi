package com.dotohtwo.readapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.dotohtwo.readapi.model.Reviewable;
import com.dotohtwo.readapi.repository.ReviewableRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewableService {
    @Autowired
    private ReviewableRepository reviewableRepository;
       
    public List<Reviewable> getAll() {
        return reviewableRepository.findAll();// needs paging
    }

    public Optional<Reviewable> get(Long id) {
        return reviewableRepository.findById(id);
    }

    public void create(Reviewable reviewable) {
        reviewableRepository.save(reviewable);
    }

    public void update(Long id, Reviewable reviewableDTO) { // What kind of update? just one field?
        Reviewable daoReviewable = reviewableRepository.findById(id).map(reviewable -> {
            reviewable.title = reviewableDTO.title;
            reviewable.description = reviewableDTO.description;
            reviewable.type = reviewableDTO.type;
            reviewable.content = reviewableDTO.content;
            reviewable.info = reviewableDTO.info;
            reviewable.statistics = reviewableDTO.statistics;
            return reviewable;
        }).orElseThrow(() -> {
            return new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Reviewable not found with given id: " + id
            );
        });

        reviewableRepository.save(daoReviewable);
    }
}