-- Integrantes: W - X - Y - Z

DROP TABLE IF EXISTS users; -- Usuarios
CREATE TABLE users(
    id_user INT(11) NOT NULL auto_increment PRIMARY KEY,
    email VARCHAR(60) UNIQUE,
    first_name VARCHAR(56),
    last_name VARCHAR(56)
);	

DROP TABLE IF EXISTS vehicles;  -- Vehiculos
CREATE TABLE vehicles(
    id_vehicle INT(11) NOT NULL auto_increment PRIMARY KEY,
    id_user INT(11) NOT NULL,
	model VARCHAR(20),
    color VARCHAR(20),
    km INT(6) NOT NULL,
    mark VARCHAR(15),
	year INT(4) NOT NULL
);


DROP TABLE IF EXISTS posts; -- Posts
CREATE TABLE posts(
    id_post INT(11) NOT NULL auto_increment PRIMARY KEY,
	id_user INT(11) NOT NULL,
	id_vehicle INT(11) NOT NULL,
    title VARCHAR(30),
    description VARCHAR(500),
    price INT(12)
);

DROP TABLE IF EXISTS answers; -- Respuestas
CREATE TABLE answers(
    id_answer INT(11) NOT NULL auto_increment PRIMARY KEY,
	id_question INT(11) NOT NULL,
    id_user INT(11) NOT NULL,
    text VARCHAR(200)
);

DROP TABLE IF EXISTS questions; -- Preguntas
CREATE TABLE questions(
    id_question INT(11) NOT NULL auto_increment PRIMARY KEY,
	id_post INT(11) NOT NULL,
    id_user INT(11) NOT NULL,
    text VARCHAR(200)
    
);


