/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : rocket_platform

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 21/08/2020 14:23:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for rocket_info
-- ----------------------------
DROP TABLE IF EXISTS `rocket_info`;
CREATE TABLE `rocket_info` (
  `id` varchar(32) NOT NULL,
  `gmt_create` bigint(20) NOT NULL,
  `gmt_modify` bigint(20) NOT NULL,
  `addr_ip` varchar(50) NOT NULL COMMENT 'rocketmq的ip地址',
  `comments` varchar(50) DEFAULT NULL COMMENT '消息队列说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for rocket_topic_msg_model
-- ----------------------------
DROP TABLE IF EXISTS `rocket_topic_msg_model`;
CREATE TABLE `rocket_topic_msg_model` (
  `id` varchar(32) NOT NULL,
  `gmt_create` bigint(20) NOT NULL,
  `gmt_modify` bigint(20) NOT NULL,
  `topic_name` varchar(100) NOT NULL COMMENT '主题名',
  `consumer_group` varchar(100) NOT NULL COMMENT '消费者组',
  `sub_expression` varchar(50) NOT NULL COMMENT 'tag,过个tag时，tag1||tag2||tag3\n',
  `msg_model` text COMMENT '解析消息的模板，只支持一维健值对',
  `rocket_id` varchar(32) NOT NULL COMMENT '消息队列信息id',
  `msg_model_type` int(1) NOT NULL DEFAULT '1' COMMENT '1集群模式，2广播模式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
