Alter table items add column account_id int references account(id);
update items set account_id = 1;
alter table items alter column account_id set not null;

