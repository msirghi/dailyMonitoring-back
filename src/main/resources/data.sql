insert into USERS(username, FULL_NAME, PASSWORD, EMAIL, STATUS, CREATED_AT)
values
('user1', 'Sirghi Mihail', 'password', 'user1@email.com', 'ACTIVE', '2020-04-04 15:07:39.154493'),
('user1', 'Nicolae Savastin', 'password', 'user1@email.com', 'ACTIVE', '2020-06-04 15:07:39.154493'),
('user1', 'Dima Sokolovschii', 'password', 'user1@email.com', 'ACTIVE', '2020-06-04 15:07:39.154493'),
('user1', 'Full Name', 'password', 'user1@email.com', 'ACTIVE', '2020-07-04 15:07:39.154493'),
('user1', 'Full Name', 'password', 'mr.serven@yahoo.com', 'ACTIVE', '2020-05-04 15:07:39.154493'),
('user2', 'Full Name2', 'password', 'user2@email.com', 'ACTIVE', '2020-05-03 15:07:39.154493');

insert into TASKS(USER_ID, NAME, DESCRIPTION, STATUS, CREATED_AT, UPDATED_AT, DELETED)
values
(1, 'Task 1', 'Desc 1', 'DONE', '2020-05-04 15:07:39.154493', '2020-05-04 15:07:39.154493', false),
(1, 'Task 2', 'Desc 2', 'DONE', '2020-05-04 15:07:39.154493', '2020-05-04 15:07:39.154493', false),
(1, 'Task 3', 'Desc 3', 'DONE', '2020-05-04 15:07:39.154493', '2020-05-04 15:07:39.154493', false),
(1, 'Task 4', 'Desc 4', 'DONE', '2020-05-04 15:07:39.154493', '2020-05-04 15:07:39.154493', false),
(1, 'Task 5', 'Desc 5', 'DONE', '2020-05-04 15:07:39.154493', '2020-05-04 15:07:39.154493', false),
(1, 'Task 6', 'Desc 6', 'DONE', '2020-05-04 15:07:39.154493', '2020-05-04 15:07:39.154493', false);

insert into PROJECTS(NAME, DESCRIPTION, CREATED_AT, DELETED)
values
('Project 1', 'Desc', '2020-02-04 15:07:39.154493', false),
('Project 2', 'Desc', '2020-05-04 15:07:39.154493', false),
('Project 3', 'Desc', '2020-06-04 15:07:39.154493', false),
('Project 4', 'Desc', '2020-05-04 15:07:39.154493', true);

insert into USER_PROJECT(USER_ID, PROJECT_ID)
values
(1, 1),
(2, 1),
(3, 1),
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
