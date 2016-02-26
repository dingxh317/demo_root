/*
Navicat MySQL Data Transfer

Source Server         : local_mariadb
Source Server Version : 50529
Source Host           : localhost:3308
Source Database       : db

Target Server Type    : MYSQL
Target Server Version : 50529
File Encoding         : 65001

Date: 2016-02-26 15:15:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `employee`
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `email` varchar(30) NOT NULL,
  `address` varchar(256) NOT NULL,
  `phoneNumber` varchar(20) NOT NULL,
  `remark` varchar(256) NOT NULL,
  `joinTime` bigint(20) unsigned NOT NULL COMMENT '加入日期',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '1: 启用，0：停用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=199 DEFAULT CHARSET=utf8 COMMENT='员工表';

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES ('168', '1', '111@qq.com', '1', '18611278794', 'aaa', '0', '1');
INSERT INTO `employee` VALUES ('173', '4', '4@qq.com', '4', '010-88888888', '44444', '1455120000000', '1');
INSERT INTO `employee` VALUES ('175', '5', '5@qq.com', '5', '010-88888888', '555', '1454688000000', '1');
INSERT INTO `employee` VALUES ('176', '5', '5@qq.com', '5', '010-88888888', '555', '1454688000000', '0');
INSERT INTO `employee` VALUES ('181', '77', '7@qq.com', '7', '010-88888888', '777', '1455897600000', '1');
INSERT INTO `employee` VALUES ('191', '8', '8@qq.com', '8', '010-88888888', '888', '1455724800000', '1');
INSERT INTO `employee` VALUES ('192', '9', '9@qq.com', '9', '010-88888888', '999', '1455724800000', '1');
INSERT INTO `employee` VALUES ('193', '9', '9@qq.com', '9', '010-88888888', '9999', '1455724800000', '1');
INSERT INTO `employee` VALUES ('194', '10', '10@qq.com', '10', '010-88888888', '101010', '1453910400000', '1');
INSERT INTO `employee` VALUES ('195', '10', '10@qq.com', '10', '010-88888888', '101010', '1453910400000', '1');
INSERT INTO `employee` VALUES ('196', '10', '10@qq.com', '10', '010-88888888', '101010', '1453910400000', '1');
INSERT INTO `employee` VALUES ('197', '11', '10@qq.com', '11', '010-88888888', '111111', '1456070400000', '1');
INSERT INTO `employee` VALUES ('198', '12', '10@qq.com', '12', '010-88888888', '121212', '1456156800000', '1');
