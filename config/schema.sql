-- Integrantes: Arangue Ezequiel - Furlan Javier - Martinez Gustavo - Tissera Adrian
drop database if exists carsapp_development;
create database if not exists carsapp_development;
DROP TABLE IF EXISTS carsapp_development.users; 
CREATE TABLE carsapp_development.users(
    id INT NOT NULL AUTO_INCREMENT,
    email VARCHAR(60) UNIQUE,
    first_name VARCHAR(56),
    last_name VARCHAR(56),
    passs varchar(20),
    points int,  
    count_operation int ,
    
  CONSTRAINT users_pk PRIMARY KEY (id)
);

DROP TABLE IF EXISTS carsapp_development.vehicles; -- Vehiculos
CREATE TABLE carsapp_development.vehicles(
    patent varchar(6) not null,
    model VARCHAR(25),
    brand VARCHAR(30),
    id_user int ,
    CONSTRAINT vehicle_usr_fk foreign key (id_user) REFERENCES users(id),
    CONSTRAINT vehicles_pk PRIMARY KEY (patent)
);

DROP TABLE IF EXISTS carsapp_development.trucks; -- Camionetas
CREATE TABLE carsapp_development.trucks(
    id int AUTO_INCREMENT NOT NULL,
    id_vehicle varchar(6),
    count_belt int,
    CONSTRAINT truck_pk PRIMARY KEY (id),
    CONSTRAINT vehicle_truck_fk foreign key (id_vehicle) REFERENCES vehicles(patent)
    on update cascade on delete cascade
);

DROP TABLE IF EXISTS carsapp_development.cars; -- Autos
CREATE TABLE carsapp_development.cars(
    id int AUTO_INCREMENT NOT NULL,
    id_vehicle varchar(6),
    is_coupe boolean,
    CONSTRAINT car_pk PRIMARY KEY (id),
    CONSTRAINT vehicle_car_fk foreign key (id_vehicle) REFERENCES vehicles(patent)
    on update cascade on delete cascade
);

DROP TABLE IF EXISTS carsapp_development.motorcycles; -- Motos
CREATE TABLE carsapp_development.motorcycles(
    id int AUTO_INCREMENT NOT NULL,
    id_vehicle varchar(6),
    wheel_size int,
    engine_size int,
    CONSTRAINT motorcycles_pk PRIMARY KEY (id),
    CONSTRAINT vehicle_motorcycle_fk foreign key (id_vehicle) REFERENCES vehicles(patent)
    on update cascade on delete cascade
);

DROP TABLE IF EXISTS carsapp_development.posts; -- Publicaciones
CREATE TABLE carsapp_development.posts(
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR (50),
    description VARCHAR(2000),
    id_users int,
    id_vehicle varchar(6),
    CONSTRAINT posts_pk PRIMARY KEY (id),
    CONSTRAINT users_posts_fk FOREIGN KEY (id_users) REFERENCES users(id),  
    CONSTRAINT vehicle_post_fk FOREIGN KEY (id_vehicle) REFERENCES vehicles(patent)
);

DROP TABLE IF EXISTS carsapp_development.questions; -- Preguntas
CREATE TABLE carsapp_development.questions(
    id INT NOT NULL AUTO_INCREMENT,
    description VARCHAR(2000),
    id_users int,
    id_posts int,
    CONSTRAINT question_pk PRIMARY KEY (id),
    CONSTRAINT posts_questions_fk foreign key (id_posts) REFERENCES posts(id),
    CONSTRAINT users_questionsfk foreign key (id_users) REFERENCES users(id)
);

DROP TABLE IF EXISTS carsapp_development.answers; 
CREATE TABLE carsapp_development.answers(
    id INT NOT NULL AUTO_INCREMENT,
    description VARCHAR(2000),
    id_users int,
    id_question int,
    CONSTRAINT answers_pk PRIMARY KEY (id),
    CONSTRAINT answers_questions_fk FOREIGN KEY (id_question) REFERENCES questions(id),
    CONSTRAINT user_answers_fk FOREIGN KEY (id_users) REFERENCES users(id)
    on update cascade on delete cascade
);

DROP TABLE IF EXISTS carsapp_development.rates; -- Calificaciones de las publicaciones
CREATE TABLE carsapp_development.rates(
    id INT NOT NULL AUTO_INCREMENT,
    stars int,
    id_post int not null,
    id_user int not null,
    CONSTRAINT rates_pk PRIMARY KEY (id),
    CONSTRAINT posts_rates_fk foreign key (id_post) REFERENCES posts(id),
    CONSTRAINT users_rates_fk foreign key (id_user) REFERENCES users(id)
    on update cascade on delete cascade

);

DROP TABLE IF EXISTS carsapp_development.punctuations; -- Puntuaciones a los usuarios
CREATE TABLE carsapp_development.punctuations(
    id INT NOT NULL AUTO_INCREMENT,
    point_u int,  
    id_user int,
    id_user_receiver int,
    CONSTRAINT users_punctuations_fk foreign key (id_user) REFERENCES users(id),
    CONSTRAINT users_punctuations_receiver_fk foreign key (id_user_receiver) REFERENCES users(id),
    CONSTRAINT punctuations_pk PRIMARY KEY (id)
);

DROP TABLE IF EXISTS carsapp_development.addresses; -- Direccion
CREATE TABLE carsapp_development.addresses(
    id int AUTO_INCREMENT,
    street varchar(40) NOT NULL,
    num int,
    department varchar(20),
    city varchar(20),
    CONSTRAINT addresses_pk PRIMARY KEY (id)
);

DROP TABLE IF EXISTS carsapp_development.users_addresses; -- tabla que contiene la informacion para referenciar a usuario y direccion
CREATE TABLE carsapp_development.users_addresses(
    address_id int,
    user_id int,
    CONSTRAINT addresses_fk foreign key (address_id) REFERENCES addresses(id),
    CONSTRAINT users_fk foreign key (user_id) REFERENCES users(id)
);
