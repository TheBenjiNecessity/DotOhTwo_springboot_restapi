package com.dotohtwo.readapi.auth;

import com.dotohtwo.readapi.model.AppUser;
import com.dotohtwo.readapi.service.AppUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Optional;

public class Oauth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private AppUserService appUserService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        DefaultOAuth2User oauth2User = (DefaultOAuth2User) authentication.getPrincipal();
        AppUser appUser = AppUser.fromOauthUser(oauth2User);
        Optional<AppUser> dbUser = this.appUserService.getByUsername(appUser.getUsername());
        AppAuthenticationToken token = dbUser.map(AppAuthenticationToken::new).orElseGet(() -> new AppAuthenticationToken(appUser));

        // TODO set user in db if not exists

        SecurityContextHolder.getContext().setAuthentication(token);
        response.sendRedirect("http://localhost:3000/" + appUser.getUsername()); // TODO get from properties
    }
}
