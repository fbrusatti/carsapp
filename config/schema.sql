-- Integrantes:  - CASTAGNERIS NAZARENO - LANZONI LUCAS - MOLLEA FEDERICO
DROP DATABASE IF EXISTS  carsapp_development;
CREATE DATABASE IF NOT EXISTS carsapp_development;

-- DROP TABLE IF EXISTS users; -- Usuarios
CREATE TABLE carsapp_development.users(
    id INT(11) NOT NULL auto_increment,
    first_name VARCHAR(40),
    last_name VARCHAR(40),
	email VARCHAR(40) UNIQUE,
	pass VARCHAR(20),
	address_id INT(11), 
	CONSTRAINT users_pk PRIMARY KEY (id)
);
-- DROP TABLE IF EXISTS administrators;
CREATE TABLE administrators(
  id INT NOT NULL auto_increment PRIMARY KEY,
  first_name VARCHAR(40),
  last_name VARCHAR(40),
  pass VARCHAR(20) NOT NULL,
  email VARCHAR(40) NOT NULL unique
);

-- DROP TABLE IF EXISTS addresses; -- Ciudad
CREATE TABLE carsapp_development.addresses (
	id INT(11) NOT NULL auto_increment,
	country VARCHAR(20),
	state VARCHAR(20),
	name VARCHAR(20),
	CONSTRAINT city_pk PRIMARY KEY (id)
);

-- DROP TABLE IF EXISTS vehicles;  -- Vehiculos
CREATE TABLE carsapp_development.vehicles(
    id INT(11) NOT NULL auto_increment,
	model VARCHAR(20),
	patent VARCHAR(20) unique,
    color VARCHAR(20),
    km VARCHAR(40) NOT NULL,
    mark VARCHAR(20),
	year VARCHAR(10) NOT NULL,
	user_id INT(11),
	CONSTRAINT vehicles_pk PRIMARY KEY (id)
);



-- DROP TABLE IF EXISTS cars;  -- Autos 
CREATE TABLE carsapp_development.cars(
	id INT(11) NOT NULL auto_increment,
	vehicle_id INT(11),
	doors VARCHAR(5) NOT NULL,
	version VARCHAR(15),
	transmission ENUM('Manual','Automatica'),
	direction ENUM('Hidraulica','Asistida','Mecanica'),
	CONSTRAINT cars_pk PRIMARY KEY (id)
);

-- DROP TABLE IF EXISTS motorcycles;  -- Motocicletas
CREATE TABLE carsapp_development.motorcycles(
	id INT(11) NOT NULL auto_increment,
	type ENUM('Street','Chopper','Standard','Sport','Touring','Scooters'), 
	type_motor VARCHAR(11), -- tiempos
	boot_system ENUM('Pedal','Electrico','Pedal y Electrico'),
	displacement VARCHAR(11), -- cilindrada 
	vehicle_id INT(11),
    CONSTRAINT motorcycle_pk PRIMARY KEY (id)
);

-- DROP TABLE IF EXISTS trucks; -- Camiones
CREATE TABLE carsapp_development.trucks(
	id INT(11) NOT NULL auto_increment,
	brake_system VARCHAR(20), 
	direction ENUM('Hidraulica','Asistida','Mecanica'),
	capacity VARCHAR(11),
	vehicle_id  INT(11),
    CONSTRAINT trucks_pk PRIMARY KEY (id)
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
    textA TEXT,
	question_id INT(11),
    user_id INT(11),
	CONSTRAINT answer_pk PRIMARY KEY (id)
);

-- DROP TABLE IF EXISTS questions; -- Preguntas
CREATE TABLE carsapp_development.questions(
    id INT(11) NOT NULL auto_increment,
    textQ TEXT, 
	post_id INT(11),
    user_id INT(11),
	CONSTRAINT question_pk PRIMARY KEY (id)
);

-- INSERT INTO carsapp_development.users (first_name,last_name,email,pass,address_id) VALUES 
-- ("jorge","martin","jorge@gmail.com","123",3);
