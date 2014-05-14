-- Integrantes: Arangue Ezequiel - Furlan Javier - Martinez Gustavo - Tissera Adrian
drop database carsapp_development;
create database carsapp_development;
use carsapp_development;
DROP TABLE IF EXISTS users; -- Usuarios
CREATE TABLE users(
    id INT(11) NOT NULL AUTO_INCREMENT,
    email VARCHAR(60) UNIQUE,
    first_name VARCHAR(56),
    last_name VARCHAR(56),
    -- points int,  
    -- count_operation INT NOT NULL,
	CONSTRAINT users_pk PRIMARY KEY (id)
);

DROP TABLE IF EXISTS answers; -- Respuesta
CREATE TABLE answers(
    id INT(11) NOT NULL AUTO_INCREMENT,
    description VARCHAR(2000),
     id_users int(11),
  CONSTRAINT answers_pk PRIMARY KEY (id),
  CONSTRAINT user_answers_fk FOREIGN KEY (id_users) REFERENCES users(id)
);

DROP TABLE IF EXISTS questions; -- Preguntas
CREATE TABLE questions(
    id INT(11) NOT NULL AUTO_INCREMENT,
    description VARCHAR(2000),
    id_users int(11),
    id_answers int(11),
  CONSTRAINT question_pk PRIMARY KEY (id),
  CONSTRAINT answers_questions_fk FOREIGN KEY (id_answers) REFERENCES answers(id)
  on update cascade on delete cascade

);

DROP TABLE IF EXISTS puntutations; -- Puntuaciones a los usuarios
CREATE TABLE puntuations(
    id INT(11) NOT NULL AUTO_INCREMENT,
    point_u int(11),  
    id_user int(11),
    CONSTRAINT users_puntuations_fk foreign key (id_user) REFERENCES users(id),
    CONSTRAINT puntuations_pk PRIMARY KEY (id)
);

DROP TABLE IF EXISTS puntutationsprovides; -- Puntuaciones brindada a los usuarios por otros usuarios
CREATE TABLE puntuations_provides(
    id_puntuation int(11),  
    id_user int(11),
    CONSTRAINT users_puntuationprovides_fk foreign key (id_user) REFERENCES users(id),
    CONSTRAINT puntuation_fk foreign key (id_puntuation) REFERENCES puntuations(id)
);

DROP TABLE IF EXISTS vehicles; -- Vehiculos
CREATE TABLE vehicles(
    patent varchar(6) NOT NULL,
    model VARCHAR(25),
    mark VARCHAR(30),
    id_user int(11),
    CONSTRAINT user_vehicle_fk foreign key (id_user) REFERENCES users(id),
    CONSTRAINT vehicles_pk PRIMARY KEY (patent)
);

DROP TABLE IF EXISTS truks; -- Camionetas
CREATE TABLE trucks(
    id int(11) AUTO_INCREMENT NOT NULL,
    id_vehicle varchar(6),
    count_belt int,
    CONSTRAINT truck_pk PRIMARY KEY (id),
    CONSTRAINT vehicle_truck_fk foreign key (id_vehicle) REFERENCES vehicles(patent)
);

DROP TABLE IF EXISTS cars; -- Autos
CREATE TABLE cars(
    id int(11) AUTO_INCREMENT NOT NULL,
    id_vehicle varchar(6),
    is_coupe boolean,
    CONSTRAINT vehicle_car_fk foreign key (id_vehicle) REFERENCES vehicles(patent),
    CONSTRAINT truck_pk PRIMARY KEY (id)
);

DROP TABLE IF EXISTS motocicles; -- Autos
CREATE TABLE motocicles(
    id int(11) AUTO_INCREMENT NOT NULL,
    id_vehicle varchar(6),
    roll int, -- rodado
    cylinder int(3), -- cilindrada
    CONSTRAINT vehicle_motocicle_fk foreign key (id_vehicle) REFERENCES vehicles(patent),
    CONSTRAINT motocicle_pk PRIMARY KEY (id)
);


DROP TABLE IF EXISTS adress; -- Direccion
CREATE TABLE adress(
    direction varchar(20) NOT NULL,
    city varchar(20),
    province varchar(20),
    postal_code varchar(5),
    CONSTRAINT adresss_pk PRIMARY KEY (direction)
);

DROP TABLE IF EXISTS user_adress; -- tabla que contiene la informacion para referenciar a usuario y direccion
CREATE TABLE user_adress(
    direction_adress varchar(20) NOT NULL,
    id_user int(11),
    CONSTRAINT adress_user_u_fk foreign key (direction_adress) REFERENCES adress(direction),
    CONSTRAINT adress_user_d_fk foreign key (id_user) REFERENCES users(id)

);

DROP TABLE IF EXISTS posts; -- Publicaciones
CREATE TABLE posts(
    id INT(11) NOT NULL AUTO_INCREMENT,
    description VARCHAR(2000),
    id_users int(11),
    id_question  int(11),
    id_vehicle varchar(6),
   CONSTRAINT users_posts_fk FOREIGN KEY (id_users) REFERENCES users(id),
   CONSTRAINT vehicle_post_fk FOREIGN KEY (id_vehicle) REFERENCES vehicles(patent),  
   CONSTRAINT question_posts_fk FOREIGN KEY (id_question) REFERENCES questions(id),  
   CONSTRAINT posts_pk PRIMARY KEY (id)

);

DROP TABLE IF EXISTS rates; -- Calificaciones de las publicaciones
CREATE TABLE rates(
    id INT(11) NOT NULL AUTO_INCREMENT,
    points_p int,
    id_post int not null,
    id_user int not null,
  CONSTRAINT rates_pk PRIMARY KEY (id),
  CONSTRAINT posts_rates_fk foreign key (id_post) REFERENCES posts(id),
  CONSTRAINT users_rates_fk foreign key (id_user) REFERENCES users(id)
  on update cascade on delete cascade

);
