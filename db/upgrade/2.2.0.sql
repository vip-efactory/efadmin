-- 更新脚本的意义在于，基于上一个版本的数据库表结构及数据，通过执行更新脚本可以达到最新的状态，这个最新状态和全新安装的状态是一致的！ --

-- -------------------------------- 对管理租户数据库进行以下操作：-----------------------
USE `db_ef_tenants`;
ALTER TABLE `sys_menu`
    AUTO_INCREMENT=127;
-- ---------------------------------
-- 说明: 增加百度的富文本编辑器Ueditor的菜单及权限关联
-- 作者: dbdu
-- 日期: 2020-06-21
-- ---------------------------------
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` (`id`, `i_frame`, `name`, `component`, `pid`, `sort`, `icon`, `path`, `cache`, `hidden`, `component_name`, `permission`, `type`, `locale_key`, `remark`, `create_time`, `creator_num`, `update_time`, `updater_num`) VALUES
(126, b'0', 'Ueditor富文本', 'components/Ueditor', 10, 55, 'fwb', 'ueditor', b'0', b'0', 'Ueditor', NULL, 1, 'ueditor.text', NULL, '2020-06-14 19:20:46', 'admin1', '2020-06-21 07:32:43', 'admin1');
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;

/*!40000 ALTER TABLE `sys_roles_menus` DISABLE KEYS */;
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES
(126, 1);
/*!40000 ALTER TABLE `sys_roles_menus` DISABLE KEYS */;


-- -------------------------------- 对租户1数据库进行以下操作：-----------------------
USE `db_efadmin1`;
ALTER TABLE `sys_menu`
    AUTO_INCREMENT=123;
-- ---------------------------------
-- 说明: 增加百度的富文本编辑器Ueditor的菜单及权限关联
-- 作者: dbdu
-- 日期: 2020-06-21
-- ---------------------------------
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` (`id`, `i_frame`, `name`, `component`, `pid`, `sort`, `icon`, `path`, `cache`, `hidden`, `component_name`, `permission`, `type`, `locale_key`, `remark`, `create_time`, `creator_num`, `update_time`, `updater_num`) VALUES
(122, b'0', 'Ueditor富文本', 'components/Ueditor', 10, 55, 'fwb', 'ueditor', b'0', b'0', 'Ueditor', NULL, 1, 'ueditor.text', NULL, '2020-06-14 19:20:46', 'admin1', '2020-06-21 07:32:43', 'admin1');
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;

/*!40000 ALTER TABLE `sys_roles_menus` DISABLE KEYS */;
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES
(122, 1);
/*!40000 ALTER TABLE `sys_roles_menus` DISABLE KEYS */;


-- -------------------------------- 对租户2数据库进行以下操作：-----------------------
USE `db_efadmin2`;
ALTER TABLE `sys_menu`
    AUTO_INCREMENT=123;
-- ---------------------------------
-- 说明: 增加百度的富文本编辑器Ueditor的菜单及权限关联
-- 作者: dbdu
-- 日期: 2020-06-21
-- ---------------------------------
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` (`id`, `i_frame`, `name`, `component`, `pid`, `sort`, `icon`, `path`, `cache`, `hidden`, `component_name`, `permission`, `type`, `locale_key`, `remark`, `create_time`, `creator_num`, `update_time`, `updater_num`) VALUES
(122, b'0', 'Ueditor富文本', 'components/Ueditor', 10, 55, 'fwb', 'ueditor', b'0', b'0', 'Ueditor', NULL, 1, 'ueditor.text', NULL, '2020-06-14 19:20:46', 'admin1', '2020-06-21 07:32:43', 'admin1');
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;

/*!40000 ALTER TABLE `sys_roles_menus` DISABLE KEYS */;
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES
(122, 1);
/*!40000 ALTER TABLE `sys_roles_menus` DISABLE KEYS */;
