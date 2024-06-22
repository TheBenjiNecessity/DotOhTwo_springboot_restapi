package com.dotohtwo.readapi.model;
 
import com.dotohtwo.readapi.controller.DTO.AppUserDTO;
import com.dotohtwo.readapi.model.appUser.AppUserContent;
import com.dotohtwo.readapi.model.appUser.AppUserPreferences;
import com.dotohtwo.readapi.model.appUser.AppUserSettings;
import com.dotohtwo.readapi.model.appUser.AppUserStatistics;

import com.dotohtwo.readapi.repository.DAO.AppUserDAO;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.springframework.security.oauth2.core.user.DefaultOAuth2User;


public class AppUser implements Serializable {
    private Long id;

    public Date joined;
 
    public String email;
    public String phone;
    public Date DOB;

    private final String username;
    private final String roles;

    public AppUserContent content;
    public AppUserSettings settings;
    public AppUserPreferences preferences;
    public AppUserStatistics statistics;

    public AppUser(AppUserDAO appUserDAO) {
        this.id = appUserDAO.id;
        this.joined = appUserDAO.joined;
        this.email = appUserDAO.email;
        this.phone = appUserDAO.phone;
        this.DOB = appUserDAO.DOB;
        this.username = appUserDAO.username;
        this.roles = appUserDAO.roles;
        this.content = appUserDAO.content;
        this.settings = appUserDAO.settings;
        this.preferences = appUserDAO.preferences;
        this.statistics = appUserDAO.statistics;
    }

    public AppUser(Map<String, Object> attributes) {
        //this.id = Long.parseLong((String)attributes.get("id"));
        this.username = (String)attributes.get("login");
        this.email = (String)attributes.get("email");
        //this.imageUrl = (String)attributes.get("avatar_url");
        this.roles = "USER";

        if (this.email == null || this.email.isBlank()) {
            // TODO what to do about no email?
            this.email = "noemail@noemail.com"; // TODO need to remove
        }
    }

    public Long getId() {
        return this.id;
    }

    public String getUsername() {
        return username;
    }

    public String getRoles() {
        return roles;
    }

    public AppUserDTO toDTO() {
        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.joined = this.joined;
        appUserDTO.email = this.email;
        appUserDTO.phone = this.phone;
        appUserDTO.DOB = this.DOB;
        appUserDTO.username = this.username;
        appUserDTO.content = this.content;
        appUserDTO.settings = this.settings;
        appUserDTO.preferences = this.preferences;
        appUserDTO.statistics = this.statistics;
        return appUserDTO;
    }

    public AppUserDAO toDAO() {
        AppUserDAO appUserDAO = new AppUserDAO();
        appUserDAO.email = this.email;
        appUserDAO.phone = this.phone;
        appUserDAO.DOB = this.DOB;
        appUserDAO.username = this.username;
        appUserDAO.content = this.content;
        appUserDAO.settings = this.settings;
        appUserDAO.preferences = this.preferences;
        appUserDAO.statistics = this.statistics;
        appUserDAO.roles = this.roles;
        return appUserDAO;
    }

    public static AppUser fromOauthUser(DefaultOAuth2User oauth2User) {
        Map<String, Object> attributes = oauth2User.getAttributes();
        return new AppUser(attributes);
    }
}