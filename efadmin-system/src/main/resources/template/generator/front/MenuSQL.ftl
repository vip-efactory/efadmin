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
declare pId int;

-- 插入组件的管理菜单权限 --
INSERT INTO `sys_menu` (`i_frame`, `name`, `component`, `pid`, `sort`, `icon`, `path`, `cache`, `hidden`, `component_name`, `permission`, `type`) VALUES
(b'0', '${apiAlias}管理', '${moduleName}/${changeClassName}/index', 1, 999, 'zujian', '${changeClassName}', b'0', b'0', '${className}', '${changeClassName}:list', 1);

-- 选择出组件的管理菜单id，为按钮父id --
SELECT id INTO pId FROM `sys_menu` WHERE `name`='${apiAlias}管理' and `component_name`='${className}';

-- 插入常见的按钮的权限 --
INSERT INTO `sys_menu` (`i_frame`, `name`, `component`, `pid`, `sort`, `icon`, `path`, `cache`, `hidden`, `component_name`, `permission`, `type`) VALUES
(b'0', '${apiAlias}新增', '', pId, 2, '', '', b'0', b'0', '', '${changeClassName}:add', 2),
(b'0', '${apiAlias}编辑', '', pId, 3, '', '', b'0', b'0', '', '${changeClassName}:edit', 2),
(b'0', '${apiAlias}删除', '', pId, 4, '', '', b'0', b'0', '', '${changeClassName}:del', 2);

END //
-- 还原分隔符为分号 --
DELIMITER ;
-- 调用存储程序 --
CALL create_menu_job();
-- 删除存储程序 --
DROP PROCEDURE IF EXISTS create_menu_job;
