package com.dotohtwo.readapi.repository.DAO;

import com.dotohtwo.readapi.controller.DTO.AppUserDTO;
import com.dotohtwo.readapi.model.appUser.AppUserContent;
import com.dotohtwo.readapi.model.appUser.AppUserPreferences;
import com.dotohtwo.readapi.model.appUser.AppUserSettings;
import com.dotohtwo.readapi.model.appUser.AppUserStatistics;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;

@Entity
@Table(name = "app_user")
public class AppUserDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(insertable = false)
    public Date joined;

    public String email;
    public String phone;
    public Date DOB;

    public String username;

    public String roles;
    // private String salt;

    @JdbcTypeCode(value = SqlTypes.JSON)
    public AppUserContent content;

    @JdbcTypeCode(value = SqlTypes.JSON)
    public AppUserSettings settings;

    @JdbcTypeCode(value = SqlTypes.JSON)
    public AppUserPreferences preferences;

    @JdbcTypeCode(value = SqlTypes.JSON)
    public AppUserStatistics statistics;

    public AppUserDAO() {}

    public AppUserDAO(AppUserDTO appUserDTO) {
        this.email = appUserDTO.email;
        this.phone = appUserDTO.phone;
        this.DOB = appUserDTO.DOB;
        this.username = appUserDTO.username;
        this.content = appUserDTO.content;
        this.settings = appUserDTO.settings;
        this.preferences = appUserDTO.preferences;
        this.statistics = appUserDTO.statistics;

        this.roles = "USER";
    }
}
