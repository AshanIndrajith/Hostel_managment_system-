-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Nov 05, 2023 at 04:58 PM
-- Server version: 8.0.31
-- PHP Version: 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `fthms`
--

DELIMITER $$
--
-- Procedures
--
DROP PROCEDURE IF EXISTS `AssignRoomToStudent`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `AssignRoomToStudent` (IN `inRoomId` VARCHAR(255), IN `inUserId` VARCHAR(255))   BEGIN
    DECLARE userExists INT;
    DECLARE userAssigned INT;

    
    SELECT COUNT(*)
    INTO userExists
    FROM users
    WHERE user_index = inUserId;

    
    SELECT COUNT(*)
    INTO userAssigned
    FROM room_assignment
    WHERE user_id = inUserId;

    IF userExists = 0 THEN
        
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'User is not registered in the system';
    ELSEIF userAssigned > 0 THEN
        
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'User is already assigned to a room';
    ELSE
        
        INSERT INTO room_assignment (user_id, room_id, assignment_date)
        VALUES (inUserId, inRoomId, CURDATE());
    END IF;
END$$

DROP PROCEDURE IF EXISTS `CreateMonthlyComplaintView`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `CreateMonthlyComplaintView` ()   BEGIN
    SELECT
        id, 
        approved_date,
        complainant,
        complaint_date,
        description,
        image_name,
        property_type,
        property_uniq_id,
        room_number,
        status
    FROM
        complain
    WHERE
        MONTH(complaint_date) = MONTH(CURDATE())
    ORDER BY
        status ASC; 
END$$

DROP PROCEDURE IF EXISTS `CreateTodayComplaintView`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `CreateTodayComplaintView` ()   BEGIN
   
  
    SELECT
        id, 
        approved_date,
        complainant,
        complaint_date,
        description,
        image_name,
        property_type,
        property_uniq_id,
        room_number,
        status
    FROM
        complain
    WHERE
        DATE(complaint_date) = CURDATE();
END$$

