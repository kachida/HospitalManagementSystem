use usersvc;

CREATE TABLE `user` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `role` varchar(20) NOT NULL,
  `username` varchar(100) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phonenumber` varchar(50) DEFAULT NULL,
  `address` varchar(300) DEFAULT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `created_date` timestamp DEFAULT CURRENT_TIMESTAMP,
  `last_Modified_By` varchar(300) DEFAULT NULL,
  `last_Modified_Date` timestamp DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `id` (`ID`)
);

/*
CREATE TABLE `USER_ROLE`(
`row_id` int NOT NULL AUTO_INCREMENT,
`user_id` int NOT NULL,
`role_id` int NOT NULL
)*/

CREATE TABLE `patient` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `complain` varchar(255) NOT NULL,
  `phoneno` varchar(30) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `date_of_visit` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` int NOT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `created_date` timestamp DEFAULT CURRENT_TIMESTAMP,
  `last_Modified_By` varchar(300) DEFAULT NULL,
  `last_Modified_Date` timestamp DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

CREATE TABLE `vitalsign` (
  `id` int NOT NULL AUTO_INCREMENT,
  `temperature` double(10,2) DEFAULT NULL,
  `bloodsugar` double(10,2) DEFAULT NULL,
  `weight` double(10,2) DEFAULT NULL,
  `height` double(10,2) DEFAULT NULL,
  `spo2` double(10,2) DEFAULT NULL,
  `pulse` double(10,2) DEFAULT NULL,
  `patient_id` int NOT NULL,
  `user_id` int NOT NULL,
  `patient_name` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `created_date` timestamp DEFAULT CURRENT_TIMESTAMP,
  `last_Modified_By` varchar(300) DEFAULT NULL,
  `last_Modified_Date` timestamp DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_patient` (`patient_id`),
  CONSTRAINT `fk_patient` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `roles` (
  `ROLE_ID` int NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) DEFAULT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `created_date` timestamp DEFAULT CURRENT_TIMESTAMP,
  `last_Modified_By` varchar(300) DEFAULT NULL,
  `last_Modified_Date` timestamp DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ROLE_ID`)
);
