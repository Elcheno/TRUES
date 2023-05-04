CREATE DATABASE TRUES;
USE TRUES;

CREATE TABLE employee(
id INT PRIMARY KEY AUTO_INCREMENT,
cod INT UNIQUE,
dni VARCHAR(9) UNIQUE,
_name VARCHAR(50),
lastname VARCHAR(100)
);

CREATE TABLE line(
id INT PRIMARY KEY AUTO_INCREMENT,
_description VARCHAR(100)
);

CREATE TABLE product(
id INT PRIMARY KEY AUTO_INCREMENT,
cod INT UNIQUE,
_description VARCHAR(100),
id_line INT,
date_p DATE,
FOREIGN KEY (id_line) REFERENCES line(id) ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE TABLE emp_line(
id_emp INT,
id_line INT,
_date DATE,
date_in TIME(0),
date_out TIME(0),
FOREIGN KEY (id_emp) REFERENCES employee(id) ON UPDATE NO ACTION ON DELETE CASCADE,
FOREIGN KEY (id_line) REFERENCES line(id) ON UPDATE NO ACTION ON DELETE CASCADE,
PRIMARY KEY(id_emp, id_line, _date, date_in, date_out)
);