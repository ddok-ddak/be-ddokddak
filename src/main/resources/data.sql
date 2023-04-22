insert into member (template_type, created_at, modified_at) values ('STUDENT', now(), now());
insert into member (template_type, created_at, modified_at) values ('WORKER', now(), now());

-- 1
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at) values (0, '#FFC5CC', '학업', 'N', null, 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at) values (0, '#FFCDA0', '성장', 'N', null, 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at) values (0, '#FFE49F', '관계', 'N', null, 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at) values (0, '#B5F9AA', '건강', 'N', null, 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at) values (0, '#B9E2FF', '낭비', 'N', null, 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at) values (0, '#D2C7FF', '취미', 'N', null, 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at) values (0, '#ECB8FF', '기타', 'N', null, 1, now(), now());

insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#FFC5CC', '강의', 'N', (select id from (select id from category where member_id=1 and name='학업') tmp), 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#FFC5CC', '과제', 'N', (select id from (select id from category where member_id=1 and name='학업') tmp), 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#FFC5CC', '스터디', 'N', (select id from (select id from category where member_id=1 and name='학업') tmp), 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#FFC5CC', '팀플', 'N', (select id from (select id from category where member_id=1 and name='학업') tmp), 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#FFC5CC', '시험공부', 'N', (select id from (select id from category where member_id=1 and name='학업') tmp), 1, now(), now());

insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#FFCDA0', '독서', 'N', (select id from (select id from category where member_id=1 and name='성장') tmp), 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#FFCDA0', '강의', 'N', (select id from (select id from category where member_id=1 and name='성장') tmp), 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#FFCDA0', '자격증', 'N', (select id from (select id from category where member_id=1 and name='성장') tmp), 1, now(), now());

insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#FFE49F', '친구', 'N', (select id from (select id from category where member_id=1 and name='관계') tmp), 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#FFE49F', '가족', 'N', (select id from (select id from category where member_id=1 and name='관계') tmp), 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#FFE49F', '연인', 'N', (select id from (select id from category where member_id=1 and name='관계') tmp), 1, now(), now());

insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#B5F9AA', '잠', 'N', (select id from (select id from category where member_id=1 and name='건강') tmp), 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#B5F9AA', '식사', 'N', (select id from (select id from category where member_id=1 and name='건강') tmp), 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#B5F9AA', '운동', 'N', (select id from (select id from category where member_id=1 and name='건강') tmp), 1, now(), now());

insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#B9E2FF', 'sns', 'N', (select id from (select id from category where member_id=1 and name='낭비') tmp), 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#B9E2FF', '웹서핑', 'N', (select id from (select id from category where member_id=1 and name='낭비') tmp), 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#B9E2FF', '미디어', 'N', (select id from (select id from category where member_id=1 and name='낭비') tmp), 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#B9E2FF', '멍', 'N', (select id from (select id from category where member_id=1 and name='낭비') tmp), 1, now(), now());

insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#D2C7FF', '게임', 'N', (select id from (select id from category where member_id=1 and name='취미') tmp), 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#D2C7FF', '영화', 'N', (select id from (select id from category where member_id=1 and name='취미') tmp), 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#D2C7FF', '음악', 'N', (select id from (select id from category where member_id=1 and name='취미') tmp), 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#D2C7FF', '악기', 'N', (select id from (select id from category where member_id=1 and name='취미') tmp), 1, now(), now());

insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#ECB8FF', '집안일', 'N', (select id from (select id from category where member_id=1 and name='기타') tmp), 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#ECB8FF', '이동시간', 'N', (select id from (select id from category where member_id=1 and name='기타') tmp), 1, now(), now());

-- 2
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at) values (0, '#FFC5CC', '직장', 'N', null, 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at) values (0, '#FFCDA0', '성장', 'N', null, 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at) values (0, '#FFE49F', '관계', 'N', null, 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at) values (0, '#B5F9AA', '건강', 'N', null, 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at) values (0, '#B9E2FF', '낭비', 'N', null, 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at) values (0, '#D2C7FF', '취미', 'N', null, 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at) values (0, '#ECB8FF', '기타', 'N', null, 2, now(), now());

insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#FFC5CC', '업무', 'N', (select id from (select id from category where member_id=2 and name='직장') tmp), 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#FFC5CC', '야근', 'N', (select id from (select id from category where member_id=2 and name='직장') tmp), 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#FFC5CC', '출장', 'N', (select id from (select id from category where member_id=2 and name='직장') tmp), 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#FFC5CC', '회식', 'N', (select id from (select id from category where member_id=2 and name='직장') tmp), 2, now(), now());

insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#FFCDA0', '독서', 'N', (select id from (select id from category where member_id=2 and name='성장') tmp), 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#FFCDA0', '강의', 'N', (select id from (select id from category where member_id=2 and name='성장') tmp), 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#FFCDA0', '자격증', 'N', (select id from (select id from category where member_id=2 and name='성장') tmp), 2, now(), now());

insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#FFE49F', '친구', 'N', (select id from (select id from category where member_id=2 and name='관계') tmp), 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#FFE49F', '가족', 'N', (select id from (select id from category where member_id=2 and name='관계') tmp), 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#FFE49F', '연인', 'N', (select id from (select id from category where member_id=2 and name='관계') tmp), 2, now(), now());

insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#B5F9AA', '잠', 'N', (select id from (select id from category where member_id=2 and name='건강') tmp), 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#B5F9AA', '식사', 'N', (select id from (select id from category where member_id=2 and name='건강') tmp), 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#B5F9AA', '운동', 'N', (select id from (select id from category where member_id=2 and name='건강') tmp), 2, now(), now());

insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#B9E2FF', 'sns', 'N', (select id from (select id from category where member_id=2 and name='낭비') tmp), 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#B9E2FF', '웹서핑', 'N', (select id from (select id from category where member_id=2 and name='낭비') tmp), 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#B9E2FF', '미디어', 'N', (select id from (select id from category where member_id=2 and name='낭비') tmp), 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#B9E2FF', '멍', 'N', (select id from (select id from category where member_id=2 and name='낭비') tmp), 2, now(), now());

insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#D2C7FF', '게임', 'N', (select id from (select id from category where member_id=2 and name='취미') tmp), 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#D2C7FF', '영화', 'N', (select id from (select id from category where member_id=2 and name='취미') tmp), 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#D2C7FF', '음악', 'N', (select id from (select id from category where member_id=2 and name='취미') tmp), 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#D2C7FF', '악기', 'N', (select id from (select id from category where member_id=2 and name='취미') tmp), 2, now(), now());

insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#ECB8FF', '집안일', 'N', (select id from (select id from category where member_id=2 and name='기타') tmp), 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at)
values (1, '#ECB8FF', '이동시간', 'N', (select id from (select id from category where member_id=2 and name='기타') tmp), 2, now(), now());
