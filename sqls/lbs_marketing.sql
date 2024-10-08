/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.33-log : Database - tarena_lbs_marketing
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`tarena_lbs_marketing` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `tarena_lbs_marketing`;

/*Table structure for table `coupon` */

DROP TABLE IF EXISTS `coupon`;

CREATE TABLE `coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `business_id` int(11) NOT NULL COMMENT '商家ID',
  `coupon_name` varchar(64) NOT NULL COMMENT '优惠券名称',
  `discount_value` decimal(10,0) NOT NULL COMMENT '抵扣金额：代金券是具体金额、折扣卷是百分比',
  `max_discount_amount` decimal(10,0) NOT NULL COMMENT '最大折扣金额',
  `applicable` char(10) NOT NULL COMMENT '使用范围：默认通用',
  `usage_limit` int(11) NOT NULL COMMENT '总共可被领取次数',
  `max_usage_limit` int(11) NOT NULL COMMENT '单次消费最多使用限制',
  `start_date` datetime NOT NULL COMMENT '开始时间',
  `end_date` datetime NOT NULL COMMENT '结束时间',
  `status` tinyint(4) NOT NULL COMMENT '状态：激活、过期、禁用等',
  `describes` varchar(1024) NOT NULL COMMENT '说明',
  `create_at` datetime NOT NULL COMMENT '创建时间',
  `update_at` datetime DEFAULT NULL COMMENT '更新时间',
  `coupon_type` tinyint(4) NOT NULL COMMENT '优惠券类型',
  `usage_num` int(11) NOT NULL COMMENT '领取数量',
  `enable_status` tinyint(11) NOT NULL COMMENT '启用状态',
  `exclusion_type` tinyint(11) DEFAULT NULL COMMENT '互斥规则',
  `order_amount` decimal(11,0) DEFAULT NULL COMMENT '订单满多少元可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COMMENT='优惠券表';

/*Data for the table `coupon` */

/*Table structure for table `coupon_code` */

DROP TABLE IF EXISTS `coupon_code`;

CREATE TABLE `coupon_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_id` int(11) NOT NULL COMMENT '券id',
  `business_id` int(11) NOT NULL COMMENT '商家id',
  `status` tinyint(4) DEFAULT NULL COMMENT '分配状态已分配未分配',
  `coupon_code` varchar(64) NOT NULL COMMENT '优惠券编码',
  `create_at` datetime NOT NULL COMMENT '创建时间',
  `update_at` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=181865 DEFAULT CHARSET=utf8mb4 COMMENT='优惠券券码表';

/*Data for the table `coupon_code` */

/*Table structure for table `marketing_activity` */

DROP TABLE IF EXISTS `activity`;

CREATE TABLE `activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activity_name` varchar(64) NOT NULL COMMENT '影片名称',
  `start_date` datetime DEFAULT NULL COMMENT '活动开始时间',
  `end_date` datetime DEFAULT NULL COMMENT '活动开始时间',
  `describes` varchar(64) NOT NULL COMMENT '活动说明、活动规则',
  `img_ids` varchar(64) NOT NULL COMMENT '活动图片ID逗号分割',
  `shop_ids` varchar(64) NOT NULL COMMENT '活动店铺ID逗号分割',
  `target_customer` varchar(64) NOT NULL COMMENT '目标人群ID',
  `activity_target` varchar(64) NOT NULL COMMENT '活动目标',
  `delivery_channel` tinyint(11) NOT NULL COMMENT '投放渠道',
  `reward_coupon_id` varchar(64) NOT NULL COMMENT '奖励优惠券ID',
  `share_profile` varchar(64) NOT NULL COMMENT '活动分享标题',
  `share_url` varchar(256) NOT NULL COMMENT '活动分享链接',
  `status` tinyint(11) DEFAULT NULL COMMENT '活动状态',
  `business_id` int(11) NOT NULL COMMENT '商家ID',
  `create_at` datetime NOT NULL COMMENT '创建时间',
  `update_at` datetime DEFAULT NULL COMMENT '更新时间',
  `reward_type` tinyint(4) NOT NULL COMMENT '奖励类型',
  `enable_status` tinyint(11) DEFAULT NULL COMMENT '启用状态',
  `activity_type` tinyint(11) DEFAULT NULL COMMENT '活动类型',
  `channel_type` tinyint(11) DEFAULT NULL COMMENT '渠道类',
  `marketing_type` tinyint(11) DEFAULT NULL,
  `activity_json` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='活动表';

/*Data for the table `marketing_activity` */

/*Table structure for table `undo_log` */

DROP TABLE IF EXISTS `undo_log`;

CREATE TABLE `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `undo_log` */

/*Table structure for table `user_receive_coupon` */

DROP TABLE IF EXISTS `user_coupons`;

CREATE TABLE `user_coupons` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `coupon_id` int(11) NOT NULL COMMENT '优惠卷ID',
  `receive_channel` tinyint(4) NOT NULL COMMENT '领取渠道：内容页嵌入活动领取、系统主动推送、用户行为触发的投放等',
  `status` tinyint(4) NOT NULL COMMENT '状态：未使用、已使用、已过期',
  `create_at` datetime NOT NULL COMMENT '创建时间',
  `update_at` datetime DEFAULT NULL COMMENT '更新时间',
  `activity_id` int(11) NOT NULL COMMENT '活动id',
  `coupon_code` varchar(255) NOT NULL COMMENT '优惠券券码',
  `order_no` varchar(255) DEFAULT NULL COMMENT '订单编号',
  `used_time` datetime DEFAULT NULL COMMENT '使用时间',
  `coupon_type` tinyint(11) NOT NULL COMMENT '优惠券类型',
  `coupon_value` decimal(11,0) NOT NULL COMMENT '优惠券面额',
  `shop_id` int(11) NOT NULL COMMENT '店铺ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券表';

/*Data for the table `user_receive_coupon` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
