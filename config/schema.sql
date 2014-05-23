-- Integrantes: -BERTORELLO GABRIELA - CASTAGNERIS NAZARENO - LANZONI LUCAS - MOLLEA FEDERICO

DROP TABLE IF EXISTS users; -- Usuarios
CREATE TABLE users(
    id_user INT(11) NOT NULL auto_increment PRIMARY KEY,
    first_name VARCHAR(40),
    last_name VARCHAR(40),
	email VARCHAR(40) UNIQUE,
	pass VARCHAR(20),
	id_city INT(11)
);

DROP TABLE IF EXISTS cities; -- Ciudad
CREATE TABLE cities (
	id_city INT(11) NOT NULL auto_increment PRIMARY KEY,
	country VARCHAR(20),
	state VARCHAR(20),
	name VARCHAR(20)
);

DROP TABLE IF EXISTS vehicles;  -- Vehiculos
CREATE TABLE vehicles(
    id_vehicle INT(11) NOT NULL auto_increment PRIMARY KEY,
	model VARCHAR(20),
    color VARCHAR(20),
    km INT(6) NOT NULL,
    mark VARCHAR(20),
	year INT(4) NOT NULL,
	version VARCHAR(15),
	combustible VARCHAR(11),
	id_user INT(11) NOT NULL
);

DROP TABLE IF EXISTS cars;  -- Autos 
CREATE TABLE cars(
	doors INT(11) NOT NULL,
	version VARCHAR(15),
	transmission ENUM('Manual','Automatica'),
	direction ENUM('Hidraulica','Asistida','Mecanica'),
    id_vehicle INT(11) NOT NULL,
    id_user INT(11) NOT NULL
);

DROP TABLE IF EXISTS motorclicles;  -- Motocicletas
CREATE TABLE motorcicles(
	type ENUM('Street','Chopper','Standard','Sport','Touring','Scooters'), 
	type_motor VARCHAR(11), -- tiempos
	boot_system ENUM('Pedal','Electrico','Pedal y Electrico'),
	displacement VARCHAR(11), -- cilindrada 
	id_vehicle INT(11) NOT NULL,
    id_user INT(11) NOT NULL
);

DROP TABLE IF EXISTS trucks; -- Camiones
CREATE TABLE trucks(
	brake_system VARCHAR(20), 
	direction ENUM('Hidraulica','Asistida','Mecanica'),
	capacity INT(11),
	id_vehicle INT(11) NOT NULL,
    id_user INT(11) NOT NULL
);

DROP TABLE IF EXISTS posts; -- Posts
CREATE TABLE posts(
    id_post INT(11) NOT NULL auto_increment PRIMARY KEY,
    title VARCHAR(30),
    description TEXT, 
	price INT(12),
	id_user INT(11) NOT NULL,
	id_vehicle INT(11) NOT NULL
);

DROP TABLE IF EXISTS answers; -- Respuestas
CREATE TABLE answers(
    id_answer INT(11) NOT NULL auto_increment PRIMARY KEY,
    text TEXT,
	id_question INT(11) NOT NULL,
    id_user INT(11) NOT NULL
);

DROP TABLE IF EXISTS questions; -- Preguntas
CREATE TABLE questions(
    id_question INT(11) NOT NULL auto_increment PRIMARY KEY,
    text TEXT,
	id_post INT(11) NOT NULL,
    id_user INT(11) NOT NULL
);


