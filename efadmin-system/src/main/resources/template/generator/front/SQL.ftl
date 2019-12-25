-- 模板不可以直接执行!!! 后面可以写成存储程序,一键执行即可 ---

-- 权限模板 --
INSERT INTO `sys_permission` ( `alias`, `name`, `pid`, `remark`) VALUES
('${tableRemark}管理', '${upperCaseClassName}_ALL', 上级菜单ID,  'System Gen'),
('${tableRemark}查询', '${upperCaseClassName}_SELECT', 父菜单ID,  'System Gen'),
('${tableRemark}创建', '${upperCaseClassName}_CREATE', 父菜单ID,  'System Gen'),
('${tableRemark}编辑', '${upperCaseClassName}_EDIT', 父菜单ID,  'System Gen'),
('${tableRemark}删除', '${upperCaseClassName}_DELETE', 父菜单ID,  'System Gen');

-- 菜单模板 --
INSERT INTO `sys_menu` (`i_frame`, `name`, `component`, `pid`, `sort`, `icon`, `path`, `remark`) VALUES
(b'0', '${tableRemark}管理', '${moduleName}/${changeClassName}/index', 上级菜单ID, 菜单排序号, '${changeClassName}', '${changeClassName}', 'System Gen');
