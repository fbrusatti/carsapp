-- Integrantes: -BERTORELLO GABRIELA - CASTAGNERIS NAZARENO - LANZONI LUCAS - MOLLEA FEDERICO

DROP TABLE IF EXISTS users; -- Usuarios
CREATE TABLE users(
    id_user INT(11) NOT NULL auto_increment PRIMARY KEY,
	id_city INT(11),
    email VARCHAR(60) UNIQUE,
    first_name VARCHAR(56),
    last_name VARCHAR(56)
);

DROP TABLE IF EXISTS cities; -- Ciudad
CREATE TABLE cities (
	id_city INT(11) NOT NULL auto_increment PRIMARY KEY,
	name VARCHAR(56)
	
);

DROP TABLE IF EXISTS vehicles;  -- Vehiculos
CREATE TABLE vehicles(
    id_vehicle INT(11) NOT NULL auto_increment PRIMARY KEY,
    id_user INT(11) NOT NULL,
	model VARCHAR(20),
    color VARCHAR(20),
    km INT(6) NOT NULL,
    mark VARCHAR(20),
	year INT(4) NOT NULL,
	version VARCHAR(15),
	combustible ENUM('Gasoil','Nafta','Gas','Electrico','Biodiesel')
);

DROP TABLE IF EXISTS cars;  -- Autos 
CREATE TABLE cars(
    id_vehicle INT(11) NOT NULL,
    id_user INT(11) NOT NULL,
	doors INT(11) NOT NULL,
	version VARCHAR(15),
	transmission ENUM('Manual','Automatica'),
	direction ENUM('Hidraulica','Asistida','Mecanica')
);

DROP TABLE IF EXISTS motorclicles;  -- Motocicletas
CREATE TABLE motorcicles(
	id_vehicle INT(11) NOT NULL,
    id_user INT(11) NOT NULL,
	type ENUM('Street','Chopper','Standard','Sport','Touring','Scooters'), 
	type_motor VARCHAR(11), -- tiempos
	boot_sistem ENUM('Pedal','Electrico'),
	displacement VARCHAR(11) -- cilindrada  
);

DROP TABLE IF EXISTS trucks; -- Camiones
CREATE TABLE trucks(
	id_vehicle INT(11) NOT NULL,
    id_user INT(11) NOT NULL,
	brake_system ENUM('Disco Delantero','Cinta','Tambor','Llanta'), 
	direction ENUM('Hidraulica','Asistida','Mecanica'),
	capacity INT(11)
);

DROP TABLE IF EXISTS posts; -- Posts
CREATE TABLE posts(
    id_post INT(11) NOT NULL auto_increment PRIMARY KEY,
	id_user INT(11) NOT NULL,
	id_vehicle INT(11) NOT NULL,
    title VARCHAR(30),
    description TEXT, 
	price INT(12)
);

DROP TABLE IF EXISTS answers; -- Respuestas
CREATE TABLE answers(
    id_answer INT(11) NOT NULL auto_increment PRIMARY KEY,
	id_question INT(11) NOT NULL,
    id_user INT(11) NOT NULL,
    text TEXT
);

DROP TABLE IF EXISTS questions; -- Preguntas
CREATE TABLE questions(
    id_question INT(11) NOT NULL auto_increment PRIMARY KEY,
	id_post INT(11) NOT NULL,
    id_user INT(11) NOT NULL,
    text TEXT
    
);


