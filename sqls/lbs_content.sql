/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.33-log : Database - tarena_lbs_content
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`tarena_lbs_content` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `tarena_lbs_content`;

/*Table structure for table `article_category` */

DROP TABLE IF EXISTS `article_category`;

CREATE TABLE `article_category` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '文章分类主键ID',
  `category_name` varchar(100) DEFAULT NULL COMMENT '分类名称',
  `sort` int(10) DEFAULT NULL COMMENT '排序',
  `category_status` int(1) DEFAULT '1' COMMENT '状态：1==启用 2==禁用',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4;

/*Data for the table `article_category` */

insert  into `article_category`(`id`,`category_name`,`sort`,`category_status`,`create_time`,`update_time`) values (57,'美食',99,1,'2024-06-11 10:07:44','2024-06-11 10:07:44');
insert  into `article_category`(`id`,`category_name`,`sort`,`category_status`,`create_time`,`update_time`) values (58,'购物',99,1,'2024-06-11 10:07:53','2024-07-09 15:04:35');
insert  into `article_category`(`id`,`category_name`,`sort`,`category_status`,`create_time`,`update_time`) values (59,'旅游',99,1,'2024-07-01 16:17:20','2024-07-09 15:04:14');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
