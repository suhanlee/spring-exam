-- Insert role
insert into role (name) values ('ROLE_USER');

-- Insert two users (password are both 'password')
insert into user (username, password) values ('user', 'password');
insert into user (username, password) values ('user2', 'password');
insert into user (username, password) values ('suhan', '1234');

-- Insert two projects
insert into project (name) values ('labs1');
insert into project (name) values ('labs2');

-- Insert tasks
insert into task (complete, subject, description, user_id, project_id) values (false,'title', 'Code Task entity', 1, 2);
insert into task (complete, subject, description, user_id, project_id) values (false,'title2', 'Discuss users and roles', 1, 1);
insert into task (complete, subject, description, user_id, project_id) values (false,'title3', 'Enable Spring Security', 2, 1);
insert into task (complete, subject, description, user_id, project_id) values (false,'title4', 'Test application', 2, 2);

