create database koombea_scraper;


--USER TABLE
create table users (
id int not null generated always as identity,
username varchar(50) not null unique,
password varchar not null,

primary key (id)
);


-- Web page TABLE
create table web_page(
id int not null generated always as identity,
user_id int not null,
name varchar not null,
url varchar not null,

primary key (id),
constraint fk_user foreign key(user_id) references users(id)
);

-- LINKS TBALE
create table link(
id int not null generated always as identity,
body varchar(200) not null,
url varchar not null,
web_page int not null,


primary key (id),
constraint fk_web_page foreign key(web_page) references web_page(id)
);