DROP PROCEDURE IF EXISTS `create_room_assignment_view`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `create_room_assignment_view` (IN `room_number` VARCHAR(255))   BEGIN
    SET @sql = CONCAT('CREATE OR REPLACE VIEW room_assignment_view AS
    SELECT assignment_id , user_id , room_id , assignment_date 
    FROM room_assignment
    WHERE room_id = ''', room_number, ''';');
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END$$

DROP PROCEDURE IF EXISTS `GetPropertiesByUniqID`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetPropertiesByUniqID` (IN `p_property_uniq_id` VARCHAR(255))   BEGIN
    SELECT property_id, room_number, property_name
    FROM property
    WHERE property_uniq_id = p_property_uniq_id;
END$$

--
-- Functions
--
DROP FUNCTION IF EXISTS `GetComplaintCount`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `GetComplaintCount` () RETURNS INT  BEGIN
    DECLARE complaint_count INT;
    SELECT COUNT(*) INTO complaint_count FROM complain;
    RETURN complaint_count;
END$$

DROP FUNCTION IF EXISTS `GetPendingComplaintCount`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `GetPendingComplaintCount` () RETURNS INT  BEGIN
    DECLARE complaint_count INT;
    SELECT COUNT(*) INTO complaint_count FROM complain WHERE status = 'pending';
    RETURN complaint_count;
END$$

DROP FUNCTION IF EXISTS `GetRoomCount`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `GetRoomCount` () RETURNS INT  BEGIN
    DECLARE room_count INT;
    SELECT COUNT(*) INTO room_count FROM room;
    RETURN room_count;
END$$

DROP FUNCTION IF EXISTS `GetTotalRegisteredStudents`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `GetTotalRegisteredStudents` () RETURNS INT  BEGIN
    DECLARE total_students INT;
    
    SELECT COUNT(*)
    INTO total_students
    FROM users
    WHERE role = 'STUDENT';
    
    RETURN total_students;
END$$

DROP FUNCTION IF EXISTS `Remove_Student_From_Room`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `Remove_Student_From_Room` (`student_id` VARCHAR(255)) RETURNS INT  BEGIN
    DECLARE success INT;

    DELETE FROM room_assignment
    WHERE user_id = student_id;

    SET success = ROW_COUNT();

    RETURN success;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_index` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `fname` varchar(255) NOT NULL,
  `lname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `user_index`, `password`, `fname`, `lname`, `email`) VALUES
(1, 'A001', '$2a$10$NQJ/qAMkrkDipiRhyU/yXe0VLrVyGvPRcCFbPbtCHHXQd.W34Cmea', 'ashan', 'indrajith', 'ashan@gmail.com'),
(2, 'TG/2019/005', '$2a$10$1fP90QtO0508SeCRDqKZsOyaW/OtPwiEK3pqwCoZv511UkA.HF7xm', 'ashan', 'indrajith', 'ashan@gmail.com');

-- --------------------------------------------------------

--
-- Stand-in structure for view `approvedcomplaints`
-- (See below for the actual view)
--
DROP VIEW IF EXISTS `approvedcomplaints`;
CREATE TABLE IF NOT EXISTS `approvedcomplaints` (
`id` bigint
,`approved_date` varchar(255)
,`complainant` varchar(255)
,`complaint_date` varchar(255)
,`description` varchar(255)
,`image_name` varchar(255)
,`property_type` varchar(255)
,`property_uniq_id` varchar(255)
,`room_number` varchar(255)
,`status` varchar(255)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `available_rooms`
-- (See below for the actual view)
--
DROP VIEW IF EXISTS `available_rooms`;
CREATE TABLE IF NOT EXISTS `available_rooms` (
`room_number` varchar(255)
,`available_capacity` bigint
);

-- --------------------------------------------------------

--
-- Table structure for table `complain`
--

DROP TABLE IF EXISTS `complain`;
CREATE TABLE IF NOT EXISTS `complain` (
  `id` bigint NOT NULL,
  `approved_date` varchar(255) DEFAULT NULL,
  `complainant` varchar(255) DEFAULT NULL,
  `complaint_date` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image_name` varchar(255) DEFAULT NULL,
  `property_type` varchar(255) DEFAULT NULL,
  `property_uniq_id` varchar(255) DEFAULT NULL,
  `room_number` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `complain`
--

INSERT INTO `complain` (`id`, `approved_date`, `complainant`, `complaint_date`, `description`, `image_name`, `property_type`, `property_uniq_id`, `room_number`, `status`) VALUES
(204, '2023-11-04', 'TG500', '2023-11-05', 'damage', 'issue.png', 'Chair', 'ch1108', '108', 'Approved'),
(205, '2023-11-04', 'TG500', '2023-11-04', 'damage', 'issue.png', 'Chair', 'ch1108', '108', 'Approved');

-- --------------------------------------------------------

--
-- Table structure for table `complaint`
--

DROP TABLE IF EXISTS `complaint`;
CREATE TABLE IF NOT EXISTS `complaint` (
  `id` bigint NOT NULL,
  `status` varchar(255) NOT NULL,
  `admin_level` varchar(255) NOT NULL,
  `complainant` varchar(255) NOT NULL,
  `complained_date` date NOT NULL,
  `description` varchar(255) NOT NULL,
  `feedback` varchar(255) DEFAULT NULL,
  `property` varchar(255) NOT NULL,
  `room_no` int NOT NULL,
  `defect_type` varchar(255) NOT NULL,
  `img` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `complaint_seq`
--

DROP TABLE IF EXISTS `complaint_seq`;
CREATE TABLE IF NOT EXISTS `complaint_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `complaint_seq`
--

INSERT INTO `complaint_seq` (`next_val`) VALUES
(1);

-- --------------------------------------------------------

--
-- Table structure for table `complain_seq`
--

DROP TABLE IF EXISTS `complain_seq`;
CREATE TABLE IF NOT EXISTS `complain_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `complain_seq`
--

INSERT INTO `complain_seq` (`next_val`) VALUES
(401);

-- --------------------------------------------------------

--
-- Stand-in structure for view `daily_complaint_count`
-- (See below for the actual view)
--
DROP VIEW IF EXISTS `daily_complaint_count`;
CREATE TABLE IF NOT EXISTS `daily_complaint_count` (
`complaint_day` date
,`num_complaints` bigint
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `dean_complain_view`
-- (See below for the actual view)
--
DROP VIEW IF EXISTS `dean_complain_view`;
CREATE TABLE IF NOT EXISTS `dean_complain_view` (
`id` bigint
,`approved_date` varchar(255)
,`complainant` varchar(255)
,`complaint_date` varchar(255)
,`description` varchar(255)
,`image_name` varchar(255)
,`property_type` varchar(255)
,`property_uniq_id` varchar(255)
,`room_number` varchar(255)
,`status` varchar(255)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `dean_view`
-- (See below for the actual view)
--
DROP VIEW IF EXISTS `dean_view`;
CREATE TABLE IF NOT EXISTS `dean_view` (
`id` bigint
,`email` varchar(255)
,`fname` varchar(255)
,`lname` varchar(255)
,`role` varchar(255)
,`user_index` varchar(255)
);

-- --------------------------------------------------------

--
-- Table structure for table `property`
--

DROP TABLE IF EXISTS `property`;
CREATE TABLE IF NOT EXISTS `property` (
  `property_id` int NOT NULL AUTO_INCREMENT,
  `property_name` varchar(255) NOT NULL,
  `property_uniq_id` varchar(255) NOT NULL,
  `room_number` varchar(255) NOT NULL,
  PRIMARY KEY (`property_id`)
) ENGINE=MyISAM AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `property`
--

INSERT INTO `property` (`property_id`, `property_name`, `property_uniq_id`, `room_number`) VALUES
(1, 'Chair', 'ch1108', '108'),
(2, 'Chair', 'ch2108', '108'),
(3, 'Chair', 'ch3108', '108'),
(4, 'Chair', 'ch4108', '108'),
(5, 'Table', 'table1108', '108'),
(6, 'Table', 'table2108', '108'),
(7, 'Table', 'table3108', '108'),
(8, 'Table', 'table4108', '108'),
(9, 'Bed', 'bed1108', '108'),
(10, 'Bed', 'bed2108', '108'),
(11, 'Bulb', 'bulb1108', '108'),
(12, 'Bulb', 'bulb2108', '108'),
(13, 'Chair', 'ch1R102', 'R102'),
(14, 'Chair', 'ch2R102', 'R102'),
(15, 'Chair', 'ch3R102', 'R102'),
(16, 'Chair', 'ch4R102', 'R102'),
(17, 'Table', 'table1R102', 'R102'),
(18, 'Table', 'table2R102', 'R102'),
(19, 'Table', 'table3R102', 'R102'),
(20, 'Table', 'table4R102', 'R102'),
(21, 'Bed', 'bed1R102', 'R102'),
(22, 'Bed', 'bed2R102', 'R102'),
(23, 'Bulb', 'bulb1R102', 'R102'),
(24, 'Bulb', 'bulb2R102', 'R102'),
(25, 'Chair', 'ch1R001', 'R001'),
(26, 'Chair', 'ch2R001', 'R001'),
(27, 'Chair', 'ch3R001', 'R001'),
(28, 'Chair', 'ch4R001', 'R001'),
(29, 'Table', 'table1R001', 'R001'),
(30, 'Table', 'table2R001', 'R001'),
(31, 'Table', 'table3R001', 'R001'),
(32, 'Table', 'table4R001', 'R001'),
(33, 'Bed', 'bed1R001', 'R001'),
(34, 'Bed', 'bed2R001', 'R001'),
(35, 'Bulb', 'bulb1R001', 'R001'),
(36, 'Bulb', 'bulb2R001', 'R001'),
(37, 'Chair', 'ch1100', '100'),
(38, 'Chair', 'ch2100', '100'),
(39, 'Chair', 'ch3100', '100'),
(40, 'Chair', 'ch4100', '100'),
(41, 'Table', 'table1100', '100'),
(42, 'Table', 'table2100', '100'),
(43, 'Table', 'table3100', '100'),
(44, 'Table', 'table4100', '100'),
(45, 'Bed', 'bed1100', '100'),
(46, 'Bed', 'bed2100', '100'),
(47, 'Bulb', 'bulb1100', '100'),
(48, 'Bulb', 'bulb2100', '100'),
(49, 'Chair', 'ch1R101', 'R101'),
(50, 'Chair', 'ch2R101', 'R101'),
(51, 'Chair', 'ch3R101', 'R101'),
(52, 'Chair', 'ch4R101', 'R101'),
(53, 'Table', 'table1R101', 'R101'),
(54, 'Table', 'table2R101', 'R101'),
(55, 'Table', 'table3R101', 'R101'),
(56, 'Table', 'table4R101', 'R101'),
(57, 'Bed', 'bed1R101', 'R101'),
(58, 'Bed', 'bed2R101', 'R101'),
(59, 'Bulb', 'bulb1R101', 'R101'),
(60, 'Bulb', 'bulb2R101', 'R101');

-- --------------------------------------------------------

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
CREATE TABLE IF NOT EXISTS `room` (
  `roomid` bigint NOT NULL,
  `room_capacity` int DEFAULT NULL,
  `room_number` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`roomid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `room`
--

INSERT INTO `room` (`roomid`, `room_capacity`, `room_number`, `status`) VALUES
(0, 4, '108', 'available'),
(1, 4, '101', '1'),
(2, 4, '001', '0'),
(3, 4, '002', '0'),
(52, 4, '003', '0'),
(102, 4, 'R001', '0'),
(152, 4, 'R102', '0'),
(202, 4, 'R001', '0'),
(252, 4, '100', '0'),
(302, 4, 'R101', '0');

--
-- Triggers `room`
--
DROP TRIGGER IF EXISTS `room_insert_trigger`;
DELIMITER $$
CREATE TRIGGER `room_insert_trigger` AFTER INSERT ON `room` FOR EACH ROW BEGIN
    
    INSERT INTO property (property_name, property_uniq_id, room_number)
    VALUES ('Chair', CONCAT('ch1', NEW.room_number), NEW.room_number),
           ('Chair', CONCAT('ch2', NEW.room_number), NEW.room_number),
           ('Chair', CONCAT('ch3', NEW.room_number), NEW.room_number),
           ('Chair', CONCAT('ch4', NEW.room_number), NEW.room_number);

    
    INSERT INTO property (property_name, property_uniq_id, room_number)
    VALUES ('Table', CONCAT('table1', NEW.room_number), NEW.room_number),
           ('Table', CONCAT('table2', NEW.room_number), NEW.room_number),
           ('Table', CONCAT('table3', NEW.room_number), NEW.room_number),
           ('Table', CONCAT('table4', NEW.room_number), NEW.room_number);

    
    INSERT INTO property (property_name, property_uniq_id, room_number)
    VALUES ('Bed', CONCAT('bed1', NEW.room_number), NEW.room_number),
           ('Bed', CONCAT('bed2', NEW.room_number), NEW.room_number);

    
    INSERT INTO property (property_name, property_uniq_id, room_number)
    VALUES ('Bulb', CONCAT('bulb1', NEW.room_number), NEW.room_number),
           ('Bulb', CONCAT('bulb2', NEW.room_number), NEW.room_number);
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `roomassignmentview`
-- (See below for the actual view)
--
DROP VIEW IF EXISTS `roomassignmentview`;
CREATE TABLE IF NOT EXISTS `roomassignmentview` (
`Room number` varchar(255)
,`Member 1` varchar(255)
,`Member 2` varchar(255)
,`Member 3` varchar(255)
,`Member 4` varchar(255)
);

-- --------------------------------------------------------

--
-- Table structure for table `room_assignment`
--

DROP TABLE IF EXISTS `room_assignment`;
CREATE TABLE IF NOT EXISTS `room_assignment` (
  `assignment_id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) DEFAULT NULL,
  `room_id` varchar(255) DEFAULT NULL,
  `assignment_date` date DEFAULT NULL,
  PRIMARY KEY (`assignment_id`),
  KEY `user_id` (`user_id`(250)),
  KEY `room_id` (`room_id`(250))
) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `room_assignment`
--

INSERT INTO `room_assignment` (`assignment_id`, `user_id`, `room_id`, `assignment_date`) VALUES
(15, 'TG-2019-485', 'R101', '2023-11-05'),
(2, 'TG-2019-502', '001', '2023-11-01'),
(11, 'TG/2019/500', '108', '2023-11-05'),
(5, 'TG/2019/001', '101', '2023-11-01'),
(6, 'TG/2019/004', '002', '2023-11-02'),
(8, 'TG/2019/509', '101', '2023-11-03'),
(12, 'TG/2019/488', '108', '2023-11-05');

--
-- Triggers `room_assignment`
--
DROP TRIGGER IF EXISTS `Before_Remove_Student`;
DELIMITER $$
CREATE TRIGGER `Before_Remove_Student` BEFORE DELETE ON `room_assignment` FOR EACH ROW BEGIN
    INSERT INTO room_assignment_history (assignment_id, user_id, room_id, assignment_date, unassignment_date)
    VALUES (OLD.assignment_id, OLD.user_id, OLD.room_id, OLD.assignment_date, CURDATE());
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `before_delete_assignment`;
DELIMITER $$
CREATE TRIGGER `before_delete_assignment` BEFORE DELETE ON `room_assignment` FOR EACH ROW BEGIN
    INSERT INTO room_assignment_old_data (old_user_id, old_room_id, old_assignment_date, deleted_date)
    VALUES (OLD.user_id, OLD.room_id, OLD.assignment_date, NOW());
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `room_assignment_history`
--

DROP TABLE IF EXISTS `room_assignment_history`;
CREATE TABLE IF NOT EXISTS `room_assignment_history` (
  `history_id` int NOT NULL AUTO_INCREMENT,
  `assignment_id` int DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `room_id` varchar(255) DEFAULT NULL,
  `assignment_date` date DEFAULT NULL,
  `unassignment_date` date DEFAULT NULL,
  PRIMARY KEY (`history_id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `room_assignment_history`
--

INSERT INTO `room_assignment_history` (`history_id`, `assignment_id`, `user_id`, `room_id`, `assignment_date`, `unassignment_date`) VALUES
(1, 1, 'TG501', '001', '2023-11-01', '2023-11-02'),
(2, 9, 'TG501', 'R001', '2023-11-04', '2023-11-04'),
(3, 4, 'TG509', '002', '2023-11-01', '2023-11-04'),
(4, 3, 'TG500', '002', '2023-11-01', '2023-11-04'),
(5, 7, 'TG503', '101', '2023-11-03', '2023-11-05'),
(6, 10, 'TG501', '108', '2023-11-05', '2023-11-05'),
(7, 13, 'TG500', '108', '2023-11-05', '2023-11-05'),
(8, 14, 'TG-2019-485', '108', '2023-11-05', '2023-11-05');

-- --------------------------------------------------------

--
-- Table structure for table `room_assignment_old_data`
--

DROP TABLE IF EXISTS `room_assignment_old_data`;
CREATE TABLE IF NOT EXISTS `room_assignment_old_data` (
  `old_assignment_id` int NOT NULL AUTO_INCREMENT,
  `old_user_id` varchar(255) DEFAULT NULL,
  `old_room_id` varchar(255) DEFAULT NULL,
  `old_assignment_date` date DEFAULT NULL,
  `deleted_date` datetime DEFAULT NULL,
  PRIMARY KEY (`old_assignment_id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `room_assignment_old_data`
--

INSERT INTO `room_assignment_old_data` (`old_assignment_id`, `old_user_id`, `old_room_id`, `old_assignment_date`, `deleted_date`) VALUES
(1, 'TG509', '002', '2023-11-01', NULL),
(2, 'TG500', '002', '2023-11-01', '2023-11-04 23:53:04'),
(3, 'TG503', '101', '2023-11-03', '2023-11-05 10:57:41'),
(4, 'TG501', '108', '2023-11-05', '2023-11-05 12:27:18'),
(5, 'TG500', '108', '2023-11-05', '2023-11-05 12:49:34'),
(6, 'TG-2019-485', '108', '2023-11-05', '2023-11-05 12:54:21');

-- --------------------------------------------------------

--
-- Stand-in structure for view `room_assignment_view`
-- (See below for the actual view)
--
DROP VIEW IF EXISTS `room_assignment_view`;
CREATE TABLE IF NOT EXISTS `room_assignment_view` (
`assignment_id` int
,`user_id` varchar(255)
,`room_id` varchar(255)
,`assignment_date` date
);

-- --------------------------------------------------------

--
-- Table structure for table `room_seq`
--

DROP TABLE IF EXISTS `room_seq`;
CREATE TABLE IF NOT EXISTS `room_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `room_seq`
--

INSERT INTO `room_seq` (`next_val`) VALUES
(401);

-- --------------------------------------------------------

--
-- Stand-in structure for view `student_view`
-- (See below for the actual view)
--
DROP VIEW IF EXISTS `student_view`;
CREATE TABLE IF NOT EXISTS `student_view` (
`id` bigint
,`email` varchar(255)
,`fname` varchar(255)
,`lname` varchar(255)
,`role` varchar(255)
,`user_index` varchar(255)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `sub_warden_view`
-- (See below for the actual view)
--
DROP VIEW IF EXISTS `sub_warden_view`;
CREATE TABLE IF NOT EXISTS `sub_warden_view` (
`id` bigint
,`email` varchar(255)
,`fname` varchar(255)
,`lname` varchar(255)
,`role` varchar(255)
,`user_index` varchar(255)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `today_complaints_view`
-- (See below for the actual view)
--
DROP VIEW IF EXISTS `today_complaints_view`;
CREATE TABLE IF NOT EXISTS `today_complaints_view` (
`id` bigint
,`approved_date` varchar(255)
,`complainant` varchar(255)
,`complaint_date` varchar(255)
,`description` varchar(255)
,`image_name` varchar(255)
,`property_type` varchar(255)
,`property_uniq_id` varchar(255)
,`room_number` varchar(255)
,`status` varchar(255)
);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `fname` varchar(255) NOT NULL,
  `lname` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  `user_index` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_5km0o2vp1i1yjcpniy6rb6hnw` (`user_index`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `email`, `enabled`, `fname`, `lname`, `password`, `role`, `user_index`) VALUES
(1, 'ashan@gmail.com', b'0', 'ashan', 'indrajith', '$2a$10$nWtwwi9jvsCW5LpqfC2lOOxagCKtwFRkY/kHWJYyk0R0021FznMKW', 'ADMIN', 'TG503'),
(2, 'ashan@gmail.com', b'0', 'ashan', 'indrajith', '$2a$10$gKVForOqgC5SBjJgWQpQU.wS/onxyxcoVhSRUDk3JUYDDYm2W5Lp2', 'ADMIN', 'TG504'),
(16, 'powerapremuditha@gail.com', b'0', 'Pawara', 'Premuditha', '$2a$10$Ofewytp2fsKWObJ/BbjSxONBjLF5q4QVGgnZ8GlY3gIKzqsy64kYO', 'STUDENT', 'TG-2019-482'),
(17, 'Liyanarachchi@gail.com', b'0', 'Kulunu', 'Liyanarachchi', '$2a$10$oQzb8HsaxNTBlIRkPYFQAOk7PgIG/peccO/YwlsI42ExlsYlkqzWm', 'STUDENT', 'TG-2019-483'),
(18, 'Rathnayaker@gail.com', b'0', 'Hansini', 'Rathnayake', '$2a$10$iJFgTg4/gfAuGflkH2pobeqc2t.qaapiAuyFmFdraQdssOnNho9bK', 'STUDENT', 'TG-2019-484'),
(22, 'chathuranagi@gmail.com', b'0', 'Hansini', 'chathurangi', '$2a$10$YLIJDb1oPQDThv0H7Xah2.laG5EtqmQZhyo4cWVn9ZQ5am0Ndmk7y', 'STUDENT', 'TG-2019-486'),
(23, 'ashan@gmail.com', b'0', 'ashan', 'indrajith', '$2a$10$NQJ/qAMkrkDipiRhyU/yXe0VLrVyGvPRcCFbPbtCHHXQd.W34Cmea', 'ADMIN', 'A001'),
(24, 'sanjeewa@gmail.com', b'0', 'Sanjeewa', 'chathuranga', '$2a$10$WyryayHzapDhF688U7FenuGokXlNs6Q2krrMpPiml6IIDwuh8UCiq', 'STUDENT', 'TG-2019-500'),
(25, 'ashan@gmail.com', b'0', 'ashan', 'indrajith', '$2a$10$1fP90QtO0508SeCRDqKZsOyaW/OtPwiEK3pqwCoZv511UkA.HF7xm', 'ADMIN', 'TG/2019/005'),
(30, 'umeen@gmail.com', b'0', 'Umeen', 'Rathnayaka', '$2a$10$iQkQk7bNWXTZ5UL8dNLsMeYVyLY7WuvPRd0uw6g2QKkzBRjkJ3D42', 'STUDENT', 'TG-2019-488'),
(31, 'saputhanthri@gmail.com', b'0', 'Raviduuu', 'saputhanthri', '$2a$10$pRbefzbFNpTnLbJURUhXvOgRE6Po8XTJOKvKPfuPvlICb7PVaZWna', 'STUDENT', 'TG-2019-489'),
(46, 'ashan@gmail.com', b'0', 'ashan', 'indrajith', '$2a$10$8N8lqxMHZYUcHYCbZlrsKu5cw0PknOl3DAihJaVMf4YTkKX/.f9CO', 'STUDENT', 'TG-2019-485'),
(47, 'Ekanayake@gmail.com', b'0', 'Banuka', 'Ekanayake', '$2a$10$B4fsoTHQj6NEA3g5m49Q5uX..7p3syOby8Htde8lY1coKNFee3OvO', 'WARDEN', 'W001'),
(51, 'subash@gmail.com', b'0', 'Subash', 'Jayasinghee', '$2a$10$fRi7B9hZe02imtO/hEgicOwWYL4S47B3SbebHpR4/bHroibw.tORq', 'DEAN', 'D001'),
(53, 'Sadun@gmail.com', b'0', 'Sadun', 'Kumara', '$2a$10$a.ILHjFG4rl2YHxzBbJu.ehMe6z.a8M1hkdYiLGR3Qi7ede8HogOO', 'SUBWARDEN', 'SB001'),
(54, 'rashmika@gmail.com', b'0', 'Nethmi', 'Rashmika', '$2a$10$0UxbfYE3meDhEMfZGSmkU.T0E82VRwTij10MCp2sB6veoHOrjiXDK', 'STUDENT', 'TG-2019-511'),
(55, 'nalin@gmail.com', b'0', 'Nalin', 'Rathnayake', '$2a$10$WF1en19zhaN3Q3xVoTU3SOVGfRMuXDmCgR4aqpx0/cditBvAw/8NK', 'WARDEN', 'W003');

--
-- Triggers `users`
--
DROP TRIGGER IF EXISTS `after_user_insert`;
DELIMITER $$
CREATE TRIGGER `after_user_insert` AFTER INSERT ON `users` FOR EACH ROW BEGIN
    IF NEW.role = "ADMIN" THEN
        INSERT INTO admin (user_index, password, fname, lname, email)
        VALUES (NEW.user_index, NEW.password, NEW.fname, NEW.lname, NEW.email);
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `warden_complain_view`
-- (See below for the actual view)
--
DROP VIEW IF EXISTS `warden_complain_view`;
CREATE TABLE IF NOT EXISTS `warden_complain_view` (
`id` bigint
,`approved_date` varchar(255)
,`complainant` varchar(255)
,`complaint_date` varchar(255)
,`description` varchar(255)
,`image_name` varchar(255)
,`property_type` varchar(255)
,`property_uniq_id` varchar(255)
,`room_number` varchar(255)
,`status` varchar(255)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `warden_view`
-- (See below for the actual view)
--
DROP VIEW IF EXISTS `warden_view`;
CREATE TABLE IF NOT EXISTS `warden_view` (
`id` bigint
,`email` varchar(255)
,`fname` varchar(255)
,`lname` varchar(255)
,`role` varchar(255)
,`user_index` varchar(255)
,`password` varchar(255)
);

-- --------------------------------------------------------

--
-- Table structure for table `wardern_complain`
--

DROP TABLE IF EXISTS `wardern_complain`;
CREATE TABLE IF NOT EXISTS `wardern_complain` (
  `id` int NOT NULL AUTO_INCREMENT,
  `approved_date` datetime DEFAULT NULL,
  `complainant` varchar(255) DEFAULT NULL,
  `complaint_date` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image_name` varchar(255) DEFAULT NULL,
  `property_type` varchar(255) DEFAULT NULL,
  `property_uniq_id` varchar(255) DEFAULT NULL,
  `room_number` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=303 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `wardern_complain`
--

INSERT INTO `wardern_complain` (`id`, `approved_date`, `complainant`, `complaint_date`, `description`, `image_name`, `property_type`, `property_uniq_id`, `room_number`, `status`) VALUES
(152, NULL, 'TG500', '2023-11-01', 'damage', 'issue.png', 'Chair', 'ch1108', '108', 'pending'),
(202, NULL, 'TG500', '2023-11-04', 'damage', 'issue.png', 'Chair', 'ch1108', '108', 'pending'),
(203, NULL, 'TG500', '2023-11-01', 'damage', 'issue.png', 'Chair', 'ch1108', '108', 'pending'),
(252, NULL, 'TG500', '2023-11-05', 'broken', 'issue.png', 'Chair', 'ch4108', '108', 'pending'),
(302, NULL, 'TG-2019-511', '2023-11-05', 'damage', 'ashan.png', 'Chair', 'ch1108', '108', 'pending'),
(153, NULL, 'TG-2019-500', '2023-10-01', 'damage', 'issue.png', 'Chair', 'ch1108', '108', 'pending'),
(154, NULL, 'TG500', '2023-11-04', 'damage', 'issue.png', 'Chair', 'ch1108', '108', 'pending');

-- --------------------------------------------------------

--
-- Structure for view `approvedcomplaints`
--
DROP TABLE IF EXISTS `approvedcomplaints`;

DROP VIEW IF EXISTS `approvedcomplaints`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `approvedcomplaints`  AS SELECT `complain`.`id` AS `id`, `complain`.`approved_date` AS `approved_date`, `complain`.`complainant` AS `complainant`, `complain`.`complaint_date` AS `complaint_date`, `complain`.`description` AS `description`, `complain`.`image_name` AS `image_name`, `complain`.`property_type` AS `property_type`, `complain`.`property_uniq_id` AS `property_uniq_id`, `complain`.`room_number` AS `room_number`, `complain`.`status` AS `status` FROM `complain` WHERE (`complain`.`status` = 'approved')  ;

-- --------------------------------------------------------

--
-- Structure for view `available_rooms`
--
DROP TABLE IF EXISTS `available_rooms`;

DROP VIEW IF EXISTS `available_rooms`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `available_rooms`  AS SELECT `r`.`room_number` AS `room_number`, (`r`.`room_capacity` - ifnull(`ra`.`assigned_students`,0)) AS `available_capacity` FROM (`room` `r` left join (select `room_assignment`.`room_id` AS `room_id`,count(0) AS `assigned_students` from `room_assignment` group by `room_assignment`.`room_id`) `ra` on((`r`.`room_number` = `ra`.`room_id`))) WHERE ((`ra`.`assigned_students` is null) OR (`ra`.`assigned_students` < `r`.`room_capacity`))  ;

-- --------------------------------------------------------

--
-- Structure for view `daily_complaint_count`
--
DROP TABLE IF EXISTS `daily_complaint_count`;

DROP VIEW IF EXISTS `daily_complaint_count`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `daily_complaint_count`  AS SELECT cast(`complain`.`complaint_date` as date) AS `complaint_day`, count(0) AS `num_complaints` FROM `complain` GROUP BY cast(`complain`.`complaint_date` as date)  ;

-- --------------------------------------------------------

--
-- Structure for view `dean_complain_view`
--
DROP TABLE IF EXISTS `dean_complain_view`;

DROP VIEW IF EXISTS `dean_complain_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `dean_complain_view`  AS SELECT `complain`.`id` AS `id`, `complain`.`approved_date` AS `approved_date`, `complain`.`complainant` AS `complainant`, `complain`.`complaint_date` AS `complaint_date`, `complain`.`description` AS `description`, `complain`.`image_name` AS `image_name`, `complain`.`property_type` AS `property_type`, `complain`.`property_uniq_id` AS `property_uniq_id`, `complain`.`room_number` AS `room_number`, `complain`.`status` AS `status` FROM `complain` WHERE ((`complain`.`status` <> 'Approved') AND (timestampdiff(DAY,`complain`.`complaint_date`,cast(now() as date)) >= 7))  ;

-- --------------------------------------------------------

--
-- Structure for view `dean_view`
--
DROP TABLE IF EXISTS `dean_view`;

DROP VIEW IF EXISTS `dean_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `dean_view`  AS SELECT `users`.`id` AS `id`, `users`.`email` AS `email`, `users`.`fname` AS `fname`, `users`.`lname` AS `lname`, `users`.`role` AS `role`, `users`.`user_index` AS `user_index` FROM `users` WHERE (`users`.`role` = 'DEAN')  ;

-- --------------------------------------------------------

--
-- Structure for view `roomassignmentview`
--
DROP TABLE IF EXISTS `roomassignmentview`;

DROP VIEW IF EXISTS `roomassignmentview`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `roomassignmentview`  AS SELECT `ra`.`room_id` AS `Room number`, ifnull(`s1`.`user_id`,' available') AS `Member 1`, ifnull(`s2`.`user_id`,' available') AS `Member 2`, ifnull(`s3`.`user_id`,' available') AS `Member 3`, ifnull(`s4`.`user_id`,' available') AS `Member 4` FROM (((((select distinct `room_assignment`.`room_id` AS `room_id` from `room_assignment`) `ra` left join `room_assignment` `s1` on(((`ra`.`room_id` = `s1`.`room_id`) and (`s1`.`assignment_id` = (select `room_assignment`.`assignment_id` from `room_assignment` where (`room_assignment`.`room_id` = `ra`.`room_id`) order by `room_assignment`.`assignment_id` limit 1))))) left join `room_assignment` `s2` on(((`ra`.`room_id` = `s2`.`room_id`) and (`s2`.`assignment_id` = (select `room_assignment`.`assignment_id` from `room_assignment` where (`room_assignment`.`room_id` = `ra`.`room_id`) order by `room_assignment`.`assignment_id` limit 1,1))))) left join `room_assignment` `s3` on(((`ra`.`room_id` = `s3`.`room_id`) and (`s3`.`assignment_id` = (select `room_assignment`.`assignment_id` from `room_assignment` where (`room_assignment`.`room_id` = `ra`.`room_id`) order by `room_assignment`.`assignment_id` limit 2,1))))) left join `room_assignment` `s4` on(((`ra`.`room_id` = `s4`.`room_id`) and (`s4`.`assignment_id` = (select `room_assignment`.`assignment_id` from `room_assignment` where (`room_assignment`.`room_id` = `ra`.`room_id`) order by `room_assignment`.`assignment_id` limit 3,1)))))  ;

-- --------------------------------------------------------

--
-- Structure for view `room_assignment_view`
--
DROP TABLE IF EXISTS `room_assignment_view`;

DROP VIEW IF EXISTS `room_assignment_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `room_assignment_view`  AS SELECT `room_assignment`.`assignment_id` AS `assignment_id`, `room_assignment`.`user_id` AS `user_id`, `room_assignment`.`room_id` AS `room_id`, `room_assignment`.`assignment_date` AS `assignment_date` FROM `room_assignment` WHERE (`room_assignment`.`room_id` = '101')  ;

-- --------------------------------------------------------

--
-- Structure for view `student_view`
--
DROP TABLE IF EXISTS `student_view`;

DROP VIEW IF EXISTS `student_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `student_view`  AS SELECT `users`.`id` AS `id`, `users`.`email` AS `email`, `users`.`fname` AS `fname`, `users`.`lname` AS `lname`, `users`.`role` AS `role`, `users`.`user_index` AS `user_index` FROM `users` WHERE (`users`.`role` = 'STUDENT')  ;

-- --------------------------------------------------------

--
-- Structure for view `sub_warden_view`
--
DROP TABLE IF EXISTS `sub_warden_view`;

DROP VIEW IF EXISTS `sub_warden_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `sub_warden_view`  AS SELECT `users`.`id` AS `id`, `users`.`email` AS `email`, `users`.`fname` AS `fname`, `users`.`lname` AS `lname`, `users`.`role` AS `role`, `users`.`user_index` AS `user_index` FROM `users` WHERE (`users`.`role` = 'SUBWARDEN')  ;

-- --------------------------------------------------------

--
-- Structure for view `today_complaints_view`
--
DROP TABLE IF EXISTS `today_complaints_view`;

DROP VIEW IF EXISTS `today_complaints_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `today_complaints_view`  AS SELECT `complain`.`id` AS `id`, `complain`.`approved_date` AS `approved_date`, `complain`.`complainant` AS `complainant`, `complain`.`complaint_date` AS `complaint_date`, `complain`.`description` AS `description`, `complain`.`image_name` AS `image_name`, `complain`.`property_type` AS `property_type`, `complain`.`property_uniq_id` AS `property_uniq_id`, `complain`.`room_number` AS `room_number`, `complain`.`status` AS `status` FROM `complain` WHERE (cast(`complain`.`complaint_date` as date) = curdate())  ;

-- --------------------------------------------------------

--
-- Structure for view `warden_complain_view`
--
DROP TABLE IF EXISTS `warden_complain_view`;

DROP VIEW IF EXISTS `warden_complain_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `warden_complain_view`  AS SELECT `complain`.`id` AS `id`, `complain`.`approved_date` AS `approved_date`, `complain`.`complainant` AS `complainant`, `complain`.`complaint_date` AS `complaint_date`, `complain`.`description` AS `description`, `complain`.`image_name` AS `image_name`, `complain`.`property_type` AS `property_type`, `complain`.`property_uniq_id` AS `property_uniq_id`, `complain`.`room_number` AS `room_number`, `complain`.`status` AS `status` FROM `complain` WHERE ((`complain`.`status` <> 'Approved') AND (timestampdiff(DAY,`complain`.`complaint_date`,cast(now() as date)) >= 3))  ;

-- --------------------------------------------------------

--
-- Structure for view `warden_view`
--
DROP TABLE IF EXISTS `warden_view`;

DROP VIEW IF EXISTS `warden_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `warden_view`  AS SELECT `users`.`id` AS `id`, `users`.`email` AS `email`, `users`.`fname` AS `fname`, `users`.`lname` AS `lname`, `users`.`role` AS `role`, `users`.`user_index` AS `user_index`, `users`.`password` AS `password` FROM `users` WHERE (`users`.`role` = 'WARDEN')  ;

DELIMITER $$
--
-- Events
--
DROP EVENT IF EXISTS `RefreshDeanComplainViewEvent`$$
CREATE DEFINER=`root`@`localhost` EVENT `RefreshDeanComplainViewEvent` ON SCHEDULE EVERY 2 MINUTE STARTS '2023-11-05 19:57:55' ON COMPLETION NOT PRESERVE ENABLE DO BEGIN
  
  DROP VIEW IF EXISTS dean_complain_view;
  
  
  CREATE VIEW dean_complain_view AS
  SELECT *
  FROM complain
  WHERE status <> 'Approved' AND TIMESTAMPDIFF(DAY, complaint_date, DATE(NOW())) >= 7;
END$$

DROP EVENT IF EXISTS `RefreshWardenComplainViewEvent`$$
CREATE DEFINER=`root`@`localhost` EVENT `RefreshWardenComplainViewEvent` ON SCHEDULE EVERY 2 MINUTE STARTS '2023-11-05 19:57:42' ON COMPLETION NOT PRESERVE ENABLE DO BEGIN
  
  DROP VIEW IF EXISTS warden_complain_view;
  
  
  CREATE VIEW warden_complain_view AS
  SELECT *
  FROM complain
  WHERE status <> 'Approved' AND TIMESTAMPDIFF(DAY, complaint_date, DATE(NOW())) >= 3;
END$$

DROP EVENT IF EXISTS `SendComplaintsToWarden`$$
CREATE DEFINER=`root`@`localhost` EVENT `SendComplaintsToWarden` ON SCHEDULE EVERY 1 MINUTE STARTS '2023-11-04 00:01:00' ON COMPLETION NOT PRESERVE ENABLE DO BEGIN
  INSERT INTO wardern_complain (id,approved_date,complainant,complaint_date,description,image_name,property_type,property_uniq_id,room_number,status)


  SELECT  id,approved_date,complainant,complaint_date,description,image_name,property_type,property_uniq_id,room_number,status
  FROM complain
  WHERE status="pending" AND complaint_date <= NOW() - INTERVAL  1 MINUTE;

  DELETE FROM complain
  WHERE status="pending" AND complaint_date <= NOW() - INTERVAL  1 MINUTE;
END$$

DELIMITER ;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
