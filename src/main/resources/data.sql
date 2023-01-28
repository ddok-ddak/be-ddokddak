insert into member (created_at, modified_at) values (now(), now());
insert into member (created_at, modified_at) values (now(), now());
insert into member (created_at, modified_at) values (now(), now());
insert into member (created_at, modified_at) values (now(), now());
insert into member (created_at, modified_at) values (now(), now());

insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at) values (0, 'test', 'test', 'N', null, 1, now(), now());
-- insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at) values (0, 'test', 'test', 'N', null, 1, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at) values (0, 'test', 'test', 'N', null, 2, now(), now());
-- insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at) values (0, 'test', 'test', 'N', null, 2, now(), now());
insert into category(`level`, color, name, delete_yn, parent_id, member_id, created_at, modified_at) values (1, 'test2', 'test2', 'N', 1, 3, now(), now());
