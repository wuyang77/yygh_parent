/*
 Navicat Premium Data Transfer

 Source Server         : linux_mysql5.7
 Source Server Type    : MySQL
 Source Server Version : 50741
 Source Host           : 192.168.6.100:3306
 Source Schema         : yygh_user

 Target Server Type    : MySQL
 Target Server Version : 50741
 File Encoding         : 65001

 Date: 30/06/2023 16:19:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for patient
-- ----------------------------
DROP TABLE IF EXISTS `patient`;
CREATE TABLE `patient`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `certificates_type` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件类型',
  `certificates_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件编号',
  `sex` tinyint(3) NULL DEFAULT NULL COMMENT '性别',
  `birthdate` date NULL DEFAULT NULL COMMENT '出生年月',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机',
  `is_marry` tinyint(3) NULL DEFAULT NULL COMMENT '是否结婚',
  `province_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省code',
  `city_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '市code',
  `district_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区code',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '详情地址',
  `contacts_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人姓名',
  `contacts_certificates_type` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人证件类型',
  `contacts_certificates_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人证件号',
  `contacts_phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人手机',
  `card_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊卡号',
  `is_insure` tinyint(3) NULL DEFAULT 0 COMMENT '是否有医保',
  `status` tinyint(3) NOT NULL DEFAULT 0 COMMENT '状态（0：默认 1：已认证）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '就诊人表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of patient
-- ----------------------------
INSERT INTO `patient` VALUES (4, 26, '1', '10', '1', 1, '2021-07-31', '1', 0, '110000', '110100', '110101', '1', '', '', '', '', NULL, 0, 0, '2021-08-01 20:13:11', '2023-06-29 16:56:47', 1);
INSERT INTO `patient` VALUES (5, 26, '2', '10', '2', 1, '2021-07-31', '2', 0, '140000', '140100', '140101', '2', '', '', '', '', NULL, 0, 0, '2021-08-01 20:13:25', '2023-06-29 16:56:42', 1);
INSERT INTO `patient` VALUES (6, 26, '3', '10', '3', 1, '2021-07-31', '3', 0, '110000', '110100', '110101', '3', '', '', '', '', NULL, 0, 0, '2021-08-01 20:13:39', '2023-06-29 16:56:39', 1);
INSERT INTO `patient` VALUES (9, 45, '吴洋', '10', '430922199710290910', 1, '1997-10-28', '15874919151', 0, '430000', '430100', '430104', '湖南师范大学', '吴洋', '10', '430922199710290910', '15874919151', NULL, 0, 0, '2023-04-05 20:18:28', '2023-04-05 20:18:28', 0);
INSERT INTO `patient` VALUES (10, 45, 'sdfsd', '10', '', 1, '2023-04-02', '递四方速递', 0, '110000', '110100', '110102', 'dsasdas', '', '20', 'dsadas', 'weqw', NULL, 0, 0, '2023-04-05 22:00:33', '2023-04-06 09:31:24', 0);
INSERT INTO `patient` VALUES (12, 3, '吴洋', '10', '430922199710290910', 1, '1997-10-28', '15874919151', 0, '430000', '430100', '430104', '湖南师范大学中和楼', '吴武军', '10', '23423423423423', '312312321', NULL, 1, 0, '2023-04-08 11:52:05', '2023-04-08 11:52:05', 0);
INSERT INTO `patient` VALUES (13, 26, 'wuy', '10', '430922199710290910', 1, '1997-10-28', '17375588560', 0, '110000', '110100', '110105', '湖南师范大学中和楼', '', '', '', '', NULL, 1, 0, '2023-06-29 16:58:34', '2023-06-29 16:58:34', 0);

-- ----------------------------
-- Table structure for patient_copy1
-- ----------------------------
DROP TABLE IF EXISTS `patient_copy1`;
CREATE TABLE `patient_copy1`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `certificates_type` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件类型',
  `certificates_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件编号',
  `sex` tinyint(3) NULL DEFAULT NULL COMMENT '性别',
  `birthdate` date NULL DEFAULT NULL COMMENT '出生年月',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机',
  `is_marry` tinyint(3) NULL DEFAULT NULL COMMENT '是否结婚',
  `province_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省code',
  `city_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '市code',
  `district_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区code',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '详情地址',
  `contacts_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人姓名',
  `contacts_certificates_type` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人证件类型',
  `contacts_certificates_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人证件号',
  `contacts_phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人手机',
  `card_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊卡号',
  `is_insure` tinyint(3) NULL DEFAULT 0 COMMENT '是否有医保',
  `status` tinyint(3) NOT NULL DEFAULT 0 COMMENT '状态（0：默认 1：已认证）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '就诊人表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of patient_copy1
-- ----------------------------
INSERT INTO `patient_copy1` VALUES (4, 26, '1', '10', '1', 1, '2021-07-31', '1', 0, '110000', '110100', '110101', '1', '', '', '', '', NULL, 0, 0, '2021-08-01 20:13:11', '2021-08-01 20:13:11', 0);
INSERT INTO `patient_copy1` VALUES (5, 26, '2', '10', '2', 1, '2021-07-31', '2', 0, '140000', '140100', '140101', '2', '', '', '', '', NULL, 0, 0, '2021-08-01 20:13:25', '2021-08-01 20:13:25', 0);
INSERT INTO `patient_copy1` VALUES (6, 26, '3', '10', '3', 1, '2021-07-31', '3', 0, '110000', '110100', '110101', '3', '', '', '', '', NULL, 0, 0, '2021-08-01 20:13:39', '2021-08-01 20:13:39', 0);
INSERT INTO `patient_copy1` VALUES (7, 26, '张翠山1', '10', '123456789099', 1, '2021-07-31', '15611248741', 0, '110000', '110100', '110101', '北京市昌平区', '测试1', '10', '222', '111', NULL, 0, 0, '2021-08-04 10:23:22', '2021-08-04 20:20:54', 0);

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `openid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信openid',
  `nick_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '手机号',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `certificates_type` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件类型',
  `certificates_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件编号',
  `certificates_url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件路径',
  `auth_status` tinyint(3) NOT NULL DEFAULT 0 COMMENT '认证状态（0：未认证 1：认证中 2：认证成功 -1：认证失败）',
  `status` tinyint(3) NOT NULL DEFAULT 1 COMMENT '状态（0：锁定 1：正常）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `uk_mobile`(`phone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, 'o3_SC581FDnK7SqdxNidwuL8pDkY', '陈海军', '17673413493', NULL, NULL, NULL, NULL, 0, 1, '2023-04-01 10:49:32', '2023-04-05 23:02:22', 0);
INSERT INTO `user_info` VALUES (2, 'o3_SC5177n3_sk4TtTCWEkM0ZPYE', 'w', '17873041652', '', '', '', '', 0, 1, '2023-04-03 19:22:37', '2023-04-05 23:02:24', 0);
INSERT INTO `user_info` VALUES (4, NULL, 'YY', '17358866845', '洋哥', '户口本', '666666666666666666', NULL, 2, 1, '2023-04-05 22:22:10', '2023-04-06 12:00:14', 0);
INSERT INTO `user_info` VALUES (5, NULL, 'EE', '17358342155', '田开心', '身份证', '324231432141234123', NULL, 0, 1, '2023-04-05 22:23:25', '2023-04-06 12:00:16', 0);
INSERT INTO `user_info` VALUES (6, NULL, 'XX', '12312312312', '光谷红灯区', '身份证', '453265432642365234', NULL, 2, 1, '2023-04-05 22:24:40', '2023-04-06 12:00:17', 0);
INSERT INTO `user_info` VALUES (7, NULL, 'UU', '17556656664', '北京陆家嘴', '身份证', '234231231412342314', NULL, 0, 1, '2023-04-05 22:29:26', '2023-04-06 12:00:20', 0);
INSERT INTO `user_info` VALUES (8, NULL, 'II', '23423423412', '杭州不夜城', '身份证', '125454545456154565', NULL, 0, 1, '2023-04-05 22:30:06', '2023-04-06 12:00:23', 0);
INSERT INTO `user_info` VALUES (9, NULL, 'KK', '21312321312', '灵黛玉', '身份证', '123213123123123123', NULL, 2, 1, '2023-04-05 22:31:01', '2023-04-06 12:00:26', 0);
INSERT INTO `user_info` VALUES (10, NULL, 'LL', '12321312312', '诸葛亮', '身份证', '123123123213123121', NULL, 2, 1, '2023-04-05 22:31:40', '2023-04-06 12:00:28', 0);
INSERT INTO `user_info` VALUES (11, NULL, 'DD', '21312312312', '廖凤香', '身份证', '123123213124214421', NULL, 2, 1, '2023-04-05 22:32:19', '2023-04-06 12:00:31', 0);
INSERT INTO `user_info` VALUES (12, 'o3_SC57D-1U6gfvqIn6YcyUGo7l4', 'club', '', NULL, NULL, NULL, NULL, 0, 1, '2023-05-31 21:01:45', '2023-05-31 21:01:45', 0);
INSERT INTO `user_info` VALUES (13, 'o3_SC520sBIthzLnvPx11BuokbNU', 'T', '', NULL, NULL, NULL, NULL, 0, 1, '2023-06-01 20:14:04', '2023-06-01 20:14:04', 0);
INSERT INTO `user_info` VALUES (15, 'o3_SC54Psmev7Oa4VAevOQRJMQZw', 'จุ๊บ好名字', '', NULL, NULL, NULL, NULL, 0, 1, '2023-06-28 19:09:01', '2023-06-28 19:09:01', 0);
INSERT INTO `user_info` VALUES (26, 'o3_SC53dvuEWKVOnXkLwli1vjZ3Y', 'wy', '17375588560', 'wuy', '身份证', '430922199710290910', 'https://wuyang77.oss-cn-beijing.aliyuncs.com/2023/06/29/a40f89e5edf24724b50d1b41f925c40a身份证背面.jpg', 2, 1, '2023-06-28 20:58:39', '2023-06-28 20:58:36', 0);

-- ----------------------------
-- Table structure for user_login_record
-- ----------------------------
DROP TABLE IF EXISTS `user_login_record`;
CREATE TABLE `user_login_record`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `ip` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户登录记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_login_record
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
