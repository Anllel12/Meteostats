-- USANDO COMANDO C:\xampp\mysql\bin\mysqldump -P 3306 -h 195.235.211.197 -u pri_meteostats -p primeteostats > backup.sql
-- MariaDB dump 10.19  Distrib 10.4.27-MariaDB, for Win64 (AMD64)
--
-- Host: 195.235.211.197    Database: primeteostats
-- ------------------------------------------------------
-- Server version	10.6.12-MariaDB-0ubuntu0.22.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `id_admin` int(10) NOT NULL,
  `usuario` int(10) NOT NULL,
  KEY `id_admin` (`id_admin`),
  KEY `usuario` (`usuario`),
  CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`id_admin`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE,
  CONSTRAINT `admin_ibfk_2` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,3),(1,5),(7,14),(8,15),(7,16),(8,17),(7,18),(6,19),(1,20);
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mensaje`
--

DROP TABLE IF EXISTS `mensaje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mensaje` (
  `usuario_from` int(10) NOT NULL,
  `usuario_to` int(10) NOT NULL,
  `mensaje` int(10) NOT NULL,
  KEY `usuario_from` (`usuario_from`),
  KEY `usuario_to` (`usuario_to`),
  KEY `mensaje` (`mensaje`),
  CONSTRAINT `mensaje_ibfk_1` FOREIGN KEY (`usuario_from`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE,
  CONSTRAINT `mensaje_ibfk_2` FOREIGN KEY (`usuario_to`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE,
  CONSTRAINT `mensaje_ibfk_3` FOREIGN KEY (`mensaje`) REFERENCES `mensajes` (`id_mensaje`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mensaje`
--

