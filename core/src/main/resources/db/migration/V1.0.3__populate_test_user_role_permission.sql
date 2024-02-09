insert into t_user_group(user_group_name, group_note) values('ADMIN_GROUP', 'system administrative users');

insert into t_role(name, note) values('ADMIN_ROLE', 'administrative role');

insert into t_permission(role_id, permission_name)
values
    (1, 'ADMIN_ROLE.WRITE'),
    (1, 'ADMIN_ROLE.READ'),
    (1, 'ADMIN_ROLE.UPDATE'),
    (1, 'ADMIN_ROLE.DELETE');

insert into t_group_authority(user_group_id, permission_id)
values
    (1, 1),
    (1, 2),
    (1, 3),
    (1, 4);

insert into t_user(username, password, user_group_id, user_type)
values('admin', '$2y$12$R2gix.Nr/E4j9pmKVQHA4u/x.oyWf/wEPUBcQwxYerQSwqQXMcWZO', 1, 'ADMIN');