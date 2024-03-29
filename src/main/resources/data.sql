drop table if exists roles, user_312, user_roles;

create table if not exists user_312
(
    id         bigint primary key auto_increment,
    first_name   varchar(50)  not null,
    last_name   varchar(50)  not null,
    age   int(3)  not null,
    email   varchar(50)  not null,
    password   varchar(255) not null
);

create table if not exists roles
(
    id        bigint primary key auto_increment,
    role_name varchar(50) not null
);

create table if not exists user_roles
(
    user_id bigint,
    foreign key (user_id) references user_312 (id),
    role_id bigint,
    foreign key (user_id) references user_312 (id),
    primary key (user_id, role_id)
);

insert into user_312 (first_name, last_name, age, email, password)
values ('admin','admin', '20', 'admin@mail.ru', '$2y$10$gL55G6i9Oq.cii1/wVL1g.iGxglm5HsfGI9B5p.PmmdusTVDoWSiq');

insert into user_312 (first_name, last_name, age, email, password)
values ('user', 'user','20', 'user@mail.ru', '$2y$10$F15vn213tGuaj2wxZevqHumymATjv36BV1BHQ5dm.D/A7qLCaMb7u');

insert into roles (role_name)
values ('ROLE_ADMIN'),
       ('ROLE_USER');

insert into user_roles (user_id, role_id)
values ((select id from user_312 where first_name = 'admin'),
        (select id from roles where role_name = 'ROLE_ADMIN'));

insert into user_roles (user_id, role_id)
values ((select id from user_312 where first_name = 'admin'),
        (select id from roles where role_name = 'ROLE_USER'));

insert into user_roles (user_id, role_id)
values ((select id from user_312 where first_name = 'user'),
        (select id from roles where role_name = 'ROLE_USER'));

