ALTER TABLE app_user ADD CONSTRAINT uq_app_user_username UNIQUE (username);
ALTER TABLE app_user ADD CONSTRAINT uq_app_user_email UNIQUE (email);
ALTER TABLE app_user ADD CONSTRAINT uq_app_user_phone UNIQUE (phone);
