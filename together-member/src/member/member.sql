--Member table 생성  
create table Member(
	name varchar2(50),
	password varchar2(50),
	phone varchar2(20),
	email varchar2(50),
	temp01 varchar2(30),
	temp02 varchar2(30),
	temp03 varchar2(30),
	constraint member_pk primary key(phone)
); 

--Friend table 생성  
create sequence f_seq;
create table Friend(
	f_seq number,
	uphone varchar2(20),
	fphone varchar2(20),
	constraint friend_pk primary key (f_seq),
	constraint score_member_fk foreign key (uphone) 
		references Member(phone)
);

--Member table 내에 column별 테스트용 parameter 입력
insert into member values(
'kim',
'1',
'012',
'k@k.com',
'2',
'3',
'4');


insert into member values(
'lee',
'1',
'123',
'l@l.com',
'2',
'3',
'4');

insert into member values(
'pak',
'1',
'234',
'p@p.com',
'2',
'3',
'4');

--sequence 생성
create sequence f_seq;

--Friend table 내에 column별 테스트용 parameter 입력
insert into friend values(
f_seq.nextval,
'012',
'123');

insert into friend values(
f_seq.nextval,
'012',
'234');

insert into friend values(
f_seq.nextval,
'123',
'234');

insert into friend values(
f_seq.nextval,
'234',
'123');

--Friend table과 Member table을 join시켜서 사용자의 친구로 등록된 사람의 폰번호를 가져와서 
--Member table에서 그 폰번호에 해당하는 이름과 이메일을 가져오는 쿼리문
select name, email from member 
where phone in  (
select fphone from member m inner join friend f
on m.phone = f.uphone
where m.phone = 012);

