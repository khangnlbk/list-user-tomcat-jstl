-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.38-community


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema 24_nguyenlinhkhang_manageuser
--

CREATE DATABASE IF NOT EXISTS 24_nguyenlinhkhang_manageuser;
USE 24_nguyenlinhkhang_manageuser;

--
-- Definition of table `mst_group`
--

DROP TABLE IF EXISTS `mst_group`;
CREATE TABLE `mst_group` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(255) NOT NULL,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `mst_group`
--

/*!40000 ALTER TABLE `mst_group` DISABLE KEYS */;
INSERT INTO `mst_group` (`group_id`,`group_name`) VALUES 
 (1,'Phòng phát triển số 1'),
 (2,'Phòng phát triển số 2'),
 (3,'Phòng phát triển số 3'),
 (4,'Phòng phát triển số 4');
/*!40000 ALTER TABLE `mst_group` ENABLE KEYS */;


--
-- Definition of table `mst_japan`
--

DROP TABLE IF EXISTS `mst_japan`;
CREATE TABLE `mst_japan` (
  `code_level` varchar(15) NOT NULL,
  `name_level` varchar(255) NOT NULL,
  PRIMARY KEY (`code_level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `mst_japan`
--

/*!40000 ALTER TABLE `mst_japan` DISABLE KEYS */;
INSERT INTO `mst_japan` (`code_level`,`name_level`) VALUES 
 ('N1','Trình độ tiếng nhật cấp 1'),
 ('N2','Trình độ tiếng nhật cấp 2'),
 ('N3','Trình độ tiếng nhật cấp 3'),
 ('N4','Trình độ tiếng nhật cấp 4'),
 ('N5','Trình độ tiếng nhật cấp 5');
/*!40000 ALTER TABLE `mst_japan` ENABLE KEYS */;


--
-- Definition of table `tbl_detail_user_japan`
--

DROP TABLE IF EXISTS `tbl_detail_user_japan`;
CREATE TABLE `tbl_detail_user_japan` (
  `detail_user_japan_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `code_level` varchar(15) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `total` int(11) NOT NULL,
  PRIMARY KEY (`detail_user_japan_id`),
  KEY `user_id` (`user_id`),
  KEY `code_level` (`code_level`),
  CONSTRAINT `tbl_detail_user_japan_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`user_id`),
  CONSTRAINT `tbl_detail_user_japan_ibfk_2` FOREIGN KEY (`code_level`) REFERENCES `mst_japan` (`code_level`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_detail_user_japan`
--

/*!40000 ALTER TABLE `tbl_detail_user_japan` DISABLE KEYS */;
INSERT INTO `tbl_detail_user_japan` (`detail_user_japan_id`,`user_id`,`code_level`,`start_date`,`end_date`,`total`) VALUES 
 (1,1,'N5','2005-07-08','2006-07-08',90),
 (2,4,'N1','2005-05-20','2006-05-20',100),
 (6,20,'N1','2020-08-13','2021-08-13',123),
 (7,47,'N1','2020-08-19','2021-08-19',123),
 (9,8,'N1','2020-08-19','2021-08-19',22),
 (11,26,'N2','2020-08-19','2021-08-19',44),
 (12,21,'N2','2020-01-01','2021-01-01',123),
 (13,12,'N1','2020-01-01','2020-01-02',66);
/*!40000 ALTER TABLE `tbl_detail_user_japan` ENABLE KEYS */;


--
-- Definition of table `tbl_user`
--

DROP TABLE IF EXISTS `tbl_user`;
CREATE TABLE `tbl_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL,
  `login_name` varchar(15) NOT NULL,
  `password` varchar(50) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `full_name_kana` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `tel` varchar(15) NOT NULL,
  `birthday` date NOT NULL,
  `rule` int(1) NOT NULL,
  `salt` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`),
  KEY `group_id` (`group_id`),
  CONSTRAINT `tbl_user_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `mst_group` (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_user`
--

/*!40000 ALTER TABLE `tbl_user` DISABLE KEYS */;
INSERT INTO `tbl_user` (`user_id`,`group_id`,`login_name`,`password`,`full_name`,`full_name_kana`,`email`,`tel`,`birthday`,`rule`,`salt`) VALUES 
 (1,1,'ntmhuong','1e1dba5e6fb1d5bf348b88885836791bdcf06ba8','Nguyễn Thị Mai Hương','Hương Kana','huong@gmail.com','123456789','1983-07-08',0,'20200715142300000'),
 (2,1,'hieudt','123456','Đoàn Trọng Hiếu','Hiếu Kana','hieu@gmail.com','987456123','1983-08-08',1,'abcxyz2'),
 (3,2,'longth','123456','Trần Hoàng Long','Long Kana','long@gmail.com','654123789','1983-09-08',1,'abcxyz3'),
 (4,2,'dungdv','123456','Đỗ Văn Dũng','カタカナ','dung@gmail.com','1-1-1','1983-10-08',1,'abcxyz4'),
 (5,3,'phuongnv','123456','Nguyễn Việt Phương','Phương Kana','phuong@gmail.com','456987213','1983-11-08',0,'abcxyz5'),
 (6,1,'admin','1e1dba5e6fb1d5bf348b88885836791bdcf06ba8','Nguyen Linh Khang','グエン・リン・カン','khangnl.bk@gmail.com','0123-5678-0123','1995-10-18',0,'20200715142300000'),
 (7,1,'カカ１０カカ','wwwwwwww10wwwwwwww20wwwwwwww30wwwwwwww40wwwwwwww50','wwwwwwww10wwwwwwww20wwwwwwww30wwwwwwww40wwwwwwww50wwwwwwww60wwwwwwww70wwwwwwww80wwwwwwww90wwwwwwww00wwwwwwww10wwwwwwww20wwwwwwww30wwwwwwww40wwwwwwww50wwwwwwww60wwwwwwww70wwwwwwww80wwwwwwww90wwwwwwww00wwwwwwww10wwwwwwww20wwwwwwww30wwwwwwww40wwwwwwww50wwwww','カカカカカカカカ１０カカカカカカカカ２０カカカカカカカカ３０カカカカカカカカ４０カカカカカカカカ５０カカカカカカカカ６０カカカカカカカカ７０カカカカカカカカ８０カカカカカカカカ９０カカカカカカカカ００カカカカカカカカ１０カカカカカカカカ２０カカカカカカカカ３０カカカカカカカカ４０カカカカカカカカ５０カカカカカカカカ６０カカカカカカカカ７０カカカカカカカカ８０カカカカカカカカ９０カカカカカカカカ００カカカカカカカカ１０カカカカカカカカ２０カカカカカカカカ３０カカカカカカカカ４０カカカカカカカカ５０カカカカカ','wwwwwwww10wwwwwwww20wwwwwwww30wwwwwwww40wwwwwwww50wwwwwwww60wwwwwwww70wwwwwwww80wwwwwwww90wwwwwwww00wwwwwwww10wwwwwwww20wwwwwwww30wwwwwwww40wwwwwwww50wwwwwwww60wwwwwwww70wwwwwwww80wwwwwwww90wwwwwwww00wwwwwwww10wwwwwwww20wwwwwwww30wwwwwwww40wwwwwwww50a@a.a','1234-6789-1234','1990-10-10',1,'123456'),
 (8,1,'test8','123456','8','','test8@gmail.com','1234-6789-1234','1990-10-10',9,'123456'),
 (9,1,'test9','123456','_test','test_kana','test9@gmail.com','1234-6789-1234','1990-10-10',1,'123456'),
 (10,1,'test10','123456','カカ１０カカ','test_kana','test10@gmail.com','1234-6789-1234','1990-10-10',1,'123456'),
 (12,1,'test12','123456','123','カカカカカカカカカカカカカカカカカカカカカカカカカカカ','d@gmail.com','1234-6789-1234','1995-03-31',0,'123456'),
 (13,1,'test13','123456','test','カタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカナカタカ','test13@gmail.com','1234-6789-1234','1990-10-10',1,'123456'),
 (14,1,'test14','123456','test','','test14@gmail.com','1234-6789-1234','1990-10-10',1,'123456'),
 (15,1,'test15','123456','test','test_kana','test15@gmail.com','1234-6789-1234','1990-10-10',1,'123456'),
 (16,1,'test16','123456','testwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww','','test16@gmail.com','1234-6789-1234','1990-10-10',1,'123456'),
 (18,1,'test18','123456','test','test_kana','test18@gmail.com','1234-6789-1234','1990-10-10',1,'123456'),
 (20,1,'test20','123456','test','カタカナ','test20@gmail.com','1234-6789-1234','1990-10-10',1,'123456'),
 (21,1,'test21','123456','test','','@@@@@@@@@@........@@@...','1234-6789-1234','1990-10-10',1,'123456'),
 (22,1,'test22','123456','test','test_kana','test22@gmail.com','1234-6789-1234','1990-10-10',1,'123456'),
 (26,1,'test26','123456','test','','test26@gmail.com','1234-6789-1234','1990-10-10',1,'123456'),
 (27,1,'test27','123456','test','test_kana','test27@gmail.com','1234-6789-1234','1990-10-10',1,'123456'),
 (29,1,'test29','123456','test','test_kana','test29@gmail.com','1234-6789-1234','1990-10-10',1,'123456'),
 (30,1,'test30','123456','test','test_kana','test30@gmail.com','1234-6789-1234','1990-10-10',6,'123456'),
 (31,1,'test31','123456','test','test_kana','test31@gmail.com','1234-6789-1234','1990-10-10',1,'123456'),
 (33,1,'test33','123456','test','test_kana','test33@gmail.com','1234-6789-1234','1990-10-10',1,'123456'),
 (36,1,'ngvana_123','0c2c8375269619e23edd37642f069c2c8d798cf2','鼻の穴','','ngvana@luvina.net','097-123-456','1999-01-02',1,'20200817135914140'),
 (37,1,'ngvana_12','8e0bee2e485ae42d14f9c302398bd16839077ddb','NguyenVanAA','カカカカカ','NguyenVanA@gmail.com','1-1-1','2020-08-17',6,'20200817143114049'),
 (38,1,'TestTrungEmail','d46738cd7ba62775f536ed6a991d993c48e13826','TestTrungEmail','','ngvana12@luvina.net','1-1-1','2020-08-17',1,'20200817143550496'),
 (40,1,'TestAddSucc','431847746eb0fa906472f947381d2142854b9cba','TestAddSucc','','TestAddSucc@a.a','1-1-1','2020-08-17',1,'20200817145321480'),
 (41,1,'TestTrungLogin','ec8a2b2e02968568042f447ff6a8cd168862a8a0','TestTrungLogin','','TestTrungLogin@a.a','1-1-1','2020-08-17',1,'20200817145750786'),
 (42,1,'TestTrungLogin1','a7177c41739b23f47d798973d8a0b2dd333b6ade','TestTrungLogin1','','TestTrungLogin1@1.1','1-1-1','2020-07-17',1,'20200817150011648'),
 (43,1,'TestTrungLogin2','ffbe918b196efbbfdaa1af2d585db4335c1d58a9','TestTrungLogin2','','TestTrungLogin2@a.a','2-2-2','2020-08-17',1,'20200817150410583'),
 (44,1,'TestTrungLogin3','1e731f8d64eabaf8d725173fd687698c940a4f5e','TestTrungLogin3','','TestTrungLogin3@3.3','1-1-1','2020-08-17',1,'20200817150929803'),
 (45,1,'TestTrungLogin4','c4cf46a84dcdbe1e77b92168bf221f5587437dad','TestTrungLogin4','','TestTrungLogin4@a.a','1-1-1','2020-08-17',1,'20200817151148584'),
 (46,1,'TestTrungLogin5','48fa10199ba60551c7e42d20adbdbd4cdca84282','TestTrungLogin5','','TestTrungLogin5@a.a','1-1-1','2020-08-17',1,'20200817151529031'),
 (47,1,'duymanhtest','6f0a898685cfa225a891a882f8a52b6cb1ca33b2','wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww256','','wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww@gm.com','1234-6789-1234','2020-08-19',1,'20200819090100430');
/*!40000 ALTER TABLE `tbl_user` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
