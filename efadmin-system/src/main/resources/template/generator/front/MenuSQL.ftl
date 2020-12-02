-- 作者： dbdu，日期：2020-0102
-- 这个存储程序是创建组件的权限的存储程序，
-- 组件的上级菜单暂时挂在系统管理下,方便执行后在UI就可以看到界面，后面要自己修改;
-- 组件排序默认999，后面要自己修改;
-- 组件的图标，使用默认组件的zujian图标，后面要自己修改;

-- 指定使用的数据库 --
USE `db_efadmin`;

-- 如果存在这个存储程序则删除 --
DROP PROCEDURE IF EXISTS create_menu_job;
DELIMITER //
CREATE PROCEDURE create_menu_job()
BEGIN
-- 定义接收组件查询菜单的id变量
declare pId int;
-- 定义接收管理员角色的id变量
declare roleId int;
-- 定义接收三个按钮的id变量
declare btnListId int;
declare btnAddId int;
declare btnEditId int;
declare btnDelId int;

-- 插入组件的管理菜单权限 --
INSERT INTO `sys_menu` (`i_frame`, `name`, `component`, `pid`, `sort`, `icon`, `path`, `cache`, `hidden`, `component_name`, `permission`, `locale_key`,`type`) VALUES
(b'0', '${apiAlias}管理', '${path}/${changeClassName}/index', 1, 999, 'zujian', '${changeClassName}', b'0', b'0', '${className}', '', '${changeClassName}.manage', 1);

-- 选择出组件的管理菜单id，为按钮父id --
SELECT id INTO pId FROM `sys_menu` WHERE `name`='${apiAlias}管理' and `component_name`='${className}';

-- 插入常见的按钮的权限 --
INSERT INTO `sys_menu` (`i_frame`, `name`, `component`, `pid`, `sort`, `icon`, `path`, `cache`, `hidden`, `component_name`, `permission`, `locale_key`, `type`) VALUES
(b'0', '${apiAlias}查询', '', pId, 1, '', '', b'0', b'0', '', '${changeClassName}:list', '${changeClassName}.list', 2),
(b'0', '${apiAlias}新增', '', pId, 2, '', '', b'0', b'0', '', '${changeClassName}:add', '${changeClassName}.add', 2),
(b'0', '${apiAlias}编辑', '', pId, 3, '', '', b'0', b'0', '', '${changeClassName}:edit', '${changeClassName}.edit', 2),
(b'0', '${apiAlias}删除', '', pId, 4, '', '', b'0', b'0', '', '${changeClassName}:del', '${changeClassName}.delete', 2);

-- 将新增的权限菜单与admin管理员角色绑定 ---
-- 先查询出管理员角色的id，及按钮权限的id ---
SELECT id INTO roleId FROM `sys_role` WHERE `level`=1 and `permission`='admin';
SELECT id INTO btnListId  FROM `sys_menu` WHERE `name`='${apiAlias}查询' and `permission`='${changeClassName}:list';
SELECT id INTO btnAddId  FROM `sys_menu` WHERE `name`='${apiAlias}新增' and `permission`='${changeClassName}:add';
SELECT id INTO btnEditId FROM `sys_menu` WHERE `name`='${apiAlias}编辑' and `permission`='${changeClassName}:edit';
SELECT id INTO btnDelId  FROM `sys_menu` WHERE `name`='${apiAlias}删除' and `permission`='${changeClassName}:del';

-- 插入关联的数据
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`) VALUES
(pId, roleId),
(btnListId, roleId),
(btnAddId, roleId),
(btnEditId, roleId),
(btnDelId, roleId);

END //
-- 还原分隔符为分号 --
DELIMITER ;
-- 调用存储程序 --
CALL create_menu_job();
-- 删除存储程序 --
DROP PROCEDURE IF EXISTS create_menu_job;
