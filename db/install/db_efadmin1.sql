-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        8.0.17 - MySQL Community Server - GPL
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  10.3.0.5771
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 db_efadmin1 的数据库结构
CREATE DATABASE IF NOT EXISTS `db_efadmin1` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `db_efadmin1`;

-- 导出  表 db_efadmin1.sys_alipay_config 结构
CREATE TABLE IF NOT EXISTS `sys_alipay_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '应用ID',
  `charset` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '编码',
  `format` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '类型 固定格式json',
  `gateway_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '网关地址',
  `notify_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '异步回调',
  `private_key` text CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs COMMENT '私钥',
  `public_key` text CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs COMMENT '公钥',
  `return_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '回调地址',
  `sign_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '签名方式',
  `sys_service_provider_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '商户号',
  `remark` varchar(1024) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs ROW_FORMAT=COMPACT COMMENT='支付宝配置类';

-- 正在导出表  db_efadmin1.sys_alipay_config 的数据：~0 rows (大约)
DELETE FROM `sys_alipay_config`;
/*!40000 ALTER TABLE `sys_alipay_config` DISABLE KEYS */;
INSERT INTO `sys_alipay_config` (`id`, `app_id`, `charset`, `format`, `gateway_url`, `notify_url`, `private_key`, `public_key`, `return_url`, `sign_type`, `sys_service_provider_id`, `remark`, `create_time`, `creator_num`, `update_time`, `updater_num`) VALUES
	(1, '2016091700532697', 'utf-8', 'JSON', 'https://openapi.alipaydev.com/gateway.do', 'http://api.auauz.net/api/aliPay/notify', 'MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC5js8sInU10AJ0cAQ8UMMyXrQ+oHZEkVt5lBwsStmTJ7YikVYgbskx1YYEXTojRsWCb+SH/kDmDU4pK/u91SJ4KFCRMF2411piYuXU/jF96zKrADznYh/zAraqT6hvAIVtQAlMHN53nx16rLzZ/8jDEkaSwT7+HvHiS+7sxSojnu/3oV7BtgISoUNstmSe8WpWHOaWv19xyS+Mce9MY4BfseFhzTICUymUQdd/8hXA28/H6osUfAgsnxAKv7Wil3aJSgaJczWuflYOve0dJ3InZkhw5Cvr0atwpk8YKBQjy5CdkoHqvkOcIB+cYHXJKzOE5tqU7inSwVbHzOLQ3XbnAgMBAAECggEAVJp5eT0Ixg1eYSqFs9568WdetUNCSUchNxDBu6wxAbhUgfRUGZuJnnAll63OCTGGck+EGkFh48JjRcBpGoeoHLL88QXlZZbC/iLrea6gcDIhuvfzzOffe1RcZtDFEj9hlotg8dQj1tS0gy9pN9g4+EBH7zeu+fyv+qb2e/v1l6FkISXUjpkD7RLQr3ykjiiEw9BpeKb7j5s7Kdx1NNIzhkcQKNqlk8JrTGDNInbDM6inZfwwIO2R1DHinwdfKWkvOTODTYa2MoAvVMFT9Bec9FbLpoWp7ogv1JMV9svgrcF9XLzANZ/OQvkbe9TV9GWYvIbxN6qwQioKCWO4GPnCAQKBgQDgW5MgfhX8yjXqoaUy/d1VjI8dHeIyw8d+OBAYwaxRSlCfyQ+tieWcR2HdTzPca0T0GkWcKZm0ei5xRURgxt4DUDLXNh26HG0qObbtLJdu/AuBUuCqgOiLqJ2f1uIbrz6OZUHns+bT/jGW2Ws8+C13zTCZkZt9CaQsrp3QOGDx5wKBgQDTul39hp3ZPwGNFeZdkGoUoViOSd5Lhowd5wYMGAEXWRLlU8z+smT5v0POz9JnIbCRchIY2FAPKRdVTICzmPk2EPJFxYTcwaNbVqL6lN7J2IlXXMiit5QbiLauo55w7plwV6LQmKm9KV7JsZs5XwqF7CEovI7GevFzyD3w+uizAQKBgC3LY1eRhOlpWOIAhpjG6qOoohmeXOphvdmMlfSHq6WYFqbWwmV4rS5d/6LNpNdL6fItXqIGd8I34jzql49taCmi+A2nlR/E559j0mvM20gjGDIYeZUz5MOE8k+K6/IcrhcgofgqZ2ZED1ksHdB/E8DNWCswZl16V1FrfvjeWSNnAoGAMrBplCrIW5xz+J0Hm9rZKrs+AkK5D4fUv8vxbK/KgxZ2KaUYbNm0xv39c+PZUYuFRCz1HDGdaSPDTE6WeWjkMQd5mS6ikl9hhpqFRkyh0d0fdGToO9yLftQKOGE/q3XUEktI1XvXF0xyPwNgUCnq0QkpHyGVZPtGFxwXiDvpvgECgYA5PoB+nY8iDiRaJNko9w0hL4AeKogwf+4TbCw+KWVEn6jhuJa4LFTdSqp89PktQaoVpwv92el/AhYjWOl/jVCm122f9b7GyoelbjMNolToDwe5pF5RnSpEuDdLy9MfE8LnE3PlbE7E5BipQ3UjSebkgNboLHH/lNZA5qvEtvbfvQ==', 'MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAut9evKRuHJ/2QNfDlLwvN/S8l9hRAgPbb0u61bm4AtzaTGsLeMtScetxTWJnVvAVpMS9luhEJjt+Sbk5TNLArsgzzwARgaTKOLMT1TvWAK5EbHyI+eSrc3s7Awe1VYGwcubRFWDm16eQLv0k7iqiw+4mweHSz/wWyvBJVgwLoQ02btVtAQErCfSJCOmt0Q/oJQjj08YNRV4EKzB19+f5A+HQVAKy72dSybTzAK+3FPtTtNen/+b5wGeat7c32dhYHnGorPkPeXLtsqqUTp1su5fMfd4lElNdZaoCI7osZxWWUo17vBCZnyeXc9fk0qwD9mK6yRAxNbrY72Xx5VqIqwIDAQAB', 'http://api.auauz.net/api/aliPay/return', 'RSA2', '2088102176044281', NULL, '2019-12-26 22:01:43', NULL, '2019-12-26 22:01:43', NULL);
/*!40000 ALTER TABLE `sys_alipay_config` ENABLE KEYS */;

-- 导出  表 db_efadmin1.sys_column_config 结构
CREATE TABLE IF NOT EXISTS `sys_column_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `table_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL,
  `column_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL,
  `column_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL,
  `dict_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL,
  `extra` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL,
  `form_show` bit(1) DEFAULT NULL,
  `form_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL,
  `key_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL,
  `list_show` bit(1) DEFAULT NULL,
  `not_null` bit(1) DEFAULT NULL,
  `query_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL,
  `date_annotation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs ROW_FORMAT=COMPACT COMMENT='代码生成字段信息存储';

-- 正在导出表  db_efadmin1.sys_column_config 的数据：~75 rows (大约)
DELETE FROM `sys_column_config`;
/*!40000 ALTER TABLE `sys_column_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_column_config` ENABLE KEYS */;

-- 导出  表 db_efadmin1.sys_dept 结构
CREATE TABLE IF NOT EXISTS `sys_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL COMMENT '名称',
  `pid` bigint(20) NOT NULL COMMENT '上级部门',
  `enabled` bit(1) NOT NULL COMMENT '状态',
  `remark` varchar(1024) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `creator_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs ROW_FORMAT=COMPACT COMMENT='部门';

-- 正在导出表  db_efadmin1.sys_dept 的数据：~7 rows (大约)
DELETE FROM `sys_dept`;
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;
INSERT INTO `sys_dept` (`id`, `name`, `pid`, `enabled`, `remark`, `create_time`, `creator_num`, `update_time`, `updater_num`) VALUES
	(1, 'EF-ADMIN', 0, b'1', NULL, '2019-03-01 12:07:37', NULL, '2019-12-31 16:58:26', 'admin'),
	(2, '研发部', 7, b'1', NULL, '2019-03-25 09:15:32', NULL, '2019-12-26 22:01:44', NULL),
	(5, '运维部', 7, b'1', 'Maintenance Department', '2019-03-25 09:20:44', NULL, '2020-01-26 12:29:21', 'admin'),
	(6, '测试部(QA)', 8, b'1', NULL, '2019-03-25 09:52:18', NULL, '2020-01-26 12:10:50', 'admin'),
	(7, '华南分部', 1, b'1', NULL, '2019-03-25 11:04:50', NULL, '2019-12-26 22:01:44', NULL),
	(8, '华北分部', 1, b'1', NULL, '2019-03-25 11:04:53', NULL, '2019-12-26 22:01:44', NULL),
	(11, '人事部(HR)', 8, b'1', NULL, '2019-03-25 11:07:58', NULL, '2020-01-26 12:10:05', 'admin'),
	(13, '采购部PUR', 1, b'1', '采购部门', '2020-03-21 10:36:12', 'admin', '2020-03-21 10:36:12', 'admin');
/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;

-- 导出  表 db_efadmin1.sys_dict 结构
CREATE TABLE IF NOT EXISTS `sys_dict` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL COMMENT '字典名称',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `creator_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs ROW_FORMAT=COMPACT COMMENT='数据字典';

