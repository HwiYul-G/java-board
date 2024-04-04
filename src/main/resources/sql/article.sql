create table article(
    id bigint not null,
    title varchar(255),
    content varchar(255),
    writer varchar(255),
    createdAt datetime(6),
    updatedAt datetime(6),
    primary key (id)
);