package com.dotohtwo.readapi.model;
 
import com.dotohtwo.readapi.model.appUser.AppUserContent;
import com.dotohtwo.readapi.model.appUser.AppUserPreferences;
import com.dotohtwo.readapi.model.appUser.AppUserSettings;
import com.dotohtwo.readapi.model.appUser.AppUserStatistics;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.util.Date;
import java.util.Map;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(insertable = false)
    public Date joined;
 
    public String email;
    public String phone;
    public Date DOB;

    private String username;

    private String password;
    private String roles;
    // private String salt;

    @JdbcTypeCode(value = SqlTypes.JSON)
    public AppUserContent content;

    @JdbcTypeCode(value = SqlTypes.JSON)
    public AppUserSettings settings;

    @JdbcTypeCode(value = SqlTypes.JSON)
    public AppUserPreferences preferences;

    @JdbcTypeCode(value = SqlTypes.JSON)
    public AppUserStatistics statistics;

    public Long getId() {
        return this.id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getRoles() {
        return roles;
    }

    public void setValuesForNewUser() {
        this.roles = "USER";
    }

    public String toString() {
        return String.format("App User: email: %s", this.email);
    }

    public static AppUser fromOauthUser(DefaultOAuth2User oauth2User) {
        Map<String, Object> attributes = oauth2User.getAttributes();
        AppUser appUser = new AppUser();
        //appUser.id = Long.parseLong((String)attributes.get("id"));
        appUser.username = (String)attributes.get("login");
        appUser.email = (String)attributes.get("email");
        //appUser.imageUrl = (String)attributes.get("avatar_url");
        return appUser;
    }
}