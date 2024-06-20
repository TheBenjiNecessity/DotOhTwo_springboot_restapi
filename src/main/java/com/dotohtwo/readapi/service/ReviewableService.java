package com.dotohtwo.readapi.service;

import com.dotohtwo.readapi.repository.DAO.ReviewableDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.dotohtwo.readapi.model.Reviewable;
import com.dotohtwo.readapi.repository.ReviewableRepository;

import java.util.List;
import java.util.Collection;
import java.util.Optional;

@Service
public class ReviewableService {
    @Autowired
    private ReviewableRepository reviewableRepository;
       
    public List<Reviewable> getAll() {
        return reviewableRepository.findAll().stream().map(Reviewable::new).toList();// needs paging
    }

    public Optional<Reviewable> get(Long id) {
        return reviewableRepository.findById(id).map(Reviewable::new);
    }

    public Collection<Reviewable> search(String searchText, String locale, Integer limit, Integer offset) {
        return reviewableRepository
                .findReviewablesByTitle(searchText, locale, limit, offset)
                .stream()
                .map(Reviewable::new)
                .toList();
    }

    public Reviewable create(ReviewableDAO reviewable) {
        return new Reviewable(reviewableRepository.save(reviewable));
    }

    public Reviewable update(Long id, ReviewableDAO reviewableDTO) { // What kind of update? just one field?
        ReviewableDAO daoReviewable = reviewableRepository.findById(id).map(reviewable -> {
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
        return new Reviewable(daoReviewable);
    }

    public void delete(Long id) {
        reviewableRepository.deleteById(id);
    }
}