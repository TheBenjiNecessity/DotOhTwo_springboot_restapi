package com.dotohtwo.readapi.controller;

import java.util.Collection;
import java.util.List;

import com.dotohtwo.readapi.controller.DTO.AppUserDTO;
import com.dotohtwo.readapi.controller.DTO.CompleteProfileDTO;
import com.dotohtwo.readapi.repository.DAO.AppUserDAO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import java.util.UUID;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dotohtwo.readapi.model.AppUser;
import com.dotohtwo.readapi.service.AppUserService;

@RestController
@RequestMapping("/users")
public class AppUserController {
    @Autowired
    private AppUserService appUserService;

    @GetMapping
    public AppUserDTO get(@RequestParam(value = "username") String username) {
        // potentially unsafe endpoint as any user could access this with an access token
        // maybe I could just not make this api publicly available
        return appUserService
                .getByUsername(username)
                .map(AppUser::toDTO)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "AppUser not found with given username: " + username
                ));
    }

    @GetMapping("/me")
    public AppUserDTO getMe(@AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaim("name");
        return appUserService
                .getByUsername(username)
                .map(AppUser::toDTO)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "AppUser not found with given username: " + username
                ));
    }

    @GetMapping("/{id}")
    public AppUserDTO getUserById(@PathVariable("id") UUID id) {
        return appUserService
                .get(id)
                .map(AppUser::toDTO)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "AppUser not found with given id: " + id
                ));
    }

    @PostMapping
    public AppUserDTO create(@RequestBody AppUserDTO appUserDTO) {
        AppUserDAO appUserDAO = new AppUserDAO(appUserDTO);
        return appUserService.create(appUserDAO).toDTO();
    }

    @PutMapping
    public AppUserDTO update(@RequestBody AppUserDTO appUserDTO) {
        AppUserDAO appUserDAO = new AppUserDAO(appUserDTO);
        return appUserService.update(appUserDAO).toDTO();
    }

    @PutMapping("/complete-profile")
    public AppUserDTO completeProfile(@AuthenticationPrincipal Jwt jwt, @RequestBody CompleteProfileDTO completeProfileDTO) {
        String username = jwt.getClaim("name");
        AppUser appUser = appUserService
            .getByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "AppUser not found with given username: " + username
            ));

        appUser.email = completeProfileDTO.email;
        appUser.isComplete = true;

        return appUserService.update(appUser.toDAO()).toDTO();
    }

    @PostMapping("/{id}/follow")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void follow(@AuthenticationPrincipal Jwt jwt, @PathVariable("id") UUID id) {
        appUserService.follow(jwt.getClaim("name"), id);
    }

    @GetMapping("/{id}/followers")
    public List<String> getFollowers(@PathVariable("id") UUID id) {
        return appUserService.getFollowers(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") UUID id) {
        appUserService.delete(id);
    }
}