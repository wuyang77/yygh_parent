/*
 Navicat Premium Data Transfer

 Source Server         : linux_mysql5.7
 Source Server Type    : MySQL
 Source Server Version : 50741
 Source Host           : 192.168.6.100:3306
 Source Schema         : yygh_order

 Target Server Type    : MySQL
 Target Server Version : 50741
 File Encoding         : 65001

 Date: 30/06/2023 16:19:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint(20) NULL DEFAULT NULL,
  `out_trade_no` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单交易号',
  `hoscode` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院编号',
  `hosname` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医院名称',
  `depcode` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '科室编号',
  `depname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '科室名称',
  `title` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '医生职称',
  `hos_schedule_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '排班编号（医院自己的排班主键）',
  `reserve_date` date NULL DEFAULT NULL COMMENT '安排日期',
  `reserve_time` tinyint(3) NULL DEFAULT 0 COMMENT '安排时间（0：上午 1：下午）',
  `patient_id` bigint(20) NULL DEFAULT NULL COMMENT '就诊人id',
  `patient_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊人名称',
  `patient_phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '就诊人手机',
  `hos_record_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预约记录唯一标识（医院预约记录主键）',
  `number` int(11) NULL DEFAULT NULL COMMENT '预约号序',
  `fetch_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '建议取号时间',
  `fetch_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '取号地点',
  `amount` decimal(10, 0) NULL DEFAULT NULL COMMENT '医事服务费',
  `quit_time` datetime NULL DEFAULT NULL COMMENT '退号时间',
  `order_status` tinyint(3) NULL DEFAULT NULL COMMENT '订单状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_out_trade_no`(`out_trade_no`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `idx_hoscode`(`hoscode`) USING BTREE,
  INDEX `idx_hos_schedule_id`(`hos_schedule_id`) USING BTREE,
  INDEX `idx_hos_record_id`(`hos_record_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES (32, 3, '168562439076379', '10000', '北京协和医院', '200040878', '多发性硬化专科门诊', '副主任医师', '5', '2023-06-01', 0, 12, '吴洋', '15874919151', '54', 35, '2023-06-0109:00前', '一层114窗口', 100, '2023-06-01 07:30:00', 0, '2023-06-01 20:59:41', '2023-06-01 20:59:41', 0);
INSERT INTO `order_info` VALUES (33, 3, '168778591201332', '10000', '北京协和医院', '200040878', '多发性硬化专科门诊', '医师', '22', '2023-06-26', 0, 12, '吴洋', '15874919151', '55', 12, '2023-06-2609:00前', '一层114窗口', 100, '2023-06-26 07:30:00', 1, '2023-06-26 21:23:25', '2023-06-26 21:23:25', 0);
INSERT INTO `order_info` VALUES (34, 26, '168802921040693', '10000', '北京协和医院', '200040878', '多发性硬化专科门诊', '医师', '31', '2023-06-30', 0, 13, 'wuy', '17375588560', '56', 12, '2023-06-3009:00前', '一层114窗口', 100, '2023-06-30 07:30:00', 1, '2023-06-29 17:00:04', '2023-06-29 17:00:04', 0);
INSERT INTO `order_info` VALUES (35, 26, '168803910023526', '10000', '北京协和医院', '200040878', '多发性硬化专科门诊', '副主任医师', '5', '2023-07-01', 0, 13, 'wuy', '17375588560', '57', 35, '2023-07-0109:00前', '一层114窗口', 100, '2023-07-01 07:30:00', 1, '2023-06-29 19:44:54', '2023-06-29 19:44:54', 0);
INSERT INTO `order_info` VALUES (36, 26, '168811280401783', '10000', '北京协和医院', '200040878', '多发性硬化专科门诊', '副主任医师', '5', '2023-07-01', 0, 13, 'wuy', '17375588560', '58', 36, '2023-07-0109:00前', '一层114窗口', 100, '2023-07-01 07:30:00', -1, '2023-06-30 16:13:23', '2023-06-30 16:13:23', 0);
INSERT INTO `order_info` VALUES (37, 26, '16881128283467', '10000', '北京协和医院', '200040878', '多发性硬化专科门诊', '医师', '37', '2023-07-02', 0, 13, 'wuy', '17375588560', '59', 12, '2023-07-0209:00前', '一层114窗口', 100, '2023-07-02 07:30:00', 1, '2023-06-30 16:13:47', '2023-06-30 16:13:47', 0);

-- ----------------------------
-- Table structure for payment_info
-- ----------------------------
DROP TABLE IF EXISTS `payment_info`;
CREATE TABLE `payment_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `out_trade_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对外业务编号',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT '订单id',
  `payment_type` tinyint(1) NULL DEFAULT NULL COMMENT '支付类型（微信 支付宝）',
  `trade_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易编号',
  `total_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '支付金额',
  `subject` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易内容',
  `payment_status` tinyint(3) NULL DEFAULT NULL COMMENT '支付状态',
  `callback_time` datetime NULL DEFAULT NULL COMMENT '回调时间',
  `callback_content` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回调信息',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_out_trade_no`(`out_trade_no`) USING BTREE,
  INDEX `idx_order_id`(`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '支付信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of payment_info
-- ----------------------------
INSERT INTO `payment_info` VALUES (1, '168093431599755', 2, 2, '4200001832202304089762322977', 100.00, '2023-04-09|北京协和医院|多发性硬化专科门诊|医师', 2, '2023-04-12 18:00:38', '{transaction_id=4200001832202304089762322977, nonce_str=VKkxwsmajeulp6id, trade_state=SUCCESS, bank_type=OTHERS, openid=oHwsHuPOlwejOxDC16zfySeTnOg4, sign=AEC65199BAF575A1AF1155AB6585914C, return_msg=OK, fee_type=CNY, mch_id=1558950191, cash_fee=1, out_trade_no=168093431599755, cash_fee_type=CNY, appid=wx74862e0dfcf69954, total_fee=1, trade_state_desc=支付成功, trade_type=NATIVE, result_code=SUCCESS, attach=, time_end=20230408211712, is_subscribe=N, return_code=SUCCESS}', '2023-04-08 21:16:52', '2023-04-08 21:16:52', 0);
INSERT INTO `payment_info` VALUES (2, '16812807836450', 8, 2, NULL, 100.00, '2023-04-12|北京协和医院|多发性硬化专科门诊|医师', 1, NULL, NULL, '2023-04-12 14:26:27', '2023-04-12 14:26:27', 0);
INSERT INTO `payment_info` VALUES (3, '168129301150297', 9, 2, NULL, 100.00, '2023-04-14|北京协和医院|多发性硬化专科门诊|医师', -1, NULL, NULL, '2023-04-12 17:50:13', '2023-04-13 12:59:43', 0);
INSERT INTO `payment_info` VALUES (4, '168094844279713', 3, 2, '4200001822202304126584085815', 100.00, '2023-04-09|北京协和医院|多发性硬化专科门诊|医师', 2, '2023-04-12 18:23:07', '{transaction_id=4200001822202304126584085815, nonce_str=heQ9AVdwHST1V1RX, trade_state=SUCCESS, bank_type=OTHERS, openid=oHwsHuPOlwejOxDC16zfySeTnOg4, sign=8BA80324ABFDE752FD90335DDD287A33, return_msg=OK, fee_type=CNY, mch_id=1558950191, cash_fee=1, out_trade_no=168094844279713, cash_fee_type=CNY, appid=wx74862e0dfcf69954, total_fee=1, trade_state_desc=支付成功, trade_type=NATIVE, result_code=SUCCESS, attach=, time_end=20230412182304, is_subscribe=N, return_code=SUCCESS}', '2023-04-12 18:00:50', '2023-04-12 18:00:50', 0);
INSERT INTO `payment_info` VALUES (5, '16809502373100', 4, 2, '4200001849202304123097596593', 100.00, '2023-04-08|北京协和医院|多发性硬化专科门诊|副主任医师', 2, '2023-04-12 21:51:21', '{transaction_id=4200001849202304123097596593, nonce_str=BLz4f33O935WDtIm, trade_state=SUCCESS, bank_type=OTHERS, openid=oHwsHuPOlwejOxDC16zfySeTnOg4, sign=0E38620D5FA2A9ABA1A60F4634306FB6, return_msg=OK, fee_type=CNY, mch_id=1558950191, cash_fee=1, out_trade_no=16809502373100, cash_fee_type=CNY, appid=wx74862e0dfcf69954, total_fee=1, trade_state_desc=支付成功, trade_type=NATIVE, result_code=SUCCESS, attach=, time_end=20230412215121, is_subscribe=N, return_code=SUCCESS}', '2023-04-12 21:51:08', '2023-04-12 21:51:08', 0);
INSERT INTO `payment_info` VALUES (6, '168130836233960', 10, 2, '4200001839202304126989856791', 100.00, '2023-04-16|北京协和医院|多发性硬化专科门诊|副主任医师', -1, '2023-04-12 22:06:15', '{transaction_id=4200001839202304126989856791, nonce_str=ZF7FCKGnrvQlo9e8, trade_state=SUCCESS, bank_type=OTHERS, openid=oHwsHuPOlwejOxDC16zfySeTnOg4, sign=56B8300BCE7AE6ED59BDF0067A308038, return_msg=OK, fee_type=CNY, mch_id=1558950191, cash_fee=1, out_trade_no=168130836233960, cash_fee_type=CNY, appid=wx74862e0dfcf69954, total_fee=1, trade_state_desc=支付成功, trade_type=NATIVE, result_code=SUCCESS, attach=, time_end=20230412220614, is_subscribe=N, return_code=SUCCESS}', '2023-04-12 22:06:05', '2023-04-12 22:19:17', 0);
INSERT INTO `payment_info` VALUES (7, '168130923394198', 12, 2, '4200001838202304127951849710', 100.00, '2023-04-15|北京协和医院|多发性硬化专科门诊|医师', 2, '2023-04-12 22:20:47', '{transaction_id=4200001838202304127951849710, nonce_str=3xDquBx8VR3f6cY1, trade_state=SUCCESS, bank_type=OTHERS, openid=oHwsHuPOlwejOxDC16zfySeTnOg4, sign=F823E6DFCFF4B648A9786FFCBDD47DBC, return_msg=OK, fee_type=CNY, mch_id=1558950191, cash_fee=1, out_trade_no=168130923394198, cash_fee_type=CNY, appid=wx74862e0dfcf69954, total_fee=1, trade_state_desc=支付成功, trade_type=NATIVE, result_code=SUCCESS, attach=, time_end=20230412222046, is_subscribe=N, return_code=SUCCESS}', '2023-04-12 22:20:37', '2023-04-12 22:20:38', 0);
INSERT INTO `payment_info` VALUES (8, '168136393053092', 13, 2, '4200001823202304139638664060', 100.00, '2023-04-17|北京协和医院|多发性硬化专科门诊|医师', 2, '2023-04-13 13:33:37', '{transaction_id=4200001823202304139638664060, nonce_str=fw0Ba73LvlY81qBu, trade_state=SUCCESS, bank_type=OTHERS, openid=oHwsHuPOlwejOxDC16zfySeTnOg4, sign=A3F7FBEB7D5C518FAE66042E35374C25, return_msg=OK, fee_type=CNY, mch_id=1558950191, cash_fee=1, out_trade_no=168136393053092, cash_fee_type=CNY, appid=wx74862e0dfcf69954, total_fee=1, trade_state_desc=支付成功, trade_type=NATIVE, result_code=SUCCESS, attach=, time_end=20230413133329, is_subscribe=N, return_code=SUCCESS}', '2023-04-13 13:32:18', '2023-04-13 13:32:17', 0);
INSERT INTO `payment_info` VALUES (9, '16813657671624', 15, 2, NULL, 100.00, '2023-04-17|北京协和医院|多发性硬化专科门诊|医师', 1, NULL, NULL, '2023-04-13 14:02:50', '2023-04-13 14:02:49', 0);
INSERT INTO `payment_info` VALUES (10, '168137452945226', 30, 2, '4200001836202304133596182559', 100.00, '2023-04-15|北京协和医院|多发性硬化专科门诊|医师', 2, '2023-04-13 16:39:30', '{transaction_id=4200001836202304133596182559, nonce_str=wg2d5epbEcLDOiGj, trade_state=SUCCESS, bank_type=OTHERS, openid=oHwsHuPOlwejOxDC16zfySeTnOg4, sign=1F4C1400970327A5C49BC80A8405FF4F, return_msg=OK, fee_type=CNY, mch_id=1558950191, cash_fee=1, out_trade_no=168137452945226, cash_fee_type=CNY, appid=wx74862e0dfcf69954, total_fee=1, trade_state_desc=支付成功, trade_type=NATIVE, result_code=SUCCESS, attach=, time_end=20230413163928, is_subscribe=N, return_code=SUCCESS}', '2023-04-13 16:39:14', '2023-04-13 16:39:14', 0);
INSERT INTO `payment_info` VALUES (11, '168562119483582', 31, 2, '4200001828202306019385690935', 100.00, '2023-06-04|北京协和医院|多发性硬化专科门诊|医师', 2, '2023-06-01 20:06:59', '{transaction_id=4200001828202306019385690935, nonce_str=k1LzIR9eicJR0x49, trade_state=SUCCESS, bank_type=OTHERS, openid=oHwsHuPOlwejOxDC16zfySeTnOg4, sign=6A87F59736EEDE9F6CF0491B60B61F5D, return_msg=OK, fee_type=CNY, mch_id=1558950191, cash_fee=1, out_trade_no=168562119483582, cash_fee_type=CNY, appid=wx74862e0dfcf69954, total_fee=1, trade_state_desc=支付成功, trade_type=NATIVE, result_code=SUCCESS, attach=, time_end=20230601200648, is_subscribe=N, return_code=SUCCESS}', '2023-06-01 20:06:43', '2023-06-01 20:06:33', 0);
INSERT INTO `payment_info` VALUES (12, '168562439076379', 32, 2, NULL, 100.00, '2023-06-01|北京协和医院|多发性硬化专科门诊|副主任医师', 1, NULL, NULL, '2023-06-01 20:59:53', '2023-06-01 20:59:43', 0);
INSERT INTO `payment_info` VALUES (13, '168778591201332', 33, 2, '4200001806202306264044102467', 100.00, '2023-06-26|北京协和医院|多发性硬化专科门诊|医师', 2, '2023-06-26 21:25:27', '{transaction_id=4200001806202306264044102467, nonce_str=e7bEfsQ9RxiRnIH9, trade_state=SUCCESS, bank_type=OTHERS, openid=oHwsHuPOlwejOxDC16zfySeTnOg4, sign=F74C3E84136CA9C9AB651CDFFC221F31, return_msg=OK, fee_type=CNY, mch_id=1558950191, cash_fee=1, out_trade_no=168778591201332, cash_fee_type=CNY, appid=wx74862e0dfcf69954, total_fee=1, trade_state_desc=支付成功, trade_type=NATIVE, result_code=SUCCESS, attach=, time_end=20230626212337, is_subscribe=N, return_code=SUCCESS}', '2023-06-26 21:25:14', '2023-06-26 21:23:27', 0);
INSERT INTO `payment_info` VALUES (14, '168802921040693', 34, 2, '4200001932202306294315917777', 100.00, '2023-06-30|北京协和医院|多发性硬化专科门诊|医师', 2, '2023-06-29 17:00:26', '{transaction_id=4200001932202306294315917777, nonce_str=j7nm2u0mt1imXttY, trade_state=SUCCESS, bank_type=OTHERS, openid=oHwsHuPOlwejOxDC16zfySeTnOg4, sign=28B73CA27246DC5D0961E6B6B8021237, return_msg=OK, fee_type=CNY, mch_id=1558950191, cash_fee=1, out_trade_no=168802921040693, cash_fee_type=CNY, appid=wx74862e0dfcf69954, total_fee=1, trade_state_desc=支付成功, trade_type=NATIVE, result_code=SUCCESS, attach=, time_end=20230629170017, is_subscribe=N, return_code=SUCCESS}', '2023-06-29 17:00:13', '2023-06-29 17:00:06', 0);
INSERT INTO `payment_info` VALUES (15, '168803910023526', 35, 2, '4200001822202306294067435294', 100.00, '2023-07-01|北京协和医院|多发性硬化专科门诊|副主任医师', 2, '2023-06-29 19:45:23', '{transaction_id=4200001822202306294067435294, nonce_str=RhtjiuAyiSB7nBxy, trade_state=SUCCESS, bank_type=OTHERS, openid=oHwsHuPOlwejOxDC16zfySeTnOg4, sign=6E6AD82D2A52B288D0B8B94D41AC1AAE, return_msg=OK, fee_type=CNY, mch_id=1558950191, cash_fee=1, out_trade_no=168803910023526, cash_fee_type=CNY, appid=wx74862e0dfcf69954, total_fee=1, trade_state_desc=支付成功, trade_type=NATIVE, result_code=SUCCESS, attach=, time_end=20230629194513, is_subscribe=N, return_code=SUCCESS}', '2023-06-29 19:45:07', '2023-06-29 19:45:00', 0);
INSERT INTO `payment_info` VALUES (16, '168811280401783', 36, 2, NULL, 100.00, '2023-07-01|北京协和医院|多发性硬化专科门诊|副主任医师', -1, NULL, NULL, '2023-06-30 16:13:27', '2023-06-30 16:13:32', 0);
INSERT INTO `payment_info` VALUES (17, '16881128283467', 37, 2, '4200001807202306304381183206', 100.00, '2023-07-02|北京协和医院|多发性硬化专科门诊|医师', 2, '2023-06-30 16:14:05', '{transaction_id=4200001807202306304381183206, nonce_str=sC4E2rzjifCPDcJH, trade_state=SUCCESS, bank_type=OTHERS, openid=oHwsHuPOlwejOxDC16zfySeTnOg4, sign=14FBAE45E56885CF6DBBF9742912EA4B, return_msg=OK, fee_type=CNY, mch_id=1558950191, cash_fee=1, out_trade_no=16881128283467, cash_fee_type=CNY, appid=wx74862e0dfcf69954, total_fee=1, trade_state_desc=支付成功, trade_type=NATIVE, result_code=SUCCESS, attach=, time_end=20230630161403, is_subscribe=N, return_code=SUCCESS}', '2023-06-30 16:13:50', '2023-06-30 16:13:48', 0);

-- ----------------------------
-- Table structure for refund_info
-- ----------------------------
DROP TABLE IF EXISTS `refund_info`;
CREATE TABLE `refund_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `out_trade_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对外业务编号',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT '订单编号',
  `payment_type` tinyint(3) NULL DEFAULT NULL COMMENT '支付类型（微信 支付宝）',
  `trade_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易编号',
  `total_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '退款金额',
  `subject` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易内容',
  `refund_status` tinyint(3) NULL DEFAULT NULL COMMENT '退款状态',
  `callback_content` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回调信息',
  `callback_time` datetime NULL DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT 0 COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_out_trade_no`(`out_trade_no`) USING BTREE,
  INDEX `idx_order_id`(`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '退款信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of refund_info
-- ----------------------------
INSERT INTO `refund_info` VALUES (1, '16809502373100', 4, 2, NULL, 100.00, '2023-04-08|北京协和医院|多发性硬化专科门诊|副主任医师', 1, NULL, NULL, '2023-04-12 22:00:07', '2023-04-12 22:00:07', 0);
INSERT INTO `refund_info` VALUES (2, '168130836233960', 10, 2, NULL, 100.00, '2023-04-16|北京协和医院|多发性硬化专科门诊|副主任医师', 1, NULL, NULL, '2023-04-12 22:06:19', '2023-04-12 22:06:19', 0);
INSERT INTO `refund_info` VALUES (3, '168130923394198', 12, 2, NULL, 100.00, '2023-04-15|北京协和医院|多发性硬化专科门诊|医师', 1, NULL, NULL, '2023-04-12 22:20:50', '2023-04-12 22:20:50', 0);
INSERT INTO `refund_info` VALUES (4, '168136393053092', 13, 2, NULL, 100.00, '2023-04-17|北京协和医院|多发性硬化专科门诊|医师', 1, NULL, NULL, '2023-04-13 13:33:40', '2023-04-13 13:33:40', 0);
INSERT INTO `refund_info` VALUES (5, '168137452945226', 30, 2, NULL, 100.00, '2023-04-15|北京协和医院|多发性硬化专科门诊|医师', 1, NULL, NULL, '2023-04-13 16:43:47', '2023-04-13 16:43:47', 0);
INSERT INTO `refund_info` VALUES (6, '168562119483582', 31, 2, NULL, 100.00, '2023-06-04|北京协和医院|多发性硬化专科门诊|医师', 1, NULL, NULL, '2023-06-01 20:09:16', '2023-06-01 20:09:05', 0);
INSERT INTO `refund_info` VALUES (7, '168802921040693', 34, 2, NULL, 100.00, '2023-06-30|北京协和医院|多发性硬化专科门诊|医师', 1, NULL, NULL, '2023-06-29 17:01:18', '2023-06-29 17:01:11', 0);

SET FOREIGN_KEY_CHECKS = 1;
