INSERT INTO todos (id, parentId, description, deadline)
VALUES (1, null, 'Create a new repository in GitLab', current_timestamp());

INSERT INTO todos (id, parentId, description, deadline)
VALUES (2, 1, 'Init a new repo with a boilerplate code', current_timestamp());

INSERT INTO todos (id, parentId, description, deadline)
VALUES (3, 1, 'Set permissions', current_timestamp());

INSERT INTO todos (id, parentId,description, deadline)
VALUES (4, 3, 'Set admin and user permissions separately', current_timestamp());

INSERT INTO users (username, password)
VALUES ('test', '$2a$10$Hq5uFvYM65mY8Y0BBc9HDuh9dxuyr/vpQfJd7VXlqUgdnVMAcG97C');
