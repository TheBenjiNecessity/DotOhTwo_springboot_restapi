package com.dotohtwo.readapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.dotohtwo.readapi.model.AppUser;
import com.dotohtwo.readapi.repository.AppUserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {
    @Autowired
    private AppUserRepository appUserRepository;
       
    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();// needs paging
    }

    public Optional<AppUser> getUser(Long id) {
        return appUserRepository.findById(id);
    }

    public Optional<AppUser> getUserByUserame(String username) {
        return appUserRepository.findByUsername(username);
    }

    public void createUser(AppUser user) {
        appUserRepository.save(user);
    }

    public void updateUser(AppUser user) { // What kind of update? just one field?
        String username = user.getUsername();
        AppUser daoUser = appUserRepository.findByUsername(username).map(appUser -> {
            appUser.DOB = user.DOB;
            appUser.email = user.email;
            appUser.phone = user.phone;
            // appUser.roles = user.roles;
            appUser.content = user.content;
            appUser.settings = user.settings;
            appUser.preferences = user.preferences;
            appUser.statistics = user.statistics;
            return appUser;
        }).orElseThrow(() -> {
            return new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "AppUser not found with given username: " + username
            );
        });

        appUserRepository.save(daoUser);
    }
}