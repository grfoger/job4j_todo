Alter table items add column account_id int not null references account(id) default 1;