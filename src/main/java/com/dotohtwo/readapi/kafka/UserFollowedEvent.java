package com.dotohtwo.readapi.kafka;

import java.util.UUID;

public record UserFollowedEvent(UUID followerId, UUID followedId) {}
