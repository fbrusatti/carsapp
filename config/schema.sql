-- Integrantes: -BERTORELLO GABRIELA - CASTAGNERIS NAZARENO - LANZONI LUCAS - MOLLEA FEDERICO

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
	year INT(4) NOT NULL,
	version VARCHAR(15),
	combustible VARCHAR(15)
);

DROP TABLE IF EXISTS car;  -- Autos 
CREATE TABLE car(
    id_vehicle INT(11) NOT NULL,
    id_user INT(11) NOT NULL,
	doors INT(11) NOT NULL,
	version VARCHAR(15),
	transmission VARCHAR (15),
	direction VARCHAR(15)
);

DROP TABLE IF EXISTS motorclicle;  -- Motocicletas
CREATE TABLE motorcicle(
	id_vehicle INT(11) NOT NULL,
    id_user INT(11) NOT NULL,
	type VARCHAR(11), 
	type_motor VARCHAR(11),
	boot_sistem VARCHAR(11),
	displacement VARCHAR(11)
);

DROP TABLE IF EXISTS truck; -- Camiones
CREATE TABLE truck(
	id_vehicle INT(11) NOT NULL,
    id_user INT(11) NOT NULL,
	brake_system VARCHAR(11),
	direction VARCHAR(15),
	capacity INT(11)
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


