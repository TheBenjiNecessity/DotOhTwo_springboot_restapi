ALTER TABLE app_user DROP CONSTRAINT app_user_pkey;
ALTER TABLE app_user ALTER COLUMN id DROP DEFAULT;
ALTER TABLE app_user ALTER COLUMN id TYPE UUID USING gen_random_uuid();
ALTER TABLE app_user ALTER COLUMN id SET DEFAULT gen_random_uuid();
ALTER TABLE app_user ADD PRIMARY KEY (id);

ALTER TABLE reviewable DROP CONSTRAINT reviewable_pkey;
ALTER TABLE reviewable ALTER COLUMN id DROP DEFAULT;
ALTER TABLE reviewable ALTER COLUMN id TYPE UUID USING gen_random_uuid();
ALTER TABLE reviewable ALTER COLUMN id SET DEFAULT gen_random_uuid();
ALTER TABLE reviewable ADD PRIMARY KEY (id);
