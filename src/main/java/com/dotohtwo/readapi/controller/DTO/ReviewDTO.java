package com.dotohtwo.readapi.controller.DTO;

import com.dotohtwo.readapi.model.review.ReviewContent;
import com.dotohtwo.readapi.model.review.ReviewInfo;

import java.util.Date;

public class ReviewDTO {
    public Long id;

    public Long userId;
    public Long reviewableId;

    public Date created;

    public String comment;
    public Integer rating;

    public ReviewContent content;
    public ReviewInfo info;
}
