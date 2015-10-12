insert into auth_users (id, username, password, email, enable, status, created_date) values (1, 'test', 'test', 'test@test.com', true, 0, CURRENT_TIMESTAMP);
insert into auth_users (id, username, password, email, enable, status, created_date) values (2, 'super', 'super', 'super@test.com', true, 0, CURRENT_TIMESTAMP);
insert into auth_users (id, username, password, email, enable, status, created_date) values (3, 'admin', 'admin', 'admin@test.com', true, 0, CURRENT_TIMESTAMP);
insert into auth_roles (id, name, description, enable) values (1, 'User', 'Normal User', true);
insert into auth_roles (id, name, description, enable) values (2, 'SuperUser', 'Super User', true);
insert into auth_roles (id, name, description, enable) values (3, 'Admin', 'Administrator', true);
insert into auth_role_member (user_id, role_id) values (1, 1); -- user role add user
insert into auth_role_member (user_id, role_id) values (2, 2); -- super user role add super
insert into auth_role_member (user_id, role_id) values (3, 3); -- admin role add admin
insert into auth_authorities (id, authority, description, enable) values (1, 'READ', 'Read', true);
insert into auth_authorities (id, authority, description, enable) values (2, 'WRITE', 'Write', true);
insert into auth_authorities (id, authority, description, enable) values (3, 'EXECUTE', 'Exceute', true);
insert into auth_authorities (id, authority, description, enable) values (4, 'ALL', 'All', true);
insert into auth_authority_resource (id, name, enable) values (1, 'ALL', true);
insert into auth_role_authority (role_id, auth_res_link_id) values (3, 1); -- grant all permission to admin
-- update sequence table
insert into sequence_id_gen values ('authority_seq', 5);
insert into sequence_id_gen values ('authority_resource_seq', 2);
insert into sequence_id_gen values ('cookie_user_seq', 1);
insert into sequence_id_gen values ('resource_seq', 1);
insert into sequence_id_gen values ('role_seq', 4);
insert into sequence_id_gen values ('user_seq', 4);