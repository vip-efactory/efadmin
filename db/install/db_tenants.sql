-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        8.0.17 - MySQL Community Server - GPL
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  10.3.0.5771
-- --------------------------------------------------------

-- 导出 db_ef_tenants 的数据库结构
CREATE DATABASE IF NOT EXISTS `db_ef_tenants` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs */ /*!80016 DEFAULT ENCRYPTION='N' */;

-- 所有租户的数据源所在的库
USE `db_ef_tenants`;

-- --------------------------------------------------------
-- 描述: 新增租户管理组件表，仅默认租户数据源需要，其他租户不需要！
-- 日期: 2020-04-10
-- 作者: vip-efactory
-- --------------------------------------------------------
-- 导出  表 db_efadmin.sys_tenant 结构
CREATE TABLE IF NOT EXISTS `sys_tenant` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'pk',
  `tenant_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '租户名称',
  `tenant_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '租户编码',
  `db_username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT 'DB用户名',
  `db_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT 'DB密码',
  `driver_class_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '驱动类名',
  `jdbc_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT 'jdbc链接',
  `status` int(11) DEFAULT '0' COMMENT '租户状态,-1禁用；0正常等',
  `remark` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_num` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater_num` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs COMMENT='系统租户表';

-- 插入租户数据源关键配置，如果此处数据库密码是加密的，则在租户数据源初始化的时候需要解密.
/*!40000 ALTER TABLE `sys_tenant` DISABLE KEYS */;
INSERT INTO `sys_tenant` (`id`, `tenant_name`, `tenant_code`, `db_username`, `db_password`, `driver_class_name`, `jdbc_url`, `status`, `remark`, `create_time`, `creator_num`, `update_time`, `updater_num`) VALUES
	(1, '租户1', 'TENANT1', 'root', '123456', 'net.sf.log4jdbc.sql.jdbcapi.DriverSpy', 'jdbc:log4jdbc:mysql://localhost:3306/db_efadmin1?serverTimezone=Asia/Shanghai&characterEncoding=utf8&useSSL=false', 0, NULL, '2020-04-12 16:57:29', NULL, '2020-04-12 16:58:10', NULL),
	(2, '租户2', 'TENANT2', 'root', '123456', 'net.sf.log4jdbc.sql.jdbcapi.DriverSpy', 'jdbc:log4jdbc:mysql://localhost:3306/db_efadmin2?serverTimezone=Asia/Shanghai&characterEncoding=utf8&useSSL=false', 0, NULL, '2020-04-12 16:57:29', NULL, '2020-04-12 16:58:31', NULL);
/*!40000 ALTER TABLE `sys_tenant` ENABLE KEYS */;
