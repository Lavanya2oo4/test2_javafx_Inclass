create database appointment;
use appointment;
create table members(
id int auto_increment primary key,
memberName varchar(50) not null,
memberEmail varchar(100) unique not null,
password varchar(50) not null);

insert into members values
(1,"Angie","angie@yahoo.com","1234");

select * from members;

create table appointments(
id int auto_increment primary key,
patientName varchar(50) not null,
appointmentDate date not null,
doctor varchar(50) not null);
