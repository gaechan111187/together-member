create table Member(
	userid varchar2(50),
	password varchar2(50),
	name varchar2(50),
	birth varchar2(50),
	phone varchar2(50),
	email varchar2(50),
	gender varchar2(50),
	addr varchar2(50),
	regdate varchar2(50),
	profile varchar2(50),
);

alter table Member
add constraint member_pk primary key(userid);