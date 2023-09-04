insert into member (template_type, created_at, modified_at) values ('STUDENT', now(), now());
insert into member (template_type, created_at, modified_at) values ('WORKER', now(), now());

-- 1
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, '#FFC5CC', '학업', 0, null, 1, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, '#FFCDA0', '성장', 0, null, 1, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, '#FFE49F', '관계', 0, null, 1, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, '#B5F9AA', '건강', 0, null, 1, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, '#B9E2FF', '낭비', 0, null, 1, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, '#D2C7FF', '취미', 0, null, 1, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, '#ECB8FF', '기타', 0, null, 1, now(), now());

insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#FFC5CC', '강의', 0, (select id from (select id from category where member_id=1 and name='학업') tmp), 1, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#FFC5CC', '과제', 0, (select id from (select id from category where member_id=1 and name='학업') tmp), 1, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#FFC5CC', '스터디', 0, (select id from (select id from category where member_id=1 and name='학업') tmp), 1, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#FFC5CC', '팀플', 0, (select id from (select id from category where member_id=1 and name='학업') tmp), 1, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#FFC5CC', '시험공부', 0, (select id from (select id from category where member_id=1 and name='학업') tmp), 1, now(), now());

insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#FFCDA0', '독서', 0, (select id from (select id from category where member_id=1 and name='성장') tmp), 1, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#FFCDA0', '강의', 0, (select id from (select id from category where member_id=1 and name='성장') tmp), 1, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#FFCDA0', '자격증', 0, (select id from (select id from category where member_id=1 and name='성장') tmp), 1, now(), now());

insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#FFE49F', '친구', 0, (select id from (select id from category where member_id=1 and name='관계') tmp), 1, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#FFE49F', '가족', 0, (select id from (select id from category where member_id=1 and name='관계') tmp), 1, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#FFE49F', '연인', 0, (select id from (select id from category where member_id=1 and name='관계') tmp), 1, now(), now());

insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#B5F9AA', '잠', 0, (select id from (select id from category where member_id=1 and name='건강') tmp), 1, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#B5F9AA', '식사', 0, (select id from (select id from category where member_id=1 and name='건강') tmp), 1, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#B5F9AA', '운동', 0, (select id from (select id from category where member_id=1 and name='건강') tmp), 1, now(), now());

insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#B9E2FF', 'sns', 0, (select id from (select id from category where member_id=1 and name='낭비') tmp), 1, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#B9E2FF', '웹서핑', 0, (select id from (select id from category where member_id=1 and name='낭비') tmp), 1, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#B9E2FF', '미디어', 0, (select id from (select id from category where member_id=1 and name='낭비') tmp), 1, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#B9E2FF', '멍', 0, (select id from (select id from category where member_id=1 and name='낭비') tmp), 1, now(), now());

insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#D2C7FF', '게임', 0, (select id from (select id from category where member_id=1 and name='취미') tmp), 1, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#D2C7FF', '영화', 0, (select id from (select id from category where member_id=1 and name='취미') tmp), 1, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#D2C7FF', '음악', 0, (select id from (select id from category where member_id=1 and name='취미') tmp), 1, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#D2C7FF', '악기', 0, (select id from (select id from category where member_id=1 and name='취미') tmp), 1, now(), now());

insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#ECB8FF', '집안일', 0, (select id from (select id from category where member_id=1 and name='기타') tmp), 1, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#ECB8FF', '이동시간', 0, (select id from (select id from category where member_id=1 and name='기타') tmp), 1, now(), now());

-- 2
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, '#FFC5CC', '직장', 0, null, 2, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, '#FFCDA0', '성장', 0, null, 2, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, '#FFE49F', '관계', 0, null, 2, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, '#B5F9AA', '건강', 0, null, 2, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, '#B9E2FF', '낭비', 0, null, 2, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, '#D2C7FF', '취미', 0, null, 2, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, '#ECB8FF', '기타', 0, null, 2, now(), now());

insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#FFC5CC', '업무', 0, (select id from (select id from category where member_id=2 and name='직장') tmp), 2, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#FFC5CC', '야근', 0, (select id from (select id from category where member_id=2 and name='직장') tmp), 2, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#FFC5CC', '출장', 0, (select id from (select id from category where member_id=2 and name='직장') tmp), 2, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#FFC5CC', '회식', 0, (select id from (select id from category where member_id=2 and name='직장') tmp), 2, now(), now());

insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#FFCDA0', '독서', 0, (select id from (select id from category where member_id=2 and name='성장') tmp), 2, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#FFCDA0', '강의', 0, (select id from (select id from category where member_id=2 and name='성장') tmp), 2, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#FFCDA0', '자격증', 0, (select id from (select id from category where member_id=2 and name='성장') tmp), 2, now(), now());

insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#FFE49F', '친구', 0, (select id from (select id from category where member_id=2 and name='관계') tmp), 2, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#FFE49F', '가족', 0, (select id from (select id from category where member_id=2 and name='관계') tmp), 2, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#FFE49F', '연인', 0, (select id from (select id from category where member_id=2 and name='관계') tmp), 2, now(), now());

insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#B5F9AA', '잠', 0, (select id from (select id from category where member_id=2 and name='건강') tmp), 2, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#B5F9AA', '식사', 0, (select id from (select id from category where member_id=2 and name='건강') tmp), 2, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#B5F9AA', '운동', 0, (select id from (select id from category where member_id=2 and name='건강') tmp), 2, now(), now());

insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#B9E2FF', 'sns', 0, (select id from (select id from category where member_id=2 and name='낭비') tmp), 2, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#B9E2FF', '웹서핑', 0, (select id from (select id from category where member_id=2 and name='낭비') tmp), 2, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#B9E2FF', '미디어', 0, (select id from (select id from category where member_id=2 and name='낭비') tmp), 2, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#B9E2FF', '멍', 0, (select id from (select id from category where member_id=2 and name='낭비') tmp), 2, now(), now());

insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#D2C7FF', '게임', 0, (select id from (select id from category where member_id=2 and name='취미') tmp), 2, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#D2C7FF', '영화', 0, (select id from (select id from category where member_id=2 and name='취미') tmp), 2, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#D2C7FF', '음악', 0, (select id from (select id from category where member_id=2 and name='취미') tmp), 2, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#D2C7FF', '악기', 0, (select id from (select id from category where member_id=2 and name='취미') tmp), 2, now(), now());

insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#ECB8FF', '집안일', 0, (select id from (select id from category where member_id=2 and name='기타') tmp), 2, now(), now());
insert into category(`level`, color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, '#ECB8FF', '이동시간', 0, (select id from (select id from category where member_id=2 and name='기타') tmp), 2, now(), now());