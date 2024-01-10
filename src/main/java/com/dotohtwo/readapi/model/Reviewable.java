package com.dotohtwo.readapi.model;
 
import com.dotohtwo.readapi.model.reviewable.ReviewableContent;
import com.dotohtwo.readapi.model.reviewable.ReviewableInfo;
import com.dotohtwo.readapi.model.reviewable.ReviewableStatistics;

//import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
public class Reviewable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column(insertable = false)
    // public Date created; ???

    private String title;
    private String description;
    private String type;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    @JdbcTypeCode(value = SqlTypes.JSON)
    public ReviewableContent content;

    @JdbcTypeCode(value = SqlTypes.JSON)
    public ReviewableInfo info;

    @JdbcTypeCode(value = SqlTypes.JSON)
    public ReviewableStatistics statistics;

    public String toString() {
        return "Reviewable";
    }
}