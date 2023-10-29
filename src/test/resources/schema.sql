create table if not exists Weather (
                                         id SERIAL not null primary key,
                                         name varchar not null,
                                         region varchar not null,
                                         country varchar not null,
                                         tempC int,
                                         tempF int
);

insert into Weather (name, region, country, tempC, tempF) values ('TestCity', 'TestRegion', 'TestCountry', 0, 0);
insert into Weather (name, region, country, tempC, tempF) values ('TestCity2', 'TestRegion', 'TestCountry', 0, 0);