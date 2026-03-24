package com.dotohtwo.readapi.model;
 
import com.dotohtwo.readapi.controller.DTO.ReviewableDTO;
import com.dotohtwo.readapi.model.reviewable.ReviewableContent;
import com.dotohtwo.readapi.model.reviewable.ReviewableInfo;
import com.dotohtwo.readapi.model.reviewable.ReviewableStatistics;

import com.dotohtwo.readapi.repository.DAO.ReviewableDAO;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

public class Reviewable {
    private Long id;

    public String slug;

    public Long getId() {
        return this.id;
    }

    @JdbcTypeCode(value = SqlTypes.JSON)
    public ReviewableContent content;

    @JdbcTypeCode(value = SqlTypes.JSON)
    public ReviewableInfo info;

    @JdbcTypeCode(value = SqlTypes.JSON)
    public ReviewableStatistics statistics;

    public Reviewable(ReviewableDAO reviewableDAO) {
        this.id = reviewableDAO.id;
        this.slug = reviewableDAO.slug;
        this.content = reviewableDAO.content;
        this.info = reviewableDAO.info;
        this.statistics = reviewableDAO.statistics;
    }

    public ReviewableDTO toDTO() {
        ReviewableDTO reviewableDTO = new ReviewableDTO();
        reviewableDTO.id = this.id;
        reviewableDTO.slug = this.slug;
        reviewableDTO.content = this.content;
        reviewableDTO.info = this.info;
        reviewableDTO.statistics = this.statistics;
        return reviewableDTO;
    }
}