package com.dotohtwo.readapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dotohtwo.readapi.model.AppUser;
import com.dotohtwo.readapi.model.SecurityUser;
import com.dotohtwo.readapi.service.AppUserService;

@RestController
@RequestMapping("/user")
public class AppUserController {
    @Autowired
    private AppUserService appUserService;

    @GetMapping
    public AppUser get(SecurityUser principal, @RequestParam(required = false) String username) {
        // TODO getting a user other than the signed in user should return less data
        String name = username != null ? username : principal.getUser().getUsername();

        return appUserService.getUserByUserame(name).orElseThrow(() -> {
            return new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "AppUser not found with given username: " + name
            );
        });
    }
       
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/list")
    public List<AppUser> listAll() {
        return appUserService.getAllUsers();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public Optional<AppUser> get(@PathVariable("id") String id) {
        return appUserService.getUser(Long.parseLong(id));
    }  

    @PostMapping
    public AppUser create(@RequestBody AppUser user) {
        user.setValuesForNewUser();
        appUserService.createUser(user);

        String username = user.getUsername();
        return appUserService.getUserByUserame(username).orElseThrow(() -> {
            return new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "AppUser not found with given username: " + username
            );
        });
    }
}