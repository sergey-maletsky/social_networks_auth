CREATE SEQUENCE IF NOT EXISTS user_id_seq MINVALUE 1;

CREATE TABLE IF NOT EXISTS users
(
  id BIGINT PRIMARY KEY NOT NULL,
  social_media_id VARCHAR NOT NULL,
  email VARCHAR NOT NULL,
  first_name VARCHAR NOT NULL,
  last_name VARCHAR NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS user_email_idx
  ON users (email);