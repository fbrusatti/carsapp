-- Integrantes: Emiliano Guttlein - Santiago Lapiana - Jorge Avedaño - Nicolas Boccolini
DROP schema if exists carsapp_test;
create schema carsapp_test;


DROP TABLE IF EXISTS carsapp_test.users;
CREATE TABLE carsapp_test.users(
		id_user INT(11) NOT NULL AUTO_INCREMENT,
		email VARCHAR(60) UNIQUE,
		first_name VARCHAR(56),
		last_name VARCHAR(56),
		contrasena VARCHAR(60),
		CONSTRAINT pk_users PRIMARY KEY (id_user));

DROP TABLE IF EXISTS carsapp_test.addresses; 
CREATE TABLE carsapp_test.addresses( 
		id_address INT(11) NOT NULL AUTO_INCREMENT, 
		street varchar(56) NOT NULL, 
		city varchar(56), 
		province varchar(56),
		postal_code varchar(6), 
		num INT(10),
		CONSTRAINT pk_addresses PRIMARY KEY (id_address));

DROP TABLE IF EXISTS carsapp_test.users_addresses;
CREATE TABLE carsapp_test.users_addresses(
		id_users_address INT(11) NOT NULL AUTO_INCREMENT, 
		id_user INT(11),
		id_address INT(11),
		CONSTRAINT pk_users_address PRIMARY KEY (id_users_address));
		-- CONSTRAINT fk_addresses FOREIGN KEY (id_addresss) REFERENCES address (id_addresses) ON DELETE CASCADE ON UPDATE CASCADE);
		-- CONSTRAINT fk_users FOREIGN KEY (id_user) REFERENCES users (id_users) ON DELETE CASCADE ON UPDATE CASCADE);

DROP TABLE IF EXISTS carsapp_test.vehicles; 
CREATE TABLE carsapp_test.vehicles(
		id_vehicle INT NOT NULL AUTO_INCREMENT,
		patent VARCHAR(60),
		mark VARCHAR(56),
		model VARCHAR(56),
		id_user INT(11),
		color VARCHAR(20),
		tipo VARCHAR(20),
		cc INT(20),
		isCoupe VARCHAR(10),
		capacity INT(20),
		CONSTRAINT pk_vehicle PRIMARY KEY (id_vehicle));
		-- CONSTRAINT fk_vehicle_user FOREIGN KEY (id_user) REFERENCES users(id_users));

-- DROP TABLE IF EXISTS carsapp_test.cars;
-- CREATE TABLE carsapp_test.cars (
		-- id_cars INT(11) NOT NULL AUTO_INCREMENT,
		-- patent VARCHAR(60),
		-- isCoupe BOOL,
		-- CONSTRAINT pk_cars PRIMARY KEY (id_cars));
		-- CONSTRAINT fk_cars_vehicle FOREIGN KEY (patent) REFERENCES vehicles (patents) ON DELETE CASCADE ON UPDATE CASCADE);

-- DROP TABLE IF EXISTS carsapp_test.motocicles;
-- CREATE TABLE carsapp_test.motocicles (
		-- id_motocicles INT(11) NOT NULL AUTO_INCREMENT,
		-- patent VARCHAR(60),
		-- cc INT,
		-- CONSTRAINT pk_motocicles PRIMARY KEY (id_motocicles));
		-- CONSTRAINT fk_motocicles_vehicle FOREIGN KEY (patent) REFERENCES vehicles (patents) ON DELETE CASCADE ON UPDATE CASCADE);


-- DROP TABLE IF EXISTS carsapp_test.trucks;
-- CREATE TABLE carsapp_test.trucks (
		-- id_trucks INT(11) NOT NULL AUTO_INCREMENT,
		-- patent VARCHAR(60),
		-- capacity VARCHAR(20),
		-- CONSTRAINT pk_trucks PRIMARY KEY (id_trucks));
		-- CONSTRAINT fk_trucks_vehicle FOREIGN KEY (patent) REFERENCES vehicles (patents) ON DELETE CASCADE ON UPDATE CASCADE);


-- DROP TABLE IF EXISTS carsapp_test.others;
-- CREATE TABLE carsapp_test.others (
		-- id_others INT(11) NOT NULL AUTO_INCREMENT,
		-- patent VARCHAR(60),
		-- transmission VARCHAR(12),
		-- CONSTRAINT pk_others PRIMARY KEY (id_others));
		-- CONSTRAINT fk_others_vehicle FOREIGN KEY (patent) REFERENCES vehicles (patents) ON DELETE CASCADE ON UPDATE CASCADE);

DROP TABLE IF EXISTS carsapp_test.answers; 
CREATE TABLE carsapp_test.answers(
		id_answer INT(11) NOT NULL AUTO_INCREMENT,
		id_user INT(11),
		description VARCHAR(3000),
		id_question INT(11),
		CONSTRAINT pk_answers PRIMARY KEY (id_answer));
       -- CONSTRAINT fk_answers_questions FOREIGN KEY (id_question) REFERENCES questions(id_question),
	   -- CONSTRAINT fk_user_answers FOREIGN KEY (id_user) REFERENCES users(id_users));

DROP TABLE IF EXISTS carsapp_test.questions; 
CREATE TABLE carsapp_test.questions(
		id_question INT(11) NOT NULL AUTO_INCREMENT,
		id_user INT(11),
		id_post INT(11),
		description VARCHAR(3000),
		CONSTRAINT pk_question PRIMARY KEY (id_question));
        -- CONSTRAINT fk_posts_questions foreign key (id_post) REFERENCES posts(id_post) ON DELETE CASCADE ON UPDATE CASCADE,
		-- CONSTRAINT fk_answers_questions FOREIGN KEY (id_answer) REFERENCES answers(id_answers) ON DELETE CASCADE ON UPDATE CASCADE,
		-- CONSTRAINT fk_users_questions FOREIGN KEY (id_user) REFERENCES users(id_users) ON DELETE CASCADE ON UPDATE CASCADE);


DROP TABLE IF EXISTS carsapp_test.posts; 
CREATE TABLE carsapp_test.posts(
		id_post INT(11) NOT NULL AUTO_INCREMENT,
		id_user INT(11),
		patent VARCHAR(60),
		description VARCHAR(3000),
		CONSTRAINT pk_posts PRIMARY KEY (id_post));
		-- CONSTRAINT fk_user_post FOREIGN KEY (id_user) REFERENCES users (id_users),  
		-- CONSTRAINT fk_vehicle_post FOREIGN KEY (patent) REFERENCES vehicles (patents)); 

DROP TABLE IF EXISTS carsapp_test.messages;
CREATE TABLE carsapp_test.messages(
		id_message INT(11) NOT NULL AUTO_INCREMENT,
		id_user INT(11),
		description VARCHAR(3000),
		CONSTRAINT pk_message PRIMARY KEY (id_message));

-- DROP TABLE IF EXISTS carsapp_test.qualify; 
-- CREATE TABLE carsapp_test.qualify(
		-- id_qualify INT(11) NOT NULL AUTO_INCREMENT,
		-- id_post INT(11),
		-- id_user INT(11),
		-- points INT,
		-- CONSTRAINT pk_qualify PRIMARY KEY (id_qualify));
		-- CONSTRAINT fk_posts_qualify FOREIGN KEY (id_post) REFERENCES posts(id_posts),
		-- CONSTRAINT fk_users_qualify FOREIGN KEY (id_user) REFERENCES users(id_users));