create table if not exists Weather
(
     id SERIAL not null primary key,
     name varchar not null,
     region varchar not null,
     country varchar not null,
     tempC int,
     tempF int
);

create table if not exists users
(
    username varchar(50) not null primary key,
    password varchar(2048) not null
);

create table if not exists authorities
(
    username  varchar(50) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users foreign key (username) references users (username)
);
--create unique index ix_auth_username on authorities (username, authority);