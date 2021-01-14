/*
 Navicat Premium Data Transfer

 Source Server         : localhost_MySQL
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : flashsale

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 10/01/2021 10:25:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '秒杀活动ID',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '秒杀活动名称',
  `commodity_id` bigint NOT NULL,
  `old_price` decimal(10, 2) NOT NULL COMMENT '商品原价',
  `seckill_price` decimal(10, 2) NOT NULL COMMENT '秒杀价格',
  `activity_status` tinyint NOT NULL DEFAULT 1 COMMENT '秒杀活动的状态，0:下架 1:正常',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '活动开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '活动结束时间',
  `total_stock` bigint UNSIGNED NOT NULL COMMENT '秒杀活动',
  `available_stock` bigint NOT NULL COMMENT '可用库存',
  `lock_stock` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '锁定库存数量',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id`(`id`, `name`, `commodity_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of activity
-- ----------------------------
INSERT INTO `activity` VALUES (1, '你好好红红火火', 1, 99.99, 88.88, 0, '2020-11-01 19:21:20', '2021-01-10 10:24:10', 0, 0, 0);
INSERT INTO `activity` VALUES (2, '超值秒杀-Apple 苹果 iPhone 11 Pro 手机 全网通 双卡双待 暗夜绿色 256GB', 1, 8769.00, 7769.00, 1, '2020-11-01 19:21:20', '2020-12-10 10:24:05', 10, 0, 0);
INSERT INTO `activity` VALUES (3, '超值秒杀-Apple 苹果 iPhone 11 Pro 手机 全网通 双卡双待 暗夜绿色 256GB', 2, 8769.00, 7769.00, 0, '2020-11-01 19:21:20', '2020-11-20 16:50:40', 10, 0, 0);
INSERT INTO `activity` VALUES (4, '超值秒杀-Apple 苹果 iPhone 11 Pro 手机 全网通 双卡双待 暗夜绿色 256GB', 2, 99.99, 88.88, 1, '2020-11-01 19:21:20', '2020-11-18 16:50:33', 10, 0, 0);
INSERT INTO `activity` VALUES (5, 'iPhone12 Pro 秒杀抢购活动', 2, 7888.00, 5888.00, 1, '2020-11-05 08:39:24', '2020-11-05 08:39:24', 10, 10, 0);

-- ----------------------------
-- Table structure for commodity
-- ----------------------------
DROP TABLE IF EXISTS `commodity`;
CREATE TABLE `commodity`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `commodity_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `commodity_desc` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `commodity_price` decimal(10, 0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1002 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of commodity
-- ----------------------------
INSERT INTO `commodity` VALUES (1, 'iphone12', '分辨率：1920*1080(FHD)\n后置摄像头：1200万像素\n前置摄像头：500万像素\n核 数：其他\n频 率：以官网信息为准\n品牌： Apple\n商品名称：APPLEiPhone 6s Plus\n商品编号：1861098\n商品毛重：0.51kg\n商品产地：中国大陆\n热点：指纹识别，Apple Pay，金属机身，拍照神器\n系统：苹果（IOS）\n像素：1000-1600万\n机身内存：64GB', 999);
INSERT INTO `commodity` VALUES (2, 'iphone12 pro', 'iphone12 pro 非常好', 1999);

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `id` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `order_status` int NOT NULL,
  `seckill_activity_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `order_amount` decimal(10, 0) UNSIGNED NOT NULL,
  `create_time` datetime(0) NOT NULL,
  `pay_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
