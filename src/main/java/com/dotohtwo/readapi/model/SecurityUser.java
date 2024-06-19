package com.dotohtwo.readapi.model;

import java.util.Arrays;
import java.util.Collection;

import com.dotohtwo.readapi.repository.DAO.AppUserDAO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUser implements UserDetails {

    private final AppUserDAO appUserDAO;

    public SecurityUser(AppUserDAO appUserDAO) {
        this.appUserDAO = appUserDAO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(appUserDAO
                .roles
                .split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getPassword() {
        return null; // TODO needed? appUser.getPassword();
    }

    @Override
    public String getUsername() {
        return appUserDAO.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public AppUserDAO getUser() {
        return this.appUserDAO;
    }
}
