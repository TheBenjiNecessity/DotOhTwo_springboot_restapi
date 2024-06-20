package com.dotohtwo.readapi.model;
 
import com.dotohtwo.readapi.controller.DTO.ReviewDTO;
import com.dotohtwo.readapi.model.review.ReviewContent;
import com.dotohtwo.readapi.model.review.ReviewInfo;

import com.dotohtwo.readapi.repository.DAO.ReviewDAO;
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

    public Review(ReviewDAO reviewDAO) {
        this.id = reviewDAO.id;
        this.userId = reviewDAO.userId;
        this.reviewableId = reviewDAO.reviewableId;
        this.created = reviewDAO.created;
        this.comment = reviewDAO.comment;
        this.rating = reviewDAO.rating;
        this.content = reviewDAO.content;
        this.info = reviewDAO.info;
    }

    public ReviewDTO toDTO() {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.id = this.id;
        reviewDTO.userId = this.userId;
        reviewDTO.reviewableId = this.reviewableId;
        reviewDTO.created = this.created;
        reviewDTO.comment = this.comment;
        reviewDTO.rating = this.rating;
        reviewDTO.content = this.content;
        reviewDTO.info = this.info;
        return reviewDTO;
    }
}