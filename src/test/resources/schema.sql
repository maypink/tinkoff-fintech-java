create table if not exists Weather (
                                         id SERIAL not null primary key,
                                         name varchar not null,
                                         region varchar not null,
                                         country varchar not null,
                                         temp_c int,
                                         temp_f int,
                                         date_time varchar
);

insert into Weather (name, region, country, temp_c, temp_f, date_time) values ('TestCity', 'TestRegion', 'TestCountry', 0, 0, '2023-06-09 23:34');
insert into Weather (name, region, country, temp_c, temp_f, date_time) values ('TestCity2', 'TestRegion', 'TestCountry', 0, 0, '2023-06-09 23:35');