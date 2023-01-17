create table votes
(
    id         bigserial         not null,
    post_id    bigint            not null,
    up_votes   numeric default 0 not null,
    down_votes numeric default 0 not null,
    primary key (id),
    UNIQUE (post_id)
);
