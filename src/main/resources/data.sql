insert into member (template_type, created_at, modified_at) values ('STUDENT', now(), now());
insert into member (template_type, created_at, modified_at) values ('WORKER', now(), now());

-- 1
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at) values (0, 'test', '학업', 'N', null, 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at) values (0, 'test', '성장', 'N', null, 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at) values (0, 'test', '관계', 'N', null, 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at) values (0, 'test', '건강', 'N', null, 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at) values (0, 'test2', '낭비', 'N', null, 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at) values (0, 'test2', '취미', 'N', null, 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at) values (0, 'test2', '기타', 'N', null, 1, now(), now());

insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '강의', 'N', (select id from (select id from category where member_id=1 and name='학업') tmp), 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '과제', 'N', (select id from (select id from category where member_id=1 and name='학업') tmp), 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '스터디', 'N', (select id from (select id from category where member_id=1 and name='학업') tmp), 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '팀플', 'N', (select id from (select id from category where member_id=1 and name='학업') tmp), 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '시험공부', 'N', (select id from (select id from category where member_id=1 and name='학업') tmp), 1, now(), now());

insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '독서', 'N', (select id from (select id from category where member_id=1 and name='성장') tmp), 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '강의', 'N', (select id from (select id from category where member_id=1 and name='성장') tmp), 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '자격증', 'N', (select id from (select id from category where member_id=1 and name='성장') tmp), 1, now(), now());

insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '친구', 'N', (select id from (select id from category where member_id=1 and name='관계') tmp), 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '가족', 'N', (select id from (select id from category where member_id=1 and name='관계') tmp), 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '연인', 'N', (select id from (select id from category where member_id=1 and name='관계') tmp), 1, now(), now());

insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '잠', 'N', (select id from (select id from category where member_id=1 and name='건강') tmp), 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '식사', 'N', (select id from (select id from category where member_id=1 and name='건강') tmp), 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '운동', 'N', (select id from (select id from category where member_id=1 and name='건강') tmp), 1, now(), now());

insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', 'sns', 'N', (select id from (select id from category where member_id=1 and name='낭비') tmp), 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '웹서핑', 'N', (select id from (select id from category where member_id=1 and name='낭비') tmp), 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '미디어', 'N', (select id from (select id from category where member_id=1 and name='낭비') tmp), 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '멍', 'N', (select id from (select id from category where member_id=1 and name='낭비') tmp), 1, now(), now());

insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '게임', 'N', (select id from (select id from category where member_id=1 and name='취미') tmp), 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '영화', 'N', (select id from (select id from category where member_id=1 and name='취미') tmp), 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '음악', 'N', (select id from (select id from category where member_id=1 and name='취미') tmp), 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '악기', 'N', (select id from (select id from category where member_id=1 and name='취미') tmp), 1, now(), now());

insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '집안일', 'N', (select id from (select id from category where member_id=1 and name='기타') tmp), 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '이동시간', 'N', (select id from (select id from category where member_id=1 and name='기타') tmp), 1, now(), now());

-- 2
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at) values (0, 'test', '직장', 'N', null, 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at) values (0, 'test', '성장', 'N', null, 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at) values (0, 'test', '관계', 'N', null, 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at) values (0, 'test', '건강', 'N', null, 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at) values (0, 'test2', '낭비', 'N', null, 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at) values (0, 'test2', '취미', 'N', null, 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at) values (0, 'test2', '기타', 'N', null, 2, now(), now());

insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '업무', 'N', (select id from (select id from category where member_id=2 and name='직장') tmp), 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '야근', 'N', (select id from (select id from category where member_id=2 and name='직장') tmp), 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '출장', 'N', (select id from (select id from category where member_id=2 and name='직장') tmp), 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '회식', 'N', (select id from (select id from category where member_id=2 and name='직장') tmp), 2, now(), now());

insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '독서', 'N', (select id from (select id from category where member_id=2 and name='성장') tmp), 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '강의', 'N', (select id from (select id from category where member_id=2 and name='성장') tmp), 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '자격증', 'N', (select id from (select id from category where member_id=2 and name='성장') tmp), 2, now(), now());

insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '친구', 'N', (select id from (select id from category where member_id=2 and name='관계') tmp), 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '가족', 'N', (select id from (select id from category where member_id=2 and name='관계') tmp), 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '연인', 'N', (select id from (select id from category where member_id=2 and name='관계') tmp), 2, now(), now());

insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '잠', 'N', (select id from (select id from category where member_id=2 and name='건강') tmp), 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '식사', 'N', (select id from (select id from category where member_id=2 and name='건강') tmp), 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '운동', 'N', (select id from (select id from category where member_id=2 and name='건강') tmp), 2, now(), now());

insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', 'sns', 'N', (select id from (select id from category where member_id=2 and name='낭비') tmp), 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '웹서핑', 'N', (select id from (select id from category where member_id=2 and name='낭비') tmp), 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '미디어', 'N', (select id from (select id from category where member_id=2 and name='낭비') tmp), 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '멍', 'N', (select id from (select id from category where member_id=2 and name='낭비') tmp), 2, now(), now());

insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '게임', 'N', (select id from (select id from category where member_id=2 and name='취미') tmp), 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '영화', 'N', (select id from (select id from category where member_id=2 and name='취미') tmp), 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '음악', 'N', (select id from (select id from category where member_id=2 and name='취미') tmp), 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '악기', 'N', (select id from (select id from category where member_id=2 and name='취미') tmp), 2, now(), now());

insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '집안일', 'N', (select id from (select id from category where member_id=2 and name='기타') tmp), 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, 'test', '이동시간', 'N', (select id from (select id from category where member_id=2 and name='기타') tmp), 2, now(), now());
