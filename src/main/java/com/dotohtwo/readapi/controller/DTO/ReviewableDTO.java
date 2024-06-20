package com.dotohtwo.readapi.controller.DTO;

import com.dotohtwo.readapi.model.reviewable.ReviewableContent;
import com.dotohtwo.readapi.model.reviewable.ReviewableInfo;
import com.dotohtwo.readapi.model.reviewable.ReviewableStatistics;

public class ReviewableDTO {
    public Long id;

    public String title;
    public String description;
    public String type;

    public ReviewableContent content;
    public ReviewableInfo info;
    public ReviewableStatistics statistics;
}
