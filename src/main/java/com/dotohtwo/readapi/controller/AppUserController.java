package com.dotohtwo.readapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dotohtwo.readapi.model.AppUser;
import com.dotohtwo.readapi.service.AppUserService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class AppUserController {
    @Autowired
    private AppUserService appUserService;

    @GetMapping
    public Optional<AppUser> get(Principal principal, @RequestParam(required = false) String username) {
        String name = username != null ? username : principal.getName();
        // TODO getting a user other than the signed in user should return less data
        return appUserService.getUserByUserame(name);
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
}