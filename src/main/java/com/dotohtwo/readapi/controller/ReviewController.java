package com.dotohtwo.readapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
public class ReviewController {
    @GetMapping("")
    public String get(@RequestParam(value = "id") String id) {
      return "review";
    }

    @GetMapping("/list")
    public String list(@RequestParam(value = "time") String name) {
      return "list";
    }



}
