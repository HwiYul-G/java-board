create table user(
    id bigint not null,
    email varchar(45) not null primary key,
    name varchar(255),
    nickname varchar(255),
    password varchar(255),
    profile_image BLOB
);