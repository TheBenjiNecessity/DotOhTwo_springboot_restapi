package com.dotohtwo.readapi.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
public class ReviewController {
    @GetMapping("/{id}")
    public String get(@PathVariable("id") String id) {
      return "review";
    }

    @GetMapping("/list")
    public String list(@RequestParam(value = "time") String name) {
      return "list";
    }

    @PostMapping("/{id}")
    public String post(@PathVariable("id") String id) {
      return "review";
    }

    // @PatchMapping("/{id}")
    // public String get(@PathVariable("id") String id) {
    //   return "review";
    // }

    @PutMapping("/{id}")
    public String put(@PathVariable("id") String id) {
      return "review";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String id) {
      return "review";
    }
}
