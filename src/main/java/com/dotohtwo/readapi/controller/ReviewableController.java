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

import com.dotohtwo.readapi.model.Reviewable;
import com.dotohtwo.readapi.service.ReviewableService;

@RestController
@RequestMapping("/reviewable")
public class ReviewableController {
    @Autowired
    private ReviewableService reviewableService;

    @GetMapping("/{id}")
    public Reviewable get(@PathVariable("id") String id) {
        return reviewableService.get(Long.parseLong(id)).orElseThrow(() -> {
            return new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Reviewable not found with given id: " + id
            );
        });
    }

    @GetMapping("/list")
    public List<Reviewable> list(@RequestParam(value = "time") String time) {
      return reviewableService.getAll();
    }

    @PostMapping("/{id}")
    public Reviewable post(@RequestBody Reviewable reviewable) {
        return reviewableService.create(reviewable);
    }

    // @PatchMapping("/{id}")
    // public String get(@PathVariable("id") String id) {
    //   return "review";
    // }

    @PutMapping("/{id}")
    public Reviewable put(@PathVariable("id") String id, @RequestBody Reviewable reviewable) {
      return reviewableService.update(Long.parseLong(id), reviewable);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
      reviewableService.delete(Long.parseLong(id));
      // TODO return status?
    }
}
