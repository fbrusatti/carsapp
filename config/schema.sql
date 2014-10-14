-- Integrantes: Cardozo Sergio - Chaves Angelo - Chaves Maximiliano

DROP TABLE IF EXISTS users; -- Usuarios
CREATE TABLE users(
    id INT(11) NOT NULL AUTO_INCREMENT,
    email VARCHAR(60) UNIQUE,
    first_name VARCHAR(56),
    last_name VARCHAR(56),
    is_admin TINYINT(1),
  CONSTRAINT users_pk PRIMARY KEY (id)
);

DROP TABLE IF EXISTS addresses;
CREATE TABLE addresses(
    id INT(11) NOT NULL AUTO_INCREMENT,
    user_id INT(11),
    street VARCHAR(120),
    address_number INT(5),
  CONSTRAINT addresses_pk PRIMARY KEY (id)
);

DROP TABLE IF EXISTS vehicles;
CREATE TABLE vehicles(
    id INT(11) NOT NULL AUTO_INCREMENT,
    user_id INT(11),
    name VARCHAR(56),
    model INT(4),
    km INT(7),
  CONSTRAINT vehicles_pk PRIMARY KEY (id)
);

DROP TABLE IF EXISTS cars;
CREATE TABLE cars(
    vehicle_id INT(11) NOT NULL AUTO_INCREMENT,
    type ENUM('sedan','compact','coupe','wagon','sports','other') DEFAULT 'other',
  CONSTRAINT cars_pk PRIMARY KEY (vehicle_id)
);


DROP TABLE IF EXISTS trucks;
CREATE TABLE trucks(
    vehicle_id INT(11),
    type ENUM('light','medium','heavy','other') DEFAULT 'other',
  CONSTRAINT trucks_pk PRIMARY KEY (vehicle_id)
);

DROP TABLE IF EXISTS motorcycles;
CREATE TABLE motorcycles(
    vehicle_id INT(11),
    type ENUM('standart','cruiser','sport','touring','scooter','off-road','other') DEFAULT 'other',
  CONSTRAINT motorcycles_pk PRIMARY KEY (vehicle_id)
);

DROP TABLE IF EXISTS posts;
CREATE TABLE posts(
    id INT(11) NOT NULL AUTO_INCREMENT,
    user_id INT(11),
    vehicle_id INT(11),
    price INT(7) NOT NULL,
    description VARCHAR(200) NOT NULL,
    rate ENUM('0','1','2','3','4','5') DEFAULT '0',
    total_rating INT(11),
    sum_rate INT(11),
  CONSTRAINT posts_pk PRIMARY KEY (id)
);

DROP TABLE IF EXISTS questions;
CREATE TABLE questions(
    id INT(11) NOT NULL AUTO_INCREMENT,
    user_id INT(11),
    post_id INT(11),
    description VARCHAR(200) NOT NULL,
    active TINYINT(1), 
  CONSTRAINT questions_pk PRIMARY KEY (id)
);

DROP TABLE IF EXISTS answers;
CREATE TABLE answers(
    id INT(11) NOT NULL AUTO_INCREMENT,
    question_id INT(11),
    user_id INT(11),
    post_id INT(11),
    description VARCHAR(200) NOT NULL,
  CONSTRAINT answers_pk PRIMARY KEY (id)
);

/* Inserting values */

INSERT INTO users (first_name,last_name,email,is_admin) VALUES("Admin","Inistrator","admin@admin.com",1); 
INSERT INTO addresses (id,user_id,street,address_number) VALUES(1,1,"Street A",10);