package com.dotohtwo.readapi.controller.DTO;

import com.dotohtwo.readapi.model.appUser.AppUserContent;
import com.dotohtwo.readapi.model.appUser.AppUserPreferences;
import com.dotohtwo.readapi.model.appUser.AppUserSettings;
import com.dotohtwo.readapi.model.appUser.AppUserStatistics;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;

public class AppUserDTO {
    private Long id;

    public Date joined;

    public String email;
    public String phone;
    public Date DOB;

    public String username;

    @JdbcTypeCode(value = SqlTypes.JSON)
    public AppUserContent content;

    @JdbcTypeCode(value = SqlTypes.JSON)
    public AppUserSettings settings;

    @JdbcTypeCode(value = SqlTypes.JSON)
    public AppUserPreferences preferences;

    @JdbcTypeCode(value = SqlTypes.JSON)
    public AppUserStatistics statistics;
}
