CREATE DATABASE board;
USE board;

CREATE TABLE article(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    content TEXT,
    writer VARCHAR(255),
    created_at DATETIME,
    updated_at DATETIME
);

CREATE TABLE comment(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    writer VARCHAR(255),
    content TEXT,
    created_at DATETIME,
    updated_at DATETIME,
    article_id BIGINT,
    FOREIGN KEY (article_id) REFERENCES Article(id)
);

CREATE TABLE app_user (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(45) UNIQUE NOT NULL,
  password VARCHAR(255),
  name VARCHAR(255),
  nickname VARCHAR(255),
  profile_image_url VARCHAR(255)
);

ALTER DATABASE board SET TIMEZONE = 'Korea Standard Time';