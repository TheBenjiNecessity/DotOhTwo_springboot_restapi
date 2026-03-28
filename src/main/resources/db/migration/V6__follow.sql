CREATE TABLE follow (
    follower_id UUID NOT NULL REFERENCES app_user(id),
    followed_id UUID NOT NULL REFERENCES app_user(id),
    PRIMARY KEY (follower_id, followed_id)
);
