package com.dotohtwo.readapi.model;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.util.Date;
 
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
}