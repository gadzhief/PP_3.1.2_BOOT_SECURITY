INSERT INTO users (first_name, last_name, email, password, age)
VALUES ('John', 'Doe', 'johndoe@example.com', 'password1', 30),
       ('Jane', 'Doe', 'janedoe@example.com', 'password2', 20),
       ('Alice', 'Smith', 'alicesmith@example.com', 'password3', 19);

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1), (2, 1), (3, 2);