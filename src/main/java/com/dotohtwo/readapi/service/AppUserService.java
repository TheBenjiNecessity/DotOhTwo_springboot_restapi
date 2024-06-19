package com.dotohtwo.readapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.dotohtwo.readapi.model.AppUser;
import com.dotohtwo.readapi.repository.AppUserRepository;
import com.dotohtwo.readapi.repository.DAO.AppUserDAO;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {
    @Autowired
    private AppUserRepository appUserRepository;
       
    public List<AppUser> getAll() {
        return appUserRepository
                .findAll()
                .stream()
                .map(AppUser::new)
                .toList(); // needs paging
    }

    public Optional<AppUser> get(Long id) {
        return appUserRepository.findById(id).map(AppUser::new);
    }

    public Optional<AppUser> getByUsername(String username) {
        return appUserRepository.findByUsername(username).map(AppUser::new);
    }

    public Collection<AppUser> search(String searchText, Integer limit, Integer offset) {
        return appUserRepository.findUsersBySearch(searchText, limit, offset).stream()
                .map(AppUser::new).toList();
    }

    public AppUser create(AppUserDAO user) {
        return new AppUser(appUserRepository.save(user));
    }

    public AppUser update(AppUserDAO user) { // What kind of update? just one field?
        String username = user.username;
        AppUserDAO daoUser = appUserRepository.findByUsername(username).map(appUser -> {
            appUser.DOB = user.DOB;
            appUser.email = user.email;
            appUser.phone = user.phone;
            appUser.roles = user.roles;
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

        return new AppUser(daoUser);
    }

    public void delete(Long id) {
        appUserRepository.deleteById(id);
    }
}