-- 正在导出表  db_efadmin1.sys_dict 的数据：~3 rows (大约)
DELETE FROM `sys_dict`;
/*!40000 ALTER TABLE `sys_dict` DISABLE KEYS */;
INSERT INTO `sys_dict` (`id`, `name`, `remark`, `create_time`, `creator_num`, `update_time`, `updater_num`) VALUES
	(1, 'user_status', '用户状态', '2019-10-27 20:31:36', NULL, '2019-12-26 22:01:44', NULL),
	(4, 'dept_status', '部门状态', '2019-10-27 20:31:36', NULL, '2019-12-26 22:01:44', NULL),
	(5, 'job_status', '岗位状态', '2019-10-27 20:31:36', NULL, '2019-12-26 22:01:44', NULL),
	(6, 'employee_status', '员工状态', '2020-04-24 14:03:31', 'admin', '2020-04-24 14:03:31', 'admin'),
    (7, 'switch_status', '开关状态', '2020-05-30 09:08:39', 'admin', '2020-05-30 09:08:39', 'admin');
/*!40000 ALTER TABLE `sys_dict` ENABLE KEYS */;

-- 导出  表 db_efadmin1.sys_dict_detail 结构
CREATE TABLE IF NOT EXISTS `sys_dict_detail` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL COMMENT '字典标签',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL COMMENT '字典值',
  `sort` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '排序',
  `dict_id` bigint(11) DEFAULT NULL COMMENT '字典id',
  `remark` varchar(1024) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `creator_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK5tpkputc6d9nboxojdbgnpmyb` (`dict_id`) USING BTREE,
  CONSTRAINT `FK5tpkputc6d9nboxojdbgnpmyb` FOREIGN KEY (`dict_id`) REFERENCES `sys_dict` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs ROW_FORMAT=COMPACT COMMENT='数据字典详情';

-- 正在导出表  db_efadmin1.sys_dict_detail 的数据：~8 rows (大约)
DELETE FROM `sys_dict_detail`;
/*!40000 ALTER TABLE `sys_dict_detail` DISABLE KEYS */;
INSERT INTO `sys_dict_detail` (`id`, `label`, `value`, `sort`, `dict_id`, `remark`, `create_time`, `creator_num`, `update_time`, `updater_num`) VALUES
	(1, '激活(Active)', 'true', '1', 1, NULL, '2019-10-27 20:31:36', NULL, '2020-01-26 11:57:30', 'admin'),
	(2, '禁用(Disable)', 'false', '2', 1, NULL, NULL, NULL, '2020-01-26 11:57:46', 'admin'),
	(3, '启用(Active)', 'true', '1', 4, NULL, NULL, NULL, '2020-01-27 10:42:08', 'admin'),
	(4, '停用(Disable)', 'false', '2', 4, NULL, '2019-10-27 20:31:36', NULL, '2020-01-27 10:42:23', 'admin'),
	(5, '启用(Active)', 'true', '1', 5, NULL, NULL, NULL, '2020-01-27 10:42:46', 'admin'),
	(6, '停用(Disable)', 'false', '2', 5, NULL, '2019-10-27 20:31:36', NULL, '2020-01-27 10:43:01', 'admin'),
	(7, '在职', '０', '1', 6, NULL, '2020-04-24 14:04:20', NULL, '2020-04-24 14:05:13', 'admin'),
	(8, '休假', '1', '2', 6, NULL, '2020-04-24 14:05:53', 'admin', '2020-04-24 14:05:53', 'admin'),
	(9, '离职', '2', '3', 6, NULL, '2020-04-24 14:06:29', 'admin', '2020-04-24 14:06:29', 'admin'),
    (10, '启用(Active)', 'true', '1', 7, NULL, '2020-05-30 09:09:11', 'admin', '2020-05-30 09:09:11', 'admin'),
    (11, '停用(Disable)', 'false', '2', 7, NULL, '2020-05-30 09:09:38', 'admin', '2020-05-30 09:09:38', 'admin');
/*!40000 ALTER TABLE `sys_dict_detail` ENABLE KEYS */;

-- 导出  表 db_efadmin1.sys_email_config 结构
CREATE TABLE IF NOT EXISTS `sys_email_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `from_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '收件人',
  `host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '邮件服务器SMTP地址',
  `pass` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '密码',
  `port` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '端口',
  `user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '发件者用户名',
  `remark` varchar(1024) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs ROW_FORMAT=COMPACT COMMENT='邮箱配置';

-- 正在导出表  db_efadmin1.sys_email_config 的数据：~0 rows (大约)
DELETE FROM `sys_email_config`;
/*!40000 ALTER TABLE `sys_email_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_email_config` ENABLE KEYS */;

-- 导出  表 db_efadmin1.sys_gen_config 结构
CREATE TABLE IF NOT EXISTS `sys_gen_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `table_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '表名',
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '作者',
  `cover` bit(1) DEFAULT NULL COMMENT '是否覆盖',
  `module_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '模块名称',
  `pack` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '至于哪个包下',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '前端代码生成的路径',
  `api_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '前端Api文件路径',
  `prefix` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '表前缀',
  `api_alias` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '接口名称',
  `remark` varchar(1024) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs ROW_FORMAT=COMPACT COMMENT='代码生成器配置';

-- 正在导出表  db_efadmin1.sys_gen_config 的数据：~0 rows (大约)
DELETE FROM `sys_gen_config`;
/*!40000 ALTER TABLE `sys_gen_config` DISABLE KEYS */;
INSERT INTO `sys_gen_config` (`id`, `table_name`, `author`, `cover`, `module_name`, `pack`, `path`, `api_path`, `prefix`, `api_alias`, `remark`, `create_time`, `creator_num`, `update_time`, `updater_num`) VALUES
	(1, 'tbl_employee', 'dbdu', b'1', 'efadmin-system', 'vip.efactory.demo', 'system', 'employee/', 'tbl_', '员工信息', NULL, '2019-12-31 08:54:51', 'admin', '2020-03-14 08:59:45', 'admin'),
	(6, 'sys_tenant', 'vip-efactory', b'1', 'efadmin-system', 'vip.efactory.modules.tenant', 'tenant', 'tenant/', 'sys_', '系统租户', NULL, '2020-04-10 11:35:08', 'admin', '2020-04-10 13:24:46', 'admin');
/*!40000 ALTER TABLE `sys_gen_config` ENABLE KEYS */;

-- 导出  表 db_efadmin1.sys_gen_test 结构
CREATE TABLE IF NOT EXISTS `sys_gen_test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL,
  `sex` int(255) DEFAULT NULL COMMENT '性别',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs ROW_FORMAT=COMPACT COMMENT='代码生成测试';

-- 正在导出表  db_efadmin1.sys_gen_test 的数据：~0 rows (大约)
DELETE FROM `sys_gen_test`;
/*!40000 ALTER TABLE `sys_gen_test` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_gen_test` ENABLE KEYS */;

-- 导出  表 db_efadmin1.sys_job 结构
CREATE TABLE IF NOT EXISTS `sys_job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL COMMENT '岗位名称',
  `enabled` bit(1) NOT NULL COMMENT '岗位状态',
  `sort` bigint(20) NOT NULL COMMENT '岗位排序',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `remark` varchar(1024) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `creator_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FKmvhj0rogastlctflsxf1d6k3i` (`dept_id`) USING BTREE,
  CONSTRAINT `FKmvhj0rogastlctflsxf1d6k3i` FOREIGN KEY (`dept_id`) REFERENCES `sys_dept` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs ROW_FORMAT=COMPACT COMMENT='岗位';

-- 正在导出表  db_efadmin1.sys_job 的数据：~4 rows (大约)
DELETE FROM `sys_job`;
/*!40000 ALTER TABLE `sys_job` DISABLE KEYS */;
INSERT INTO `sys_job` (`id`, `name`, `enabled`, `sort`, `dept_id`, `remark`, `create_time`, `creator_num`, `update_time`, `updater_num`) VALUES
	(8, '人事专员', b'1', 3, 11, '如果要做国际化最好名称上有英文简写', '2019-03-29 14:52:28', NULL, '2020-03-14 09:00:58', NULL),
	(10, '产品经理', b'1', 4, 2, NULL, '2019-03-29 14:55:51', NULL, '2019-12-26 22:01:45', NULL),
	(11, '全栈开发', b'1', 2, 2, NULL, '2019-03-31 13:39:30', NULL, '2019-12-26 22:01:45', NULL),
	(12, '软件测试', b'1', 5, 2, NULL, '2019-03-31 13:39:43', NULL, '2019-12-26 22:01:45', NULL);
/*!40000 ALTER TABLE `sys_job` ENABLE KEYS */;

-- 导出  表 db_efadmin1.sys_local_storage 结构
CREATE TABLE IF NOT EXISTS `sys_local_storage` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '文件真实的名称',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '文件名',
  `suffix` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '后缀',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '路径',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '类型',
  `size` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '大小',
  `operate` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '操作人',
  `remark` varchar(1024) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `creator_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs ROW_FORMAT=COMPACT COMMENT='本地存储';

