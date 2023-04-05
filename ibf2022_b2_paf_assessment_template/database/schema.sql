use railway;

create table user(
user_id varchar(8) not null,
username varchar(150),
name varchar(150),
constraint user_pk primary key(username)
);

create table task(
task_id varchar(20) not null,
description varchar(255),
priority int,
due_date date,
username varchar(150),
constraint tasks_pk primary key (task_id),
constraint tasks_fk_username foreign key(username) references user (username)
);