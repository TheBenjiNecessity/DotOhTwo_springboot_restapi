package com.dotohtwo.readapi.repository.DAO;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class FollowId implements Serializable {
    public UUID followerId;
    public UUID followedId;
}
