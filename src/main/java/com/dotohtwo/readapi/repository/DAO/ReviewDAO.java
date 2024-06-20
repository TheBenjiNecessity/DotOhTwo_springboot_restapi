package com.dotohtwo.readapi.repository.DAO;

import com.dotohtwo.readapi.controller.DTO.ReviewDTO;
import com.dotohtwo.readapi.model.review.ReviewContent;
import com.dotohtwo.readapi.model.review.ReviewInfo;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;

@Entity
public class ReviewDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public Long userId;
    public Long reviewableId;

    @Column(insertable = false)
    public Date created;

    public String comment;
    public Integer rating;

    @JdbcTypeCode(value = SqlTypes.JSON)
    public ReviewContent content;

    @JdbcTypeCode(value = SqlTypes.JSON)
    public ReviewInfo info;

    public ReviewDAO() {}

    public ReviewDAO(ReviewDTO reviewDTO) {
        this.userId = reviewDTO.userId;
        this.reviewableId = reviewDTO.reviewableId;
        this.created = reviewDTO.created;
        this.comment = reviewDTO.comment;
        this.rating = reviewDTO.rating;
        this.content = reviewDTO.content;
        this.info = reviewDTO.info;
    }
}
