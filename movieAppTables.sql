
drop database if exists movie_db;
create database movie_db;
use movie_db;



create table movies
(
	id int primary key auto_increment,
    name varchar(30),
    description varchar(250)
);

create table users
(
	id int primary key auto_increment,
	email varchar(40) unique,
    password varchar(30)
);

CREATE TABLE ratings (
  id INT PRIMARY KEY AUTO_INCREMENT,
  movie_id INT NOT NULL,
  user_id INT,
  rating DECIMAL(3, 1) NOT NULL,
  -- description varchar(250),
  FOREIGN KEY (movie_id) REFERENCES movies(id),
  FOREIGN KEY (user_id) REFERENCES users(id)
);



INSERT INTO movies (name, description)
VALUES 
       ('Moonlight', 'A chronicle of the childhood, adolescence, and adulthood of a young African-American man growing up in a rough neighborhood in Miami.'),
 	   ('Lord of the Rings', 'A young hobbit named Frodo Baggins embarks on a journey to destroy a powerful ring and save Middle-earth from the dark lord Sauron.'),
       ('Mario Bros', 'Two Italian-American plumbers, Mario and Luigi, must save Princess Peach from the evil King Koopa and his minions in this adaptation of the popular video game.'),
       ('Puss in Boots', 'A swashbuckling cat named Puss in Boots teams up with a mastermind Humpty Dumpty and a street-savvy Kitty Softpaws to steal the famous Golden Goose.'),
       ('Hercules', 'The son of Zeus, Hercules, must prove himself as a true hero and complete a series of tasks to regain his immortality and join the gods on Mount Olympus.'),
       ('Lilo & Stitch', 'A young girl named Lilo befriends an alien named Stitch, who is on the run from his home planet. Together, they embark on adventures and discover the true meaning of family.'),
       ('Juno', 'A teenage girl named Juno becomes pregnant and decides to give her baby up for adoption. Along the way, she navigates the challenges of pregnancy, relationships, and growing up.'),
       ('Mean Girls', 'A teenage girl named Cady Heron moves to a new school and becomes embroiled in a clique of popular girls known as the "Plastics," leading to drama, betrayal, and life lessons.'),
       ('Divergent', 'In a dystopian future society, a young woman named Tris Prior discovers she is "Divergent," possessing unique abilities that make her a target as she navigates a dangerous initiation process to join a faction.');

-- You can continue adding more movies to the list with additional INSERT INTO statements


select * from users;
select * from movies;
select * from ratings;
insert into users(email, password)
	values('username1@gmail.com', 'password'); 
insert into users(email, password)
	values('username4@gmail.com', 'password'); 
    insert into users(email, password)
	values('username2@gmail.com', 'password'); 
SELECT * FROM users WHERE email = 'a@gmail.com' AND password = 'pass';
SELECT * FROM users WHERE email = 'j' AND password = 'pass';

SELECT m.id AS movie_id, m.name AS movie_name, m.description, r.rating
FROM movies m
LEFT JOIN ratings r ON m.id = r.movie_id
WHERE r.user_id = 4;
select * from users where email = 'j@gmail.com';
-- insert into users(username, password)
-- 	values('username2', 'password'); 
-- insert into users(username, password)
-- 	values('username3', 'password'); 
-- insert into users(username, password)
-- 	values('username4', 'password'); 