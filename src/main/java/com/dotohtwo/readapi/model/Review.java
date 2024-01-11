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

    private Long userId;
    private Long reviewableId;

    @Column(insertable = false)
    private Date created;

    public String comment;
    public Integer rating;

    public Long getId() {
        return this.id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public Long getReviewableId() {
        return this.reviewableId;
    }

    public Date getCreated() {
        return this.created;
    }

    @JdbcTypeCode(value = SqlTypes.JSON)
    public ReviewContent content;

     @JdbcTypeCode(value = SqlTypes.JSON)
    public ReviewInfo info;

    public String toString() {
        return "Review";
    }
}