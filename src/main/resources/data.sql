insert into roles (name) values
                                ('ROLE_ADMIN'),
                                ('ROLE_USER');

insert into statuses (name) values
                                   ('None'),
                                   ('Ready'),
                                   ('Work in progress'),
                                   ('Complete'),
                                   ('Cancelled');

insert into users (first_name, last_name, username, email, password, is_account_expired, is_account_locked, is_credentials_expired, is_enabled) values
    ('Patryk', 'Kosieradzki', 'k0siara', 'k0siara@scruminium.com', '$2a$10$uNAzprV2iWcc4eac.FEiYuREKtciHG7XntQmwZ6etcqvskLaC3I22', false, false, false, true);

insert into user_roles (user_id, role_id) values
                                                 (1, 1);

insert into projects (name, description) values ('Project 1', 'Testowy projekt w aplikacji Scruminium');
insert into projects (name, description) values ('Project 2', 'Testowy projekt w aplikacji Scruminium');

insert into user_projects(user_id, project_id, user_capacity) values (1, 1, 20);
insert into user_projects(user_id, project_id, user_capacity) values (1, 2, 40);

insert into sprints (title, description, begin_date, end_date, status_id, project_id) values ('1. Pierwszy sprint', 'opis testowy', current_timestamp(), current_timestamp(), 2, 1);
insert into sprints (title, description, begin_date, end_date, status_id, project_id) values ('2. Drugi sprint', 'opis testowy', current_timestamp(), current_timestamp(), 2, 1);
insert into sprints (title, description, begin_date, end_date, status_id, project_id) values ('1. Pierwszy sprint drugiego projektu', 'opis testowy', current_timestamp(), current_timestamp(), 2, 2);

insert into stories (title, short_description, description, number, acceptance_criteria, story_points, sprint_id, status_id, project_id, added_by_user_id, created_at)
values ('Pierwsza historyjka w 1 Sprincie', 'krotki opis story', 'dlugi opis', 1, 'akceptacja cos tam', 5, 1, 2, 1, 1, current_timestamp());
insert into stories (title, short_description, description, number, acceptance_criteria, story_points, sprint_id, status_id, project_id, added_by_user_id, created_at)
values ('Druga historyjka w 1 Sprincie', 'krotki opis story', 'dlugi opis', 1, 'akceptacja cos tam', 5, 1, 3, 1, 1, current_timestamp());

insert into stories (title, short_description, description, number, acceptance_criteria, story_points, sprint_id, status_id, project_id, added_by_user_id, created_at)
values ('Pierwsza historyjka w 2 Sprincie', 'krotki opis story', 'dlugi opis', 1, 'akceptacja cos tam', 5, 2, 3, 1, 1, current_timestamp());
insert into stories (title, short_description, description, number, acceptance_criteria, story_points, sprint_id, status_id, project_id, added_by_user_id, created_at)
values ('Druga historyjka w 2 Sprincie', 'krotki opis story', 'dlugi opis', 1, 'akceptacja cos tam', 5, 2, 2, 1, 1, current_timestamp());

insert into stories (title, short_description, description, number, acceptance_criteria, story_points, sprint_id, status_id, project_id, added_by_user_id, created_at)
values ('Pierwsza historyjka w 1 Sprincie drugiego projektu', 'krotki opis story', 'dlugi opis', 1, 'akceptacja cos tam', 5, 3, 2, 2, 1, current_timestamp());

insert into stories (title, short_description, description, number, acceptance_criteria, story_points, sprint_id, status_id, project_id, added_by_user_id, created_at)
values ('Nieprzypisana historyjka w drugim projekcie', 'krotki opis story', 'dlugi opis', 1, 'akceptacja cos tam', 5, null, 1, 2, 1, current_timestamp());