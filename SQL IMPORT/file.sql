


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table gameRoom
# ------------------------------------------------------------

DROP TABLE IF EXISTS `gameRoom`;

CREATE TABLE `gameRoom` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) NOT NULL,
  `room_name` varchar(250) NOT NULL,
  `createdAt` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `password` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

LOCK TABLES `gameRoom` WRITE;
/*!40000 ALTER TABLE `gameRoom` DISABLE KEYS */;

INSERT INTO `gameRoom` (`id`, `type`, `room_name`, `createdAt`, `password`) VALUES
	(2, 2, 'sala low risk', '2024-12-17 20:35:08', '$2y$13$jhhWO.8syaRi9LsEpLS0zu7OyRaZJqy6WJiZRJnv.6nkXQvVTs7O2'),
	(3, 2, 'sala1', '2024-12-17 21:06:55', NULL),
	(4, 2, 'sala lowRoll', '2024-12-17 23:57:56', NULL),
	(5, 2, 'sala12', '2024-12-18 00:01:51', NULL),
	(6, 2, 'sala12', '2024-12-18 00:05:27', NULL),
	(7, 2, 'sala12', '2024-12-18 00:11:07', NULL),
	(8, 2, 'salaABC', '2024-12-18 00:14:04', NULL),
	(9, 2, 'salaBBC', '2024-12-18 00:15:02', NULL),
	(10, 2, 'ff', '2024-12-18 00:26:17', NULL);

/*!40000 ALTER TABLE `gameRoom` ENABLE KEYS */;
UNLOCK TABLES;



# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(250) NOT NULL,
  `password` varchar(250) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `chips` int(11) NOT NULL DEFAULT 0,
  `admin` tinyint(1) NOT NULL DEFAULT 0,
  `ban` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;

INSERT INTO `user` (`id`, `username`, `password`, `created_at`, `chips`, `admin`, `ban`) VALUES
	(21, 'YOLO', '$2y$13$..I3h3tnusNwAMtIqoQrC.tyrsNWPbCF.HQSez1IFF80D5W6k1Fii', '2024-12-17 12:10:59', -2090860545, 0, 0),
	(22, 'AirBaker', '$2y$13$JXUfF3F.g0DeOFnxlaoeBeHjuezm7CkrBVx5QqigfthOV6O6H7n0S', '2024-12-17 12:12:05', 0, 1, 0),
	(23, 'userA', '$2y$13$pux6p7XFCy8ODR3mg629rOBkBgnC0yVjOd45aP5mbilN/d73aPHbO', '2024-12-17 14:03:01', 0, 1, 0),
	(24, 'pepe', '$2y$13$cGlYEx8M1wid3dEuF6uLDuDv6Hkvn/jcVkTBraiqjKpY9r3R/PzcC', '2024-12-18 00:31:42', 0, 0, 0),
	(25, 'userB', '$2y$13$VfSrwqD3M9VRtOOAIP6bVuVMjsT0fBq8mrE0.tQwwcQJnIjw5bDZu', '2024-12-18 00:43:40', 3290, 0, 0),
	(26, 'admin', '$2y$13$b1BifZ48emohjzbrmew2ouIyKKG.XiiJOO39ZbbnJNRensvxf/z9q', '2024-12-18 02:48:34', 0, 1, 0),
	(27, 'alvaro', '$2y$13$ybh9TV/LWdwIel.UKG4RuetTSzyz2GOD72VXoaC2awO7INKkMlQ76', '2024-12-18 05:06:22', 150, 0, 0);

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;



# Dump of table lowRollUsers
# ------------------------------------------------------------

DROP TABLE IF EXISTS `lowRollUsers`;

CREATE TABLE `lowRollUsers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `game_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `guess` int(11) NOT NULL,
  `chips` int(11) NOT NULL,
  PRIMARY KEY (`id`,`game_id`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

LOCK TABLES `lowRollUsers` WRITE;
/*!40000 ALTER TABLE `lowRollUsers` DISABLE KEYS */;

INSERT INTO `lowRollUsers` (`id`, `game_id`, `user_id`, `guess`, `chips`) VALUES
	(2, 10, 25, 4, 10),
	(3, 10, 21, 1, 50),
	(4, 6, 21, 1, 50),
	(5, 7, 21, 1, 1050);

/*!40000 ALTER TABLE `lowRollUsers` ENABLE KEYS */;
UNLOCK TABLES;



# Dump of table friendships
# ------------------------------------------------------------

DROP TABLE IF EXISTS `friendships`;

CREATE TABLE `friendships` (
  `user_id1` int(11) NOT NULL,
  `user_id2` int(11) NOT NULL,
  PRIMARY KEY (`user_id1`,`user_id2`),
  KEY `FK_friendships_user_id2` (`user_id2`),
  CONSTRAINT `FK_friendships_user_id1` FOREIGN KEY (`user_id1`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_friendships_user_id2` FOREIGN KEY (`user_id2`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `friendships` WRITE;
/*!40000 ALTER TABLE `friendships` DISABLE KEYS */;

INSERT INTO `friendships` (`user_id1`, `user_id2`) VALUES
	(21, 22),
	(21, 24),
	(22, 24),
	(23, 25),
	(25, 27);

/*!40000 ALTER TABLE `friendships` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;


