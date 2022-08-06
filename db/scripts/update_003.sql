create table if not exists category(
                                    id serial primary key,
                                    name varchar(255)
);

create table if not exists items_category(
                                       items_id int references items(id),
                                       category_id int references category(id)
);