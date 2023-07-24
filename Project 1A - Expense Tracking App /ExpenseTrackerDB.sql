
drop database if exists expenses_db;
create database expenses_db;
use expenses_db;

create table users
(
	id int primary key auto_increment,
	username varchar(40) unique NOT NULL,
    password varchar(30) NOT NULL
);

create table expenses
(
	id int primary key auto_increment,
    user_id INT,
    category varchar(30),
    description varchar(250),
    amount Decimal(10, 2),
    expense_day INT NOT NULL,
    expense_month INT NOT NULL,
    expense_year INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

--   id INT PRIMARY KEY AUTO_INCREMENT,
  -- user_id INT,
  -- FOREIGN KEY (user_id) REFERENCES users(id)
-- );

CREATE TABLE budgets (
    budget_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    category VARCHAR(50),
    description VARCHAR(255),
    amount DECIMAL(10, 2),
    budget_day INT NOT NULL,
    budget_month INT NOT NULL,
    budget_year INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);