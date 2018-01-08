/*
SQLyog Community v12.2.1 (64 bit)
MySQL - 5.6.11-enterprise-commercial-advanced : Database - ammbr_beta
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ammbr_beta` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `ammbr_beta`;

/*Table structure for table `address_index` */

DROP TABLE IF EXISTS `address_index`;

CREATE TABLE `address_index` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `last_index` decimal(19,2) DEFAULT NULL,
  `payment_recieved` bit(1) DEFAULT NULL,
  `address_index` decimal(19,2) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `creation_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*Table structure for table `ether_transfer_state` */

DROP TABLE IF EXISTS `ether_transfer_state`;

CREATE TABLE `ether_transfer_state` (
  `ether_transfer_state` bigint(20) NOT NULL AUTO_INCREMENT,
  `hash` varchar(255) DEFAULT NULL,
  `mining_state` bit(1) DEFAULT NULL,
  `transfer_ether` varchar(255) DEFAULT NULL,
  `unique_address` varchar(255) DEFAULT NULL,
  `creation_date` datetime DEFAULT NULL,
  PRIMARY KEY (`ether_transfer_state`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*Table structure for table `index_counter` */

DROP TABLE IF EXISTS `index_counter`;

CREATE TABLE `index_counter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `last_index` decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*Table structure for table `pay_to_wallet` */

DROP TABLE IF EXISTS `pay_to_wallet`;

CREATE TABLE `pay_to_wallet` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `block_number` decimal(19,2) DEFAULT NULL,
  `cumulative_gas_used` decimal(19,2) DEFAULT NULL,
  `gas_used` decimal(19,2) DEFAULT NULL,
  `transaction_hash` varchar(255) DEFAULT NULL,
  `unique_address` varchar(255) DEFAULT NULL,
  `token_recieved` varchar(255) DEFAULT NULL,
  `rest_response_json` text,
  `email` varchar(255) DEFAULT NULL,
  `creation_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*Table structure for table `wallet_to_payee` */

DROP TABLE IF EXISTS `wallet_to_payee`;

CREATE TABLE `wallet_to_payee` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `block_number` varchar(255) DEFAULT NULL,
  `cumulative_gas_used` varchar(255) DEFAULT NULL,
  `gas_used` varchar(255) DEFAULT NULL,
  `payment_address` varchar(255) DEFAULT NULL,
  `transaction_hash` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `number_of_token` varchar(255) DEFAULT NULL,
  `creation_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
