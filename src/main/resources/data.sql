insert into member (template_type, created_at, modified_at) values ('STUDENT', now(), now());
insert into member (template_type, created_at, modified_at) values ('WORKER', now(), now());

-- 1
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, 'default.jpg', '#FFC5CC', '#FF7184', '학업', 0, null, 1, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, 'default.jpg', '#FFCDA0', '#FFA24F', '성장', 0, null, 1, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, 'default.jpg', '#FFE49F', '#FFC32B', '관계', 0, null, 1, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, 'default.jpg', '#C8F9AA', '#3EE423', '건강', 0, null, 1, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, 'default.jpg', '#B9E2FF', '#54B8FF', '낭비', 0, null, 1, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, 'default.jpg', '#D2C7FF', '#886AFF', '취미', 0, null, 1, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, 'default.jpg', '#ECB8FF', '#D971FF', '기타', 0, null, 1, now(), now());

insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'lecture.jpg', '#FFC5CC', '#FF7184', '강의', 0, (select id from (select id from category where member_id=1 and name='학업') tmp), 1, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'assignment.jpg', '#FFC5CC', '#FF7184', '과제', 0, (select id from (select id from category where member_id=1 and name='학업') tmp), 1, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'group_study.jpg', '#FFC5CC', '#FF7184', '스터디', 0, (select id from (select id from category where member_id=1 and name='학업') tmp), 1, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'group_project.jpg', '#FFC5CC', '#FF7184', '팀플', 0, (select id from (select id from category where member_id=1 and name='학업') tmp), 1, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'exam.jpg', '#FFC5CC', '#FF7184', '시험공부', 0, (select id from (select id from category where member_id=1 and name='학업') tmp), 1, now(), now());

insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'reading.jpg', '#FFCDA0', '#FFA24F', '독서', 0, (select id from (select id from category where member_id=1 and name='성장') tmp), 1, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'lecture.jpg', '#FFCDA0', '#FFA24F', '강의', 0, (select id from (select id from category where member_id=1 and name='성장') tmp), 1, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'certificate.jpg', '#FFCDA0', '#FFA24F', '자격증', 0, (select id from (select id from category where member_id=1 and name='성장') tmp), 1, now(), now());

insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'friend.jpg', '#FFE49F', '#FFC32B', '친구', 0, (select id from (select id from category where member_id=1 and name='관계') tmp), 1, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'family.jpg', '#FFE49F', '#FFC32B', '가족', 0, (select id from (select id from category where member_id=1 and name='관계') tmp), 1, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'dating.jpg', '#FFE49F', '#FFC32B', '연인', 0, (select id from (select id from category where member_id=1 and name='관계') tmp), 1, now(), now());

insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'sleep.jpg', '#C8F9AA', '#3EE423', '잠', 0, (select id from (select id from category where member_id=1 and name='건강') tmp), 1, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'meal.jpg', '#C8F9AA', '#3EE423', '식사', 0, (select id from (select id from category where member_id=1 and name='건강') tmp), 1, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'exercise.jpg', '#C8F9AA', '#3EE423', '운동', 0, (select id from (select id from category where member_id=1 and name='건강') tmp), 1, now(), now());

insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'sns.jpg', '#B9E2FF', '#54B8FF', 'sns', 0, (select id from (select id from category where member_id=1 and name='낭비') tmp), 1, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'internet.jpg', '#B9E2FF', '#54B8FF', '웹서핑', 0, (select id from (select id from category where member_id=1 and name='낭비') tmp), 1, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'media.jpg', '#B9E2FF', '#54B8FF', '미디어', 0, (select id from (select id from category where member_id=1 and name='낭비') tmp), 1, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'wasting.jpg', '#B9E2FF', '#54B8FF', '멍', 0, (select id from (select id from category where member_id=1 and name='낭비') tmp), 1, now(), now());

insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'game.jpg', '#D2C7FF', '#886AFF', '게임', 0, (select id from (select id from category where member_id=1 and name='취미') tmp), 1, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'movie.jpg', '#D2C7FF', '#886AFF', '영화', 0, (select id from (select id from category where member_id=1 and name='취미') tmp), 1, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'music.jpg', '#D2C7FF', '#886AFF', '음악', 0, (select id from (select id from category where member_id=1 and name='취미') tmp), 1, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'instrument.jpg', '#D2C7FF', '#886AFF', '악기', 0, (select id from (select id from category where member_id=1 and name='취미') tmp), 1, now(), now());

insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'cleaning.jpg', '#ECB8FF', '#D971FF', '집안일', 0, (select id from (select id from category where member_id=1 and name='기타') tmp), 1, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'moving.jpg', '#ECB8FF', '#D971FF', '이동시간', 0, (select id from (select id from category where member_id=1 and name='기타') tmp), 1, now(), now());

