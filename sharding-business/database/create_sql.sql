/*
Navicat MySQL Data Transfer
Date: 2019-05-05 15:20:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_member0
-- ----------------------------
DROP TABLE IF EXISTS `t_member0`;
CREATE TABLE `t_member0` (
  `member_id` bigint(20) NOT NULL,
  `member_name` varchar(36) DEFAULT NULL,
  `nick_name` varchar(36) DEFAULT NULL,
  `account_no` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `birth_date` datetime DEFAULT NULL,
  `ebl_flag` varchar(1) DEFAULT '1',
  `del_flag` varchar(1) DEFAULT '0',
  `description` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员表';

-- ----------------------------
-- Table structure for t_member1
-- ----------------------------
DROP TABLE IF EXISTS `t_member1`;
CREATE TABLE `t_member1` (
  `member_id` bigint(20) NOT NULL,
  `member_name` varchar(36) DEFAULT NULL,
  `nick_name` varchar(36) DEFAULT NULL,
  `account_no` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `birth_date` datetime DEFAULT NULL,
  `ebl_flag` varchar(1) DEFAULT '1',
  `del_flag` varchar(1) DEFAULT '0',
  `description` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员表';

-- ----------------------------
-- Table structure for t_order0
-- ----------------------------
DROP TABLE IF EXISTS `t_order0`;
CREATE TABLE `t_order0` (
  `order_id` bigint(20) NOT NULL,
  `member_id` varchar(36) DEFAULT NULL,
  `order_code` varchar(36) DEFAULT NULL,
  `order_amount` double DEFAULT NULL,
  `status` varchar(1) DEFAULT '1',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';

-- ----------------------------
-- Table structure for t_order1
-- ----------------------------
DROP TABLE IF EXISTS `t_order1`;
CREATE TABLE `t_order1` (
  `order_id` bigint(20) NOT NULL,
  `member_id` varchar(36) DEFAULT NULL,
  `order_code` varchar(36) DEFAULT NULL,
  `order_amount` double DEFAULT NULL,
  `status` varchar(1) DEFAULT '1',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';

-- ----------------------------
-- Table structure for t_order_item0
-- ----------------------------
DROP TABLE IF EXISTS `t_order_item0`;
CREATE TABLE `t_order_item0` (
  `item_id` bigint(20) NOT NULL,
  `order_id` varchar(36) DEFAULT NULL,
  `product_name` varchar(128) DEFAULT NULL,
  `item_account` double DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单详情表';

-- ----------------------------
-- Table structure for t_order_item1
-- ----------------------------
DROP TABLE IF EXISTS `t_order_item1`;
CREATE TABLE `t_order_item1` (
  `item_id` bigint(20) NOT NULL,
  `order_id` varchar(36) DEFAULT NULL,
  `product_name` varchar(128) DEFAULT NULL,
  `item_account` double DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单详情表';
