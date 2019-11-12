-- MySQL dump 10.13  Distrib 5.7.27, for Linux (x86_64)
--
-- Host: localhost    Database: ControleDeEmprestimos
-- ------------------------------------------------------
-- Server version	5.7.27-0ubuntu0.18.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Aluno`
--

DROP TABLE IF EXISTS `Aluno`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Aluno` (
  `matricula` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `estadoMatricula` tinyint(1) NOT NULL,
  `email` varchar(45) NOT NULL,
  `telefone` varchar(45) DEFAULT NULL,
  `sobrenome` varchar(45) NOT NULL,
  `penalidade` datetime DEFAULT NULL,
  PRIMARY KEY (`matricula`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Aluno`
--

LOCK TABLES `Aluno` WRITE;
/*!40000 ALTER TABLE `Aluno` DISABLE KEYS */;
INSERT INTO `Aluno` VALUES (2939,'Suyan',1,'suyan@email.com','48 99999-9991','Moriel',NULL),(6433,'Lucas',1,'lucas@email.com','48 99999-9994','Thiesen',NULL),(7760,'Marcone',1,'marcone@email.com','48 99999-9993','Augusto',NULL),(12814,'Joao',1,'joao@email.com','48 99999-9995','Victor',NULL),(13181,'Vinicius',1,'vinicius@email.com','48 99999-9990','Luz',NULL),(17927,'Guilherme',1,'guilherme@email.com','48 99999-9996','Roque',NULL),(21570,'Fabiano',1,'fabiano@email.com','48 99999-9997','Kraemer',NULL),(36210,'Douglas',1,'douglas@email.com','48 99999-9998','Amorim',NULL),(36300,'Allex',1,'allex@email.com','48 99999-9999','Magno',NULL),(44920,'Natalia',1,'natalia@email.com','48 99999-9992','Adriana',NULL);
/*!40000 ALTER TABLE `Aluno` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Atividade`
--

DROP TABLE IF EXISTS `Atividade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Atividade` (
  `idAtividade` int(11) NOT NULL AUTO_INCREMENT,
  `nomeAtividade` varchar(45) NOT NULL,
  PRIMARY KEY (`idAtividade`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1 COMMENT='\n';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Atividade`
--

LOCK TABLES `Atividade` WRITE;
/*!40000 ALTER TABLE `Atividade` DISABLE KEYS */;
INSERT INTO `Atividade` VALUES (1,'TCC'),(2,'Pesquisa'),(3,'Extensão'),(4,'Ensino');
/*!40000 ALTER TABLE `Atividade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Atividade_has_Aluno`
--

DROP TABLE IF EXISTS `Atividade_has_Aluno`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Atividade_has_Aluno` (
  `Atividade_idAtividade` int(11) NOT NULL,
  `Aluno_matricula` int(11) NOT NULL,
  PRIMARY KEY (`Atividade_idAtividade`,`Aluno_matricula`),
  KEY `fk_Atividade_has_Aluno_Aluno1_idx` (`Aluno_matricula`),
  KEY `fk_Atividade_has_Aluno_Atividade_idx` (`Atividade_idAtividade`),
  CONSTRAINT `fk_Atividade_has_Aluno_Aluno1` FOREIGN KEY (`Aluno_matricula`) REFERENCES `Aluno` (`matricula`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Atividade_has_Aluno_Atividade` FOREIGN KEY (`Atividade_idAtividade`) REFERENCES `Atividade` (`idAtividade`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Atividade_has_Aluno`
--

LOCK TABLES `Atividade_has_Aluno` WRITE;
/*!40000 ALTER TABLE `Atividade_has_Aluno` DISABLE KEYS */;
INSERT INTO `Atividade_has_Aluno` VALUES (1,2939),(4,2939),(2,6433),(4,6433),(3,7760),(4,7760),(1,12814),(4,12814),(2,13181),(4,13181),(3,17927),(4,17927),(1,21570),(4,21570),(2,36210),(4,36210),(3,36300),(4,36300),(1,44920),(4,44920);
/*!40000 ALTER TABLE `Atividade_has_Aluno` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Componentes`
--

DROP TABLE IF EXISTS `Componentes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Componentes` (
  `idComponentes` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `modelo` varchar(45) DEFAULT NULL,
  `Equipamentos_patrimonio` int(11) NOT NULL,
  PRIMARY KEY (`idComponentes`,`Equipamentos_patrimonio`),
  KEY `fk_Componentes_Equipamentos1_idx` (`Equipamentos_patrimonio`),
  CONSTRAINT `fk_Componentes_Equipamentos1` FOREIGN KEY (`Equipamentos_patrimonio`) REFERENCES `Equipamentos` (`patrimonio`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Componentes`
--

LOCK TABLES `Componentes` WRITE;
/*!40000 ALTER TABLE `Componentes` DISABLE KEYS */;
INSERT INTO `Componentes` VALUES (1,'Fonte ','DC 0 ~ 12 V',1),(2,'Fonte','AC 0 ~ 220V',2),(3,'Multímetro','Minipa',3),(4,'HDMI p/ DVI','15 cm',4),(5,'Ferro de solda','Hikari',5),(6,'Soprador','Bosch',6),(7,'Hobby','Bosch',7),(8,'Osciloscópio','Digital',8),(9,'Altera','DE2-115',9),(10,'Gerador','12345',10),(11,'Arduino ','UNO',11),(12,'Protoboard','100 furos',11),(13,'Jumper','macho-macho',11),(14,'Arduino ','MEGA',13),(15,'Protoboard','100 furos',13),(16,'Jumper','macho-macho',13),(17,'Arduino ','NANO',14),(18,'Protoboard','100 furos',14),(19,'Jumper','macho-fêmea',14),(20,'Raspberry','PI 3',12),(21,'Raspberry','PI 4',15),(22,'Fonte','PI 3',12),(23,'Fonte','PI 4',15);
/*!40000 ALTER TABLE `Componentes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Emprestimo`
--

DROP TABLE IF EXISTS `Emprestimo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Emprestimo` (
  `idEmprestimo` int(11) NOT NULL AUTO_INCREMENT,
  `dataEmprestimo` datetime NOT NULL,
  `previsaoDevolucao` datetime NOT NULL,
  `devolucao` datetime DEFAULT NULL,
  `renovacao` int(11) NOT NULL,
  `penalidade` int(11) NOT NULL,
  `Aluno_matricula` int(11) NOT NULL,
  `situacao` tinyint(1) NOT NULL,
  `finalidade` int(11) NOT NULL,
  PRIMARY KEY (`idEmprestimo`,`Aluno_matricula`),
  KEY `fk_Emprestimo_Aluno1_idx` (`Aluno_matricula`),
  CONSTRAINT `fk_Emprestimo_Aluno1` FOREIGN KEY (`Aluno_matricula`) REFERENCES `Aluno` (`matricula`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Emprestimo`
--

LOCK TABLES `Emprestimo` WRITE;
/*!40000 ALTER TABLE `Emprestimo` DISABLE KEYS */;
INSERT INTO `Emprestimo` VALUES (1,'2019-11-10 22:03:13','2019-12-17 23:59:59','2019-11-10 22:09:53',0,0,2939,0,1),(2,'2019-11-10 22:08:20','2019-11-25 22:08:20','2019-11-10 22:10:04',0,0,6433,0,4),(3,'2019-11-10 22:08:40','2019-11-25 22:08:40','2019-11-10 22:10:12',0,0,7760,0,4),(4,'2019-11-10 22:09:43','2019-11-25 22:09:43','2019-11-10 22:10:29',0,0,12814,0,4),(5,'2019-11-10 22:11:01','2019-12-17 23:59:59',NULL,0,0,2939,1,1),(6,'2019-11-10 22:11:19','2019-11-25 22:11:19',NULL,0,0,6433,1,4),(7,'2019-11-10 22:11:44','2019-11-25 22:11:54',NULL,1,0,7760,1,4),(8,'2019-11-10 22:12:24','2019-11-25 22:12:24',NULL,0,0,12814,1,4);
/*!40000 ALTER TABLE `Emprestimo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Emprestimo_has_Equipamentos`
--

DROP TABLE IF EXISTS `Emprestimo_has_Equipamentos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Emprestimo_has_Equipamentos` (
  `Emprestimo_idEmprestimo` int(11) NOT NULL,
  `Equipamentos_patrimonio` int(11) NOT NULL,
  PRIMARY KEY (`Emprestimo_idEmprestimo`,`Equipamentos_patrimonio`),
  KEY `fk_Emprestimo_has_Equipamentos_Equipamentos1_idx` (`Equipamentos_patrimonio`),
  KEY `fk_Emprestimo_has_Equipamentos_Emprestimo1_idx` (`Emprestimo_idEmprestimo`),
  CONSTRAINT `fk_Emprestimo_has_Equipamentos_Emprestimo1` FOREIGN KEY (`Emprestimo_idEmprestimo`) REFERENCES `Emprestimo` (`idEmprestimo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Emprestimo_has_Equipamentos_Equipamentos1` FOREIGN KEY (`Equipamentos_patrimonio`) REFERENCES `Equipamentos` (`patrimonio`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Emprestimo_has_Equipamentos`
--

LOCK TABLES `Emprestimo_has_Equipamentos` WRITE;
/*!40000 ALTER TABLE `Emprestimo_has_Equipamentos` DISABLE KEYS */;
INSERT INTO `Emprestimo_has_Equipamentos` VALUES (6,3),(8,8),(5,11),(7,14);
/*!40000 ALTER TABLE `Emprestimo_has_Equipamentos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Equipamentos`
--

DROP TABLE IF EXISTS `Equipamentos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Equipamentos` (
  `patrimonio` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `tipo` varchar(45) NOT NULL,
  `Reserva_idReserva` int(11) DEFAULT NULL,
  `Reserva_Aluno_matricula` int(11) DEFAULT NULL,
  `situacao` tinyint(1) NOT NULL,
  PRIMARY KEY (`patrimonio`),
  KEY `fk_Equipamentos_Reserva1_idx` (`Reserva_idReserva`,`Reserva_Aluno_matricula`),
  CONSTRAINT `fk_Equipamentos_Reserva1` FOREIGN KEY (`Reserva_idReserva`, `Reserva_Aluno_matricula`) REFERENCES `Reserva` (`idReserva`, `Aluno_matricula`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Equipamentos`
--

LOCK TABLES `Equipamentos` WRITE;
/*!40000 ALTER TABLE `Equipamentos` DISABLE KEYS */;
INSERT INTO `Equipamentos` VALUES (1,'Fonte contínua','Único',NULL,NULL,0),(2,'Fonte alternada','Único',NULL,NULL,0),(3,'Multímetro','Único',NULL,NULL,1),(4,'Conversor HDMI','Único',NULL,NULL,0),(5,'Ferro de solda','Único',NULL,NULL,0),(6,'Soprador térmico','Único',NULL,NULL,0),(7,'Furadeira','Único',NULL,NULL,0),(8,'Osciloscópio','Único',NULL,NULL,1),(9,'FPGA','Único',NULL,NULL,0),(10,'Gerador','Único',NULL,NULL,0),(11,'Arduino U','Kit',NULL,NULL,1),(12,'Raspberry pi 3','Kit',NULL,NULL,0),(13,'Arduino M','Kit',NULL,NULL,0),(14,'Arduino N','Kit',NULL,NULL,1),(15,'Raspberry pi 4','Kit',NULL,NULL,0);
/*!40000 ALTER TABLE `Equipamentos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Reserva`
--

DROP TABLE IF EXISTS `Reserva`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Reserva` (
  `idReserva` int(11) NOT NULL,
  `Aluno_matricula` int(11) NOT NULL,
  PRIMARY KEY (`idReserva`,`Aluno_matricula`),
  KEY `fk_Reserva_Aluno1_idx` (`Aluno_matricula`),
  CONSTRAINT `fk_Reserva_Aluno1` FOREIGN KEY (`Aluno_matricula`) REFERENCES `Aluno` (`matricula`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Reserva`
--

LOCK TABLES `Reserva` WRITE;
/*!40000 ALTER TABLE `Reserva` DISABLE KEYS */;
/*!40000 ALTER TABLE `Reserva` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-10 22:13:32