-- 2
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, 'default.jpg', '#FFC5CC', '#FF7184', '직장', 0, null, 2, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, 'default.jpg', '#FFCDA0', '#FFA24F', '성장', 0, null, 2, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, 'default.jpg', '#FFE49F', '#FFC32B', '관계', 0, null, 2, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, 'default.jpg', '#C8F9AA', '#3EE423', '건강', 0, null, 2, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, 'default.jpg', '#B9E2FF', '#54B8FF', '낭비', 0, null, 2, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, 'default.jpg', '#D2C7FF', '#886AFF', '취미', 0, null, 2, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, 'default.jpg', '#ECB8FF', '#D971FF', '기타', 0, null, 2, now(), now());

insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'work.jpg', '#FFC5CC', '#FF7184', '업무', 0, (select id from (select id from category where member_id=2 and name='직장') tmp), 2, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'overwork.jpg', '#FFC5CC', '#FF7184', '야근', 0, (select id from (select id from category where member_id=2 and name='직장') tmp), 2, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'businesstrip.jpg', '#FFC5CC', '#FF7184', '출장', 0, (select id from (select id from category where member_id=2 and name='직장') tmp), 2, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'companydinner.jpg', '#FFC5CC', '#FF7184', '회식', 0, (select id from (select id from category where member_id=2 and name='직장') tmp), 2, now(), now());

insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'reading.jpg', '#FFCDA0', '#FFA24F', '독서', 0, (select id from (select id from category where member_id=2 and name='성장') tmp), 2, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'lecture.jpg', '#FFCDA0', '#FFA24F', '강의', 0, (select id from (select id from category where member_id=2 and name='성장') tmp), 2, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'certificate.jpg', '#FFCDA0', '#FFA24F', '자격증', 0, (select id from (select id from category where member_id=2 and name='성장') tmp), 2, now(), now());

insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'friend.jpg', '#FFE49F', '#FFC32B', '친구', 0, (select id from (select id from category where member_id=2 and name='관계') tmp), 2, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'family.jpg', '#FFE49F', '#FFC32B', '가족', 0, (select id from (select id from category where member_id=2 and name='관계') tmp), 2, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'dating.jpg', '#FFE49F', '#FFC32B', '연인', 0, (select id from (select id from category where member_id=2 and name='관계') tmp), 2, now(), now());

insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'sleep.jpg', '#C8F9AA', '#3EE423', '잠', 0, (select id from (select id from category where member_id=2 and name='건강') tmp), 2, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'meal.jpg', '#C8F9AA', '#3EE423', '식사', 0, (select id from (select id from category where member_id=2 and name='건강') tmp), 2, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'exercise.jpg', '#C8F9AA', '#3EE423', '운동', 0, (select id from (select id from category where member_id=2 and name='건강') tmp), 2, now(), now());

insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'sns.jpg', '#B9E2FF', '#54B8FF', 'sns', 0, (select id from (select id from category where member_id=2 and name='낭비') tmp), 2, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'internet.jpg', '#B9E2FF', '#54B8FF', '웹서핑', 0, (select id from (select id from category where member_id=2 and name='낭비') tmp), 2, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'media.jpg', '#B9E2FF', '#54B8FF', '미디어', 0, (select id from (select id from category where member_id=2 and name='낭비') tmp), 2, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'wasting.jpg', '#B9E2FF', '#54B8FF', '멍', 0, (select id from (select id from category where member_id=2 and name='낭비') tmp), 2, now(), now());

insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'game.jpg', '#D2C7FF', '#886AFF', '게임', 0, (select id from (select id from category where member_id=2 and name='취미') tmp), 2, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'movie.jpg', '#D2C7FF', '#886AFF', '영화', 0, (select id from (select id from category where member_id=2 and name='취미') tmp), 2, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'music.jpg', '#D2C7FF', '#886AFF', '음악', 0, (select id from (select id from category where member_id=2 and name='취미') tmp), 2, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'instrument.jpg', '#D2C7FF', '#886AFF', '악기', 0, (select id from (select id from category where member_id=2 and name='취미') tmp), 2, now(), now());

insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'cleaning.jpg', '#ECB8FF', '#D971FF', '집안일', 0, (select id from (select id from category where member_id=2 and name='기타') tmp), 2, now(), now());
insert into category(`level`, icon_name, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, 'moving.jpg', '#ECB8FF', '#D971FF', '이동시간', 0, (select id from (select id from category where member_id=2 and name='기타') tmp), 2, now(), now());