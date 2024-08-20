package com.dotohtwo.readapi.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.SecurityFilterChain;

import com.dotohtwo.readapi.model.AppUser;
import com.dotohtwo.readapi.service.AppUserService;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.util.Optional;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableRedisHttpSession
public class SecurityConfig {
    @Autowired
    private AppUserService appUserService;

    @Autowired
    private Environment env;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // TODO remove for security reasons
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.anyRequest().authenticated();
                })
                .exceptionHandling(exceptionHandlingConfigurer -> {
                    exceptionHandlingConfigurer.authenticationEntryPoint(
                            new Oauth2AuthenticationEntrypoint());
                })
                .oauth2Login(auth -> auth
                        .successHandler((request, response, authentication) -> {
                            DefaultOAuth2User oauth2User = (DefaultOAuth2User) authentication.getPrincipal();
                            AppUser appUser = AppUser.fromOauthUser(oauth2User);
                            Optional<AppUser> dbUser = this.appUserService.getByUsername(appUser.getUsername());
                            AppAuthenticationToken token = dbUser.map(AppAuthenticationToken::new).orElseGet(() -> new AppAuthenticationToken(appUser));

                            if (dbUser.isEmpty()) {
                                this.appUserService.create(appUser.toDAO()); // TODO what happens on failure?
                            }

                            SecurityContextHolder.getContext().setAuthentication(token);
                            response.sendRedirect(env.getProperty("frontend.url"));
                        })
                )
                .build();
    }
}