-- Integrantes: -BERTORELLO GABRIELA - CASTAGNERIS NAZARENO - LANZONI LUCAS - MOLLEA FEDERICO
DROP DATABASE IF EXISTS  carsapp_development;
CREATE DATABASE IF NOT EXISTS carsapp_development;

-- DROP TABLE IF EXISTS users; -- Usuarios
CREATE TABLE carsapp_development.users(
    id INT(11) NOT NULL auto_increment,
    first_name VARCHAR(40),
    last_name VARCHAR(40),
	email VARCHAR(40) UNIQUE,
	pass VARCHAR(20),
	CONSTRAINT users_pk PRIMARY KEY (id)
);

-- DROP TABLE IF EXISTS addresses; -- Ciudad
CREATE TABLE carsapp_development.addresses (
	id INT(11) NOT NULL auto_increment,
	country VARCHAR(20),
	state VARCHAR(20),
	name VARCHAR(20),
	user_id INT(11),
	CONSTRAINT city_pk PRIMARY KEY (id)
);

-- DROP TABLE IF EXISTS vehicles;  -- Vehiculos
CREATE TABLE carsapp_development.vehicles(
    id INT(11) NOT NULL auto_increment,
	model VARCHAR(20),
    color VARCHAR(20),
    km INT(6) NOT NULL,
    mark VARCHAR(20),
	year INT(4) NOT NULL,
	user_id INT(11),
	CONSTRAINT vehicles_pk PRIMARY KEY (id)
);



-- DROP TABLE IF EXISTS cars;  -- Autos 
CREATE TABLE carsapp_development.cars(
	vehicle_id INT(11) NOT NULL AUTO_INCREMENT,
	doors INT(11) NOT NULL,
	version VARCHAR(15),
	transmission ENUM('Manual','Automatica'),
	direction ENUM('Hidraulica','Asistida','Mecanica'),
	CONSTRAINT cars_pk PRIMARY KEY (vehicle_id)
);

-- DROP TABLE IF EXISTS motorcycles;  -- Motocicletas
CREATE TABLE carsapp_development.motorcycles(
	type ENUM('Street','Chopper','Standard','Sport','Touring','Scooters'), 
	type_motor VARCHAR(11), -- tiempos
	boot_system ENUM('Pedal','Electrico','Pedal y Electrico'),
	displacement VARCHAR(11), -- cilindrada 
	vehicle_id INT(11) NOT NULL AUTO_INCREMENT,
    CONSTRAINT motorcycle_pk PRIMARY KEY (vehicle_id)
);

-- DROP TABLE IF EXISTS trucks; -- Camiones
CREATE TABLE carsapp_development.trucks(
	brake_system VARCHAR(20), 
	direction ENUM('Hidraulica','Asistida','Mecanica'),
	capacity INT(11),
	vehicle_id  INT(11) NOT NULL AUTO_INCREMENT,
    CONSTRAINT trucks_pk PRIMARY KEY (vehicle_id)
);

-- DROP TABLE IF EXISTS posts; -- Posts
CREATE TABLE carsapp_development.posts(
    id INT(11) NOT NULL auto_increment,
    title VARCHAR(30),
    description TEXT, 
	price INT(12),
	user_id INT(11),
	vehicle_id INT(11),
	CONSTRAINT posts_pk PRIMARY KEY (id)
);

-- DROP TABLE IF EXISTS answers; -- Respuestas
CREATE TABLE carsapp_development.answers(
    id INT(11) NOT NULL auto_increment,
    text TEXT,
	question_id INT(11),
    user_id INT(11),
	CONSTRAINT answer_pk PRIMARY KEY (id)
);

-- DROP TABLE IF EXISTS questions; -- Preguntas
CREATE TABLE carsapp_development.questions(
    id INT(11) NOT NULL auto_increment,
    text TEXT,
	post_id INT(11),
    user_id INT(11),
	CONSTRAINT question_pk PRIMARY KEY (id)
);


