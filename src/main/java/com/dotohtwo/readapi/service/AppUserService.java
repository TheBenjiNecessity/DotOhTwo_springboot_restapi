package com.dotohtwo.readapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.dotohtwo.readapi.kafka.KafkaProducerService;
import com.dotohtwo.readapi.model.AppUser;
import com.dotohtwo.readapi.repository.AppUserRepository;
import com.dotohtwo.readapi.repository.FollowRepository;
import com.dotohtwo.readapi.repository.DAO.AppUserDAO;
import com.dotohtwo.readapi.repository.DAO.FollowDAO;
import com.dotohtwo.readapi.repository.DAO.FollowId;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppUserService {
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private KafkaProducerService kafkaProducerService;
       
    public List<AppUser> getAll() {
        return appUserRepository
                .findAll()
                .stream()
                .map(AppUser::new)
                .toList(); // needs paging
    }

    public Optional<AppUser> get(UUID id) {
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
        AppUser created = new AppUser(appUserRepository.save(user));
        kafkaProducerService.publish("users.created", created);
        return created;
    }

    public AppUser update(AppUserDAO user) { // What kind of update? just one field?
        String username = user.username;
        AppUserDAO daoUser = appUserRepository.findByUsername(username).map(appUser -> {
            appUser.DOB = user.DOB;
            appUser.email = user.email;
            appUser.phone = user.phone;
            appUser.roles = user.roles;
            appUser.isComplete = user.isComplete;
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

    public void follow(String followerUsername, UUID followedId) {
        UUID followerId = appUserRepository.findByUsername(followerUsername)
                .map(u -> u.id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "AppUser not found with given username: " + followerUsername
                ));

        if (!appUserRepository.existsById(followedId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "AppUser not found with given id: " + followedId
            );
        }

        FollowId followId = new FollowId();
        followId.followerId = followerId;
        followId.followedId = followedId;

        FollowDAO follow = new FollowDAO();
        follow.id = followId;

        followRepository.save(follow);
    }

    public List<String> getFollowers(UUID userId) {
        return followRepository.findFollowerUsernamesByUserId(userId);
    }

    public void delete(UUID id) {
        appUserRepository.deleteById(id);
    }
}