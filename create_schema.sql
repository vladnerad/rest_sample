/* Базу данных должен создать администратор СУБД и назвать её как удобно.
У администратора наверняка есть паттерн для присвоения имен БД от кандидатов */

DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users (
	login VARCHAR(255) PRIMARY KEY,
	pass VARCHAR(255) NOT NULL,
	name VARCHAR(255) NOT NULL DEFAULT 'ROLE_USER'
);

DROP TABLE IF EXISTS roles CASCADE;

CREATE TABLE roles (
	id SERIAL PRIMARY KEY,
	name VARCHAR(255) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS user_role CASCADE;

CREATE TABLE user_role (
	u_login VARCHAR(255),
	role_id INTEGER,
	CONSTRAINT fk_u_log
		FOREIGN KEY (u_login)
  			REFERENCES users(login)
  				ON DELETE CASCADE,
	CONSTRAINT fk_roles_id
  		FOREIGN KEY (role_id)
  			REFERENCES roles(id)
  				ON DELETE CASCADE,
  	PRIMARY KEY (u_login, role_id)
);

INSERT INTO roles (name) VALUES ('ROLE_ADMIN'),
                                ('ROLE_USER'),
                                ('ROLE_OPERATOR'),
                                ('ROLE_ANALYTIC');