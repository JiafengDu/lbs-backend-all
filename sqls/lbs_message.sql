/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.33-log : Database - tarena_lbs_message
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`tarena_lbs_message` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `tarena_lbs_message`;

/*Table structure for table `dynamic_msg_fields` */

DROP TABLE IF EXISTS `dynamic_fields`;

CREATE TABLE `dynamic_fields` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `temp_id` int(11) DEFAULT NULL COMMENT '消息模板ID',
  `field_name` varchar(32) DEFAULT NULL COMMENT '字段名：例如，用户名字段',
  `field_value` varchar(32) DEFAULT NULL COMMENT '字段值：例如，username',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态：0有效、1无效',
  `create_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

/*Data for the table `dynamic_msg_fields` */

insert  into `dynamic_fields`(`id`,`temp_id`,`field_name`,`field_value`,`status`,`create_at`) values (6,15,'userName','用户名',0,'2024-06-05 09:13:11');
insert  into `dynamic_fields`(`id`,`temp_id`,`field_name`,`field_value`,`status`,`create_at`) values (7,15,'articleTitle','文章标题',0,'2024-06-05 09:14:08');
insert  into `dynamic_fields`(`id`,`temp_id`,`field_name`,`field_value`,`status`,`create_at`) values (8,15,'likedUserName','用户名',0,'2024-06-05 09:16:13');

/*Table structure for table `msg` */

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` int(11) NOT NULL COMMENT '用户编号',
  `msg_type` int(11) DEFAULT NULL COMMENT '1表示消息属于前台用户 2表示消息属于后台用户',
  `activity_id` int(11) DEFAULT NULL COMMENT '活动ID',
  `template_id` int(11) DEFAULT NULL COMMENT '模版ID',
  `timing_msg_task_id` int(11) DEFAULT NULL COMMENT '定时消息任务编号',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '0:定时消息不可读  1:未读  2:已读',
  `create_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `msg_title` varchar(255) DEFAULT NULL,
  `content` varchar(5000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=961 DEFAULT CHARSET=utf8mb4;

/*Data for the table `msg` */

/*Table structure for table `timing_msg_task` */

DROP TABLE IF EXISTS `timing_task`;

CREATE TABLE `timing_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `temp_id` int(11) DEFAULT NULL COMMENT '消息模版id',
  `user_group_id` int(11) DEFAULT NULL COMMENT '接收消息目标用户群',
  `scheduled_time` datetime DEFAULT NULL COMMENT '定时发送时间',
  `scheduled_interval` int(11) DEFAULT NULL COMMENT '定时周期 单位分钟',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间，周期性任务专属',
  `is_send` tinyint(1) DEFAULT NULL COMMENT '是否发送 0发送 1 未发送',
  `bussiness_id` int(11) DEFAULT NULL COMMENT '商家id',
  `create_at` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `timing_msg_task` */

/*Table structure for table `timing_msg_template` */

DROP TABLE IF EXISTS `timing_template`;

CREATE TABLE `timing_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `temp_name` varchar(64) DEFAULT NULL COMMENT '消息模版名称',
  `msg_title` varchar(64) DEFAULT NULL COMMENT '消息标题',
  `content` varchar(128) DEFAULT NULL COMMENT '消息内容',
  `activity_id` int(11) DEFAULT NULL COMMENT '营销活动id',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态 0启用 1禁用',
  `bussiness_id` int(11) DEFAULT '0' COMMENT '绑定商家id',
  `create_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4;

/*Data for the table `timing_msg_template` */

/*Table structure for table `trigger_msg_task` */

DROP TABLE IF EXISTS `trigger_task`;

CREATE TABLE `trigger_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_action` enum('1','2') DEFAULT NULL COMMENT '用户动作事件,枚举值待定',
  `temp_id` int(11) DEFAULT NULL COMMENT '消息模板ID',
  `message` varchar(256) DEFAULT NULL COMMENT '消息内容',
  `user_id` int(11) DEFAULT NULL COMMENT '接收用户ID',
  `trigger_time` datetime DEFAULT NULL COMMENT '触发推送时间',
  `is_send` tinyint(1) DEFAULT NULL COMMENT '是否推送成功 0是  1否',
  `is_read` tinyint(1) DEFAULT NULL COMMENT '是否已读：0是 1否',
  `bussiness_id` int(11) DEFAULT NULL COMMENT '商家ID',
  `create_at` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `trigger_msg_task` */

