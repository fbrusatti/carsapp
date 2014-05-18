-- Integrantes: Daniel Andruvetto - Eduardo Benmergui - Facundo Molina - Lucas Palacios

DROP TABLE IF EXISTS users; -- Usuarios
CREATE TABLE users(
    id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(60) UNIQUE,
    first_name VARCHAR(56),
    last_name VARCHAR(56),
	mobile VARCHAR(20),
	telephone VARCHAR(20),
	address VARCHAR(56),
	city_id INT(11)
);

DROP TABLE IF EXISTS cities; -- Ciudades
CREATE TABLE cities(
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(30) 
);

DROP TABLE IF EXISTS vehicles; -- Vehículos
CREATE TABLE vehicles(
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	brand VARCHAR(20) NOT NULL,
	model VARCHAR(20) NOT NULL,
	year VARCHAR(4) NOT NULL,
	color VARCHAR(20) NOT NULL, 
	user_id INT(11),
	type VARCHAR(20) NOT NULL
);

/** FORMA EN LA QUE TODOS LOS VEHICULOS VAN A LA MISMA TABLA


DROP TABLE IF EXISTS vehicles; -- Vehículos
CREATE TABLE vehicles(
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	brand VARCHAR(20) NOT NULL,
	model VARCHAR(20) NOT NULL,
	year VARCHAR(4) NOT NULL,
	color VARCHAR(20) NOT NULL, 
	type VARCHAR(10) NOT NULL,
	capacity INT(11),
	length DOUBLE,
	height DOUBLE,
	cylinder_capacity INT(11),
	user_id INT(11)
);
*/

DROP TABLE IF EXISTS posts; -- Avisos
CREATE TABLE posts(
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	title VARCHAR(30) NOT NULL,	
	description TEXT,
	user_id INT(11),
	vehicle_id INT(11) UNIQUE,
	created_at DATETIME 
);

DROP TABLE IF EXISTS cars; -- Autos
CREATE TABLE cars(
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    capacity INT(11) NOT NULL,
    vehicle_id INT(11) UNIQUE
);

DROP TABLE IF EXISTS trucks; -- Camiones
CREATE TABLE trucks(
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	length DOUBLE NOT NULL,
	height DOUBLE NOT NULL,
	vehicle_id INT(11) UNIQUE
);

DROP TABLE IF EXISTS motorcycles; -- Motocicletas
CREATE TABLE motorcycles(
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	cylinder_capacity INT(11) NOT NULL
);

DROP TABLE IF EXISTS questions; -- Preguntas
CREATE TABLE questions(
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	description TEXT,
	created_at DATETIME,
	user_id INT(11),
	post_id INT(11)
);

DROP TABLE IF EXISTS answers; -- Respuestas
CREATE TABLE answers(
	id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	description TEXT,
	created_at DATETIME,
	user_id INT(11),
	question_id INT(11) UNIQUE
);