-- 正在导出表  db_efadmin1.sys_local_storage 的数据：~0 rows (大约)
DELETE FROM `sys_local_storage`;
/*!40000 ALTER TABLE `sys_local_storage` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_local_storage` ENABLE KEYS */;

-- 导出  表 db_efadmin1.sys_log 结构
CREATE TABLE IF NOT EXISTS `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL,
  `exception_detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs,
  `log_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL,
  `method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL,
  `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs,
  `request_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL,
  `time` bigint(20) DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL,
  `browser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL,
  `remark` varchar(1024) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs ROW_FORMAT=COMPACT COMMENT='系统日志';

-- 正在导出表  db_efadmin1.sys_log 的数据：~279 rows (大约)
DELETE FROM `sys_log`;
/*!40000 ALTER TABLE `sys_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_log` ENABLE KEYS */;

-- 导出  表 db_efadmin1.sys_menu 结构
CREATE TABLE IF NOT EXISTS `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `i_frame` bit(1) DEFAULT NULL COMMENT '是否外链',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '菜单名称',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '组件',
  `pid` bigint(20) NOT NULL COMMENT '上级菜单ID',
  `sort` bigint(20) DEFAULT NULL COMMENT '排序',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '图标',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '链接地址',
  `cache` bit(1) DEFAULT b'0' COMMENT '缓存',
  `hidden` bit(1) DEFAULT b'0' COMMENT '隐藏',
  `component_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT '-' COMMENT '组件名称',
  `permission` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '权限',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `locale_key` varchar(255) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '国际化key',
  `remark` varchar(1024) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `creator_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FKqcf9gem97gqa5qjm4d3elcqt5` (`pid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs ROW_FORMAT=COMPACT COMMENT='系统菜单';

