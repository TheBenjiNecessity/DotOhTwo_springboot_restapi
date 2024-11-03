package com.dotohtwo.readapi.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dotohtwo.readapi.model.AppUser;
import com.dotohtwo.readapi.service.AppUserService;

@RestController
@RequestMapping("/user")
public class AppUserController {
    @Autowired
    private AppUserService appUserService;

    @GetMapping
    public AppUserDTO get(@AuthenticationPrincipal Jwt jwt, @RequestParam(required = false) String username) {
        // TODO getting a user other than the signed in user should return less data
        String name = username != null ? username : jwt.getClaim("name");

        return appUserService
                .getByUsername(name)
                .map(AppUser::toDTO)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "AppUser not found with given username: " + name
                ));
    }

    @GetMapping("/search")
    public Collection<AppUserDTO> search(
        @RequestParam(value = "text") String searchText,
        @RequestParam(value = "limit") Integer limit,
        @RequestParam(value = "offset") Integer offset
    ) {
        return appUserService
                .search(searchText, limit, offset)
                .stream().map(AppUser::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public Optional<AppUserDTO> get(@PathVariable("id") String id) {
        return appUserService.get(Long.parseLong(id)).map(AppUser::toDTO);
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

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
      appUserService.delete(Long.parseLong(id));
      // TODO return status?
    }
}