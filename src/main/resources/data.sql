insert into USERS(username, FULL_NAME, PASSWORD, EMAIL, STATUS)
values
('user1', 'Full Name', 'password', 'user1@email.com', 'ACTIVE'),
('user2', 'Full Name2', 'password', 'user2@email.com', 'ACTIVE');

insert into TASKS(USER_ID, NAME, DESCRIPTION, STATUS, UPDATED_AT, DELETED)
values
(1, 'Task 1', 'Desc 1', 'DONE', '2020-05-04 15:07:39.154493', false),
(1, 'Task 2', 'Desc 2', 'DONE', '2020-05-04 15:07:39.154493', false),
(1, 'Task 3', 'Desc 3', 'DONE', '2020-05-04 15:07:39.154493', false),
(1, 'Task 4', 'Desc 4', 'DONE', '2020-05-04 15:07:39.154493', false),
(1, 'Task 5', 'Desc 5', 'DONE', '2020-05-04 15:07:39.154493', false),
(1, 'Task 6', 'Desc 6', 'DONE', '2020-05-04 15:07:39.154493', false);

insert into PROJECTS(NAME, DESCRIPTION, DELETED)
values
('Project 1', 'Desc', false),
('Project 2', 'Desc', false),
('Project 3', 'Desc', false),
('Project 4', 'Desc', true);

insert into USER_PROJECT(USER_ID, PROJECT_ID)
values
(1, 1),
(1, 3),
(1, 4);

insert into PROJECT_TASK(PROJECT_ID, TASK_ID)
values
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6);
