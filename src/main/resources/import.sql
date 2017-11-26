-- Insert role
insert into role (name) values ('ROLE_USER');

-- Insert two users (password are both 'password')
insert into user (username, password, role_id) values ('user1', '$2a$10$n.KQ/AtIGAk9kgd.KSIHauogsAnpSaKsfZgCMugxPF89pcEdR5ds.', 1);
insert into user (username, password, role_id) values ('user2', '$2a$10$n.KQ/AtIGAk9kgd.KSIHauogsAnpSaKsfZgCMugxPF89pcEdR5ds.', 1);

-- Insert two projects
insert into project (name) values ('labs1');
insert into project (name) values ('labs2');

-- Insert tasks
insert into task (complete, subject, description, user_id, project_id) values (false,'lorem ipsum', 'code blah blash slslsdkfjlwkj', 1, 2);
insert into task (complete, subject, description, user_id, project_id) values (false,'title2', 'Discuss users and roles', 1, 1);
insert into task (complete, subject, description, user_id, project_id) values (false,'title3', 'Enable Spring Security', 2, 1);
insert into task (complete, subject, description, user_id, project_id) values (false,'title4', 'Test application', 2, 2);

