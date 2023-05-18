DROP DATABASE primeteostats;
DROP USER pib2;
DROP USER pri_meteostats;
CREATE DATABASE primeteostats;

USE primeteostats;

CREATE TABLE IF NOT EXISTS `usuario` (
	`id_usuario` INT(10) AUTO_INCREMENT,
	`usuario` VARCHAR(100) NOT NULL,
	`nombre` VARCHAR(100) NOT NULL,
	`apellido` VARCHAR(100) NOT NULL,
    `contrasena` VARCHAR(100) NOT NULL,
	`email` VARCHAR(100) NOT NULL,
	`rol` INT(5) NOT NULL,
	PRIMARY KEY (`id_usuario`)
);

CREATE TABLE IF NOT EXISTS `sensores` (
	`id_sensor` INT(10) AUTO_INCREMENT,
	`tipo_sensor` VARCHAR(100) NOT NULL,
	`fecha` TIMESTAMP NOT NULL,
	`lectura1` VARCHAR(100) NOT NULL,
    `usuario` INT(10) NOT NULL,
	PRIMARY KEY (`id_sensor`),
    FOREIGN KEY(`usuario`) REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `mensajes` (
	`id_mensaje` INT(10) AUTO_INCREMENT,
	`descripcion` VARCHAR(100) NOT NULL,
	`fecha` TIMESTAMP NOT NULL,
	`status` INT(10) NOT NULL,
	PRIMARY KEY (`id_mensaje`)
);

CREATE TABLE IF NOT EXISTS `mensaje` (
	`usuario_from` INT(10) NOT NULL,
	`usuario_to` INT(10) NOT NULL,
	`mensaje` INT(10) NOT NULL,
    FOREIGN KEY(`usuario_from`) REFERENCES usuario(id_usuario) ON DELETE CASCADE,
	FOREIGN KEY(`usuario_to`) REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY(`mensaje`) REFERENCES mensajes(id_mensaje) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `admin` (
	`id_admin` INT(10) NOT NULL,
	`usuario` INT(10) NOT NULL,
	FOREIGN KEY(`id_admin`) REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY(`usuario`) REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `tecnico` (
	`id_tecnico` INT(10) NOT NULL,
	`usuario` INT(10) NOT NULL,
	FOREIGN KEY(`id_tecnico`) REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY(`usuario`) REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

INSERT IGNORE INTO usuario(id_usuario, usuario, nombre, apellido, contrasena, email, rol) 
VALUES (0, "admin", "admin", "admin", "admin", "admin@gmail.com", 2);

INSERT IGNORE INTO usuario(id_usuario, usuario, nombre, apellido, contrasena, email, rol) 
VALUES (0, "tecnico", "tecnico", "tecnico", "tecnico", "tecnico@gmail.com", 3);

# PRIMERO REGISTRAR UN USUARIO EN LA BBDD PARA PODER USARLO (COMO CLIENTE) (EL ID SERA 3)
INSERT IGNORE INTO `sensores` (`id_sensor`, `tipo_sensor`, `fecha`, `lectura1`, `usuario`) VALUES
(0, 'BMP_presion', '2023-05-16 10:02:00', '50', 3),
(0, 'DHT11_temp', '2023-05-16 10:00:28', '12', 3),
(0, 'LDR', '2023-05-16 10:03:05', '8', 3),
(0, 'LDR', '2023-05-16 10:03:34', '20', 3),
(0, 'DHT11_humedad', '2023-05-16 10:14:39', '22', 3);

CREATE USER 'pib2' IDENTIFIED BY 'pib218';
GRANT ALL PRIVILEGES ON *.* TO 'pib2' REQUIRE NONE WITH GRANT OPTION MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0;
GRANT ALL PRIVILEGES ON `meteo`.* TO 'pib2';

CREATE USER 'pri_meteostats' IDENTIFIED BY 'pri_meteostats';
GRANT SELECT, INSERT, UPDATE, DELETE, FILE ON *.* TO 'pri_meteostats' REQUIRE NONE WITH MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0;