/*Table structure for table `trigger_msg_template` */

DROP TABLE IF EXISTS `trigger_template`;

CREATE TABLE `trigger_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `temp_name` varchar(64) DEFAULT NULL COMMENT '模板名称',
  `user_action` varchar(64) DEFAULT NULL COMMENT '用户动作',
  `msg_title` varchar(64) DEFAULT NULL COMMENT '消息标题',
  `content` varchar(256) DEFAULT NULL COMMENT '消息内容',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态：0启用、1禁用',
  `bussiness_id` int(11) DEFAULT NULL COMMENT '商家ID',
  `create_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4;

/*Data for the table `trigger_msg_template` */

/*Table structure for table `xxl_job_group` */

DROP TABLE IF EXISTS `xxl_job_group`;

CREATE TABLE `xxl_job_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(64) NOT NULL COMMENT '执行器AppName',
  `title` varchar(12) NOT NULL COMMENT '执行器名称',
  `address_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '执行器地址类型：0=自动注册、1=手动录入',
  `address_list` text COMMENT '执行器地址列表，多地址逗号分隔',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `xxl_job_group` */

insert  into `xxl_job_group`(`id`,`app_name`,`title`,`address_type`,`address_list`,`update_time`) values (1,'xxl-job-executor-sample','示例执行器',0,'http://192.168.1.104:9999/','2024-07-08 01:16:23');

/*Table structure for table `xxl_job_info` */

DROP TABLE IF EXISTS `xxl_job_info`;

CREATE TABLE `xxl_job_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_group` int(11) NOT NULL COMMENT '执行器主键ID',
  `job_desc` varchar(255) NOT NULL,
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `author` varchar(64) DEFAULT NULL COMMENT '作者',
  `alarm_email` varchar(255) DEFAULT NULL COMMENT '报警邮件',
  `schedule_type` varchar(50) NOT NULL DEFAULT 'NONE' COMMENT '调度类型',
  `schedule_conf` varchar(128) DEFAULT NULL COMMENT '调度配置，值含义取决于调度类型',
  `misfire_strategy` varchar(50) NOT NULL DEFAULT 'DO_NOTHING' COMMENT '调度过期策略',
  `executor_route_strategy` varchar(50) DEFAULT NULL COMMENT '执行器路由策略',
  `executor_handler` varchar(255) DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) DEFAULT NULL COMMENT '执行器任务参数',
  `executor_block_strategy` varchar(50) DEFAULT NULL COMMENT '阻塞处理策略',
  `executor_timeout` int(11) NOT NULL DEFAULT '0' COMMENT '任务执行超时时间，单位秒',
  `executor_fail_retry_count` int(11) NOT NULL DEFAULT '0' COMMENT '失败重试次数',
  `glue_type` varchar(50) NOT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) DEFAULT NULL COMMENT 'GLUE备注',
  `glue_updatetime` datetime DEFAULT NULL COMMENT 'GLUE更新时间',
  `child_jobid` varchar(255) DEFAULT NULL COMMENT '子任务ID，多个逗号分隔',
  `trigger_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '调度状态：0-停止，1-运行',
  `trigger_last_time` bigint(13) NOT NULL DEFAULT '0' COMMENT '上次调度时间',
  `trigger_next_time` bigint(13) NOT NULL DEFAULT '0' COMMENT '下次调度时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=utf8mb4;

/*Data for the table `xxl_job_info` */

/*Table structure for table `xxl_job_lock` */

DROP TABLE IF EXISTS `xxl_job_lock`;

CREATE TABLE `xxl_job_lock` (
  `lock_name` varchar(50) NOT NULL COMMENT '锁名称',
  PRIMARY KEY (`lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `xxl_job_lock` */

/*Table structure for table `xxl_job_log` */

DROP TABLE IF EXISTS `xxl_job_log`;

