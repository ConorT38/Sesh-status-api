-- MySQL dump 10.13  Distrib 5.7.21, for Win32 (AMD64)
--
-- Host: localhost    Database: sesh
-- ------------------------------------------------------
-- Server version	5.7.21-log

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
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comments` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `message` mediumtext,
  `uploaded` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `likes` int(11) DEFAULT NULL,
  `status_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (1,42,'THIS BLOWS','2018-05-17 14:31:59',0,65),(2,42,'olroight, try again','2018-05-17 14:35:18',0,65),(3,42,'wot bout this one','2018-05-17 14:35:50',0,64),(4,42,'THIS BLOWS','2018-05-17 16:31:04',0,50),(5,42,'THIS BLOWS','2018-05-17 16:31:06',0,50),(6,42,'THIS BLOWS','2018-05-17 16:31:06',0,50),(7,25,'sup','2018-05-17 17:10:13',0,50),(8,25,'omg','2018-05-17 17:10:19',0,49),(9,42,'olroight, more comments','2018-05-18 07:48:55',0,63),(10,42,'ay what\'s good homie','2018-05-18 10:06:16',0,58),(11,42,'WAY UP HIGH','2018-05-18 12:56:12',0,57),(12,25,'suhh dude','2018-05-18 12:56:32',0,32),(13,25,'','2018-05-18 12:56:48',0,38);
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments_likes`
--

DROP TABLE IF EXISTS `comments_likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comments_likes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `comment_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `comments_likes_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments_likes`
--

LOCK TABLES `comments_likes` WRITE;
/*!40000 ALTER TABLE `comments_likes` DISABLE KEYS */;
/*!40000 ALTER TABLE `comments_likes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conversation`
--

DROP TABLE IF EXISTS `conversation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `conversation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `participants` longtext,
  `last_read` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conversation`
--

