
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

-- --------------------------------------------------------
-- 描述: 新增租户管理组件表的菜单，以及github外链菜单
-- 日期: 2020-04-12
-- 作者: vip-efactory
-- --------------------------------------------------------
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` (`id`, `i_frame`, `name`, `component`, `pid`, `sort`, `icon`, `path`, `cache`, `hidden`, `component_name`, `permission`, `type`, `locale_key`, `remark`, `create_time`, `creator_num`, `update_time`, `updater_num`) VALUES
	(121, b'1', 'Github', NULL, 1, 999, 'github', 'https://github.com/vip-efactory/efadmin-ui', b'1', b'0', NULL, NULL, 1, 'github.link', NULL, '2020-03-13 10:31:45', 'admin', '2020-04-12 08:16:32', 'admin'),
	(122, b'0', '系统租户管理', 'tenant/tenant/index', 1, 999, 'zujian', 'tenant', b'0', b'0', 'Tenant', 'tenant:list', 1, 'tenant.manage', NULL, '2020-04-11 10:27:45', NULL, '2020-04-12 07:38:57', NULL),
	(123, b'0', '系统租户新增', '', 122, 2, '', '', b'0', b'0', '', 'tenant:add', 2, 'tenant.add', NULL, '2020-04-11 10:27:45', NULL, '2020-04-12 07:39:18', NULL),
	(124, b'0', '系统租户编辑', '', 122, 3, '', '', b'0', b'0', '', 'tenant:edit', 2, 'tenant.edit', NULL, '2020-04-11 10:27:45', NULL, '2020-04-12 07:39:33', NULL),
	(125, b'0', '系统租户删除', '', 122, 4, '', '', b'0', b'0', '', 'tenant:del', 2, 'tenant.delete', NULL, '2020-04-11 10:27:45', NULL, '2020-04-12 07:49:15', NULL);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
/*!40000 ALTER TABLE `sys_roles_menus` DISABLE KEYS */;
-- 让管理员角色关联此权限
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES
	(121, 1),
	(122, 1),
	(123, 1),
	(124, 1),
	(125, 1),
/*!40000 ALTER TABLE `sys_roles_menus` ENABLE KEYS */;