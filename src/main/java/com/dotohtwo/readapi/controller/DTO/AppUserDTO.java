package com.dotohtwo.readapi.controller.DTO;

import com.dotohtwo.readapi.model.appUser.AppUserContent;
import com.dotohtwo.readapi.model.appUser.AppUserPreferences;
import com.dotohtwo.readapi.model.appUser.AppUserSettings;
import com.dotohtwo.readapi.model.appUser.AppUserStatistics;

import java.util.Date;

public class AppUserDTO {
    public Long id;

    public Date joined;

    public String email;
    public String phone;
    public Date DOB;

    public String username;
    public boolean isComplete;

    public AppUserContent content;

    public AppUserSettings settings;

    public AppUserPreferences preferences;

    public AppUserStatistics statistics;
}
