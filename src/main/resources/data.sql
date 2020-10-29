-- ROLES IN SPRING APPLICATION
insert into roles (name)
values ('ROLE_ADMIN'),
       ('ROLE_USER');

-- STATUSES IN PROJECT, SPRINT, ETC.
insert into statuses (name)
values ('None'),
       ('Ready'),
       ('Work in progress'),
       ('Complete'),
       ('Cancelled');

-- APPLICATION USERS
insert into users (first_name, last_name, username, email, password, is_account_expired, is_account_locked,
                   is_credentials_expired, is_enabled)
values ('Admin', 'Admin', 'admin', 'admin@admin.com', '$2a$10$zDXtXHls88TSM4cC5AMsnevEA70FMUiqrZ5DP3N5.xJ/hVBssAJ.W',
        false, false, false, true);
insert into users (first_name, last_name, username, email, password, is_account_expired, is_account_locked,
                   is_credentials_expired, is_enabled)
values ('Anna', 'Kowalska', 'akowalska', 'anna@kowalska.com',
        '$2a$10$zDXtXHls88TSM4cC5AMsnevEA70FMUiqrZ5DP3N5.xJ/hVBssAJ.W', false, false, false, true);
insert into users (first_name, last_name, username, email, password, is_account_expired, is_account_locked,
                   is_credentials_expired, is_enabled)
values ('Piotr', 'Pieńkowski', 'ppienkowski', 'piotr@pienkowski.com',
        '$2a$10$zDXtXHls88TSM4cC5AMsnevEA70FMUiqrZ5DP3N5.xJ/hVBssAJ.W', false, false, false, true);
insert into users (first_name, last_name, username, email, password, is_account_expired, is_account_locked,
                   is_credentials_expired, is_enabled)
values ('Tomasz', 'Marcinkowski', 'tmarcinkowski', 'tomasz@marcinkowski.com',
        '$2a$10$zDXtXHls88TSM4cC5AMsnevEA70FMUiqrZ5DP3N5.xJ/hVBssAJ.W', false, false, false, true);
insert into users (first_name, last_name, username, email, password, is_account_expired, is_account_locked,
                   is_credentials_expired, is_enabled)
values ('Sylwester', 'Mazurek', 'smazurek', 'sylwester@mazurek.com',
        '$2a$10$zDXtXHls88TSM4cC5AMsnevEA70FMUiqrZ5DP3N5.xJ/hVBssAJ.W', false, false, false, true);
insert into users (first_name, last_name, username, email, password, is_account_expired, is_account_locked,
                   is_credentials_expired, is_enabled)
values ('Karol', 'Dzban', 'kdzban', 'karol@dzban.com', '$2a$10$zDXtXHls88TSM4cC5AMsnevEA70FMUiqrZ5DP3N5.xJ/hVBssAJ.W',
        false, false, false, true);
insert into users (first_name, last_name, username, email, password, is_account_expired, is_account_locked,
                   is_credentials_expired, is_enabled)
values ('Marcin', 'Jawor', 'mjawor', 'marcin@jawor.com', '$2a$10$zDXtXHls88TSM4cC5AMsnevEA70FMUiqrZ5DP3N5.xJ/hVBssAJ.W',
        false, false, false, true);

-- USER ROLES IN SPRING
insert into user_roles (user_id, role_id)
values (1, 1),
       (2, 1),
       (3, 1),
       (4, 1),
       (5, 1),
       (6, 1),
       (7, 1);

-- SCRUM PROJECTS
insert into projects (name, description)
values ('Aplikacja bankowa - Android', 'Aplikacja Android do zarządzania oszczędnościami bakowymi');
insert into projects (name, description)
values ('MaFrenchBank - Aplikacja ios', 'Aplikacja ios dla banku');
insert into projects (name, description)
values ('Testowa aplikacja', 'Aplikacja testowa');

insert into user_projects(user_id, project_id, user_capacity)
values (1, 1, 20);
insert into user_projects(user_id, project_id, user_capacity)
values (1, 2, 40);
insert into user_projects(user_id, project_id, user_capacity)
values (1, 3, 40);

