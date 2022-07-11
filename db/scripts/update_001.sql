create table if not exists items(
                     id serial primary key,
                     description text,
                     created timestamp,
                     done boolean
);

insert into items(description, created, done) values ('Большое дело', '01.02.03 12:00', FALSE);