CREATE TABLE `xxl_job_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `job_group` int(11) NOT NULL COMMENT '执行器主键ID',
  `job_id` int(11) NOT NULL COMMENT '任务，主键ID',
  `executor_address` varchar(255) DEFAULT NULL COMMENT '执行器地址，本次执行的地址',
  `executor_handler` varchar(255) DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) DEFAULT NULL COMMENT '执行器任务参数',
  `executor_sharding_param` varchar(20) DEFAULT NULL COMMENT '执行器任务分片参数，格式如 1/2',
  `executor_fail_retry_count` int(11) NOT NULL DEFAULT '0' COMMENT '失败重试次数',
  `trigger_time` datetime DEFAULT NULL COMMENT '调度-时间',
  `trigger_code` int(11) NOT NULL COMMENT '调度-结果',
  `trigger_msg` text COMMENT '调度-日志',
  `handle_time` datetime DEFAULT NULL COMMENT '执行-时间',
  `handle_code` int(11) NOT NULL COMMENT '执行-状态',
  `handle_msg` text COMMENT '执行-日志',
  `alarm_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败',
  PRIMARY KEY (`id`),
  KEY `I_trigger_time` (`trigger_time`),
  KEY `I_handle_code` (`handle_code`)
) ENGINE=InnoDB AUTO_INCREMENT=568703 DEFAULT CHARSET=utf8mb4;

/*Data for the table `xxl_job_log` */

/*Table structure for table `xxl_job_log_report` */

DROP TABLE IF EXISTS `xxl_job_log_report`;

CREATE TABLE `xxl_job_log_report` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `trigger_day` datetime DEFAULT NULL COMMENT '调度-时间',
  `running_count` int(11) NOT NULL DEFAULT '0' COMMENT '运行中-日志数量',
  `suc_count` int(11) NOT NULL DEFAULT '0' COMMENT '执行成功-日志数量',
  `fail_count` int(11) NOT NULL DEFAULT '0' COMMENT '执行失败-日志数量',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `i_trigger_day` (`trigger_day`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=184 DEFAULT CHARSET=utf8mb4;

/*Data for the table `xxl_job_log_report` */

/*Table structure for table `xxl_job_logglue` */

DROP TABLE IF EXISTS `xxl_job_logglue`;

CREATE TABLE `xxl_job_logglue` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_id` int(11) NOT NULL COMMENT '任务，主键ID',
  `glue_type` varchar(50) DEFAULT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) NOT NULL COMMENT 'GLUE备注',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `xxl_job_logglue` */

insert  into `xxl_job_logglue`(`id`,`job_id`,`glue_type`,`glue_source`,`glue_remark`,`add_time`,`update_time`) values (1,133,'GLUE_GROOVY','package com.tarena.lbs.message.web.service.xxljobhandler;\n\nimport com.tarena.lbs.message.web.service.TimingMsgExecuteService;\nimport com.xxl.job.core.handler.IJobHandler;\nimport org.springframework.beans.factory.annotation.Autowired;\n\npublic class TimingMsgGlueJobHandler extends IJobHandler {\n    @Autowired\n    TimingMsgExecuteService timingMsgExecuteService;\n\n    @Override\n    public void execute() throws Exception {\n        System.out.println(\"TimingMsgGlueJobHandler.execute()\");\n        timingMsgExecuteService.sendMessage(133);\n\n    }\n}','测试第一次','2024-07-03 12:57:08','2024-07-03 12:57:08');

/*Table structure for table `xxl_job_registry` */

DROP TABLE IF EXISTS `xxl_job_registry`;

CREATE TABLE `xxl_job_registry` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `registry_group` varchar(50) NOT NULL,
  `registry_key` varchar(255) NOT NULL,
  `registry_value` varchar(255) NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `i_g_k_v` (`registry_group`,`registry_key`,`registry_value`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8mb4;

/*Data for the table `xxl_job_registry` */

insert  into `xxl_job_registry`(`id`,`registry_group`,`registry_key`,`registry_value`,`update_time`) values (77,'EXECUTOR','xxl-job-executor-sample','http://192.168.1.104:9999/','2024-07-08 01:16:25');

/*Table structure for table `xxl_job_user` */

DROP TABLE IF EXISTS `xxl_job_user`;

CREATE TABLE `xxl_job_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '账号',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `role` tinyint(4) NOT NULL COMMENT '角色：0-普通用户、1-管理员',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限：执行器ID列表，多个逗号分割',
  PRIMARY KEY (`id`),
  UNIQUE KEY `i_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `xxl_job_user` */

insert  into `xxl_job_user`(`id`,`username`,`password`,`role`,`permission`) values (1,'admin','e10adc3949ba59abbe56e057f20f883e',1,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
