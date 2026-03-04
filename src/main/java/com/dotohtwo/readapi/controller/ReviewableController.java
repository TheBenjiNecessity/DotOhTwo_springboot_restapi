package com.dotohtwo.readapi.controller;

import java.util.Collection;

import com.dotohtwo.readapi.model.Reviewable;
import com.dotohtwo.readapi.repository.DAO.ReviewableDAO;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.dotohtwo.readapi.controller.DTO.ReviewableDTO;
import com.dotohtwo.readapi.service.ReviewableService;

@RestController
@RequestMapping("/reviewable")
public class ReviewableController {
    @Autowired
    private ReviewableService reviewableService;

    @GetMapping("/{id}")
    public ReviewableDTO get(@PathVariable("id") String id) {
        return reviewableService
                .get(Long.parseLong(id))
                .map(Reviewable::toDTO)
                .orElseThrow(() -> {
                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Reviewable not found with given id: " + id
                    );
                });
    }

    @PostMapping
    public ReviewableDTO post(@RequestBody ReviewableDTO reviewableDTO) {
        ReviewableDAO reviewableDAO = new ReviewableDAO(reviewableDTO);
        return reviewableService.create(reviewableDAO).toDTO();
    }

    @PutMapping("/{id}")
    public ReviewableDTO put(@PathVariable("id") String id, @RequestBody ReviewableDTO reviewableDTO) {
        ReviewableDAO reviewableDAO = new ReviewableDAO(reviewableDTO);
        return reviewableService.update(Long.parseLong(id), reviewableDAO).toDTO();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        reviewableService.delete(Long.parseLong(id));
    }
}
