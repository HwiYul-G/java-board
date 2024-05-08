create table article(
    id bigint not null,
    content varchar(255),
    created_at timestamp(6),
    title varchar(255),
    updated_at timestamp(6),
    writer varchar(255),
    primary key (id)
);