-- 正在导出表  db_efadmin1.sys_menu 的数据：~84 rows (大约)
DELETE FROM `sys_menu`;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` (`id`, `i_frame`, `name`, `component`, `pid`, `sort`, `icon`, `path`, `cache`, `hidden`, `component_name`, `permission`, `type`, `locale_key`, `remark`, `create_time`, `creator_num`, `update_time`, `updater_num`) VALUES
	(1, b'0', '系统管理', NULL, 0, 1, 'system', 'system', b'0', b'0', NULL, NULL, 0, 'system.manage', NULL, '2018-12-18 15:11:29', NULL, '2020-01-20 12:24:19', 'admin'),
	(2, b'0', '用户管理', 'system/user/index', 1, 2, 'peoples', 'user', b'0', b'0', 'User', 'user:list', 1, 'user.manage', NULL, '2018-12-18 15:14:44', NULL, '2020-01-20 11:02:59', NULL),
	(3, b'0', '角色管理', 'system/role/index', 1, 3, 'role', 'role', b'0', b'0', 'Role', 'roles:list', 1, 'role.manage', NULL, '2018-12-18 15:16:07', NULL, '2020-01-20 11:03:09', NULL),
	(5, b'0', '菜单管理', 'system/menu/index', 1, 5, 'menu', 'menu', b'0', b'0', 'Menu', 'menu:list', 1, 'menu.manage', NULL, '2018-12-18 15:17:28', NULL, '2020-01-20 11:03:32', NULL),
	(6, b'0', '系统监控', NULL, 0, 10, 'monitor', 'monitor', b'0', b'0', NULL, NULL, 0, 'system.monitor', NULL, '2018-12-18 15:17:48', NULL, '2020-01-20 11:03:57', NULL),
	(7, b'0', '操作日志', 'monitor/log/index', 6, 11, 'log', 'logs', b'0', b'0', 'Log', NULL, 1, 'operation.log', NULL, '2018-12-18 15:18:26', NULL, '2020-01-20 11:04:13', NULL),
	(9, b'0', 'SQL监控', 'monitor/sql/index', 6, 18, 'sqlMonitor', 'druid', b'0', b'0', 'Sql', NULL, 1, 'sql.monitor', NULL, '2018-12-18 15:19:34', NULL, '2020-01-20 11:04:49', NULL),
	(10, b'0', '组件管理', NULL, 0, 50, 'zujian', 'components', b'0', b'0', NULL, NULL, 0, 'component.manage', NULL, '2018-12-19 13:38:16', NULL, '2020-01-20 11:05:02', NULL),
	(11, b'0', '图标库', 'components/icons/index', 10, 51, 'icon', 'icon', b'0', b'0', 'Icons', NULL, 1, 'icon.library', NULL, '2018-12-19 13:38:49', NULL, '2020-01-20 11:05:21', NULL),
	(14, b'0', '邮件工具', 'tools/email/index', 36, 35, 'email', 'email', b'0', b'0', 'Email', NULL, 1, 'email.tool', NULL, '2018-12-27 10:13:09', NULL, '2020-01-20 11:08:09', NULL),
	(15, b'0', '富文本', 'components/Editor', 10, 52, 'fwb', 'tinymce', b'0', b'0', 'Editor', NULL, 1, 'rich.text', NULL, '2018-12-27 11:58:25', NULL, '2020-01-20 11:05:47', NULL),
	(16, b'0', '图床管理', 'tools/picture/index', 36, 33, 'image', 'pictures', b'0', b'0', 'Pictures', 'pictures:list', 1, 'picture.manage', NULL, '2018-12-28 09:36:53', NULL, '2020-01-20 11:06:19', NULL),
	(18, b'0', '存储管理', 'tools/storage/index', 36, 34, 'qiniu', 'storage', b'0', b'0', 'Storage', 'storage:list', 1, 'storage.manage', NULL, '2018-12-31 11:12:15', NULL, '2020-01-20 11:07:33', NULL),
	(19, b'0', '支付宝工具', 'tools/aliPay/index', 36, 37, 'alipay', 'aliPay', b'0', b'0', 'AliPay', NULL, 1, 'alipay.tool', NULL, '2018-12-31 14:52:38', NULL, '2020-01-20 11:07:44', NULL),
	(21, b'0', '多级菜单', '', 0, 900, 'menu', 'nested', b'0', b'1', NULL, NULL, 0, 'multi.menu', NULL, '2019-01-04 16:22:03', NULL, '2020-01-20 11:08:30', NULL),
	(22, b'0', '二级菜单1', 'nested/menu1/index', 21, 999, 'menu', 'menu1', b'0', b'0', NULL, NULL, 1, 'second.menu1', NULL, '2019-01-04 16:23:29', NULL, '2020-01-20 11:09:30', NULL),
	(23, b'0', '二级菜单2', 'nested/menu2/index', 21, 999, 'menu', 'menu2', b'0', b'0', NULL, NULL, 1, 'second.menu2', NULL, '2019-01-04 16:23:57', NULL, '2020-01-20 11:09:45', NULL),
	(24, b'0', '三级菜单1', 'nested/menu1/menu1-1', 22, 999, 'menu', 'menu1-1', b'0', b'0', NULL, NULL, 1, 'third.menu1', NULL, '2019-01-04 16:24:48', NULL, '2020-01-20 11:10:05', NULL),
	(27, b'0', '三级菜单2', 'nested/menu1/menu1-2', 22, 999, 'menu', 'menu1-2', b'0', b'0', NULL, NULL, 1, 'third.menu2', NULL, '2019-01-07 17:27:32', NULL, '2020-01-20 11:10:17', NULL),
	(28, b'0', '定时任务', 'system/timing/index', 36, 31, 'timing', 'timing', b'0', b'0', 'Timing', 'timing:list', 1, 'timed.task', NULL, '2019-01-07 20:34:40', NULL, '2020-01-20 11:11:34', NULL),
	(30, b'0', '代码生成', 'generator/index', 36, 32, 'dev', 'generator', b'1', b'0', 'GeneratorIndex', NULL, 1, 'code.generator', NULL, '2019-01-11 15:45:55', NULL, '2020-01-20 11:11:57', NULL),
	(32, b'0', '异常日志', 'monitor/log/errorLog', 6, 12, 'error', 'errorLog', b'0', b'0', 'ErrorLog', NULL, 1, 'error.log', NULL, '2019-01-13 13:49:03', NULL, '2020-01-20 11:12:11', NULL),
	(33, b'0', 'Markdown', 'components/MarkDown', 10, 53, 'markdown', 'markdown', b'0', b'0', 'Markdown', NULL, 1, 'markdown.editor', NULL, '2019-03-08 13:46:44', NULL, '2020-01-20 11:12:57', NULL),
	(34, b'0', 'Yaml编辑器', 'components/YamlEdit', 10, 54, 'dev', 'yaml', b'0', b'0', 'YamlEdit', NULL, 1, 'yaml.editor', NULL, '2019-03-08 15:49:40', NULL, '2020-01-20 11:12:47', NULL),
	(35, b'0', '部门管理', 'system/dept/index', 1, 6, 'dept', 'dept', b'0', b'0', 'Dept', 'dept:list', 1, 'dept.manage', NULL, '2019-03-25 09:46:00', NULL, '2020-01-20 11:13:08', NULL),
	(36, b'0', '系统工具', '', 0, 30, 'sys-tools', 'sys-tools', b'0', b'0', NULL, NULL, 0, 'system.tool', NULL, '2019-03-29 10:57:35', NULL, '2020-01-20 11:13:56', NULL),
	(37, b'0', '岗位管理', 'system/job/index', 1, 7, 'Steve-Jobs', 'job', b'0', b'0', 'Job', 'job:list', 1, 'job.manage', NULL, '2019-03-29 13:51:18', NULL, '2020-01-20 11:14:04', NULL),
	(38, b'0', '接口文档', 'tools/swagger/index', 36, 36, 'swagger', 'swagger2', b'0', b'0', 'Swagger', NULL, 1, 'api.docs', NULL, '2019-03-29 19:57:53', NULL, '2020-01-20 11:14:13', NULL),
	(39, b'0', '字典管理', 'system/dict/index', 1, 8, 'dictionary', 'dict', b'0', b'0', 'Dict', 'dict:list', 1, 'dict.manage', NULL, '2019-04-10 11:49:04', NULL, '2020-01-20 11:14:27', NULL),
	(41, b'0', '在线用户', 'monitor/online/index', 6, 10, 'Steve-Jobs', 'online', b'0', b'0', 'OnlineUser', NULL, 1, 'online.user', NULL, '2019-10-26 22:08:43', NULL, '2020-01-20 11:14:35', NULL),
	(44, b'0', '用户新增', '', 2, 2, '', '', b'0', b'0', '', 'user:add', 2, 'user.add', NULL, '2019-10-29 10:59:46', NULL, '2020-01-22 21:37:08', NULL),
	(45, b'0', '用户编辑', '', 2, 3, '', '', b'0', b'0', '', 'user:edit', 2, 'user.edit', NULL, '2019-10-29 11:00:08', NULL, '2020-01-22 21:37:16', NULL),
	(46, b'0', '用户删除', '', 2, 4, '', '', b'0', b'0', '', 'user:del', 2, 'user.delete', NULL, '2019-10-29 11:00:23', NULL, '2020-01-22 21:37:26', NULL),
	(48, b'0', '角色创建', '', 3, 2, '', '', b'0', b'0', '', 'roles:add', 2, 'role.add', NULL, '2019-10-29 12:45:34', NULL, '2020-01-22 21:37:52', NULL),
	(49, b'0', '角色修改', '', 3, 3, '', '', b'0', b'0', '', 'roles:edit', 2, 'role.edit', NULL, '2019-10-29 12:46:16', NULL, '2020-01-22 21:38:00', NULL),
	(50, b'0', '角色删除', '', 3, 4, '', '', b'0', b'0', '', 'roles:del', 2, 'role.delete', NULL, '2019-10-29 12:46:51', NULL, '2020-01-22 21:38:20', NULL),
	(52, b'0', '菜单新增', '', 5, 2, '', '', b'0', b'0', '', 'menu:add', 2, 'menu.add', NULL, '2019-10-29 12:55:07', NULL, '2020-01-22 21:38:40', NULL),
	(53, b'0', '菜单编辑', '', 5, 3, '', '', b'0', b'0', '', 'menu:edit', 2, 'menu.edit', NULL, '2019-10-29 12:55:40', NULL, '2020-01-22 21:38:45', NULL),
	(54, b'0', '菜单删除', '', 5, 4, '', '', b'0', b'0', '', 'menu:del', 2, 'menu.delete', NULL, '2019-10-29 12:56:00', NULL, '2020-01-22 21:38:54', NULL),
	(56, b'0', '部门新增', '', 35, 2, '', '', b'0', b'0', '', 'dept:add', 2, 'dept.add', NULL, '2019-10-29 12:57:09', NULL, '2020-01-22 21:39:01', NULL),
	(57, b'0', '部门编辑', '', 35, 3, '', '', b'0', b'0', '', 'dept:edit', 2, 'dept.edit', NULL, '2019-10-29 12:57:27', NULL, '2020-01-22 21:39:20', NULL),
	(58, b'0', '部门删除', '', 35, 4, '', '', b'0', b'0', '', 'dept:del', 2, 'dept.delete', NULL, '2019-10-29 12:57:41', NULL, '2020-01-22 21:39:33', NULL),
	(60, b'0', '岗位新增', '', 37, 2, '', '', b'0', b'0', '', 'job:add', 2, 'job.add', NULL, '2019-10-29 12:58:27', NULL, '2020-01-22 21:39:50', NULL),
	(61, b'0', '岗位编辑', '', 37, 3, '', '', b'0', b'0', '', 'job:edit', 2, 'job.edit', NULL, '2019-10-29 12:58:45', NULL, '2020-01-22 21:39:58', NULL),
	(62, b'0', '岗位删除', '', 37, 4, '', '', b'0', b'0', '', 'job:del', 2, 'job.delete', NULL, '2019-10-29 12:59:04', NULL, '2020-01-22 21:40:04', NULL),
	(64, b'0', '字典新增', '', 39, 2, '', '', b'0', b'0', '', 'dict:add', 2, 'dict.add', NULL, '2019-10-29 13:00:17', NULL, '2020-01-22 21:41:07', NULL),
	(65, b'0', '字典编辑', '', 39, 3, '', '', b'0', b'0', '', 'dict:edit', 2, 'dict.edit', NULL, '2019-10-29 13:00:42', NULL, '2020-01-22 21:41:17', NULL),
	(66, b'0', '字典删除', '', 39, 4, '', '', b'0', b'0', '', 'dict:del', 2, 'dict.delete', NULL, '2019-10-29 13:00:59', NULL, '2020-01-22 21:41:25', NULL),
	(70, b'0', '图片上传', '', 16, 2, '', '', b'0', b'0', '', 'pictures:add', 2, 'picture.add', NULL, '2019-10-29 13:05:34', NULL, '2020-01-22 21:41:43', NULL),
	(71, b'0', '图片删除', '', 16, 3, '', '', b'0', b'0', '', 'pictures:del', 2, 'picture.delete', NULL, '2019-10-29 13:05:52', NULL, '2020-01-22 21:42:31', NULL),
	(73, b'0', '任务新增', '', 28, 2, '', '', b'0', b'0', '', 'timing:add', 2, 'task.add', NULL, '2019-10-29 13:07:28', NULL, '2020-01-22 21:42:57', NULL),
	(74, b'0', '任务编辑', '', 28, 3, '', '', b'0', b'0', '', 'timing:edit', 2, 'task.edit', NULL, '2019-10-29 13:07:41', NULL, '2020-01-22 21:43:06', NULL),
	(75, b'0', '任务删除', '', 28, 4, '', '', b'0', b'0', '', 'timing:del', 2, 'task.delete', NULL, '2019-10-29 13:07:54', NULL, '2020-01-22 21:43:28', NULL),
	(77, b'0', '上传文件', '', 18, 2, '', '', b'0', b'0', '', 'storage:add', 2, 'storage.add', NULL, '2019-10-29 13:09:09', NULL, '2020-01-22 21:43:50', NULL),
	(78, b'0', '文件编辑', '', 18, 3, '', '', b'0', b'0', '', 'storage:edit', 2, 'storage.edit', NULL, '2019-10-29 13:09:22', NULL, '2020-01-22 21:44:03', NULL),
	(79, b'0', '文件删除', '', 18, 4, '', '', b'0', b'0', '', 'storage:del', 2, 'storage.delete', NULL, '2019-10-29 13:09:34', NULL, '2020-01-22 21:44:12', NULL),
	(80, b'0', '服务监控', 'monitor/server/index', 6, 14, 'codeConsole', 'server', b'0', b'0', 'ServerMonitor', 'server:list', 1, 'service.monitor', NULL, '2019-11-07 13:06:39', NULL, '2020-01-20 11:16:02', NULL),
	(82, b'0', '生成配置', 'generator/config', 36, 33, 'dev', 'generator/config/:tableName', b'1', b'1', 'GeneratorConfig', '', 1, 'generate.config', NULL, '2019-11-17 20:08:56', NULL, '2020-01-20 11:16:24', NULL),
	(83, b'0', '图表库', 'components/Echarts', 10, 50, 'chart', 'echarts', b'1', b'0', 'Echarts', '', 1, 'echarts.library', NULL, '2019-11-21 09:04:32', NULL, '2020-01-20 11:16:39', NULL),
	(90, b'0', '运维管理', '', 0, 20, 'mnt', 'mnt', b'0', b'0', 'Mnt', NULL, 1, 'maintenance.manage', NULL, '2019-11-09 10:31:08', NULL, '2020-01-20 11:18:02', NULL),
	(92, b'0', '服务器', 'mnt/server/index', 90, 22, 'server', 'mnt/serverDeploy', b'0', b'0', 'ServerDeploy', 'serverDeploy:list', 1, 'server', NULL, '2019-11-10 10:29:25', NULL, '2020-01-20 11:18:28', NULL),
	(93, b'0', '应用管理', 'mnt/app/index', 90, 23, 'app', 'mnt/app', b'0', b'0', 'App', 'app:list', 1, 'app.manage', NULL, '2019-11-10 11:05:16', NULL, '2020-01-20 11:18:41', NULL),
	(94, b'0', '部署管理', 'mnt/deploy/index', 90, 24, 'deploy', 'mnt/deploy', b'0', b'0', 'Deploy', 'deploy:list', 1, 'deploy.manage', NULL, '2019-11-10 15:56:55', NULL, '2020-01-20 11:19:07', NULL),
	(97, b'0', '部署备份', 'mnt/deployHistory/index', 90, 25, 'backup', 'mnt/deployHistory', b'0', b'0', 'DeployHistory', 'deployHistory:list', 1, 'deploy.backup', NULL, '2019-11-10 16:49:44', NULL, '2020-01-20 11:19:30', NULL),
	(98, b'0', '数据库管理', 'mnt/database/index', 90, 26, 'database', 'mnt/database', b'0', b'0', 'Database', 'database:list', 1, 'db.manage', NULL, '2019-11-10 20:40:04', NULL, '2020-01-20 11:19:50', NULL),
	(102, b'0', '删除', '', 97, 999, '', '', b'0', b'0', '', 'deployHistory:del', 2, 'deployHistory.delete', NULL, '2019-11-17 09:32:48', NULL, '2020-01-22 21:45:21', NULL),
	(103, b'0', '服务器新增', '', 92, 999, '', '', b'0', b'0', '', 'serverDeploy:add', 2, 'server.add', NULL, '2019-11-17 11:08:33', NULL, '2020-01-22 21:45:51', NULL),
	(104, b'0', '服务器编辑', '', 92, 999, '', '', b'0', b'0', '', 'serverDeploy:edit', 2, 'server.edit', NULL, '2019-11-17 11:08:57', NULL, '2020-01-22 21:45:59', NULL),
	(105, b'0', '服务器删除', '', 92, 999, '', '', b'0', b'0', '', 'serverDeploy:del', 2, 'server.delete', NULL, '2019-11-17 11:09:15', NULL, '2020-01-22 21:46:09', NULL),
	(106, b'0', '应用新增', '', 93, 999, '', '', b'0', b'0', '', 'app:add', 2, 'app.add', NULL, '2019-11-17 11:10:03', NULL, '2020-01-22 21:46:17', NULL),
	(107, b'0', '应用编辑', '', 93, 999, '', '', b'0', b'0', '', 'app:edit', 2, 'app.edit', NULL, '2019-11-17 11:10:28', NULL, '2020-01-22 21:46:24', NULL),
	(108, b'0', '应用删除', '', 93, 999, '', '', b'0', b'0', '', 'app:del', 2, 'app.delete', NULL, '2019-11-17 11:10:55', NULL, '2020-01-22 21:46:31', NULL),
	(109, b'0', '部署新增', '', 94, 999, '', '', b'0', b'0', '', 'deploy:add', 2, 'deploy.add', NULL, '2019-11-17 11:11:22', NULL, '2020-01-22 21:46:57', NULL),
	(110, b'0', '部署编辑', '', 94, 999, '', '', b'0', b'0', '', 'deploy:edit', 2, 'deploy.edit', NULL, '2019-11-17 11:11:41', NULL, '2020-01-22 21:47:04', NULL),
	(111, b'0', '部署删除', '', 94, 999, '', '', b'0', b'0', '', 'deploy:del', 2, 'deploy.delete', NULL, '2019-11-17 11:12:01', NULL, '2020-01-22 21:47:13', NULL),
	(112, b'0', '数据库新增', '', 98, 999, '', '', b'0', b'0', '', 'database:add', 2, 'db.add', NULL, '2019-11-17 11:12:43', NULL, '2020-01-22 21:47:40', NULL),
	(113, b'0', '数据库编辑', '', 98, 999, '', '', b'0', b'0', '', 'database:edit', 2, 'db.edit', NULL, '2019-11-17 11:12:58', NULL, '2020-01-22 21:47:48', NULL),
	(114, b'0', '数据库删除', '', 98, 999, '', '', b'0', b'0', '', 'database:del', 2, 'db.delete', NULL, '2019-11-17 11:13:14', NULL, '2020-01-22 21:47:54', NULL),
	(116, b'0', '生成预览', 'generator/preview', 36, 999, 'java', 'generator/preview/:tableName', b'1', b'1', 'Preview', NULL, 1, 'generate.preview', NULL, '2019-11-26 14:54:36', NULL, '2020-01-20 11:20:24', NULL),
	(117, b'0', '员工管理', 'system/employee/index', 1, 9, 'zujian', 'employee', b'0', b'0', 'Employee', 'employee:list', 1, 'employee.manage', NULL, '2020-01-02 10:24:50', NULL, '2020-01-20 11:20:40', 'admin'),
	(118, b'0', '员工新增', '', 117, 2, '', '', b'0', b'0', '', 'employee:add', 2, 'employee.add', NULL, '2020-01-02 10:24:50', NULL, '2020-01-22 21:48:06', 'admin'),
	(119, b'0', '员工编辑', '', 117, 3, '', '', b'0', b'0', '', 'employee:edit', 2, 'employee.edit', NULL, '2020-01-02 10:24:50', NULL, '2020-01-22 21:48:51', 'admin'),
	(120, b'0', '员工删除', '', 117, 4, '', '', b'0', b'0', '', 'employee:del', 2, 'employee.delete', NULL, '2020-01-02 10:24:50', NULL, '2020-01-22 21:48:35', 'admin'),
	(121, b'1', 'Github', NULL, 1, 999, 'github', 'https://github.com/vip-efactory/efadmin-ui', b'1', b'0', NULL, NULL, 1, 'github.link', NULL, '2020-03-13 10:31:45', 'admin', '2020-04-12 08:16:32', 'admin');
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;

-- 导出  表 db_efadmin1.sys_mnt_app 结构
CREATE TABLE IF NOT EXISTS `sys_mnt_app` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '应用ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '应用名称',
  `upload_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '上传目录',
  `deploy_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '部署路径',
  `backup_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '备份路径',
  `port` int(255) DEFAULT NULL COMMENT '应用端口',
  `start_script` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '启动脚本',
  `deploy_script` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '部署脚本',
  `remark` varchar(1024) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `creator_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs ROW_FORMAT=COMPACT COMMENT='应用管理';

-- 正在导出表  db_efadmin1.sys_mnt_app 的数据：~2 rows (大约)
DELETE FROM `sys_mnt_app`;
/*!40000 ALTER TABLE `sys_mnt_app` DISABLE KEYS */;
INSERT INTO `sys_mnt_app` (`id`, `name`, `upload_path`, `deploy_path`, `backup_path`, `port`, `start_script`, `deploy_script`, `remark`, `create_time`, `creator_num`, `update_time`, `updater_num`) VALUES
	(1, 'eladmin-monitor-2.4.jar', '/opt/upload', '/opt/monitor', '/opt/backup', 8777, 'cd /opt/monitor\nnohup java -jar eladmin-monitor-2.4.jar >nohup.out 2>&1 &', 'mv -f /opt/upload/eladmin-monitor-2.4.jar /opt/monitor\ncd /opt/monitor\nnohup java -jar eladmin-monitor-2.4.jar >nohup.out 2>&1 &', NULL, '2019-11-24 20:52:59', NULL, '2019-12-26 22:01:47', NULL),
	(2, 'eladmin-system-2.3.jar', '/opt/upload', '/opt/eladmin', '/opt/backup/eladmin', 8000, 'cd /opt/eladmin\nnohup java -jar eladmin-system-2.3.jar --spring.profiles.active=prod >nohup.out 2>&1 &', 'mv -f /opt/upload/eladmin-system-2.3.jar /opt/eladmin/\ncd /opt/eladmin\nnohup java -jar eladmin-system-2.3.jar --spring.profiles.active=prod >nohup.out 2>&1 &', NULL, '2019-12-21 16:39:57', NULL, '2019-12-26 22:01:47', NULL);
/*!40000 ALTER TABLE `sys_mnt_app` ENABLE KEYS */;

-- 导出  表 db_efadmin1.sys_mnt_database 结构
CREATE TABLE IF NOT EXISTS `sys_mnt_database` (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL COMMENT '编号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL COMMENT '名称',
  `jdbc_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL COMMENT 'jdbc连接',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL COMMENT '账号',
  `pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL COMMENT '密码',
  `remark` varchar(1024) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs ROW_FORMAT=COMPACT COMMENT='数据库管理';

-- 正在导出表  db_efadmin1.sys_mnt_database 的数据：~0 rows (大约)
DELETE FROM `sys_mnt_database`;
/*!40000 ALTER TABLE `sys_mnt_database` DISABLE KEYS */;
INSERT INTO `sys_mnt_database` (`id`, `name`, `jdbc_url`, `user_name`, `pwd`, `remark`, `create_time`, `creator_num`, `update_time`, `updater_num`) VALUES
	('c4109eefc5724c65857ca9dd2690e0dd', 'db_eladmin', 'jdbc:mysql://localhost:3306/db_eladmin?serverTimezone=Asia/Shanghai', 'root', '123456', NULL, '2019-12-21 21:11:17', NULL, '2020-02-02 13:57:06', 'admin');
/*!40000 ALTER TABLE `sys_mnt_database` ENABLE KEYS */;

-- 导出  表 db_efadmin1.sys_mnt_deploy 结构
CREATE TABLE IF NOT EXISTS `sys_mnt_deploy` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `app_id` bigint(20) DEFAULT NULL COMMENT '应用编号',
  `remark` varchar(1024) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK6sy157pseoxx4fmcqr1vnvvhy` (`app_id`) USING BTREE,
  CONSTRAINT `FK6sy157pseoxx4fmcqr1vnvvhy` FOREIGN KEY (`app_id`) REFERENCES `sys_mnt_app` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs ROW_FORMAT=COMPACT COMMENT='部署管理';

