
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
  `jdbc_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT 'JDBC链接',
  `status` int(11) DEFAULT '0' COMMENT '租户状态,-1禁用；0正常等',
  `remark` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator_num` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater_num` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_zh_0900_as_cs COMMENT='系统租户表';
