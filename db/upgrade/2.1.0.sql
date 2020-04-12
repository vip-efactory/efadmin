
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

-- 将db_efadmin更名为db_efadmin1
CREATE DATABASE `db_efadmin1` /*!40100 COLLATE 'utf8mb4_zh_0900_as_cs' */;
RENAME TABLE `db_efadmin`.`sys_alipay_config` TO `db_efadmin1`.`sys_alipay_config`, `db_efadmin`.`sys_column_config` TO `db_efadmin1`.`sys_column_config`, `db_efadmin`.`sys_dept` TO `db_efadmin1`.`sys_dept`, `db_efadmin`.`sys_dict` TO `db_efadmin1`.`sys_dict`, `db_efadmin`.`sys_dict_detail` TO `db_efadmin1`.`sys_dict_detail`, `db_efadmin`.`sys_email_config` TO `db_efadmin1`.`sys_email_config`, `db_efadmin`.`sys_gen_config` TO `db_efadmin1`.`sys_gen_config`, `db_efadmin`.`sys_gen_test` TO `db_efadmin1`.`sys_gen_test`, `db_efadmin`.`sys_job` TO `db_efadmin1`.`sys_job`, `db_efadmin`.`sys_local_storage` TO `db_efadmin1`.`sys_local_storage`, `db_efadmin`.`sys_log` TO `db_efadmin1`.`sys_log`, `db_efadmin`.`sys_menu` TO `db_efadmin1`.`sys_menu`, `db_efadmin`.`sys_mnt_app` TO `db_efadmin1`.`sys_mnt_app`, `db_efadmin`.`sys_mnt_database` TO `db_efadmin1`.`sys_mnt_database`, `db_efadmin`.`sys_mnt_deploy` TO `db_efadmin1`.`sys_mnt_deploy`, `db_efadmin`.`sys_mnt_deploy_history` TO `db_efadmin1`.`sys_mnt_deploy_history`, `db_efadmin`.`sys_mnt_deploy_server` TO `db_efadmin1`.`sys_mnt_deploy_server`, `db_efadmin`.`sys_mnt_server` TO `db_efadmin1`.`sys_mnt_server`, `db_efadmin`.`sys_monitor_server` TO `db_efadmin1`.`sys_monitor_server`, `db_efadmin`.`sys_picture` TO `db_efadmin1`.`sys_picture`, `db_efadmin`.`sys_qiniu_config` TO `db_efadmin1`.`sys_qiniu_config`, `db_efadmin`.`sys_qiniu_content` TO `db_efadmin1`.`sys_qiniu_content`, `db_efadmin`.`sys_quartz_job` TO `db_efadmin1`.`sys_quartz_job`, `db_efadmin`.`sys_quartz_log` TO `db_efadmin1`.`sys_quartz_log`, `db_efadmin`.`sys_role` TO `db_efadmin1`.`sys_role`, `db_efadmin`.`sys_roles_depts` TO `db_efadmin1`.`sys_roles_depts`, `db_efadmin`.`sys_roles_menus` TO `db_efadmin1`.`sys_roles_menus`, `db_efadmin`.`sys_tenant` TO `db_efadmin1`.`sys_tenant`, `db_efadmin`.`sys_user` TO `db_efadmin1`.`sys_user`, `db_efadmin`.`sys_users_roles` TO `db_efadmin1`.`sys_users_roles`, `db_efadmin`.`sys_user_avatar` TO `db_efadmin1`.`sys_user_avatar`, /* 大SQL查询 (2.1 KiB)，分割于 2,000 个字符 */
DROP DATABASE `db_efadmin`;

-- 请手动执行创建db_efadmin2.sql文件创建租户2的数据库及基础数据。