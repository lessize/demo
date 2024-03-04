-- 테이블 삭제
drop table product;

-- 시퀀스 삭제
drop sequence product_product_id_seq;

---------
--상품관리
--------
create table product(
    product_id  number(10),
    pname       varchar(30),
    quantity    number(10),
    price       number(10)
);
--기본키
alter table product add constraint product_product_id_pk primary key(product_id);

--시퀀스생성
create sequence product_product_id_seq;

-- default
alter table product modify cdate default systimestamp; --운영체제 일시를 기본값으로 설정
alter table product modify udate default systimestamp; --운영체제 일시를 기본값으로 설정

-- not null
alter table product modify quantity not null;
alter table product modify price not null;

--생성--
insert into product(product_id,pname,quantity,price)
     values(product_product_id_seq.nextval, '컴퓨터', 5, 1000000);

insert into product(product_id,pname,quantity,price)
     values(product_product_id_seq.nextval, '모니터', 5, 500000);

insert into product(product_id,pname,quantity,price)
     values(product_product_id_seq.nextval, '프린터', 1, 300000);

commit;

-- 테이블 삭제
drop table member;

-- 테이블 생성
create table member (
    member_id number(10), -- 내부관리
    email     varchar2(50), -- 로그인 아이디
    passwd    varchar2(12), -- 로그인 비밀번호
    nickname  varchar2(30), -- 별칭
    gubun     varchar2(11) default 'M0101', -- 회원구분 (일반, 우수, 관리자1, 관리자2,,,,)
    pic       blob,        -- 사진
    cdate     timestamp,   -- 생성일 (가입일)
    udate     timestamp    -- 수정일
);


-- 기본키 생성
alter table member add constraint member_member_id_pk primary key (member_id);

-- unique
alter table member modify email constraint member_email_uk unique;

-- default
alter table member modify gubun default 'M0101';
alter table member modify cdate default systimestamp;
alter table member modify udate default systimestamp;

-- 시퀀스
drop sequence member_member_id_seq;
create sequence member_member_id_seq;

-- 샘플데이터
insert into member (member_id, email, passwd, nickname)
    values(member_member_id_seq.nextval, 'user1@kh.com', 'user1', '사용자1');
insert into member (member_id, email, passwd, nickname)
    values(member_member_id_seq.nextval, 'user2@kh.com', 'user2', '사용자2');

commit;

