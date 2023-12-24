package com.dotohtwo.readapi.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import com.dotohtwo.readapi.detailService.JpaUserDetailsService;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JpaUserDetailsService jpaUserDetailsService;

    public SecurityConfig(JpaUserDetailsService jpaUserDetailsService) {
        this.jpaUserDetailsService = jpaUserDetailsService;
    }

    @Value("${jwtKey}")
    private byte[] jwtKey;

    // @Bean
    // UserDetailsService userDetailsService() {


    //     return new DOTUserDetailsManager();
    // }

    // @Bean
    // @Order(Ordered.HIGHEST_PRECEDENCE)
    // SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
    //     OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
    //     return http.formLogin(withDefaults()).build();
    // }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // return http
        //         .csrf(csrf -> csrf.disable())
        //                  .authorizeHttpRequests(auth -> {
        //             auth.requestMatchers("/review").permitAll();
        //             auth.requestMatchers("/user/{id}").permitAll();
        //             auth.requestMatchers("/favicon.ico").permitAll();
        //             auth.anyRequest().authenticated();
        //         })
        //         .userDetailsService(jpaUserDetailsService)
        //         .headers(withDefaults()) // sameOrigin?
        //         .httpBasic(withDefaults())
        //         .build();

        return http
                .csrf(csrf -> csrf.disable()) // TODO remove for security reasons
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(auth -> {
                    //auth.requestMatchers("/review").permitAll();
                    //auth.requestMatchers("/user/{id}").permitAll();
                    auth.requestMatchers("/favicon.ico").permitAll();
                    auth.anyRequest().authenticated();
                })
                //.userDetailsService(jpaUserDetailsService)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(auth -> auth.jwt(jwt -> jwt.decoder(jwtDecoder())))
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        SecretKey secretKey = new SecretKeySpec(jwtKey, "HMACSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
    }

}