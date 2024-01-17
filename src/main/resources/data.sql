insert into member (template_type, created_at, modified_at) values ('STUDENT', now(), now());
insert into member (template_type, created_at, modified_at) values ('WORKER', now(), now());

-- category_icon 기본
insert into category_icon (icon_group, filename, path, original_filename)
values
    ('base', 'default.svg', '/', 'default.svg'),
    ('base', 'reading.svg', '/', 'reading.svg'),
    ('base', 'lecture.svg', '/', 'lecture.svg'),
    ('base', 'certification.svg', '/', 'certification.svg'),
    ('base', 'friend.svg', '/', 'friend.svg'),
    ('base', 'family.svg', '/', 'family.svg'),
    ('base', 'dating.svg', '/', 'dating.svg'),
    ('base', 'sleep.svg', '/', 'sleep.svg'),
    ('base', 'meal.svg', '/', 'meal.svg'),
    ('base', 'exercise.svg', '/', 'exercise.svg'),
    ('base', 'wasting.svg', '/', 'wasting.svg'),
    ('base', 'sns.svg', '/', 'sns.svg'),
    ('base', 'internet.svg', '/', 'internet.svg'),
    ('base', 'media.svg', '/', 'media.svg'),
    ('base', 'game.svg', '/', 'game.svg'),
    ('base', 'movie.svg', '/', 'movie.svg'),
    ('base', 'music.svg', '/', 'music.svg'),
    ('base', 'instrument.svg', '/', 'instrument.svg'),
    ('base', 'cleaning.sv', '/', 'cleaning.svg'),
    ('base', 'moving.svg', '/', 'moving.svg'),
    ('base', 'work.svg', '/', 'work.svg'),
    ('base', 'overwork.svg', '/', 'overwork.svg'),
    ('base', 'businesstrip.svg', '/', 'businesstrip.svg'),
    ('base', 'companydinner.svg', '/', 'companydinner.svg'),
    ('base', 'assignment.svg', '/', 'assignment.svg'),
    ('base', 'study.svg', '/', 'study.svg'),
    ('base', 'groupstudy.svg', '/', 'groupstudy.svg'),
    ('base', 'growth.svg', '/', 'growth.svg'),
    ('base', 'relationship.svg', '/', 'relationship.svg'),
    ('base', 'health.svg', '/', 'health.svg'),
    ('base', 'hobby.svg', '/', 'hobby.svg'),
    ('base', 'job.svg', '/', 'job.svg'),
    ('base', 'groupwork.svg', '/', 'groupwork.svg'),
    ('base', 'studyforexam.svg', '/', 'studyforexam.svg');

-- 1 category_template 학생
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, (select id from category_icon where icon_group = 'base' and filename='study.svg'), '#FFC5CC', '#FF7184', '학업', 0, null, 1, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, (select id from category_icon where icon_group = 'base' and filename='growth.svg'), '#FFCDA0', '#FFA24F', '성장', 0, null, 1, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, (select id from category_icon where icon_group = 'base' and filename='relationship.svg'), '#FFE49F', '#FFC32B', '관계', 0, null, 1, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, (select id from category_icon where icon_group = 'base' and filename='health.svg'), '#C8F9AA', '#3EE423', '건강', 0, null, 1, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, (select id from category_icon where icon_group = 'base' and filename='wasting.svg'), '#B9E2FF', '#54B8FF', '낭비', 0, null, 1, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, (select id from category_icon where icon_group = 'base' and filename='hobby.svg'), '#D2C7FF', '#886AFF', '취미', 0, null, 1, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, (select id from category_icon where icon_group = 'base' and filename='default.svg'), '#ECB8FF', '#D971FF', '기타', 0, null, 1, now(), now());

insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='lecture.svg'), '#FFC5CC', '#FF7184', '강의', 0, (select id from (select id from category where member_id=1 and name='학업') tmp), 1, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='assignment.svg'), '#FFC5CC', '#FF7184', '과제', 0, (select id from (select id from category where member_id=1 and name='학업') tmp), 1, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='groupstudy.svg'), '#FFC5CC', '#FF7184', '스터디', 0, (select id from (select id from category where member_id=1 and name='학업') tmp), 1, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='groupwork.svg'), '#FFC5CC', '#FF7184', '팀플', 0, (select id from (select id from category where member_id=1 and name='학업') tmp), 1, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='studyforexam.svg'), '#FFC5CC', '#FF7184', '시험공부', 0, (select id from (select id from category where member_id=1 and name='학업') tmp), 1, now(), now());

insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='reading.svg'), '#FFCDA0', '#FFA24F', '독서', 0, (select id from (select id from category where member_id=1 and name='성장') tmp), 1, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='lecture.svg'), '#FFCDA0', '#FFA24F', '강의', 0, (select id from (select id from category where member_id=1 and name='성장') tmp), 1, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='certificate.svg'), '#FFCDA0', '#FFA24F', '자격증', 0, (select id from (select id from category where member_id=1 and name='성장') tmp), 1, now(), now());

insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='friend.svg'), '#FFE49F', '#FFC32B', '친구', 0, (select id from (select id from category where member_id=1 and name='관계') tmp), 1, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='family.svg'), '#FFE49F', '#FFC32B', '가족', 0, (select id from (select id from category where member_id=1 and name='관계') tmp), 1, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='dating.svg'), '#FFE49F', '#FFC32B', '연인', 0, (select id from (select id from category where member_id=1 and name='관계') tmp), 1, now(), now());

insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='sleep.svg'), '#C8F9AA', '#3EE423', '잠', 0, (select id from (select id from category where member_id=1 and name='건강') tmp), 1, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='meal.svg'), '#C8F9AA', '#3EE423', '식사', 0, (select id from (select id from category where member_id=1 and name='건강') tmp), 1, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='exercise.svg'), '#C8F9AA', '#3EE423', '운동', 0, (select id from (select id from category where member_id=1 and name='건강') tmp), 1, now(), now());

insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='sns.svg'), '#B9E2FF', '#54B8FF', 'sns', 0, (select id from (select id from category where member_id=1 and name='낭비') tmp), 1, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='internet.svg'), '#B9E2FF', '#54B8FF', '웹서핑', 0, (select id from (select id from category where member_id=1 and name='낭비') tmp), 1, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='media.svg'), '#B9E2FF', '#54B8FF', '미디어', 0, (select id from (select id from category where member_id=1 and name='낭비') tmp), 1, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='wasting.svg'), '#B9E2FF', '#54B8FF', '멍', 0, (select id from (select id from category where member_id=1 and name='낭비') tmp), 1, now(), now());

insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='game.svg'), '#D2C7FF', '#886AFF', '게임', 0, (select id from (select id from category where member_id=1 and name='취미') tmp), 1, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='movie.svg'), '#D2C7FF', '#886AFF', '영화', 0, (select id from (select id from category where member_id=1 and name='취미') tmp), 1, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='music.svg'), '#D2C7FF', '#886AFF', '음악', 0, (select id from (select id from category where member_id=1 and name='취미') tmp), 1, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='instrument.svg'), '#D2C7FF', '#886AFF', '악기', 0, (select id from (select id from category where member_id=1 and name='취미') tmp), 1, now(), now());

insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='cleaning.svg'), '#ECB8FF', '#D971FF', '집안일', 0, (select id from (select id from category where member_id=1 and name='기타') tmp), 1, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='moving.svg'), '#ECB8FF', '#D971FF', '이동시간', 0, (select id from (select id from category where member_id=1 and name='기타') tmp), 1, now(), now());

-- 2 category_template 직장인
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, (select id from category_icon where icon_group = 'base' and filename='job.svg'), '#FFC5CC', '#FF7184', '직장', 0, null, 2, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, (select id from category_icon where icon_group = 'base' and filename='growth.svg'), '#FFCDA0', '#FFA24F', '성장', 0, null, 2, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, (select id from category_icon where icon_group = 'base' and filename='relationship.svg'), '#FFE49F', '#FFC32B', '관계', 0, null, 2, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, (select id from category_icon where icon_group = 'base' and filename='health.svg'), '#C8F9AA', '#3EE423', '건강', 0, null, 2, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, (select id from category_icon where icon_group = 'base' and filename='wasting.svg'), '#B9E2FF', '#54B8FF', '낭비', 0, null, 2, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, (select id from category_icon where icon_group = 'base' and filename='hobby.svg'), '#D2C7FF', '#886AFF', '취미', 0, null, 2, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (0, (select id from category_icon where icon_group = 'base' and filename='default.svg'), '#ECB8FF', '#D971FF', '기타', 0, null, 2, now(), now());

insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='work.svg'), '#FFC5CC', '#FF7184', '업무', 0, (select id from (select id from category where member_id=2 and name='직장') tmp), 2, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='overwork.svg'), '#FFC5CC', '#FF7184', '야근', 0, (select id from (select id from category where member_id=2 and name='직장') tmp), 2, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='businesstrip.svg'), '#FFC5CC', '#FF7184', '출장', 0, (select id from (select id from category where member_id=2 and name='직장') tmp), 2, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='companydinner.svg'), '#FFC5CC', '#FF7184', '회식', 0, (select id from (select id from category where member_id=2 and name='직장') tmp), 2, now(), now());

insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='reading.svg'), '#FFCDA0', '#FFA24F', '독서', 0, (select id from (select id from category where member_id=2 and name='성장') tmp), 2, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='lecture.svg'), '#FFCDA0', '#FFA24F', '강의', 0, (select id from (select id from category where member_id=2 and name='성장') tmp), 2, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='certificate.svg'), '#FFCDA0', '#FFA24F', '자격증', 0, (select id from (select id from category where member_id=2 and name='성장') tmp), 2, now(), now());

insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='friend.svg'), '#FFE49F', '#FFC32B', '친구', 0, (select id from (select id from category where member_id=2 and name='관계') tmp), 2, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='family.svg'), '#FFE49F', '#FFC32B', '가족', 0, (select id from (select id from category where member_id=2 and name='관계') tmp), 2, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='dating.svg'), '#FFE49F', '#FFC32B', '연인', 0, (select id from (select id from category where member_id=2 and name='관계') tmp), 2, now(), now());

insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='sleep.svg'), '#C8F9AA', '#3EE423', '잠', 0, (select id from (select id from category where member_id=2 and name='건강') tmp), 2, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='meal.svg'), '#C8F9AA', '#3EE423', '식사', 0, (select id from (select id from category where member_id=2 and name='건강') tmp), 2, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='exercise.svg'), '#C8F9AA', '#3EE423', '운동', 0, (select id from (select id from category where member_id=2 and name='건강') tmp), 2, now(), now());

insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='sns.svg'), '#B9E2FF', '#54B8FF', 'sns', 0, (select id from (select id from category where member_id=2 and name='낭비') tmp), 2, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='internet.svg'), '#B9E2FF', '#54B8FF', '웹서핑', 0, (select id from (select id from category where member_id=2 and name='낭비') tmp), 2, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='media.svg'), '#B9E2FF', '#54B8FF', '미디어', 0, (select id from (select id from category where member_id=2 and name='낭비') tmp), 2, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='wasting.svg'), '#B9E2FF', '#54B8FF', '멍', 0, (select id from (select id from category where member_id=2 and name='낭비') tmp), 2, now(), now());

insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='game.svg'), '#D2C7FF', '#886AFF', '게임', 0, (select id from (select id from category where member_id=2 and name='취미') tmp), 2, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='movie.svg'), '#D2C7FF', '#886AFF', '영화', 0, (select id from (select id from category where member_id=2 and name='취미') tmp), 2, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='music.svg'), '#D2C7FF', '#886AFF', '음악', 0, (select id from (select id from category where member_id=2 and name='취미') tmp), 2, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='instrument.svg'), '#D2C7FF', '#886AFF', '악기', 0, (select id from (select id from category where member_id=2 and name='취미') tmp), 2, now(), now());

insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='cleaning.svg'), '#ECB8FF', '#D971FF', '집안일', 0, (select id from (select id from category where member_id=2 and name='기타') tmp), 2, now(), now());
insert into category(`level`, category_icon_id, color,  highlight_color, name, is_deleted, parent_id, member_id, created_at, modified_at)
values (1, (select id from category_icon where icon_group = 'base' and filename='moving.svg'), '#ECB8FF', '#D971FF', '이동시간', 0, (select id from (select id from category where member_id=2 and name='기타') tmp), 2, now(), now());

