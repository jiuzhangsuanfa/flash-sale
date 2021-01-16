/*
 Navicat Premium Data Transfer

 Source Server         : FlashSale
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : localhost:3306
 Source Schema         : seckill

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 15/01/2021 15:38:12
*/

CREATE DATABASE IF NOT EXISTS `seckill` CHARACTER SET 'utf8mb4';
USE `seckill`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for seckill_activity
-- ----------------------------
DROP TABLE IF EXISTS `seckill_activity`;
CREATE TABLE `seckill_activity`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '秒杀活动ID',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '秒杀活动名称',
  `commodity_id` bigint(20) NOT NULL,
  `old_price` decimal(10, 2) NOT NULL COMMENT '商品原价',
  `seckill_price` decimal(10, 2) NOT NULL COMMENT '秒杀价格',
  `activity_status` int(11) NOT NULL DEFAULT 0 COMMENT '秒杀活动的状态，0:下架 1:正常',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '活动开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '活动结束时间',
  `total_stock` bigint(20) UNSIGNED NOT NULL COMMENT '秒杀活动',
  `available_stock` int(11) NOT NULL COMMENT '可用库存',
  `lock_stock` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '锁定库存数量',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id`(`id`, `name`, `commodity_id`) USING BTREE,
  INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of seckill_activity
-- ----------------------------
INSERT INTO `seckill_activity` VALUES (1, 'iPhone12 Pro 秒杀抢购活动', 1, 7888.00, 5888.00, 1, '2020-11-05 08:39:24', '2020-11-05 08:39:24', 10, 10, 0);
INSERT INTO `seckill_activity` VALUES (2, 'iPhone12 秒杀抢购活动', 2, 5888.00, 4888.00, 1, '2020-11-05 08:39:24', '2020-11-05 08:39:24', 100, 100, 0);

-- ----------------------------
-- Table structure for seckill_commodity
-- ----------------------------
DROP TABLE IF EXISTS `seckill_commodity`;
CREATE TABLE `seckill_commodity`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `commodity_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `commodity_desc` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `commodity_price` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1002 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of seckill_commodity
-- ----------------------------
INSERT INTO `seckill_commodity` VALUES (1, 'iPhone12 pro', 'iphone12 pro 非常好', 9999);
INSERT INTO `seckill_commodity` VALUES (2, 'iPhone12', 'iphone12 非常好', 9999);
INSERT INTO `seckill_commodity` VALUES (999, '黄金搭档中老年型', '测试分辨率：1920*1080(FHD)\n后置摄像头：1200万像素\n前置摄像头：500万像素\n核 数：其他\n频 率：以官网信息为准\n品牌： Apple\n商品名称：APPLEiPhone 6s Plus\n商品编号：1861098\n商品毛重：0.51kg\n商品产地：中国大陆\n热点：指纹识别，Apple Pay，金属机身，拍照神器\n系统：苹果（IOS）\n像素：1000-1600万\n机身内存：64GB', 999);
INSERT INTO `seckill_commodity` VALUES (1000, 'iPhone12 pro', 'iphone12 pro 非常好', 9999);
INSERT INTO `seckill_commodity` VALUES (1001, 'iPhone12', 'iphone12 非常好', 9999);

-- ----------------------------
-- Table structure for seckill_order
-- ----------------------------
DROP TABLE IF EXISTS `seckill_order`;
CREATE TABLE `seckill_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `order_status` int(11) NOT NULL,
  `seckill_activity_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `order_amount` decimal(10, 0) UNSIGNED NOT NULL,
  `create_time` datetime(0) NOT NULL,
  `pay_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of seckill_order
-- ----------------------------

-- ----------------------------
-- Table structure for seckill_user
-- ----------------------------
DROP TABLE IF EXISTS `seckill_user`;
CREATE TABLE `seckill_user`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '地址',
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of seckill_user
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
