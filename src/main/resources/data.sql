insert into USERS(username, FULL_NAME, PASSWORD, EMAIL, STATUS, CREATED_AT)
values
('user1', 'Sirghi Mihail', 'password', 'user1@email.com', 'ACTIVE', '2020-04-04 15:07:39.154493'),
('user2', 'Nicolae Savastin', 'password', 'user1@email.com', 'ACTIVE', '2020-06-04 15:07:39.154493'),
('user3', 'Dima Sokolovschii', 'password', 'user1@email.com', 'ACTIVE', '2020-06-04 15:07:39.154493'),
('user4', 'Full Name', 'password', 'user1@email.com', 'ACTIVE', '2020-07-04 15:07:39.154493'),
('user5', 'Full Name', 'password', 'mr.serven1@yahoo.com', 'ACTIVE', '2020-05-04 15:07:39.154493'),
('user6', 'Full Name2', 'password', 'user2@email.com', 'ACTIVE', '2020-05-03 15:07:39.154493');

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

insert into USER_PROJECTS(USER_ID, PROJECT_ID, ORDER_NUMBER)
values
(1, 1, 1),
(2, 1, 1),
(1, 3, 2),
(1, 4, 3);

insert into PROJECT_TASKS(PROJECT_ID, ASSIGNED_TO_USER_ID, CREATOR_USER_ID, NAME, DESCRIPTION, STATUS, CREATED_AT, UPDATED_AT, DELETED, DONE_BY_USER_ID)
values
(1, 1, 1, 'Task 1', 'Desc 1', 'DONE', '2020-05-04 15:07:39.154493', '2020-05-04 15:07:39.154493', false, 1),
(1, 1, 1,'Task 2', 'Desc 2', 'DONE', '2020-05-04 15:07:39.154493', '2020-06-04 15:08:39.154493', false, 1),
(1, 1, 2,'Task 3', 'Desc 3', 'DONE', '2020-05-04 15:07:39.154493', '2020-06-04 15:09:39.154493', false, 1),
(1, 1, 2,'Task 4', 'Desc 4', 'DONE', '2020-05-04 15:07:39.154493', '2020-05-04 15:10:39.154493', false, 2),
(1, 1, 1,'Task 5', 'Desc 5', 'DONE', '2020-05-04 15:07:39.154493', '2020-07-04 15:11:39.154493', false, 2),
(1, 1, 1,'Task 6', 'Desc 6', 'DONE', '2020-05-04 15:07:39.154493', '2020-05-04 15:12:39.154493', false, 2);

insert into EMAIL_TEMPLATES(NAME, DESCRIPTION, TEMPLATE, DELETED)
values
('Template 1', 'Description 1', 'HTML', FALSE)
