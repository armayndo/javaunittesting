-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.1.37-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win32
-- HeidiSQL Version:             10.1.0.5464
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping data for table dblibman.book: 0 rows
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` (`id`, `author`, `status_shelved`, `isbn`, `title`, `shelf_id`) VALUES
	(1, 'aut1', 0, 'isbn1', 'title1', 1),
	(2, 'aut2', 1, 'isbn2', 'title2', 1),
	(3, 'aut3', 1, 'isbn3', 'title3', 1),
	(4, 'aut4', 1, 'isbn4', 'title4', 1),
	(5, 'aut5', 1, 'isbn5', 'title5', 2);
/*!40000 ALTER TABLE `book` ENABLE KEYS */;

-- Dumping data for table dblibman.item: 0 rows
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` (`id`, `name`, `price`, `quantity`) VALUES
	(0, 'item1', 20000, 10),
	(1, 'item2', 10000, 20),
	(3, 'item3', 15000, 30);
/*!40000 ALTER TABLE `item` ENABLE KEYS */;

-- Dumping data for table dblibman.shelf: 0 rows
/*!40000 ALTER TABLE `shelf` DISABLE KEYS */;
INSERT INTO `shelf` (`shelf_id`, `current_capacity`, `max_capacity`) VALUES
	(1, 3, 10),
	(2, 1, 10),
	(3, 0, 10);
/*!40000 ALTER TABLE `shelf` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
