package com.dotohtwo.readapi.model;
 
import com.dotohtwo.readapi.model.review.ReviewContent;
import com.dotohtwo.readapi.model.review.ReviewInfo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.util.Date;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer user_id;
    private Integer reviewable_id;

    @Column(insertable = false)
    public Date created;

    private String comment;
    private Integer rating;

    public String getComment() {
        return comment;
    }

    public Integer getRating() {
        return rating;
    }

    @JdbcTypeCode(value = SqlTypes.JSON)
    public ReviewContent content;

     @JdbcTypeCode(value = SqlTypes.JSON)
    public ReviewInfo info;

    public String toString() {
        return "Review";
    }
}