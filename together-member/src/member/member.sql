-- 1. tog 유저 만들기
create user tog identified by tog;

-- 2. 테이블 작성 권한주기
grant dba to tog;

-- 3. tog 유저 로그인
conn tog/tog;


-- 4. Member table 생성  
create table Member(
	name varchar2(50),
	phone varchar2(20),
	password varchar2(50),
	email varchar2(50),
	temp01 varchar2(30),
	temp02 varchar2(30),
	temp03 varchar2(30),
	constraint member_pk primary key(phone)
);


-- Friend table 생성, 시퀀스 생성  
create sequence f_seq;

create table Friend(
	f_seq number,
	uphone varchar2(20),
	fphone varchar2(20),
	constraint friend_pk primary key (f_seq),
	constraint friend_member_fk foreign key (uphone) 
		references Member(phone)
);

--Member table 내에 column별 테스트용 parameter 입력
insert into member values(
'이성한',
'01011112222',
'1',
'email@naver.com',
'2',
'3',
'4');


insert into member values(
'순환형',
'01012341234',
'1',
'email@naver.com',
'2',
'3',
'4');

insert into member values(
'준규형',
'01031393139',
'1',
'email@naver.com',
'2',
'3',
'4');


insert into member values(
'인석형',
'01011114444',
'1',
'email@naver.com',
'2',
'3',
'4');

insert into member values(
'한',
'01011115555',
'1',
'han@naver.com',
'2',
'3',
'4');

insert into member values(
'오형',
'01011116666',
'1',
'email@naver.com',
'2',
'3',
'4');

insert into member values(
'혁오형',
'01011117777',
'1',
'email@naver.com',
'2',
'3',
'4');

insert into member values(
'아이유',
'01011118888',
'1',
'seong@naver.com',
'2',
'3',
'4');

insert into member values(
'태연',
'01011119999',
'1',
'email@naver.com',
'2',
'3',
'4');

insert into member values(
'유인나',
'010111110000',
'pass',
'email@naver.com',
'2',
'3',
'4');



--sequence 생성
create sequence f_seq;



--Friend table 내에 column별 테스트용 parameter 입력
insert into friend values(
f_seq.nextval,
'01031393139',
'01012341234');

insert into friend values(
f_seq.nextval,
'01031393139',
'01011112222');

insert into friend values(
f_seq.nextval,
'01011112222',
'01031393139');

insert into friend values(
f_seq.nextval,
'01011112222',
'01012341234');

insert into friend values(
f_seq.nextval,
'01012341234',
'01031393139');

insert into friend values(
f_seq.nextval,
'01012341234',
'01011112222');



--Friend table과 Member table을 join시켜서 사용자의 친구로 등록된 사람의 폰번호를 가져와서 
--Member table에서 그 폰번호에 해당하는 이름과 이메일을 가져오는 쿼리문 //친구추가 쿼리문
select name, email from member 
where phone in  (
select fphone from member m inner join friend f
on m.phone = f.uphone
where m.phone = 01031393139);

select name, email from member 
where phone in  (
select fphone from member m inner join friend f
on m.phone = f.uphone
where m.phone = 01011112222);


-- 토드에서 전체회원 조회
select * from member;
select * from friend;



