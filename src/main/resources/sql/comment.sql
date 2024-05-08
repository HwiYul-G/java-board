create table comment(
    id bigint not null primary key,
    content varchar(255),
    created_at timestamp(6),
    updated_at timestamp(6),
    writer varchar(255),
    article_id bigint,

    constraint article_id_ref foreign key (article_id) references article(id) on update cascade
);