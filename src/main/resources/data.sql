insert into
    movies (title, release_year)
values
    ('The Godfather', 1972),
    ('Casablanca', 1942),
    ('The Shawshank Redemption', 1994),
    ('Gone with the Wind', 1939),
    ('Psycho', 1960);
    
insert into
    users ( name, password, email)
values
    ( 'admin','$2a$10$NYFfoMBob9PnjYnqvQti0Ods/KuOP9t0sour/.bxEgmcR80jfW.AG','admin@gmail.com');
    
insert into
    roles ( role)
values
    ( 'ROLE_ADMIN');
    
insert into
	user_roles (user_id,role_id)
values
	(1,1)