create sequence score_seq
start with 1000;
create table Score(
	score_seq number(10),
	java number(10) not null ,
	jsp number(10) not null,
	html number(10) not null,
	javascript number(10) not null,
	oracle number(10) not null,
	spring number(10) not null,
	userid varchar2(20) not null,
	constraint score_pk primary key (score_seq),
	constraint score_member_fk foreign key (userid) 
		references Member(userid)
		);
