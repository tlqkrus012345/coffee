create schema if not exists testDB;

create table if not exists member (
        member_id bigint not null auto_increment,
        point integer not null,
        version bigint,
        primary key (member_id)
);

create table if not exists menu (
        cnt integer not null,
        price integer not null,
        cafe_id bigint not null auto_increment,
        name varchar(255),
        primary key (cafe_id)
);

create table if not exists orders (
        order_id bigint not null auto_increment,
        is_pay_success boolean,
        price integer not null,
        created_at timestamp(6),
        member_id bigint,
        menu_id bigint,
        menu_name varchar(255),
        primary key (order_id)
);

create table if not exists payment (
        id bigint not null auto_increment,
        order_id bigint unique,
        primary key (id)
);