LOCK TABLES `conversation` WRITE;
/*!40000 ALTER TABLE `conversation` DISABLE KEYS */;
/*!40000 ALTER TABLE `conversation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL,
  `geolocation` text,
  `has_promotion` tinyint(4) DEFAULT '0',
  `rating` float DEFAULT NULL,
  `visitors` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,'Sesh pub','sesh st, seshland','https://sesh.ie',NULL,0,2.1,21);
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location_review`
--

DROP TABLE IF EXISTS `location_review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location_review` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `location_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `review` text,
  `rating` float DEFAULT NULL,
  `uploaded` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `location_id` (`location_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `location_review_ibfk_1` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`),
  CONSTRAINT `location_review_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location_review`
--

LOCK TABLES `location_review` WRITE;
/*!40000 ALTER TABLE `location_review` DISABLE KEYS */;
INSERT INTO `location_review` VALUES (1,1,4,'This place is shit craic.',2.1,'2013-02-10 00:00:00');
/*!40000 ALTER TABLE `location_review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logged_in`
--

DROP TABLE IF EXISTS `logged_in`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `logged_in` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `token` varchar(200) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `loggedin` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logged_in`
--

LOCK TABLES `logged_in` WRITE;
/*!40000 ALTER TABLE `logged_in` DISABLE KEYS */;
INSERT INTO `logged_in` VALUES (55,'cd0c816377cb4862b49f49c2ec39949d',25,1),(56,'df6a192be59948bb9c3ca6d47bcdacb6',25,1),(57,'0c922242d6c34a158baf15ef39ecc4e6',25,1),(58,'997a67e3d75446f7af711f4f2344ed2d',25,1),(59,'2c04c7ed07b047e6b2faa8305cc48d5d',32,1),(60,'9810165689fe4b718ba2e9bfbe52498a',33,1),(61,'279d07a5536241568fff6bfaf0779674',34,1),(62,'d3951f02b824448aa43d1160ae88c28c',25,1),(63,'6bb4b7ea07af43f29bb5dcf9da38ec95',25,1),(64,'ee475d335ca346d6ad190ba9b95a9a8c',25,1),(65,'f86db738da474f1f8c31bce624d172c5',35,1),(66,'ad86636906a249f7ad183e55d7035c5a',25,1),(67,'9ad105e2780b4a90a0fe373f44ffcd81',25,1),(68,'96be63fb95f1498cad1884d5fd61cd15',25,1),(69,'bebbec8351744c6c9650c160bd37c649',25,1),(70,'0',36,1),(71,'1c98515a70f0420baad25795f6098f3e',36,1),(72,'86240955af4540caa71679fab33999c6',25,1),(73,'df49e3c849714602b64ee9f297c09af5',25,1),(74,'a984cf2f31414419867343abb997b7e2',37,1),(75,'b3936c7cd67e4606b55640c02463af78',36,1),(76,'a75403ce4ecd4870993d1cd9f310ab05',38,1),(77,'5e900685aaaa4a189772b2b20ae12939',36,1),(78,'751be5df5e1440008e6f3f24a89dc2b3',37,1),(79,'5af831b64c454436a71382f13e5243a6',39,1),(80,'ea5cae46f8f441b3a0d96b57adc028b6',40,1),(81,'5d414507f7794c57a47e2ae28a782d9f',36,1),(82,'36dc5bdb7823455baf1a57352af16772',25,1),(83,'bfdf74f5cf0e475e94bc14669aa93626',25,1),(84,'daef28886cdf48f8a1982ffee6b75c63',25,1),(85,'4c03fc07f82641308070ac70b27ab3bc',41,1),(86,'21ab63c6e2a141f7a288f3fdbf28a931',44,1),(87,'a7496b432aec415095446c3015be62cb',42,1),(88,'8f9bb76e333d4cfa8dde5322bde11523',25,1),(89,'7528c4afed6d4b838af02a941798ea37',25,1),(90,'1c856cadf16f40df96b8fe041531cc99',25,1);
/*!40000 ALTER TABLE `logged_in` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messages` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `from_id` bigint(20) DEFAULT NULL,
  `to_ids` longtext,
  `message` longtext,
  `uploaded` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `conv_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notifications` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `type` varchar(200) DEFAULT NULL,
  `uploaded` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `seen` tinyint(4) DEFAULT NULL,
  `data` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `status` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `message` text,
  `location` int(11) DEFAULT NULL,
  `likes` int(11) DEFAULT NULL,
  `uploaded` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `going` text,
  `maybe` text,
  `not_going` text,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `status_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status`
--

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
INSERT INTO `status` VALUES (1,5,'This is the very first status',1,21,'2018-05-02 10:54:23','[0,4]','[3]','[3]'),(2,5,'This is the very first status',1,21,'2013-02-10 00:00:00','[0,4]','[3]','[3]'),(3,25,'ay',0,0,'2018-04-29 23:00:00','','',''),(4,25,'say something',0,0,'2018-04-29 23:00:00','','',''),(5,25,'say somethingdsa',0,0,'2018-04-29 23:00:00','','',''),(6,25,'say somethingdsa',0,0,'2018-04-29 23:00:00','','',''),(7,25,'sup',0,0,'2018-04-29 23:00:00','','',''),(8,25,'sa',0,0,'2018-04-29 23:00:00','','',''),(9,25,'This is my first real status.',0,0,'2018-04-29 23:00:00','','',''),(10,25,'\"\"\"',0,0,'2018-04-29 23:00:00','','',''),(11,32,'sum up mang',1,1,'2018-05-02 10:54:23',NULL,NULL,NULL),(12,32,'sum up manwq',1,1,'2018-05-02 10:54:23',NULL,NULL,NULL),(13,32,'sum up masswq',1,1,'2018-05-02 10:54:23',NULL,NULL,NULL),(14,25,'some mad shit',0,0,'2018-04-29 23:00:00','','',''),(15,25,'sup',0,0,'2018-04-29 23:00:00','','',''),(16,25,'sa',0,0,'2018-04-29 23:00:00','','',''),(17,25,'this is my message',0,0,'2018-04-29 23:00:00','','',''),(18,25,'a',0,0,'2018-04-29 23:00:00','','',''),(19,25,'<script>alert(hello)</script>',0,0,'2018-04-29 23:00:00','','',''),(20,25,'',0,0,'2018-04-30 23:00:00','','',''),(21,25,'Somethin happenin',0,0,'2018-04-30 23:00:00','','',''),(22,25,'some mad shit',0,0,'2018-05-01 16:26:19','','',''),(23,25,'another one',0,0,'2018-05-02 08:47:35','','',''),(24,25,'another one',0,0,'2018-05-02 08:48:06','','',''),(25,25,'booiiiiii',0,0,'2018-05-02 13:35:51','','',''),(26,32,'okay',0,0,'2018-05-02 14:04:43','','',''),(27,32,'somethings wrong',0,0,'2018-05-02 14:04:53','','',''),(28,33,'my first thing',0,0,'2018-05-02 15:22:27','','',''),(29,33,'my second thing',0,0,'2018-05-02 15:23:10','','',''),(30,34,'wassup',0,0,'2018-05-02 15:47:01','','',''),(31,34,'no',0,0,'2018-05-02 15:47:41','','',''),(32,34,'sa',0,-1,'2018-05-18 08:45:02','','',''),(33,34,'ay yo',0,0,'2018-05-03 13:53:55','','',''),(34,25,'SA',0,0,'2018-05-04 10:41:50','','',''),(35,25,'some gay shit',0,0,'2018-05-04 10:48:24','','',''),(36,25,'sa',0,0,'2018-05-04 10:56:37','','',''),(37,25,'',0,0,'2018-05-04 10:59:36','','',''),(38,25,'',0,0,'2018-05-04 14:04:01','','',''),(39,25,'AIN\' RIGHT',0,0,'2018-05-09 09:35:28','','',''),(40,NULL,NULL,NULL,NULL,'2018-05-09 15:15:10',NULL,NULL,NULL),(41,25,'what now',0,0,'2018-05-09 15:21:51','','',''),(49,25,'hmmmmmmmmmmmm',0,0,'2018-05-11 15:25:07','','',''),(50,25,'something',0,0,'2018-05-11 15:28:17','','',''),(51,37,'Show me the money.',0,0,'2018-05-14 10:53:16','','',''),(52,36,'I ain\'t got no money.',0,0,'2018-05-14 10:53:39','','',''),(53,38,'Some sound lads here',0,0,'2018-05-14 10:54:37','','',''),(54,37,'Anyone around for a meet up?',0,0,'2018-05-14 10:55:43','','',''),(55,38,'Terrible weather today...',0,0,'2018-05-14 10:56:06','','',''),(56,37,'I am Narl, leader of the Jarsons.',0,0,'2018-05-14 10:56:23','','',''),(57,39,'Somewhere over the rainbow',0,0,'2018-05-14 11:28:46','','',''),(58,39,'I was something like 743 lbs',0,0,'2018-05-14 11:29:02','','',''),(59,40,'Ain\'t nobody loves me better',0,0,'2018-05-14 11:30:08','','',''),(60,36,'suh',0,0,'2018-05-15 10:31:33','','',''),(61,41,'I thought I made a status?',0,0,'2018-05-17 09:05:32','','',''),(62,44,'Awah wah',0,0,'2018-05-17 10:01:43','','',''),(63,44,'me lookie',0,0,'2018-05-17 10:01:48','','',''),(64,44,'some shit',0,0,'2018-05-17 10:01:54','','',''),(65,42,'crazy y\'al',0,1,'2018-05-18 11:42:37','','','');
/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status_comment`
--

DROP TABLE IF EXISTS `status_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `status_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `message` text,
  `likes` int(11) DEFAULT NULL,
  `uploaded` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `status_id` (`status_id`),
  CONSTRAINT `status_comment_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `status_comment_ibfk_2` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status_comment`
--

LOCK TABLES `status_comment` WRITE;
/*!40000 ALTER TABLE `status_comment` DISABLE KEYS */;
INSERT INTO `status_comment` VALUES (1,1,5,'This is the very first comment',21,'2013-02-10 00:00:00'),(2,65,42,'',0,'2018-05-17 15:05:21'),(3,65,42,'',0,'2018-05-17 15:06:28'),(4,65,42,'',0,'2018-05-17 15:06:28'),(5,65,42,'',0,'2018-05-17 15:06:53'),(6,65,42,'',0,'2018-05-17 15:06:53'),(7,65,42,'ay yal',0,'2018-05-17 15:06:53'),(8,65,42,'',0,'2018-05-17 15:08:30'),(9,65,42,'ay whats good homie',0,'2018-05-17 15:08:30'),(10,65,42,'',0,'2018-05-17 15:08:30'),(11,65,42,'',0,'2018-05-17 15:08:56'),(12,65,42,'',0,'2018-05-17 15:08:56'),(13,65,42,'no',0,'2018-05-17 15:08:56'),(14,65,42,'',0,'2018-05-17 15:09:07'),(15,65,42,'',0,'2018-05-17 15:09:07'),(16,65,42,'ay',0,'2018-05-17 15:09:07'),(17,65,42,'sa',0,'2018-05-17 15:29:50');
/*!40000 ALTER TABLE `status_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status_likes`
--

DROP TABLE IF EXISTS `status_likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `status_likes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `status_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `status_likes_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status_likes`
--

LOCK TABLES `status_likes` WRITE;
/*!40000 ALTER TABLE `status_likes` DISABLE KEYS */;
INSERT INTO `status_likes` VALUES (9,42,65);
/*!40000 ALTER TABLE `status_likes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_relationship`
--

DROP TABLE IF EXISTS `user_relationship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_relationship` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `friend_id` bigint(20) DEFAULT NULL,
  `type` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_relationship`
--

LOCK TABLES `user_relationship` WRITE;
/*!40000 ALTER TABLE `user_relationship` DISABLE KEYS */;
INSERT INTO `user_relationship` VALUES (1,25,32,'friend'),(2,25,25,'friend'),(3,32,32,'friend'),(4,33,33,'friend'),(5,33,33,'friend'),(6,34,34,'friend'),(7,35,35,'friend'),(8,2,32,'friend'),(9,2,7,'friend'),(10,25,22,'friend'),(11,25,26,'friend'),(12,25,27,'friend'),(13,25,28,'friend'),(14,25,29,'friend'),(15,25,30,'friend'),(16,25,31,'friend'),(17,25,33,'friend'),(18,25,24,'friend'),(19,25,23,'friend'),(20,25,35,'friend'),(21,25,34,'friend'),(22,36,36,'friend'),(23,37,37,'friend'),(24,37,36,'friend'),(25,36,37,'friend'),(26,38,38,'friend'),(27,38,36,'friend'),(28,38,37,'friend'),(29,37,38,'friend'),(30,39,39,'friend'),(31,39,38,'friend'),(32,39,37,'friend'),(33,39,36,'friend'),(34,40,40,'friend'),(35,40,36,'friend'),(36,40,39,'friend'),(37,40,37,'friend'),(38,40,38,'friend'),(39,36,38,'friend'),(40,36,39,'friend'),(41,36,40,'friend'),(42,41,41,'friend'),(43,41,36,'friend'),(44,41,39,'friend'),(45,41,40,'friend'),(46,41,38,'friend'),(47,41,37,'friend'),(48,42,42,'friend'),(49,43,43,'friend'),(50,44,44,'friend'),(51,44,36,'friend'),(52,44,37,'friend'),(53,44,39,'friend'),(54,44,38,'friend'),(55,44,40,'friend'),(56,44,42,'friend'),(57,44,41,'friend'),(58,44,43,'friend'),(59,42,36,'friend'),(60,42,37,'friend'),(61,42,38,'friend'),(62,42,39,'friend'),(63,42,40,'friend'),(64,42,41,'friend'),(65,42,43,'friend'),(66,42,44,'friend');
/*!40000 ALTER TABLE `user_relationship` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_review`
--

DROP TABLE IF EXISTS `user_review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_review` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `location_id` bigint(20) DEFAULT NULL,
  `reviewer_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `message` text,
  `rating` float DEFAULT NULL,
  `uploaded` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `location_id` (`location_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_review_ibfk_1` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`),
  CONSTRAINT `user_review_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_review`
--

LOCK TABLES `user_review` WRITE;
/*!40000 ALTER TABLE `user_review` DISABLE KEYS */;
INSERT INTO `user_review` VALUES (1,1,4,5,'This lad is shit craic.',2.1,'2013-02-10 00:00:00');
/*!40000 ALTER TABLE `user_review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `age` int(3) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `location` int(11) NOT NULL DEFAULT '0',
  `favourite_drink` varchar(200) DEFAULT 'None',
  `rating` float DEFAULT '0',
  `email` varchar(200) DEFAULT NULL,
  `username` varchar(200) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  `gender` varchar(200) DEFAULT NULL,
  `local_spot` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,'James Mackle',21,'2013-02-10',1,'guinness',4.2,NULL,NULL,NULL,NULL,NULL),(3,'James Mackle',21,'2013-02-10',1,'guinness',4.2,NULL,NULL,NULL,NULL,NULL),(4,'James Mackle',21,'2013-02-10',1,'guinness',4.2,NULL,NULL,NULL,NULL,NULL),(5,'James Mackler dudette',21,'2013-02-10',1,NULL,0,NULL,NULL,NULL,NULL,NULL),(6,'James Mackle',21,'2013-02-10',1,'guinness',4.2,NULL,NULL,NULL,NULL,NULL),(7,'James Mackle',21,'2013-02-10',1,'guinness',4.2,NULL,NULL,NULL,NULL,NULL),(8,'ã°ÄB˜üšûôÈ™o¹$\'®Aäd›“L¤•™xR¸U',0,'2018-05-11',1,NULL,NULL,'[B@2b0f28d5','[B@2b0f28d5','ã°ÄB˜üšûôÈ™o¹$\'®Aäd›“L¤•™xR¸U',NULL,NULL),(9,'ã°ÄB˜üšûôÈ™o¹$\'®Aäd›“L¤•™xR¸U',0,'2018-05-11',1,NULL,NULL,'[B@62f639b0','[B@62f639b0','ã°ÄB˜üšûôÈ™o¹$\'®Aäd›“L¤•™xR¸U',NULL,NULL),(10,'ã°ÄB˜üšûôÈ™o¹$\'®Aäd›“L¤•™xR¸U',0,'2018-05-11',1,NULL,NULL,'[B@5edcfe8c','[B@5edcfe8c','ã°ÄB˜üšûôÈ™o¹$\'®Aäd›“L¤•™xR¸U',NULL,NULL),(11,',„E×LLìÊÕB.ò‚ÔÔ/!‡mMÉÐm™ÒS¨Å',0,'2018-05-11',1,NULL,NULL,'[B@70de5ac0','[B@70de5ac0',',„E×LLìÊÕB.ò‚ÔÔ/!‡mMÉÐm™ÒS¨Å',NULL,NULL),(12,'LIR¥Ü\0Ö¼¯ÂuÌ	�¨Šgxö*µG†�#@[Ï',0,'2018-05-11',1,NULL,NULL,'[B@2add56d4','[B@2add56d4','LIR¥Ü\0Ö¼¯ÂuÌ	�¨Šgxö*µG†�#@[Ï',NULL,NULL),(13,'LIR¥Ü\0Ö¼¯ÂuÌ	�¨Šgxö*µG†�#@[Ï',0,'2018-05-11',1,NULL,NULL,'[B@1ca888b8','[B@1ca888b8','LIR¥Ü\0Ö¼¯ÂuÌ	�¨Šgxö*µG†�#@[Ï',NULL,NULL),(14,'ã°ÄB˜üšûôÈ™o¹$\'®Aäd›“L¤•™xR¸U',0,'2018-05-11',1,NULL,NULL,'[B@528c4777','[B@528c4777','ã°ÄB˜üšûôÈ™o¹$\'®Aäd›“L¤•™xR¸U',NULL,NULL),(15,'ú”ºePrq|ìa’�<~ &»>rDÿfž¬t¦SL\'¶Åe',0,'2018-05-11',1,NULL,NULL,'[B@3e397b68','[B@3e397b68','ú”ºePrq|ìa’�<~ &»>rDÿfž¬t¦SL\'¶Åe',NULL,NULL),(16,'ú”ºePrq|ìa’�<~ &»>rDÿfž¬t¦SL\'¶Åe',0,'2018-05-11',1,NULL,NULL,'[B@4b4a7020','[B@4b4a7020','ú”ºePrq|ìa’�<~ &»>rDÿfž¬t¦SL\'¶Åe',NULL,NULL),(17,'úi‚ßÒ…&)®ºŠ‰wµ~@ü·}\Zz(²lºbY',0,'2018-05-11',1,NULL,NULL,'[B@12cb85a','[B@12cb85a','úi‚ßÒ…&)®ºŠ‰wµ~@ü·}\Zz(²lºbY',NULL,NULL),(18,'hey',0,'2018-05-11',1,NULL,NULL,'hey','hey','hey',NULL,NULL),(19,'boi',0,'2018-05-11',1,NULL,NULL,'boi','boi','boi',NULL,NULL),(20,'bi',0,'2018-05-11',1,NULL,NULL,'bi','bi','bi',NULL,NULL),(21,'ta',0,'2018-05-11',1,NULL,NULL,'ta','ta','ta',NULL,NULL),(22,'so',0,'2018-05-11',3,NULL,NULL,'so','so','so',NULL,NULL),(23,'po',0,'2018-05-11',3,NULL,NULL,'po','po','po',NULL,NULL),(24,'pee',0,'2018-05-11',3,NULL,NULL,'pee','pee','pee',NULL,NULL),(25,'pa',0,'2018-05-11',3,NULL,NULL,'pa','pa','pa',NULL,NULL),(26,'[B@16b6e651',0,'2018-05-11',3,NULL,NULL,'[B@57cb44fa','ConorT38','blizzardofozz1',NULL,NULL),(27,'[B@e707a58',0,'2018-05-11',3,NULL,NULL,'[B@73f782ca','cothompson16','password',NULL,NULL),(28,'[B@21624eb5',0,'2018-05-11',3,NULL,NULL,'[B@57eeab61','[B@ae0bed2','ç¤G~ÉEi|ägì{�ÂÄ…ÙSˆ* <•m7}Œ½',NULL,NULL),(29,'[B@523fe37f',0,'2018-05-11',3,NULL,NULL,'[B@682aad63','[B@4c498e06','ìÝ—@\\y´îw‘�Òµx“Ù°j‚S\r÷)ÚïÎõ',NULL,NULL),(30,'plp',0,'2018-05-11',3,NULL,NULL,'plp@plp.xom','plp','plp',NULL,NULL),(31,'[B@425ba800',0,'2018-05-11',3,NULL,NULL,'[B@73c9fdc1','[B@77869304','×CÈÔ	&D˜¦É�K•ä¢KmV1_ç÷ 7¡²',NULL,NULL),(32,'Conor Thompson',0,'2018-05-11',3,NULL,NULL,'sho@la.nci','sho','sho',NULL,NULL),(33,'mang',0,'2018-05-11',3,NULL,NULL,'mang@mang.com','mang','mang',NULL,NULL),(34,'The Realest Dude',0,'2018-05-11',3,NULL,NULL,'reael@dude.com','RealestDude','password',NULL,NULL),(35,'Jordan Peterson',0,'2018-05-11',3,NULL,NULL,'Jordz@gmail.com','therealJodanPeterson','password',NULL,NULL),(36,'John Johnson',NULL,NULL,0,'None',0,'john@john.ie','john','john',NULL,NULL),(37,'Narl Jarson',NULL,NULL,0,'None',0,'narl@jarson.com','narl','narl',NULL,NULL),(38,'Shame onme',NULL,NULL,0,'None',0,'shame','shame','shame',NULL,NULL),(39,'Israel \'IZ\' Kamakawiwo\'ole',NULL,NULL,0,'None',0,'is@rael.com','Israel','Israel',NULL,NULL),(40,'Chaka Khan',NULL,NULL,0,'None',0,'chaka@khan.com','ChakaKhan','ChakaKhan',NULL,NULL),(41,'John Jacob',NULL,NULL,0,'None',0,'jkohn@jkohm.com','johnjacob','johnjacob',NULL,NULL),(42,'cheech',NULL,NULL,0,'None',0,'cho@cho','chochang','chochang',NULL,NULL),(43,'Malarchy Tomas',NULL,NULL,0,'None',0,'show@tom.com','TheRealDonaldTrump','TheRealDonaldTrump',NULL,NULL),(44,'Simon Redmon',NULL,NULL,0,'None',0,'simon@redmond.com','SimonR92','Simon',NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-18 15:47:23
