create table if not exists items(
                     id serial primary key,
                     description text,
                     created timestamp,
                     done boolean
);

create table if not exists account(
                                    id serial primary key,
                                    name varchar(255),
                                    login  varchar(255) UNIQUE,
                                    password varchar(255)
);

