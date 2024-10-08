/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.33-log : Database - tarena_lbs_attach
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`tarena_lbs_attach` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `tarena_lbs_attach`;

/*Table structure for table `attach` */

DROP TABLE IF EXISTS `attach`;

CREATE TABLE `attach` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `file_uuid` varchar(64) NOT NULL,
  `client_file_name` varchar(256) NOT NULL DEFAULT '',
  `download_times` int(11) unsigned NOT NULL DEFAULT '0',
  `content_length` int(11) unsigned DEFAULT '0',
  `content_type` varchar(64) NOT NULL DEFAULT '',
  `is_cover` tinyint(1) NOT NULL DEFAULT '0',
  `width` int(10) DEFAULT '0',
  `height` int(10) DEFAULT '0',
  `business_type` int(10) NOT NULL DEFAULT '0',
  `business_id` int(10) NOT NULL DEFAULT '0',
  `status` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT 'STATUS',
  `remark` varchar(512) DEFAULT '' COMMENT '备注',
  `create_user_id` bigint(11) unsigned NOT NULL DEFAULT '0' COMMENT '创建人ID',
  `gmt_create` bigint(11) unsigned NOT NULL DEFAULT '0' COMMENT '创建时间',
  `modified_user_id` bigint(11) unsigned DEFAULT '0' COMMENT '更新人ID',
  `gmt_modified` bigint(11) unsigned DEFAULT '0' COMMENT '更新时间',
  `create_user_name` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
  `modified_user_name` varchar(64) DEFAULT '' COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=743 DEFAULT CHARSET=utf8mb4 COMMENT='attach';

/*Data for the table `attach` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
