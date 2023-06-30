/*
 Navicat Premium Data Transfer

 Source Server         : linux_mysql5.7
 Source Server Type    : MySQL
 Source Server Version : 50741
 Source Host           : 192.168.6.100:3306
 Source Schema         : yygh_hosp

 Target Server Type    : MySQL
 Target Server Version : 50741
 File Encoding         : 65001

 Date: 30/06/2023 16:19:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hospital_set
-- ----------------------------
DROP TABLE IF EXISTS `hospital_set`;
CREATE TABLE `hospital_set`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `hosname` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院名称',
  `hoscode` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编号',
  `api_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'api基础路径',
  `sign_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '签名秘钥',
  `contacts_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `contacts_phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人手机',
  `status` tinyint(3) NOT NULL DEFAULT 0 COMMENT '状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_hoscode`(`hoscode`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '医院设置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hospital_set
-- ----------------------------
INSERT INTO `hospital_set` VALUES (1, '解放军总医院', '10000', '9998', '1', '张东明', '1757894560', 1, '2021-07-29 15:18:08', '2023-03-21 16:09:27', 0);
INSERT INTO `hospital_set` VALUES (2, '北京协和医院', '1', '', NULL, '汪峰', '1687456211', 1, '2023-03-21 16:10:54', '2023-03-21 16:10:54', 0);
INSERT INTO `hospital_set` VALUES (3, '同济医院', NULL, NULL, NULL, '谢泽伦', '12312312331', 0, '2023-03-21 16:11:29', '2023-03-21 16:31:06', 1);
INSERT INTO `hospital_set` VALUES (4, '益阳市人民医院', NULL, NULL, NULL, '小高', '12312312312', 0, '2023-03-21 16:12:58', '2023-03-21 16:12:58', 0);
INSERT INTO `hospital_set` VALUES (5, '长沙市人民医院', '21312', '2342342', 'a9d95e80d1db38c22462abdc3f89a019', '1837465809', '12345678906', 1, '2023-03-21 16:29:04', '2023-03-21 16:29:04', 0);
INSERT INTO `hospital_set` VALUES (6, '同济医院', '1231231223423', '121312312', '3e62bbc3254f3dc0384ca5e567af20fe', '1231231231', '231312312', 1, '2023-03-21 16:31:44', '2023-03-21 16:31:44', 0);
INSERT INTO `hospital_set` VALUES (7, '', NULL, NULL, 'f3e876a6b19d2289407858b916ff613a', NULL, NULL, 1, '2023-03-22 16:57:58', '2023-03-22 16:57:58', 0);

SET FOREIGN_KEY_CHECKS = 1;
