-- Integrantes: W - X - Y - Z

DROP TABLE IF EXISTS users; -- Usuarios
CREATE TABLE users(
    id INT(11) NOT NULL AUTO_INCREMENT,
    email VARCHAR(60) UNIQUE,
    first_name VARCHAR(56),
    last_name VARCHAR(56),
	address_id INT(11),
  CONSTRAINT users_pk PRIMARY KEY (id)
);

CREATE TABLE address (
	id INT (11) NOT NULL AUTO_INCREMENT,
	street VARCHAR (120),
	address_number INT(5),
CONSTRAINT address_pk PRIMARY KEY (id),
);

CREATE TABLE vehicles(
	id INT(11) NOT NULL AUTO_INCREMENT,
	model INT (11),
	km INT (11),
	user_id INT(11),
CONSTRAINT vehicle_pk PRIMARY KEY (id)
);

CREATE TABLE questions(
	id INT(11) NOT NULL AUTO_INCREMENT,
	description VARCHAR (200),
	user_id INT(11),
CONSTRAINT questions_id PRIMARY KEY (id)
);

