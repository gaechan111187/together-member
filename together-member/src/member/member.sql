create table Member(
	name varchar2(50),
	password varchar2(50),
	phone number(20),
	email varchar2(50),
	temp01 varchar2(30),
	temp02 varchar2(30),
	temp03 varchar2(30)
); 

alter table Member
add constraint member_pk primary key(phone);