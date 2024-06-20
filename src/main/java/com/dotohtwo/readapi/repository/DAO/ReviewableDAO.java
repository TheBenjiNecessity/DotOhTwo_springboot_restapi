package com.dotohtwo.readapi.repository.DAO;

import com.dotohtwo.readapi.controller.DTO.ReviewableDTO;
import com.dotohtwo.readapi.model.reviewable.ReviewableContent;
import com.dotohtwo.readapi.model.reviewable.ReviewableInfo;
import com.dotohtwo.readapi.model.reviewable.ReviewableStatistics;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
public class ReviewableDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String title;
    public String description;
    public String type;

    @JdbcTypeCode(value = SqlTypes.JSON)
    public ReviewableContent content;

    @JdbcTypeCode(value = SqlTypes.JSON)
    public ReviewableInfo info;

    @JdbcTypeCode(value = SqlTypes.JSON)
    public ReviewableStatistics statistics;

    public ReviewableDAO() {}

    public ReviewableDAO(ReviewableDTO reviewableDTO) {
        this.title = reviewableDTO.title;
        this.description = reviewableDTO.description;
        this.type = reviewableDTO.type;
        this.content = reviewableDTO.content;
        this.info = reviewableDTO.info;
        this.statistics = reviewableDTO.statistics;
    }
}