-- FIRST PROJECT SPRINTS
insert into sprints (title, description, begin_date, end_date, status_id, project_id)
values ('Week 1 deliverables 26-30 October', 'opis testowy', current_timestamp(), current_timestamp(), 2, 1);
insert into sprints (title, description, begin_date, end_date, status_id, project_id)
values ('Week 2 deliverables 2-6 November', 'opis testowy', current_timestamp(), current_timestamp(), 2, 1);
insert into sprints (title, description, begin_date, end_date, status_id, project_id)
values ('Week 3 deliverables 7-14 November', 'opis testowy', current_timestamp(), current_timestamp(), 2, 1);
insert into sprints (title, description, begin_date, end_date, status_id, project_id)
values ('Week 4 deliverables 15-22 November', 'opis testowy', current_timestamp(), current_timestamp(), 2, 1);

-- SECOND PROJECT SPRINTS
insert into sprints (title, description, begin_date, end_date, status_id, project_id)
values ('Week 1 deliverables 26-30 October', 'opis testowy', current_timestamp(), current_timestamp(), 2, 2);
insert into sprints (title, description, begin_date, end_date, status_id, project_id)
values ('Week 2 deliverables 2-6 November', 'opis testowy', current_timestamp(), current_timestamp(), 2, 2);
insert into sprints (title, description, begin_date, end_date, status_id, project_id)
values ('Week 3 deliverables 7-14 November', 'opis testowy', current_timestamp(), current_timestamp(), 2, 2);
insert into sprints (title, description, begin_date, end_date, status_id, project_id)
values ('Week 4 deliverables 15-22 November', 'opis testowy', current_timestamp(), current_timestamp(), 2, 2);

-- THIRD PROJECT SPRINTS
insert into sprints (title, description, begin_date, end_date, status_id, project_id)
values ('Week 1 deliverables 26-30 October', 'opis testowy', current_timestamp(), current_timestamp(), 2, 3);
insert into sprints (title, description, begin_date, end_date, status_id, project_id)
values ('Week 2 deliverables 2-6 November', 'opis testowy', current_timestamp(), current_timestamp(), 2, 3);
insert into sprints (title, description, begin_date, end_date, status_id, project_id)
values ('Week 3 deliverables 7-14 November', 'opis testowy', current_timestamp(), current_timestamp(), 2, 3);
insert into sprints (title, description, begin_date, end_date, status_id, project_id)
values ('Week 4 deliverables 15-22 November', 'opis testowy', current_timestamp(), current_timestamp(), 2, 3);

insert into stories (title, short_description, description, number, acceptance_criteria, story_points, sprint_id,
                     status_id, project_id, added_by_user_id, created_at)
values ('User story...', 'Short description', 'Long description', 1, 'Accepted when..', 5, 1, 2, 1, 1,
        current_timestamp());
insert into stories (title, short_description, description, number, acceptance_criteria, story_points, sprint_id,
                     status_id, project_id, added_by_user_id, created_at)
values ('User story...', 'Short description', 'Long description', 2, 'Accepted when..', 5, 1, 2, 1, 1,
        current_timestamp());
insert into stories (title, short_description, description, number, acceptance_criteria, story_points, sprint_id,
                     status_id, project_id, added_by_user_id, created_at)
values ('User story...', 'Short description', 'Long description', 3, 'Accepted when..', 5, 1, 2, 1, 1,
        current_timestamp());
insert into stories (title, short_description, description, number, acceptance_criteria, story_points, sprint_id,
                     status_id, project_id, added_by_user_id, created_at)
values ('User story...', 'Short description', 'Long description', 4, 'Accepted when..', 5, 1, 2, 1, 1,
        current_timestamp());
insert into stories (title, short_description, description, number, acceptance_criteria, story_points, sprint_id,
                     status_id, project_id, added_by_user_id, created_at)
values ('User story...', 'Short description', 'Long description', 5, 'Accepted when..', 5, 1, 2, 1, 1,
        current_timestamp());
insert into stories (title, short_description, description, number, acceptance_criteria, story_points, sprint_id,
                     status_id, project_id, added_by_user_id, created_at)
values ('User story...', 'Short description', 'Long description', 6, 'Accepted when..', 5, 1, 2, 1, 1,
        current_timestamp());
