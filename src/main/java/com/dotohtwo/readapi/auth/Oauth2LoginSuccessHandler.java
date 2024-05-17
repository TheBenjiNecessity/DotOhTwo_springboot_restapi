package com.dotohtwo.readapi.auth;

import com.dotohtwo.readapi.model.AppUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class Oauth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        DefaultOAuth2User oauth2User = (DefaultOAuth2User) authentication.getPrincipal();
        AppUser appUser = AppUser.fromOauthUser(oauth2User);
        AppAuthenticationToken token = new AppAuthenticationToken(appUser);
        SecurityContextHolder.getContext().setAuthentication(token);
        response.sendRedirect("http://localhost:3000/" + appUser.getUsername());
    }
}
