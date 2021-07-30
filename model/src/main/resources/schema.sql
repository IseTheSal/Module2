create table gift_certificates
(
    id               bigserial            not null
        constraint gift_certificate_pk
            primary key,
    name             varchar(30)          not null,
    description      varchar(100)         not null,
    price            numeric              not null,
    duration         integer              not null,
    for_sale         boolean default true not null,
    create_date      timestamp            not null,
    last_update_date timestamp            not null
);

create
    unique index gift_certificate_id_uindex
    on gift_certificates (id);

create table tags
(
    id               bigserial   not null
        constraint tag_pk
            primary key,
    name             varchar(30) not null,
    create_date      timestamp   not null,
    last_update_date timestamp   not null
);

create table certificate_tag
(
    certificate_id bigint not null
        constraint certificate_fk
            references gift_certificates
            on update cascade on delete cascade,
    tag_id         bigint not null
        constraint tag_fk
            references tags
            on update cascade on delete cascade
);

create table roles
(
    id   bigserial   not null
        constraint roles_pk
            primary key,
    name varchar(15) not null
);

create unique index roles_id_uindex
    on roles (id);

create unique index roles_name_uindex
    on roles (name);

create table users
(
    id               bigserial   not null
        constraint users_pk
            primary key,
    login            varchar(40) not null,
    password         varchar(60) not null,
    first_name       varchar(30),
    last_name        varchar(30),
    create_date      timestamp   not null,
    last_update_date timestamp   not null,
    role_id          bigint      not null
        constraint role_id_fk
            references roles
            on update cascade on delete cascade
);

create unique index users_id_uindex
    on users (id);

create unique index users_login_uindex
    on users (login);


create table orders
(
    user_id          bigint    not null
        constraint user_id_fk
            references users
            on update cascade on delete cascade,
    certificate_id   bigint    not null
        constraint certificate_id_fk
            references gift_certificates
            on update cascade on delete cascade,
    price            numeric   not null,
    purchase_date    timestamp not null,
    id               bigserial not null
        constraint orders_pk
            primary key,
    create_date      timestamp not null,
    last_update_date timestamp not null
);

create unique index orders_order_id_uindex
    on orders (id);