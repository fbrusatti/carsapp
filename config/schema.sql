-- Integrantes: Arangue Ezequiel - Furlan Javier - Martinez Gustavo - Tissera Adrian
drop database if exists carsapp_development;
create database if not exists carsapp_development;
DROP TABLE IF EXISTS carsapp_development.users; 
CREATE TABLE carsapp_development.users(
    id INT NOT NULL AUTO_INCREMENT,
    email VARCHAR(60) UNIQUE,
    first_name VARCHAR(56),
    last_name VARCHAR(56),
    points int,  
    count_operation int ,
  CONSTRAINT users_pk PRIMARY KEY (id)
);

DROP TABLE IF EXISTS carsapp_development.answers; 
CREATE TABLE carsapp_development.answers(
    id INT NOT NULL AUTO_INCREMENT,
    description VARCHAR(2000),
    id_users int UNIQUE,
  CONSTRAINT answers_pk PRIMARY KEY (id),
  CONSTRAINT user_answers_fk FOREIGN KEY (id_users) REFERENCES users(id));


DROP TABLE IF EXISTS carsapp_development.questions; -- Preguntas
CREATE TABLE carsapp_development.questions(
    id INT NOT NULL AUTO_INCREMENT,
    description VARCHAR(2000),
    id_users int,
    id_answers int,
  CONSTRAINT question_pk PRIMARY KEY (id),
  CONSTRAINT answers_questions_fk FOREIGN KEY (id_answers) REFERENCES answers(id)
  on update cascade on delete cascade
);

DROP TABLE IF EXISTS carsapp_development.vehicles; -- Vehiculos
CREATE TABLE carsapp_development.vehicles(
    patent varchar(6) not null,
    model VARCHAR(25),
    mark VARCHAR(30),
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

DROP TABLE IF EXISTS carsapp_development.motorcicles; -- Motos
CREATE TABLE carsapp_development.motocicles(
    id int AUTO_INCREMENT NOT NULL,
    id_vehicle varchar(6),
    roll int,
    cylinder int,
    CONSTRAINT motorcicle_pk PRIMARY KEY (id),
    CONSTRAINT vehicle_motorcicle_fk foreign key (id_vehicle) REFERENCES vehicles(patent)
    on update cascade on delete cascade
);

DROP TABLE IF EXISTS carsapp_development.posts; -- Publicaciones
CREATE TABLE carsapp_development.posts(
    id INT NOT NULL AUTO_INCREMENT,
    description VARCHAR(2000),
    id_users int,
    id_question  int,
    id_vehicle varchar(6),
    CONSTRAINT posts_pk PRIMARY KEY (id),
    CONSTRAINT users_posts_fk FOREIGN KEY (id_users) REFERENCES users(id),  
    CONSTRAINT vehicle_post_fk FOREIGN KEY (id_vehicle) REFERENCES vehicles(patent),  
    CONSTRAINT question_posts_fk FOREIGN KEY (id_question) REFERENCES questions(id)

);

DROP TABLE IF EXISTS carsapp_development.rates; -- Calificaciones de las publicaciones
CREATE TABLE carsapp_development.rates(
    id INT NOT NULL AUTO_INCREMENT,
    points_p int,
    id_post int not null,
    id_user int not null,
    CONSTRAINT rates_pk PRIMARY KEY (id),
    CONSTRAINT posts_rates_fk foreign key (id_post) REFERENCES posts(id),
    CONSTRAINT users_rates_fk foreign key (id_user) REFERENCES users(id)
    on update cascade on delete cascade

);

DROP TABLE IF EXISTS carsapp_development.punctutations; -- Puntuaciones a los usuarios
CREATE TABLE carsapp_development.punctuations(
    id INT NOT NULL AUTO_INCREMENT,
    point_u int,  
    id_user int,
    CONSTRAINT users_punctuations_fk foreign key (id_user) REFERENCES users(id),
    CONSTRAINT punctuations_pk PRIMARY KEY (id)
);


DROP TABLE IF EXISTS carsapp_development.punctutationsprovides; -- Puntuaciones brindada a los usuarios por otros usuarios
CREATE TABLE carsapp_development.punctuations_provides(
    id_punctuation int,  
    id_user int,
    CONSTRAINT users_punctuationprovides_fk foreign key (id_user) REFERENCES users(id),
    CONSTRAINT punctuation_fk foreign key (id_punctuation) REFERENCES punctuations(id)
);


DROP TABLE IF EXISTS carsapp_development.adresses; -- Direccion
CREATE TABLE carsapp_development.adresses(
    direction varchar(20) NOT NULL,
    CONSTRAINT adresses_pk PRIMARY KEY (direction)
);

DROP TABLE IF EXISTS carsapp_development.city;
CREATE TABLE carsapp_development.city(
    name varchar(20),
    province varchar(20),
    postal_code varchar(5),
    city_direction varchar(20),
    CONSTRAINT adress_city_fk FOREIGN KEY (city_direction) REFERENCES adresses(direction),
    CONSTRAINT city_pk PRIMARY KEY (postal_code) 
);

DROP TABLE IF EXISTS carsapp_development.userAdress; -- tabla que contiene la informacion para referenciar a usuario y direccion
CREATE TABLE carsapp_development.user_adress(
    direction_adress varchar(20) NOT NULL,
    id_user int,
    CONSTRAINT adress_user_u_fk foreign key (direction_adress) REFERENCES adresses(direction),
    CONSTRAINT adress_fk foreign key (id_user) REFERENCES users(id)
);