-- 正在导出表  db_efadmin1.sys_mnt_deploy 的数据：~2 rows (大约)
DELETE FROM `sys_mnt_deploy`;
/*!40000 ALTER TABLE `sys_mnt_deploy` DISABLE KEYS */;
INSERT INTO `sys_mnt_deploy` (`id`, `app_id`, `remark`, `create_time`, `creator_num`, `update_time`, `updater_num`) VALUES
	(3, 1, NULL, '2019-12-21 15:53:06', NULL, '2019-12-26 22:01:47', NULL),
	(6, 2, NULL, '2019-12-21 17:09:02', NULL, '2019-12-26 22:01:47', NULL);
/*!40000 ALTER TABLE `sys_mnt_deploy` ENABLE KEYS */;

-- 导出  表 db_efadmin1.sys_mnt_deploy_history 结构
CREATE TABLE IF NOT EXISTS `sys_mnt_deploy_history` (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL COMMENT '编号',
  `app_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL COMMENT '应用名称',
  `deploy_date` datetime NOT NULL COMMENT '部署日期',
  `deploy_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL COMMENT '部署用户',
  `ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL COMMENT '服务器IP',
  `deploy_id` bigint(20) DEFAULT NULL COMMENT '部署编号',
  `remark` varchar(1024) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs ROW_FORMAT=COMPACT COMMENT='部署历史管理';

-- 正在导出表  db_efadmin1.sys_mnt_deploy_history 的数据：~3 rows (大约)
DELETE FROM `sys_mnt_deploy_history`;
/*!40000 ALTER TABLE `sys_mnt_deploy_history` DISABLE KEYS */;
INSERT INTO `sys_mnt_deploy_history` (`id`, `app_name`, `deploy_date`, `deploy_user`, `ip`, `deploy_id`, `remark`, `create_time`, `creator_num`, `update_time`, `updater_num`) VALUES
	('4ee0edd1f3b648a396280a542d72c121', 'eladmin-monitor-2.4.jar', '2019-12-22 13:06:07', 'admin', '132.232.129.20', 3, NULL, '2019-12-26 22:01:47', NULL, '2019-12-26 22:01:48', NULL),
	('4fd432a128c947ccae55316b3d5dcd7b', 'eladmin-monitor-2.4.jar', '2019-12-22 13:49:09', 'admin', '132.232.129.20', 3, NULL, '2019-12-26 22:01:47', NULL, '2019-12-26 22:01:48', NULL),
	('cfda21f48da341b396657af94554c890', 'eladmin-monitor-2.4.jar', '2019-12-22 13:08:22', 'admin', '132.232.129.20', 3, NULL, '2019-12-26 22:01:47', NULL, '2019-12-26 22:01:48', NULL);
/*!40000 ALTER TABLE `sys_mnt_deploy_history` ENABLE KEYS */;

-- 导出  表 db_efadmin1.sys_mnt_deploy_server 结构
CREATE TABLE IF NOT EXISTS `sys_mnt_deploy_server` (
  `deploy_id` bigint(20) NOT NULL,
  `server_id` bigint(20) NOT NULL,
  PRIMARY KEY (`deploy_id`,`server_id`) USING BTREE,
  KEY `FKeaaha7jew9a02b3bk9ghols53` (`server_id`) USING BTREE,
  CONSTRAINT `FK3cehr56tedph6nk3gxsmeq0pb` FOREIGN KEY (`deploy_id`) REFERENCES `sys_mnt_deploy` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKeaaha7jew9a02b3bk9ghols53` FOREIGN KEY (`server_id`) REFERENCES `sys_mnt_server` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs ROW_FORMAT=COMPACT COMMENT='应用与服务器关联';

-- 正在导出表  db_efadmin1.sys_mnt_deploy_server 的数据：~2 rows (大约)
DELETE FROM `sys_mnt_deploy_server`;
/*!40000 ALTER TABLE `sys_mnt_deploy_server` DISABLE KEYS */;
INSERT INTO `sys_mnt_deploy_server` (`deploy_id`, `server_id`) VALUES
	(3, 1),
	(6, 1);
/*!40000 ALTER TABLE `sys_mnt_deploy_server` ENABLE KEYS */;

-- 导出  表 db_efadmin1.sys_mnt_server 结构
CREATE TABLE IF NOT EXISTS `sys_mnt_server` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'IP地址',
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '账号',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT 'IP地址',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '名称',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '密码',
  `port` int(11) DEFAULT NULL COMMENT '端口',
  `remark` varchar(1024) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs ROW_FORMAT=COMPACT COMMENT='服务器管理';

-- 正在导出表  db_efadmin1.sys_mnt_server 的数据：~0 rows (大约)
DELETE FROM `sys_mnt_server`;
/*!40000 ALTER TABLE `sys_mnt_server` DISABLE KEYS */;
INSERT INTO `sys_mnt_server` (`id`, `account`, `ip`, `name`, `password`, `port`, `remark`, `create_time`, `creator_num`, `update_time`, `updater_num`) VALUES
	(1, 'root', '132.232.129.20', '腾讯云', 'Dqjdda1996.', 8013, NULL, '2019-11-24 20:35:02', NULL, '2019-12-26 22:01:48', NULL);
/*!40000 ALTER TABLE `sys_mnt_server` ENABLE KEYS */;

-- 导出  表 db_efadmin1.sys_monitor_server 结构
CREATE TABLE IF NOT EXISTS `sys_monitor_server` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cpu_core` int(11) DEFAULT NULL COMMENT 'CPU内核数',
  `cpu_rate` double DEFAULT NULL COMMENT 'CPU使用率',
  `disk_total` double DEFAULT NULL COMMENT '磁盘总量',
  `disk_used` double DEFAULT NULL COMMENT '磁盘使用量',
  `mem_total` double DEFAULT NULL COMMENT '内存总数',
  `mem_used` double DEFAULT NULL COMMENT '内存使用量',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '名称',
  `port` int(11) DEFAULT NULL COMMENT '访问端口',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '状态',
  `swap_total` double DEFAULT NULL COMMENT '交换区总量',
  `swap_used` double DEFAULT NULL COMMENT '交换区使用量',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL COMMENT '服务地址',
  `remark` varchar(1024) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs ROW_FORMAT=COMPACT COMMENT='服务监控';

-- 正在导出表  db_efadmin1.sys_monitor_server 的数据：~0 rows (大约)
DELETE FROM `sys_monitor_server`;
/*!40000 ALTER TABLE `sys_monitor_server` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_monitor_server` ENABLE KEYS */;

-- 导出  表 db_efadmin1.sys_picture 结构
CREATE TABLE IF NOT EXISTS `sys_picture` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `delete_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '删除的URL',
  `filename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '图片名称',
  `height` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '图片高度',
  `size` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '图片大小',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '图片地址',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '用户名称',
  `width` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '图片宽度',
  `md5code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '文件的MD5值',
  `remark` varchar(1024) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上传日期',
  `creator_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs ROW_FORMAT=COMPACT COMMENT='Sm.Ms图床';

-- 正在导出表  db_efadmin1.sys_picture 的数据：~0 rows (大约)
DELETE FROM `sys_picture`;
/*!40000 ALTER TABLE `sys_picture` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_picture` ENABLE KEYS */;

-- 导出  表 db_efadmin1.sys_qiniu_config 结构
CREATE TABLE IF NOT EXISTS `sys_qiniu_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `access_key` text CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs COMMENT 'accessKey',
  `bucket` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT 'Bucket 识别符',
  `host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL COMMENT '外链域名',
  `secret_key` text CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs COMMENT 'secretKey',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '空间类型',
  `zone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '机房',
  `remark` varchar(1024) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs ROW_FORMAT=COMPACT COMMENT='七牛云配置';

-- 正在导出表  db_efadmin1.sys_qiniu_config 的数据：~0 rows (大约)
DELETE FROM `sys_qiniu_config`;
/*!40000 ALTER TABLE `sys_qiniu_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_qiniu_config` ENABLE KEYS */;

-- 导出  表 db_efadmin1.sys_qiniu_content 结构
CREATE TABLE IF NOT EXISTS `sys_qiniu_content` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `bucket` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT 'Bucket 识别符',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '文件名称',
  `size` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '文件大小',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '文件类型：私有或公开',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '文件url',
  `suffix` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL,
  `remark` varchar(1024) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上传或同步的时间',
  `updater_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs ROW_FORMAT=COMPACT COMMENT='七牛云文件存储';

-- 正在导出表  db_efadmin1.sys_qiniu_content 的数据：~0 rows (大约)
DELETE FROM `sys_qiniu_content`;
/*!40000 ALTER TABLE `sys_qiniu_content` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_qiniu_content` ENABLE KEYS */;

-- 导出  表 db_efadmin1.sys_quartz_job 结构
CREATE TABLE IF NOT EXISTS `sys_quartz_job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `bean_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT 'Spring Bean名称',
  `cron_expression` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT 'cron 表达式',
  `is_pause` bit(1) DEFAULT NULL COMMENT '状态：1暂停、0启用',
  `job_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '任务名称',
  `method_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '方法名称',
  `params` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '参数',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `creator_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs ROW_FORMAT=COMPACT COMMENT='定时任务';

-- 正在导出表  db_efadmin1.sys_quartz_job 的数据：~3 rows (大约)
DELETE FROM `sys_quartz_job`;
/*!40000 ALTER TABLE `sys_quartz_job` DISABLE KEYS */;
INSERT INTO `sys_quartz_job` (`id`, `bean_name`, `cron_expression`, `is_pause`, `job_name`, `method_name`, `params`, `remark`, `create_time`, `creator_num`, `update_time`, `updater_num`) VALUES
	(1, 'visitsTask', '0 0 0 * * ?', b'0', '更新访客记录', 'run', NULL, '每日0点创建新的访客记录', '2019-01-08 14:53:31', NULL, '2019-12-26 22:01:49', NULL),
	(2, 'testTask', '0/5 * * * * ?', b'1', '测试1', 'run1', 'test', '带参测试，多参使用json', '2019-08-22 14:08:29', NULL, '2020-03-11 08:59:58', 'admin');
/*!40000 ALTER TABLE `sys_quartz_job` ENABLE KEYS */;

-- 导出  表 db_efadmin1.sys_quartz_log 结构
CREATE TABLE IF NOT EXISTS `sys_quartz_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `baen_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL,
  `cron_expression` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL,
  `exception_detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs,
  `is_success` bit(1) DEFAULT NULL,
  `job_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL,
  `method_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL,
  `params` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL,
  `time` bigint(20) DEFAULT NULL,
  `remark` varchar(1024) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '备注',
  `creator_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs ROW_FORMAT=COMPACT COMMENT='定时任务日志';

-- 正在导出表  db_efadmin1.sys_quartz_log 的数据：~72 rows (大约)
DELETE FROM `sys_quartz_log`;
/*!40000 ALTER TABLE `sys_quartz_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_quartz_log` ENABLE KEYS */;

-- 导出  表 db_efadmin1.sys_role 结构
CREATE TABLE IF NOT EXISTS `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL COMMENT '名称',
  `data_scope` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '数据权限',
  `level` int(255) DEFAULT NULL COMMENT '角色级别',
  `permission` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '功能权限',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `creator_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs ROW_FORMAT=COMPACT COMMENT='角色表';

-- 正在导出表  db_efadmin1.sys_role 的数据：~2 rows (大约)
DELETE FROM `sys_role`;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` (`id`, `name`, `data_scope`, `level`, `permission`, `remark`, `create_time`, `creator_num`, `update_time`, `updater_num`) VALUES
	(1, '超级管理员', '全部', 1, 'admin', '-', '2018-11-23 11:04:37', NULL, '2020-03-13 10:33:35', 'admin'),
	(2, '普通用户', '本级', 2, 'common', '-', '2018-11-23 13:09:06', NULL, '2019-12-26 22:01:50', NULL),
	(3, '测试', '自定义', 3, 'test', NULL, '2020-03-15 16:55:42', NULL, '2020-03-15 17:23:10', 'admin');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;

-- 导出  表 db_efadmin1.sys_roles_depts 结构
CREATE TABLE IF NOT EXISTS `sys_roles_depts` (
  `role_id` bigint(20) NOT NULL,
  `dept_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`dept_id`) USING BTREE,
  KEY `FK7qg6itn5ajdoa9h9o78v9ksur` (`dept_id`) USING BTREE,
  CONSTRAINT `FK7qg6itn5ajdoa9h9o78v9ksur` FOREIGN KEY (`dept_id`) REFERENCES `sys_dept` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKrg1ci4cxxfbja0sb0pddju7k` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs ROW_FORMAT=COMPACT COMMENT='角色部门关联';

-- 正在导出表  db_efadmin1.sys_roles_depts 的数据：~0 rows (大约)
DELETE FROM `sys_roles_depts`;
/*!40000 ALTER TABLE `sys_roles_depts` DISABLE KEYS */;
INSERT INTO `sys_roles_depts` (`role_id`, `dept_id`) VALUES
	(3, 1);
/*!40000 ALTER TABLE `sys_roles_depts` ENABLE KEYS */;

-- 导出  表 db_efadmin1.sys_roles_menus 结构
CREATE TABLE IF NOT EXISTS `sys_roles_menus` (
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`menu_id`,`role_id`) USING BTREE,
  KEY `FKcngg2qadojhi3a651a5adkvbq` (`role_id`) USING BTREE,
  CONSTRAINT `FKo7wsmlrrxb2osfaoavp46rv2r` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKtag324maketmxffly3pdyh193` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs ROW_FORMAT=COMPACT COMMENT='角色菜单关联';

-- 正在导出表  db_efadmin1.sys_roles_menus 的数据：~101 rows (大约)
DELETE FROM `sys_roles_menus`;
/*!40000 ALTER TABLE `sys_roles_menus` DISABLE KEYS */;
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES
	(1, 1),
	(2, 1),
	(3, 1),
	(5, 1),
	(6, 1),
	(7, 1),
	(9, 1),
	(10, 1),
	(11, 1),
	(14, 1),
	(15, 1),
	(16, 1),
	(18, 1),
	(19, 1),
	(21, 1),
	(22, 1),
	(23, 1),
	(24, 1),
	(27, 1),
	(28, 1),
	(30, 1),
	(32, 1),
	(33, 1),
	(34, 1),
	(35, 1),
	(36, 1),
	(37, 1),
	(38, 1),
	(39, 1),
	(41, 1),
	(44, 1),
	(45, 1),
	(46, 1),
	(48, 1),
	(49, 1),
	(50, 1),
	(52, 1),
	(53, 1),
	(54, 1),
	(56, 1),
	(57, 1),
	(58, 1),
	(60, 1),
	(61, 1),
	(62, 1),
	(64, 1),
	(65, 1),
	(66, 1),
	(73, 1),
	(74, 1),
	(75, 1),
	(77, 1),
	(78, 1),
	(79, 1),
	(80, 1),
	(82, 1),
	(83, 1),
	(90, 1),
	(92, 1),
	(93, 1),
	(94, 1),
	(97, 1),
	(98, 1),
	(116, 1),
	(117, 1),
	(118, 1),
	(119, 1),
	(120, 1),
	(121, 1),
	(1, 2),
	(2, 2),
	(3, 2),
	(5, 2),
	(6, 2),
	(9, 2),
	(10, 2),
	(11, 2),
	(14, 2),
	(15, 2),
	(18, 2),
	(19, 2),
	(21, 2),
	(23, 2),
	(24, 2),
	(27, 2),
	(28, 2),
	(30, 2),
	(33, 2),
	(34, 2),
	(35, 2),
	(36, 2),
	(37, 2),
	(38, 2),
	(39, 2),
	(44, 2),
	(48, 2),
	(49, 2),
	(50, 2),
	(77, 2),
	(78, 2),
	(79, 2),
	(1, 3);
/*!40000 ALTER TABLE `sys_roles_menus` ENABLE KEYS */;

-- 导出  表 db_efadmin1.sys_user 结构
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `avatar_id` bigint(20) DEFAULT NULL COMMENT '头像',
  `email` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '邮箱',
  `enabled` bigint(20) DEFAULT NULL COMMENT '状态：1启用、0禁用',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '密码',
  `username` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '用户名',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门名称',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '手机号码',
  `job_id` bigint(20) DEFAULT NULL COMMENT '岗位名称',
  `last_password_reset_time` datetime DEFAULT NULL COMMENT '最后修改密码的日期',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '昵称',
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '性别',
  `usercode` varchar(180) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '用户编号或者工号',
  `remark` varchar(1024) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `creator_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UK_kpubos9gc2cvtkb0thktkbkes` (`email`) USING BTREE,
  UNIQUE KEY `username` (`username`) USING BTREE,
  UNIQUE KEY `UK_ag19a4erqrlqvx40lk5j1aoll` (`usercode`),
  KEY `FK5rwmryny6jthaaxkogownknqp` (`dept_id`) USING BTREE,
  KEY `FKfftoc2abhot8f2wu6cl9a5iky` (`job_id`) USING BTREE,
  KEY `FKpq2dhypk2qgt68nauh2by22jb` (`avatar_id`) USING BTREE,
  CONSTRAINT `FK5rwmryny6jthaaxkogownknqp` FOREIGN KEY (`dept_id`) REFERENCES `sys_dept` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKfftoc2abhot8f2wu6cl9a5iky` FOREIGN KEY (`job_id`) REFERENCES `sys_job` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKpq2dhypk2qgt68nauh2by22jb` FOREIGN KEY (`avatar_id`) REFERENCES `sys_user_avatar` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs ROW_FORMAT=COMPACT COMMENT='系统用户';

-- 正在导出表  db_efadmin1.sys_user 的数据：~3 rows (大约)
DELETE FROM `sys_user`;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` (`id`, `avatar_id`, `email`, `enabled`, `password`, `username`, `dept_id`, `phone`, `job_id`, `last_password_reset_time`, `nick_name`, `sex`, `usercode`, `remark`, `create_time`, `creator_num`, `update_time`, `updater_num`) VALUES
	(1, 2, 'zhengjie@tom.com', 1, '$2a$10$fP.426qKaTmix50Oln8L.uav55gELhAd0Eg66Av4oG86u8km7D/Ky', 'admin1', 2, '138****9027', 11, '2019-05-18 17:34:21', '管理员', '男', NULL, NULL, '2018-08-23 09:11:56', NULL, '2020-04-28 10:56:37', 'admin'),
	(3, NULL, 'test@eladmin.net', 1, NULL, 'Test', 2, '17777777777', 10, '2019-04-01 09:15:24', '测试', '男', NULL, NULL, '2018-12-27 20:05:26', NULL, '2020-03-15 08:52:11', 'admin'),
	(4, NULL, 'dusuanyun@sina.com', 0, NULL, 'dbdu', 2, '138****9027', 10, '2019-04-01 09:15:24', 'suanyun', '男', NULL, NULL, '2019-12-27 21:31:37', NULL, '2020-03-14 10:31:11', 'admin');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;

-- 导出  表 db_efadmin1.sys_users_roles 结构
CREATE TABLE IF NOT EXISTS `sys_users_roles` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`) USING BTREE,
  KEY `FKq4eq273l04bpu4efj0jd0jb98` (`role_id`) USING BTREE,
  CONSTRAINT `FKgd3iendaoyh04b95ykqise6qh` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKt4v0rrweyk393bdgt107vdx0x` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs ROW_FORMAT=COMPACT COMMENT='用户角色关联';

-- 正在导出表  db_efadmin1.sys_users_roles 的数据：~3 rows (大约)
DELETE FROM `sys_users_roles`;
/*!40000 ALTER TABLE `sys_users_roles` DISABLE KEYS */;
INSERT INTO `sys_users_roles` (`user_id`, `role_id`) VALUES
	(1, 1),
	(3, 2),
	(4, 2);
/*!40000 ALTER TABLE `sys_users_roles` ENABLE KEYS */;

-- 导出  表 db_efadmin1.sys_user_avatar 结构
CREATE TABLE IF NOT EXISTS `sys_user_avatar` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '真实文件名',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '路径',
  `size` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '大小',
  `remark` varchar(1024) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs ROW_FORMAT=COMPACT COMMENT='系统用户头像';

-- 正在导出表  db_efadmin1.sys_user_avatar 的数据：~0 rows (大约)
DELETE FROM `sys_user_avatar`;
/*!40000 ALTER TABLE `sys_user_avatar` DISABLE KEYS */;
INSERT INTO `sys_user_avatar` (`id`, `real_name`, `path`, `size`, `remark`, `create_time`, `creator_num`, `update_time`, `updater_num`) VALUES
	(2, '管理者素养-20200128054216530.png', '/media/dbdu/BK/repository/GitHub/vip-factory/efadmin/~/eladmin/avatar/管理者素养-20200128054216530.png', '64.69KB   ', NULL, '2020-01-28 17:42:17', 'admin', '2020-01-28 17:42:17', 'admin');
/*!40000 ALTER TABLE `sys_user_avatar` ENABLE KEYS */;

-- 导出  表 db_efadmin1.sys_verification_code 结构
CREATE TABLE IF NOT EXISTS `sys_verification_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '验证码',
  `status` bit(1) DEFAULT NULL COMMENT '状态：1有效、0过期',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '验证码类型：email或者短信',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '接收邮箱或者手机号码',
  `scenes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '业务名称：如重置邮箱、重置密码等',
  `remark` varchar(1024) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `creator_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs ROW_FORMAT=COMPACT COMMENT='验证码';

-- 正在导出表  db_efadmin1.sys_verification_code 的数据：~0 rows (大约)
DELETE FROM `sys_verification_code`;
/*!40000 ALTER TABLE `sys_verification_code` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_verification_code` ENABLE KEYS */;

-- 导出  表 db_efadmin1.sys_visits 结构
CREATE TABLE IF NOT EXISTS `sys_visits` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL,
  `ip_counts` bigint(20) DEFAULT NULL,
  `pv_counts` bigint(20) DEFAULT NULL,
  `week_day` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL,
  `remark` varchar(1024) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater_num` varchar(32) COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UK_11aksgq87euk9bcyeesfs4vtp` (`date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs ROW_FORMAT=COMPACT COMMENT='访客记录';

-- 正在导出表  db_efadmin1.sys_visits 的数据：~8 rows (大约)
DELETE FROM `sys_visits`;
/*!40000 ALTER TABLE `sys_visits` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_visits` ENABLE KEYS */;

-- 导出  表 db_efadmin1.tbl_employee 结构
CREATE TABLE IF NOT EXISTS `tbl_employee` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL COMMENT '姓名',
  `sex` tinyint(4) DEFAULT NULL COMMENT '性别',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '住址',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '头像',
  `birthday` datetime(6) DEFAULT NULL COMMENT '生日',
  `code` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL COMMENT '编码',
  `email` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL COMMENT '邮箱',
  `id_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '身份证号码',
  `phone` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs NOT NULL COMMENT '手机号',
  `status` int(11) NOT NULL COMMENT '状态',
  `dept_id` bigint(20) NOT NULL COMMENT '部门',
  `job_id` bigint(20) NOT NULL COMMENT '岗位',
  `remark` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_num` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater_num` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6cagg3s9ixuixmw6o9ixumpde` (`code`),
  UNIQUE KEY `UK_mgxwx0gcqd00m8n2gt0xujijr` (`email`),
  UNIQUE KEY `UK_mfajd2wgyktyqp0xpy72sn8jq` (`phone`),
  KEY `FKdqery4ustrhu158a70r8jg802` (`dept_id`),
  KEY `FKkkanek0iwqokx6xa2wxoipe2t` (`job_id`),
  CONSTRAINT `FKdqery4ustrhu158a70r8jg802` FOREIGN KEY (`dept_id`) REFERENCES `sys_dept` (`id`),
  CONSTRAINT `FKkkanek0iwqokx6xa2wxoipe2t` FOREIGN KEY (`job_id`) REFERENCES `sys_job` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs COMMENT='员工信息';

-- 正在导出表  db_efadmin1.tbl_employee 的数据：~1 rows (大约)
DELETE FROM `tbl_employee`;
/*!40000 ALTER TABLE `tbl_employee` DISABLE KEYS */;
INSERT INTO `tbl_employee` (`id`, `name`, `sex`, `address`, `avatar`, `birthday`, `code`, `email`, `id_number`, `phone`, `status`, `dept_id`, `job_id`, `remark`, `create_time`, `creator_num`, `update_time`, `updater_num`) VALUES
	(3, '张三', NULL, '安徽省芜湖市', 'http://image.efactory.vip/icon.jpg', '2020-04-04 08:00:00.000000', 'YZ202004078', 'zhangsan@test.com', '342225198510155698', '13901010202', 1, 2, 10, '测试数据', '2020-04-25 17:13:29', 'admin', '2020-04-25 17:13:29', 'admin');
/*!40000 ALTER TABLE `tbl_employee` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