insert into stories (title, short_description, description, number, acceptance_criteria, story_points, sprint_id,
                     status_id, project_id, added_by_user_id, created_at)
values ('User story...', 'Short description', 'Long description', 7, 'Accepted when..', 5, 1, 2, 1, 1,
        current_timestamp());
insert into stories (title, short_description, description, number, acceptance_criteria, story_points, sprint_id,
                     status_id, project_id, added_by_user_id, created_at)
values ('User story...', 'Short description', 'Long description', 8, 'Accepted when..', 5, 1, 2, 1, 1,
        current_timestamp());
insert into stories (title, short_description, description, number, acceptance_criteria, story_points, sprint_id,
                     status_id, project_id, added_by_user_id, created_at)
values ('User story...', 'Short description', 'Long description', 9, 'Accepted when..', 5, 1, 2, 1, 1,
        current_timestamp());
insert into stories (title, short_description, description, number, acceptance_criteria, story_points, sprint_id,
                     status_id, project_id, added_by_user_id, created_at)
values ('User story...', 'Short description', 'Long description', 10, 'Accepted when..', 5, 1, 2, 1, 1,
        current_timestamp());

insert into stories (title, short_description, description, number, acceptance_criteria, story_points, sprint_id,
                     status_id, project_id, added_by_user_id, created_at)
values ('User story in backlog...', 'Short description', 'Long description', 1, 'Accepted when..', 5, null, 2, 1, 1,
        current_timestamp());
insert into stories (title, short_description, description, number, acceptance_criteria, story_points, sprint_id,
                     status_id, project_id, added_by_user_id, created_at)
values ('User story in backlog...', 'Short description', 'Long description', 2, 'Accepted when..', 5, null, 2, 1, 1,
        current_timestamp());
insert into stories (title, short_description, description, number, acceptance_criteria, story_points, sprint_id,
                     status_id, project_id, added_by_user_id, created_at)
values ('User story in backlog...', 'Short description', 'Long description', 3, 'Accepted when..', 5, null, 2, 1, 1,
        current_timestamp());
insert into stories (title, short_description, description, number, acceptance_criteria, story_points, sprint_id,
                     status_id, project_id, added_by_user_id, created_at)
values ('User story in backlog...', 'Short description', 'Long description', 4, 'Accepted when..', 5, null, 2, 1, 1,
        current_timestamp());
insert into stories (title, short_description, description, number, acceptance_criteria, story_points, sprint_id,
                     status_id, project_id, added_by_user_id, created_at)
values ('User story in backlog...', 'Short description', 'Long description', 5, 'Accepted when..', 5, null, 2, 1, 1,
        current_timestamp());
insert into stories (title, short_description, description, number, acceptance_criteria, story_points, sprint_id,
                     status_id, project_id, added_by_user_id, created_at)
values ('User story in backlog...', 'Short description', 'Long description', 6, 'Accepted when..', 5, null, 2, 1, 1,
        current_timestamp());
insert into stories (title, short_description, description, number, acceptance_criteria, story_points, sprint_id,
                     status_id, project_id, added_by_user_id, created_at)
values ('User story in backlog...', 'Short description', 'Long description', 7, 'Accepted when..', 5, null, 2, 1, 1,
        current_timestamp());
insert into stories (title, short_description, description, number, acceptance_criteria, story_points, sprint_id,
                     status_id, project_id, added_by_user_id, created_at)
values ('User story in backlog...', 'Short description', 'Long description', 8, 'Accepted when..', 5, null, 2, 1, 1,
        current_timestamp());
insert into stories (title, short_description, description, number, acceptance_criteria, story_points, sprint_id,
                     status_id, project_id, added_by_user_id, created_at)
values ('User story in backlog...', 'Short description', 'Long description', 9, 'Accepted when..', 5, null, 2, 1, 1,
        current_timestamp());
insert into stories (title, short_description, description, number, acceptance_criteria, story_points, sprint_id,
                     status_id, project_id, added_by_user_id, created_at)
values ('User story in backlog...', 'Short description', 'Long description', 10, 'Accepted when..', 5, null, 2, 1, 1,
        current_timestamp());