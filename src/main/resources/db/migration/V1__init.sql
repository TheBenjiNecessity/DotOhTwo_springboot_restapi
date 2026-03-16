CREATE TABLE IF NOT EXISTS app_user (
    id         BIGSERIAL PRIMARY KEY,
    joined     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    email      VARCHAR(255),
    phone      VARCHAR(255),
    dob        TIMESTAMP,
    username   VARCHAR(255),
    roles      VARCHAR(255),
    is_complete BOOLEAN NOT NULL DEFAULT FALSE,
    content    JSONB,
    settings   JSONB,
    preferences JSONB,
    statistics JSONB
);

CREATE TABLE IF NOT EXISTS reviewable (
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(255),
    description VARCHAR(255),
    type        VARCHAR(255),
    content     JSONB,
    info        JSONB,
    statistics  JSONB
);
