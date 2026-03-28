package com.dotohtwo.readapi.repository.DAO;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "follow")
public class FollowDAO {

    @EmbeddedId
    public FollowId id;
}