LOCK TABLES `mensaje` WRITE;
/*!40000 ALTER TABLE `mensaje` DISABLE KEYS */;
INSERT INTO `mensaje` VALUES (2,1,2),(1,2,3);
/*!40000 ALTER TABLE `mensaje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mensajes`
--

DROP TABLE IF EXISTS `mensajes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mensajes` (
  `id_mensaje` int(10) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(100) NOT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `status` int(10) NOT NULL,
  PRIMARY KEY (`id_mensaje`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mensajes`
--

LOCK TABLES `mensajes` WRITE;
/*!40000 ALTER TABLE `mensajes` DISABLE KEYS */;
INSERT INTO `mensajes` VALUES (2,'hola soy tecnico','2023-05-18 08:54:12',0),(3,'hola soy admin','2023-05-18 08:54:34',0);
/*!40000 ALTER TABLE `mensajes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sensores`
--

DROP TABLE IF EXISTS `sensores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sensores` (
  `id_sensor` int(10) NOT NULL AUTO_INCREMENT,
  `tipo_sensor` varchar(100) NOT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `lectura1` varchar(100) NOT NULL,
  `usuario` int(10) NOT NULL,
  PRIMARY KEY (`id_sensor`),
  KEY `usuario` (`usuario`),
  CONSTRAINT `sensores_ibfk_1` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1324 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensores`
--

LOCK TABLES `sensores` WRITE;
/*!40000 ALTER TABLE `sensores` DISABLE KEYS */;
INSERT INTO `sensores` VALUES (1,'BMP_presion','2023-05-14 08:02:00','50',3),(3,'DHT11_temp','2023-05-14 08:00:28','12',3),(1137,'LDR','2023-05-14 08:03:05','8',3),(1138,'LDR','2023-05-14 08:03:34','20',3),(1162,'DHT11_humedad','2023-05-14 08:14:39','22',3),(1165,'BMP_presion','2023-05-16 08:02:00','50',3),(1166,'DHT11_temp','2023-05-16 08:00:28','12',3),(1167,'LDR','2023-05-16 08:03:05','8',3),(1168,'LDR','2023-05-16 08:03:34','20',3),(1169,'DHT11_humedad','2023-05-16 08:14:39','22',3),(1170,'BMP_presion','2023-05-23 14:05:57','929.90',3),(1171,'BMP_presion','2023-05-23 14:07:03','929.87',3),(1172,'BMP_presion','2023-05-23 14:08:34','929.90',3),(1173,'BMP_presion','2023-05-23 14:11:18','929.89',3),(1174,'BMP_presion','2023-05-23 14:11:58','929.87',3),(1175,'BMP_presion','2023-05-23 14:14:29','929.78',3),(1176,'DHT11_humedad','2023-05-23 14:15:10','24.000',1),(1177,'DHT11_temp','2023-05-23 14:15:13','26.000',1),(1178,'BMP_presion','2023-05-23 14:15:13','929.76',3),(1179,'DHT11_humedad','2023-05-23 14:17:51','23.000',1),(1180,'DHT11_temp','2023-05-23 14:17:53','26.000',1),(1181,'LDR','2023-05-23 14:18:09','0.000',1),(1182,'DHT11_humedad','2023-05-23 14:18:51','23.000',1),(1183,'LDR','2023-05-23 14:18:52','0.000',1),(1184,'DHT11_temp','2023-05-23 14:18:54','26.000',1),(1185,'LDR','2023-05-23 14:23:28','53.000',1),(1186,'DHT11_humedad','2023-05-23 14:23:29','23.000',1),(1187,'DHT11_temp','2023-05-23 14:23:31','25.000',1),(1188,'BMP_presion','2023-05-24 22:00:00','25',1),(1189,'BMP_presion','2023-05-10 08:02:00','50',14),(1190,'DHT11_temp','2023-05-10 08:00:28','12',14),(1191,'LDR','2023-05-10 08:03:05','8',14),(1192,'LDR','2023-05-10 08:03:34','20',14),(1193,'DHT11_humedad','2023-05-10 08:14:39','22',14),(1194,'BMP_presion','2023-05-11 08:02:00','50',14),(1195,'DHT11_temp','2023-05-11 08:00:28','12',14),(1196,'LDR','2023-05-11 08:03:05','8',14),(1197,'LDR','2023-05-11 08:03:34','20',14),(1198,'DHT11_humedad','2023-05-11 08:14:39','22',14),(1199,'BMP_presion','2023-05-12 08:02:00','50',14),(1200,'DHT11_temp','2023-05-12 08:00:28','12',14),(1201,'LDR','2023-05-12 08:03:05','8',14),(1202,'LDR','2023-05-12 08:03:34','20',14),(1203,'DHT11_humedad','2023-05-12 08:14:39','22',14),(1204,'BMP_presion','2023-05-10 08:02:00','50',15),(1205,'DHT11_temp','2023-05-10 08:00:28','12',15),(1206,'LDR','2023-05-10 08:03:05','8',15),(1207,'LDR','2023-05-10 08:03:34','20',15),(1208,'DHT11_humedad','2023-05-10 08:14:39','22',15),(1209,'BMP_presion','2023-05-11 08:02:00','50',15),(1210,'DHT11_temp','2023-05-11 08:00:28','12',15),(1211,'LDR','2023-05-11 08:03:05','8',15),(1212,'LDR','2023-05-11 08:03:34','20',15),(1213,'DHT11_humedad','2023-05-11 08:14:39','22',15),(1214,'BMP_presion','2023-05-12 08:02:00','50',15),(1215,'DHT11_temp','2023-05-12 08:00:28','12',15),(1216,'LDR','2023-05-12 08:03:05','8',15),(1217,'LDR','2023-05-12 08:03:34','20',15),(1218,'DHT11_humedad','2023-05-12 08:14:39','22',15),(1219,'BMP_presion','2023-05-10 08:02:00','50',16),(1220,'DHT11_temp','2023-05-10 08:00:28','12',16),(1221,'LDR','2023-05-10 08:03:05','8',16),(1222,'LDR','2023-05-10 08:03:34','20',16),(1223,'DHT11_humedad','2023-05-10 08:14:39','22',16),(1224,'BMP_presion','2023-05-11 08:02:00','50',16),(1225,'DHT11_temp','2023-05-11 08:00:28','12',16),(1226,'LDR','2023-05-11 08:03:05','8',16),(1227,'LDR','2023-05-11 08:03:34','20',16),(1228,'DHT11_humedad','2023-05-11 08:14:39','22',16),(1229,'BMP_presion','2023-05-12 08:02:00','50',16),(1230,'DHT11_temp','2023-05-12 08:00:28','12',16),(1231,'LDR','2023-05-12 08:03:05','8',16),(1232,'LDR','2023-05-12 08:03:34','20',16),(1233,'DHT11_humedad','2023-05-12 08:14:39','22',16),(1234,'BMP_presion','2023-05-10 08:02:00','50',17),(1235,'DHT11_temp','2023-05-10 08:00:28','12',17),(1236,'LDR','2023-05-10 08:03:05','8',17),(1237,'LDR','2023-05-10 08:03:34','20',17),(1238,'DHT11_humedad','2023-05-10 08:14:39','22',17),(1239,'BMP_presion','2023-05-11 08:02:00','50',17),(1240,'DHT11_temp','2023-05-11 08:00:28','12',17),(1241,'LDR','2023-05-11 08:03:05','8',17),(1242,'LDR','2023-05-11 08:03:34','20',17),(1243,'DHT11_humedad','2023-05-11 08:14:39','22',17),(1244,'BMP_presion','2023-05-12 08:02:00','50',17),(1245,'DHT11_temp','2023-05-12 08:00:28','12',17),(1246,'LDR','2023-05-12 08:03:05','8',17),(1247,'LDR','2023-05-12 08:03:34','20',17),(1248,'DHT11_humedad','2023-05-12 08:14:39','22',17),(1249,'BMP_presion','2023-05-10 08:02:00','50',18),(1250,'DHT11_temp','2023-05-10 08:00:28','12',18),(1251,'LDR','2023-05-10 08:03:05','8',18),(1252,'LDR','2023-05-10 08:03:34','20',18),(1253,'DHT11_humedad','2023-05-10 08:14:39','22',18),(1254,'BMP_presion','2023-05-11 08:02:00','50',18),(1255,'DHT11_temp','2023-05-11 08:00:28','12',18),(1256,'LDR','2023-05-11 08:03:05','8',18),(1257,'LDR','2023-05-11 08:03:34','20',18),(1258,'DHT11_humedad','2023-05-11 08:14:39','22',18),(1259,'BMP_presion','2023-05-12 08:02:00','50',18),(1260,'DHT11_temp','2023-05-12 08:00:28','12',18),(1261,'LDR','2023-05-12 08:03:05','8',18),(1262,'LDR','2023-05-12 08:03:34','20',18),(1263,'DHT11_humedad','2023-05-12 08:14:39','22',18),(1264,'BMP_presion','2023-05-10 08:02:00','50',19),(1265,'DHT11_temp','2023-05-10 08:00:28','12',19),(1266,'LDR','2023-05-10 08:03:05','8',19),(1267,'LDR','2023-05-10 08:03:34','20',19),(1268,'DHT11_humedad','2023-05-10 08:14:39','22',19),(1269,'BMP_presion','2023-05-11 08:02:00','50',19),(1270,'DHT11_temp','2023-05-11 08:00:28','12',19),(1271,'LDR','2023-05-11 08:03:05','8',19),(1272,'LDR','2023-05-11 08:03:34','20',19),(1273,'DHT11_humedad','2023-05-11 08:14:39','22',19),(1274,'BMP_presion','2023-05-12 08:02:00','50',19),(1275,'DHT11_temp','2023-05-12 08:00:28','12',19),(1276,'LDR','2023-05-12 08:03:05','8',19),(1277,'LDR','2023-05-12 08:03:34','20',19),(1278,'DHT11_humedad','2023-05-12 08:14:39','22',19),(1279,'BMP_presion','2023-05-10 08:02:00','50',20),(1280,'DHT11_temp','2023-05-10 08:00:28','12',20),(1281,'LDR','2023-05-10 08:03:05','8',20),(1282,'LDR','2023-05-10 08:03:34','20',20),(1283,'DHT11_humedad','2023-05-10 08:14:39','22',20),(1284,'BMP_presion','2023-05-11 08:02:00','50',20),(1285,'DHT11_temp','2023-05-11 08:00:28','12',20),(1286,'LDR','2023-05-11 08:03:05','8',20),(1287,'LDR','2023-05-11 08:03:34','20',20),(1288,'DHT11_humedad','2023-05-11 08:14:39','22',20),(1289,'BMP_presion','2023-05-12 08:02:00','50',20),(1290,'DHT11_temp','2023-05-12 08:00:28','12',20),(1291,'LDR','2023-05-12 08:03:05','8',20),(1292,'LDR','2023-05-12 08:03:34','20',20),(1293,'DHT11_humedad','2023-05-12 08:14:39','22',20),(1294,'BMP_presion','2023-05-10 08:02:00','50',3),(1295,'DHT11_temp','2023-05-10 08:00:28','12',3),(1296,'LDR','2023-05-10 08:03:05','8',3),(1297,'LDR','2023-05-10 08:03:34','20',3),(1298,'DHT11_humedad','2023-05-10 08:14:39','22',3),(1299,'BMP_presion','2023-05-11 08:02:00','50',3),(1300,'DHT11_temp','2023-05-11 08:00:28','12',3),(1301,'LDR','2023-05-11 08:03:05','8',3),(1302,'LDR','2023-05-11 08:03:34','20',3),(1303,'DHT11_humedad','2023-05-11 08:14:39','22',3),(1304,'BMP_presion','2023-05-12 08:02:00','50',3),(1305,'DHT11_temp','2023-05-12 08:00:28','12',3),(1306,'LDR','2023-05-12 08:03:05','8',3),(1307,'LDR','2023-05-12 08:03:34','20',3),(1308,'DHT11_humedad','2023-05-12 08:14:39','22',3),(1309,'BMP_presion','2023-05-10 08:02:00','50',5),(1310,'DHT11_temp','2023-05-10 08:00:28','12',5),(1311,'LDR','2023-05-10 08:03:05','8',5),(1312,'LDR','2023-05-10 08:03:34','20',5),(1313,'DHT11_humedad','2023-05-10 08:14:39','22',5),(1314,'BMP_presion','2023-05-11 08:02:00','50',5),(1315,'DHT11_temp','2023-05-11 08:00:28','12',5),(1316,'LDR','2023-05-11 08:03:05','8',5),(1317,'LDR','2023-05-11 08:03:34','20',5),(1318,'DHT11_humedad','2023-05-11 08:14:39','22',5),(1319,'BMP_presion','2023-05-12 08:02:00','50',5),(1320,'DHT11_temp','2023-05-12 08:00:28','12',5),(1321,'LDR','2023-05-12 08:03:05','8',5),(1322,'LDR','2023-05-12 08:03:34','20',5),(1323,'DHT11_humedad','2023-05-12 08:14:39','22',5);
/*!40000 ALTER TABLE `sensores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tecnico`
--

DROP TABLE IF EXISTS `tecnico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tecnico` (
  `id_tecnico` int(10) NOT NULL,
  `usuario` int(10) NOT NULL,
  KEY `id_tecnico` (`id_tecnico`),
  KEY `usuario` (`usuario`),
  CONSTRAINT `tecnico_ibfk_1` FOREIGN KEY (`id_tecnico`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE,
  CONSTRAINT `tecnico_ibfk_2` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tecnico`
--

LOCK TABLES `tecnico` WRITE;
/*!40000 ALTER TABLE `tecnico` DISABLE KEYS */;
INSERT INTO `tecnico` VALUES (2,3),(2,5),(2,14),(10,15),(12,16),(13,17),(13,18),(11,19),(13,20);
/*!40000 ALTER TABLE `tecnico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `id_usuario` int(10) NOT NULL AUTO_INCREMENT,
  `usuario` varchar(100) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `apellido` varchar(100) NOT NULL,
  `contrasena` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `rol` int(5) NOT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'admin','admin','admin','admin','admin@gmail.com',2),(2,'tecnico','tecnico','tecnico','tecnico','tecnico@gmail.com',3),(3,'cl','cll','clll','cl','cl@email.com',1),(5,'jk','jk','jk','jk','jk',1),(6,'admin2','admin2','admin2','admin2','admin2@gmail.com',2),(7,'admin3','admin3','admin3','admin3','admin3@gmail.com',2),(8,'admin4','admin4','admin4','admin4','admin4@gmail.com',2),(9,'admin5','admin5','admin5','admin5','admin5@gmail.com',2),(10,'tecnico2','tecnico2','tecnico2','tecnico2','tecnico2@gmail.com',3),(11,'tecnico3','tecnico3','tecnico3','tecnico3','tecnico3@gmail.com',3),(12,'tecnico4','tecnico4','tecnico4','tecnico4','tecnico4@gmail.com',3),(13,'tecnico5','tecnico5','tecnico5','tecnico5','tecnico5@gmail.com',3),(14,'angel','angel','angel','angel','angel',1),(15,'jorge','jorge','jorge','jorge','jorge',1),(16,'alex','alex','alex','alex','alex',1),(17,'felipe','felipe','felipe','felipe','felipe',1),(18,'josejuan','Jose Juan','Gonzalez','jsjuan','josejuan@gmail.com',1),(19,'rober','roberto','carlos','rr','rober@gmail.com',1),(20,'rubenm','ruben','mingo','rrr','rubenmm@gmail.com',1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

CREATE USER 'pib2' IDENTIFIED BY 'pib218';
GRANT ALL PRIVILEGES ON *.* TO 'pib2' REQUIRE NONE WITH GRANT OPTION MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0;
GRANT ALL PRIVILEGES ON `meteo`.* TO 'pib2';

CREATE USER 'pri_meteostats' IDENTIFIED BY 'pri_meteostats';
GRANT SELECT, INSERT, UPDATE, DELETE, FILE ON *.* TO 'pri_meteostats' REQUIRE NONE WITH MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0;


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-28 18:39:16
