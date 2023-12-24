package com.dotohtwo.readapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}