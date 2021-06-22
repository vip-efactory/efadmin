
-- ---------------------------------
-- 说明: 部门表增加排序字段，三个租户库都要更新
-- 作者: dbdu
-- 日期: 2021-06-22
-- ---------------------------------
ALTER TABLE `sys_dept`
    ADD COLUMN `sort` INT NOT NULL DEFAULT 0 COMMENT '显示排序' AFTER `enabled`;
