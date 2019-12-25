-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        8.0.16 - MySQL Community Server - GPL
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  10.1.0.5464
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 db_efadmin 的数据库结构
DROP DATABASE IF EXISTS `db_efadmin`;
CREATE DATABASE IF NOT EXISTS `db_efadmin` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_zh_0900_as_cs */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `db_efadmin`;

-- 导出  表 db_efadmin.sys_alipay_config 结构
DROP TABLE IF EXISTS `sys_alipay_config`;
CREATE TABLE IF NOT EXISTS `sys_alipay_config`
(
    `id`                      bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `app_id`                  varchar(255)  DEFAULT NULL COMMENT '应用ID',
    `charset`                 varchar(255)  DEFAULT NULL COMMENT '编码',
    `format`                  varchar(255)  DEFAULT NULL COMMENT '类型 固定格式json',
    `gateway_url`             varchar(255)  DEFAULT NULL COMMENT '网关地址',
    `notify_url`              varchar(255)  DEFAULT NULL COMMENT '异步回调',
    `private_key`             text COMMENT '私钥',
    `public_key`              text COMMENT '公钥',
    `return_url`              varchar(255)  DEFAULT NULL COMMENT '回调地址',
    `sign_type`               varchar(255)  DEFAULT NULL COMMENT '签名方式',
    `sys_service_provider_id` varchar(255)  DEFAULT NULL COMMENT '商户号',
    `remark`                  varchar(1024) DEFAULT NULL COMMENT '备注',
    `create_time`             datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `creator_num`             varchar(32)   DEFAULT NULL COMMENT '创建人',
    `update_time`             datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `updater_num`             varchar(32)   DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='阿里支付配置';

-- 正在导出表  db_efadmin.sys_alipay_config 的数据：~0 rows (大约)
DELETE
FROM `sys_alipay_config`;
/*!40000 ALTER TABLE `sys_alipay_config`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_alipay_config`
    ENABLE KEYS */;

-- 导出  表 db_efadmin.sys_dept 结构
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE IF NOT EXISTS `sys_dept`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`        varchar(255) NOT NULL COMMENT '名称',
    `pid`         bigint(20)   NOT NULL COMMENT '上级部门',
    `enabled`     bit(1)       NOT NULL COMMENT '是否启用',
    `remark`      varchar(1024) DEFAULT NULL COMMENT '备注',
    `create_time` datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `creator_num` varchar(32)   DEFAULT NULL COMMENT '创建人',
    `update_time` datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `updater_num` varchar(32)   DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 13
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='部门';

-- 正在导出表  db_efadmin.sys_dept 的数据：~10 rows (大约)
DELETE
FROM `sys_dept`;
/*!40000 ALTER TABLE `sys_dept`
    DISABLE KEYS */;
INSERT INTO `sys_dept` (`id`, `name`, `pid`, `enabled`, `remark`, `create_time`, `creator_num`, `update_time`,
                        `updater_num`)
VALUES (1, 'efadmin', 0, b'1', NULL, '2019-03-25 09:14:05', NULL, '2019-07-11 21:38:59', NULL),
       (2, '研发部', 7, b'1', NULL, '2019-03-25 09:15:32', NULL, '2019-07-11 21:38:59', NULL),
       (5, '运维部', 7, b'1', NULL, '2019-03-25 09:20:44', NULL, '2019-07-11 21:38:59', NULL),
       (6, '测试部', 8, b'1', NULL, '2019-03-25 09:52:18', NULL, '2019-07-11 21:38:59', NULL),
       (7, '华南分部', 1, b'1', NULL, '2019-03-25 11:04:50', NULL, '2019-07-11 21:38:59', NULL),
       (8, '华北分部', 1, b'1', NULL, '2019-03-25 11:04:53', NULL, '2019-07-11 21:38:59', NULL),
       (9, '财务部', 7, b'1', NULL, '2019-03-25 11:05:34', NULL, '2019-07-11 21:38:59', NULL),
       (10, '行政部', 8, b'1', NULL, '2019-03-25 11:05:58', NULL, '2019-07-11 21:38:59', NULL),
       (11, '人事部', 8, b'1', NULL, '2019-03-25 11:07:58', NULL, '2019-07-11 21:38:59', NULL),
       (12, '市场部', 7, b'0', NULL, '2019-03-25 11:10:24', NULL, '2019-07-11 21:38:59', NULL);
/*!40000 ALTER TABLE `sys_dept`
    ENABLE KEYS */;

-- 导出  表 db_efadmin.sys_dict 结构
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE IF NOT EXISTS `sys_dict`
(
    `id`          bigint(11)   NOT NULL AUTO_INCREMENT,
    `name`        varchar(255) NOT NULL COMMENT '字典名称',
    `remark`      varchar(255) DEFAULT NULL COMMENT '描述',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `creator_num` varchar(32)  DEFAULT NULL COMMENT '创建人',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `updater_num` varchar(32)  DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='系统字典,仅系统模块使用,可以有业务自己的字典';

-- 正在导出表  db_efadmin.sys_dict 的数据：~3 rows (大约)
DELETE
FROM `sys_dict`;
/*!40000 ALTER TABLE `sys_dict`
    DISABLE KEYS */;
INSERT INTO `sys_dict` (`id`, `name`, `remark`, `create_time`, `creator_num`, `update_time`, `updater_num`)
VALUES (1, 'user_status', '用户状态', '2019-07-11 21:38:59', NULL, '2019-07-11 21:38:59', NULL),
       (4, 'dept_status', '部门状态', '2019-07-11 21:38:59', NULL, '2019-07-11 21:38:59', NULL),
       (5, 'job_status', '岗位状态', '2019-07-11 21:38:59', NULL, '2019-07-11 21:38:59', NULL);
/*!40000 ALTER TABLE `sys_dict`
    ENABLE KEYS */;

-- 导出  表 db_efadmin.sys_dict_detail 结构
DROP TABLE IF EXISTS `sys_dict_detail`;
CREATE TABLE IF NOT EXISTS `sys_dict_detail`
(
    `id`          bigint(11)   NOT NULL AUTO_INCREMENT,
    `label`       varchar(255) NOT NULL COMMENT '字典标签',
    `value`       varchar(255) NOT NULL COMMENT '字典值',
    `sort`        varchar(255)  DEFAULT NULL COMMENT '排序',
    `dict_id`     bigint(11)    DEFAULT NULL COMMENT '字典id',
    `remark`      varchar(1024) DEFAULT NULL COMMENT '备注',
    `create_time` datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `creator_num` varchar(32)   DEFAULT NULL COMMENT '创建人',
    `update_time` datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `updater_num` varchar(32)   DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`),
    KEY `FK5tpkputc6d9nboxojdbgnpmyb` (`dict_id`),
    CONSTRAINT `FK5tpkputc6d9nboxojdbgnpmyb` FOREIGN KEY (`dict_id`) REFERENCES `sys_dict` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 15
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='系统字典类型明细';

-- 正在导出表  db_efadmin.sys_dict_detail 的数据：~6 rows (大约)
DELETE
FROM `sys_dict_detail`;
/*!40000 ALTER TABLE `sys_dict_detail`
    DISABLE KEYS */;
INSERT INTO `sys_dict_detail` (`id`, `label`, `value`, `sort`, `dict_id`, `remark`, `create_time`, `creator_num`,
                               `update_time`, `updater_num`)
VALUES (1, '激活', 'true', '1', 1, NULL, '2019-07-11 21:38:59', NULL, '2019-07-11 21:39:00', NULL),
       (2, '锁定', 'false', '2', 1, NULL, '2019-07-11 21:38:59', NULL, '2019-07-11 21:39:00', NULL),
       (11, '正常', 'true', '1', 4, NULL, '2019-07-11 21:38:59', NULL, '2019-07-11 21:39:00', NULL),
       (12, '停用', 'false', '2', 4, NULL, '2019-07-11 21:38:59', NULL, '2019-07-11 21:39:00', NULL),
       (13, '正常', 'true', '1', 5, NULL, '2019-07-11 21:38:59', NULL, '2019-07-11 21:39:00', NULL),
       (14, '停用', 'false', '2', 5, NULL, '2019-07-11 21:38:59', NULL, '2019-07-11 21:39:00', NULL);
/*!40000 ALTER TABLE `sys_dict_detail`
    ENABLE KEYS */;

-- 导出  表 db_efadmin.sys_email_config 结构
DROP TABLE IF EXISTS `sys_email_config`;
CREATE TABLE IF NOT EXISTS `sys_email_config`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `from_user`   varchar(255)  DEFAULT NULL COMMENT '收件人',
    `host`        varchar(255)  DEFAULT NULL COMMENT '邮件服务器SMTP地址',
    `pass`        varchar(255)  DEFAULT NULL COMMENT '密码',
    `port`        varchar(255)  DEFAULT NULL COMMENT '端口',
    `user`        varchar(255)  DEFAULT NULL COMMENT '发件者用户名',
    `remark`      varchar(1024) DEFAULT NULL COMMENT '备注',
    `create_time` datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `creator_num` varchar(32)   DEFAULT NULL COMMENT '创建人',
    `update_time` datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `updater_num` varchar(32)   DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='邮件发送配置';

-- 正在导出表  db_efadmin.sys_email_config 的数据：~0 rows (大约)
DELETE
FROM `sys_email_config`;
/*!40000 ALTER TABLE `sys_email_config`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_email_config`
    ENABLE KEYS */;

-- 导出  表 db_efadmin.sys_gen_config 结构
DROP TABLE IF EXISTS `sys_gen_config`;
CREATE TABLE IF NOT EXISTS `sys_gen_config`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `author`      varchar(255)  DEFAULT NULL COMMENT '作者',
    `cover`       bit(1)        DEFAULT NULL COMMENT '是否覆盖',
    `module_name` varchar(255)  DEFAULT NULL COMMENT '模块名称',
    `pack`        varchar(255)  DEFAULT NULL COMMENT '至于哪个包下',
    `path`        varchar(255)  DEFAULT NULL COMMENT '前端代码生成的路径',
    `api_path`    varchar(255)  DEFAULT NULL COMMENT '接口路径',
    `prefix`      varchar(255)  DEFAULT NULL COMMENT '前缀',
    `remark`      varchar(1024) DEFAULT NULL COMMENT '备注',
    `create_time` datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `creator_num` varchar(32)   DEFAULT NULL COMMENT '创建人',
    `update_time` datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `updater_num` varchar(32)   DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='代码生成配置';

-- 正在导出表  db_efadmin.sys_gen_config 的数据：~1 rows (大约)
DELETE
FROM `sys_gen_config`;
/*!40000 ALTER TABLE `sys_gen_config`
    DISABLE KEYS */;
INSERT INTO `sys_gen_config` (`id`, `author`, `cover`, `module_name`, `pack`, `path`, `api_path`, `prefix`, `remark`,
                              `create_time`, `creator_num`, `update_time`, `updater_num`)
VALUES (1, 'dbdu', b'0', 'efadmin-system', 'vip.efactory.modules.test',
        'E:\\workspace\\me\\front\\efadmin-qt\\src\\views\\test', 'E:\\workspace\\me\\front\\efadmin-qt\\src\\api',
        'sys_', NULL, '2019-07-11 16:08:29', NULL, '2019-07-11 16:08:29', NULL);
/*!40000 ALTER TABLE `sys_gen_config`
    ENABLE KEYS */;

-- 导出  表 db_efadmin.sys_job 结构
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE IF NOT EXISTS `sys_job`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT,
    `name`        varchar(255) NOT NULL COMMENT '岗位名称',
    `enabled`     bit(1)       NOT NULL COMMENT '是否启用',
    `sort`        bigint(20)   NOT NULL COMMENT '排序',
    `dept_id`     bigint(20)    DEFAULT NULL COMMENT '关联部门',
    `remark`      varchar(1024) DEFAULT NULL COMMENT '备注',
    `create_time` datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `creator_num` varchar(32)   DEFAULT NULL COMMENT '创建人',
    `update_time` datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `updater_num` varchar(32)   DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`),
    KEY `FKmvhj0rogastlctflsxf1d6k3i` (`dept_id`),
    CONSTRAINT `FKmvhj0rogastlctflsxf1d6k3i` FOREIGN KEY (`dept_id`) REFERENCES `sys_dept` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 21
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='岗位管理';

-- 正在导出表  db_efadmin.sys_job 的数据：~7 rows (大约)
DELETE
FROM `sys_job`;
/*!40000 ALTER TABLE `sys_job`
    DISABLE KEYS */;
INSERT INTO `sys_job` (`id`, `name`, `enabled`, `sort`, `dept_id`, `remark`, `create_time`, `creator_num`,
                       `update_time`, `updater_num`)
VALUES (2, '董事长秘书', b'1', 2, 1, NULL, '2019-03-29 14:01:30', NULL, '2019-07-11 21:39:00', NULL),
       (8, '人事专员', b'1', 3, 11, NULL, '2019-03-29 14:52:28', NULL, '2019-07-11 21:39:00', NULL),
       (10, '产品经理', b'0', 4, 2, NULL, '2019-03-29 14:55:51', NULL, '2019-07-11 21:39:00', NULL),
       (11, '全栈开发', b'1', 6, 2, NULL, '2019-03-31 13:39:30', NULL, '2019-07-11 21:39:00', NULL),
       (12, '软件测试', b'1', 5, 2, NULL, '2019-03-31 13:39:43', NULL, '2019-07-11 21:39:00', NULL),
       (19, '董事长', b'1', 1, 1, NULL, '2019-03-31 14:58:15', NULL, '2019-07-11 21:39:00', NULL),
       (20, '软件工程师', b'1', 999, 2, NULL, '2019-07-14 06:30:31', NULL, '2019-07-14 06:30:31', NULL);
/*!40000 ALTER TABLE `sys_job`
    ENABLE KEYS */;

-- 导出  表 db_efadmin.sys_log 结构
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE IF NOT EXISTS `sys_log`
(
    `id`               bigint(20) NOT NULL AUTO_INCREMENT,
    `description`      varchar(255)  DEFAULT NULL COMMENT '描述',
    `exception_detail` text COMMENT '异常明细',
    `log_type`         varchar(255)  DEFAULT NULL COMMENT '日志类型',
    `method`           varchar(255)  DEFAULT NULL COMMENT '方法',
    `params`           text COMMENT '参数',
    `request_ip`       varchar(255)  DEFAULT NULL COMMENT '请求IP',
    `time`             bigint(20)    DEFAULT NULL COMMENT '耗时',
    `username`         varchar(255)  DEFAULT NULL COMMENT '用户名',
    `remark`           varchar(1024) DEFAULT NULL COMMENT '备注',
    `create_time`      datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `creator_num`      varchar(32)   DEFAULT NULL COMMENT '创建人',
    `update_time`      datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `updater_num`      varchar(32)   DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='系统访问日志';

-- 正在导出表  db_efadmin.sys_log 的数据：~122 rows (大约)
DELETE
FROM `sys_log`;
/*!40000 ALTER TABLE `sys_log`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_log`
    ENABLE KEYS */;

-- 导出  表 db_efadmin.sys_menu 结构
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE IF NOT EXISTS `sys_menu`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `i_frame`     bit(1)        DEFAULT NULL COMMENT '是否外链',
    `name`        varchar(255)  DEFAULT NULL COMMENT '菜单名称',
    `component`   varchar(255)  DEFAULT NULL COMMENT '组件',
    `pid`         bigint(20) NOT NULL COMMENT '上级菜单ID',
    `sort`        bigint(20) NOT NULL COMMENT '排序',
    `icon`        varchar(255)  DEFAULT NULL COMMENT '图标',
    `path`        varchar(255)  DEFAULT NULL COMMENT '链接地址',
    `remark`      varchar(1024) DEFAULT NULL COMMENT '备注',
    `create_time` datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    `creator_num` varchar(32)   DEFAULT NULL COMMENT '创建人',
    `update_time` datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `updater_num` varchar(32)   DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 41
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='系统菜单';

-- 正在导出表  db_efadmin.sys_menu 的数据：~31 rows (大约)
DELETE
FROM `sys_menu`;
/*!40000 ALTER TABLE `sys_menu`
    DISABLE KEYS */;
INSERT INTO `sys_menu` (`id`, `i_frame`, `name`, `component`, `pid`, `sort`, `icon`, `path`, `remark`, `create_time`,
                        `creator_num`, `update_time`, `updater_num`)
VALUES (1, b'0', '系统管理', NULL, 0, 1, 'system', 'system', NULL, '2018-12-18 15:11:29', NULL, '2019-07-11 21:39:00',
        NULL),
       (2, b'0', '用户管理', 'system/user/index', 1, 2, 'peoples', 'user', NULL, '2018-12-18 15:14:44', NULL,
        '2019-07-11 21:39:00', NULL),
       (3, b'0', '角色管理', 'system/role/index', 1, 3, 'role', 'role', NULL, '2018-12-18 15:16:07', NULL,
        '2019-07-11 21:39:00', NULL),
       (4, b'0', '权限管理', 'system/permission/index', 1, 4, 'permission', 'permission', NULL, '2018-12-18 15:16:45', NULL,
        '2019-07-11 21:39:00', NULL),
       (5, b'0', '菜单管理', 'system/menu/index', 1, 5, 'menu', 'menu', NULL, '2018-12-18 15:17:28', NULL,
        '2019-07-11 21:39:00', NULL),
       (6, b'0', '系统监控', NULL, 0, 10, 'monitor', 'monitor', NULL, '2018-12-18 15:17:48', NULL, '2019-07-11 21:39:00',
        NULL),
       (7, b'0', '操作日志', 'monitor/log/index', 6, 11, 'log', 'logs', NULL, '2018-12-18 15:18:26', NULL,
        '2019-07-11 21:39:00', NULL),
       (8, b'0', '系统缓存', 'monitor/redis/index', 6, 13, 'redis', 'redis', NULL, '2018-12-18 15:19:01', NULL,
        '2019-07-11 21:39:00', NULL),
       (9, b'0', 'SQL监控', 'monitor/sql/index', 6, 14, 'sqlMonitor', 'druid', NULL, '2018-12-18 15:19:34', NULL,
        '2019-07-11 21:39:00', NULL),
       (10, b'0', '组件管理', NULL, 0, 50, 'zujian', 'components', NULL, '2018-12-19 13:38:16', NULL, '2019-07-11 21:39:00',
        NULL),
       (11, b'0', '图标库', 'components/IconSelect', 10, 51, 'icon', 'icon', NULL, '2018-12-19 13:38:49', NULL,
        '2019-07-11 21:39:00', NULL),
       (14, b'0', '邮件工具', 'tools/email/index', 36, 24, 'email', 'email', NULL, '2018-12-27 10:13:09', NULL,
        '2019-07-11 21:39:00', NULL),
       (15, b'0', '富文本', 'components/Editor', 10, 52, 'fwb', 'tinymce', NULL, '2018-12-27 11:58:25', NULL,
        '2019-07-11 21:39:00', NULL),
       (16, b'0', '图床管理', 'tools/picture/index', 36, 25, 'image', 'pictures', NULL, '2018-12-28 09:36:53', NULL,
        '2019-07-11 21:39:00', NULL),
       (18, b'0', '七牛云存储', 'tools/qiniu/index', 36, 26, 'qiniu', 'qiniu', NULL, '2018-12-31 11:12:15', NULL,
        '2019-07-11 21:39:00', NULL),
       (19, b'0', '支付宝工具', 'tools/aliPay/index', 36, 27, 'alipay', 'aliPay', NULL, '2018-12-31 14:52:38', NULL,
        '2019-07-11 21:39:00', NULL),
       (21, b'0', '多级菜单', '', 0, 900, 'menu', 'nested', NULL, '2019-01-04 16:22:03', NULL, '2019-07-11 21:39:00', NULL),
       (22, b'0', '二级菜单1', 'nested/menu1/index', 21, 999, 'menu', 'menu1', NULL, '2019-01-04 16:23:29', NULL,
        '2019-07-11 21:39:00', NULL),
       (23, b'0', '二级菜单2', 'nested/menu2/index', 21, 999, 'menu', 'menu2', NULL, '2019-01-04 16:23:57', NULL,
        '2019-07-11 21:39:00', NULL),
       (24, b'0', '三级菜单1', 'nested/menu1/menu1-1', 22, 999, 'menu', 'menu1-1', NULL, '2019-01-04 16:24:48', NULL,
        '2019-07-11 21:39:00', NULL),
       (27, b'0', '三级菜单2', 'nested/menu1/menu1-2', 22, 999, 'menu', 'menu1-2', NULL, '2019-01-07 17:27:32', NULL,
        '2019-07-11 21:39:00', NULL),
       (28, b'0', '定时任务', 'system/timing/index', 36, 21, 'timing', 'timing', NULL, '2019-01-07 20:34:40', NULL,
        '2019-07-11 21:39:00', NULL),
       (30, b'0', '代码生成', 'generator/index', 36, 22, 'dev', 'generator', NULL, '2019-01-11 15:45:55', NULL,
        '2019-07-11 21:39:00', NULL),
       (32, b'0', '异常日志', 'monitor/log/errorLog', 6, 12, 'error', 'errorLog', NULL, '2019-01-13 13:49:03', NULL,
        '2019-07-11 21:39:00', NULL),
       (33, b'0', 'Markdown', 'components/MarkDown', 10, 53, 'markdown', 'markdown', NULL, '2019-03-08 13:46:44', NULL,
        '2019-07-11 21:39:00', NULL),
       (34, b'0', 'Yaml编辑器', 'components/YamlEdit', 10, 54, 'dev', 'yaml', NULL, '2019-03-08 15:49:40', NULL,
        '2019-07-11 21:39:00', NULL),
       (35, b'0', '部门管理', 'system/dept/index', 1, 6, 'dept', 'dept', NULL, '2019-03-25 09:46:00', NULL,
        '2019-07-11 21:39:00', NULL),
       (36, b'0', '系统工具', '', 0, 20, 'sys-tools', 'sys-tools', NULL, '2019-03-29 10:57:35', NULL, '2019-07-11 21:39:00',
        NULL),
       (37, b'0', '岗位管理', 'system/job/index', 1, 7, 'Steve-Jobs', 'job', NULL, '2019-03-29 13:51:18', NULL,
        '2019-07-11 21:39:00', NULL),
       (38, b'0', '接口文档', 'tools/swagger/index', 36, 23, 'swagger', 'swagger2', NULL, '2019-03-29 19:57:53', NULL,
        '2019-07-11 21:39:00', NULL),
       (39, b'0', '字典管理', 'system/dict/index', 1, 8, 'dictionary', 'dict', NULL, '2019-04-10 11:49:04', NULL,
        '2019-07-11 21:39:00', NULL);
/*!40000 ALTER TABLE `sys_menu`
    ENABLE KEYS */;

-- 导出  表 db_efadmin.sys_permission 结构
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE IF NOT EXISTS `sys_permission`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `alias`       varchar(255)  DEFAULT NULL COMMENT '别名',
    `name`        varchar(255)  DEFAULT NULL COMMENT '名称',
    `pid`         int(11)    NOT NULL COMMENT '上级权限',
    `remark`      varchar(1024) DEFAULT NULL COMMENT '备注',
    `create_time` datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    `creator_num` varchar(32)   DEFAULT NULL COMMENT '创建人',
    `update_time` datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `updater_num` varchar(32)   DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 55
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='系统权限';

-- 正在导出表  db_efadmin.sys_permission 的数据：~48 rows (大约)
DELETE
FROM `sys_permission`;
/*!40000 ALTER TABLE `sys_permission`
    DISABLE KEYS */;
INSERT INTO `sys_permission` (`id`, `alias`, `name`, `pid`, `remark`, `create_time`, `creator_num`, `update_time`,
                              `updater_num`)
VALUES (1, '超级管理员', 'ADMIN', 0, NULL, '2018-12-03 12:27:48', NULL, '2019-07-11 21:39:00', NULL),
       (2, '用户管理', 'USER_ALL', 0, NULL, '2018-12-03 12:28:19', NULL, '2019-07-11 21:39:00', NULL),
       (3, '用户查询', 'USER_SELECT', 2, NULL, '2018-12-03 12:31:35', NULL, '2019-07-11 21:39:00', NULL),
       (4, '用户创建', 'USER_CREATE', 2, NULL, '2018-12-03 12:31:35', NULL, '2019-07-11 21:39:00', NULL),
       (5, '用户编辑', 'USER_EDIT', 2, NULL, '2018-12-03 12:31:35', NULL, '2019-07-11 21:39:00', NULL),
       (6, '用户删除', 'USER_DELETE', 2, NULL, '2018-12-03 12:31:35', NULL, '2019-07-11 21:39:00', NULL),
       (7, '角色管理', 'ROLES_ALL', 0, NULL, '2018-12-03 12:28:19', NULL, '2019-07-11 21:39:00', NULL),
       (8, '角色查询', 'ROLES_SELECT', 7, NULL, '2018-12-03 12:31:35', NULL, '2019-07-11 21:39:00', NULL),
       (10, '角色创建', 'ROLES_CREATE', 7, NULL, '2018-12-09 20:10:16', NULL, '2019-07-11 21:39:00', NULL),
       (11, '角色编辑', 'ROLES_EDIT', 7, NULL, '2018-12-09 20:10:42', NULL, '2019-07-11 21:39:00', NULL),
       (12, '角色删除', 'ROLES_DELETE', 7, NULL, '2018-12-09 20:11:07', NULL, '2019-07-11 21:39:00', NULL),
       (13, '权限管理', 'PERMISSION_ALL', 0, NULL, '2018-12-09 20:11:37', NULL, '2019-07-11 21:39:00', NULL),
       (14, '权限查询', 'PERMISSION_SELECT', 13, NULL, '2018-12-09 20:11:55', NULL, '2019-07-11 21:39:00', NULL),
       (15, '权限创建', 'PERMISSION_CREATE', 13, NULL, '2018-12-09 20:14:10', NULL, '2019-07-11 21:39:00', NULL),
       (16, '权限编辑', 'PERMISSION_EDIT', 13, NULL, '2018-12-09 20:15:44', NULL, '2019-07-11 21:39:00', NULL),
       (17, '权限删除', 'PERMISSION_DELETE', 13, NULL, '2018-12-09 20:15:59', NULL, '2019-07-11 21:39:00', NULL),
       (18, '缓存管理', 'REDIS_ALL', 0, NULL, '2018-12-17 13:53:25', NULL, '2019-07-11 21:39:00', NULL),
       (20, '缓存查询', 'REDIS_SELECT', 18, NULL, '2018-12-17 13:54:07', NULL, '2019-07-11 21:39:00', NULL),
       (22, '缓存删除', 'REDIS_DELETE', 18, NULL, '2018-12-17 13:55:04', NULL, '2019-07-11 21:39:00', NULL),
       (23, '图床管理', 'PICTURE_ALL', 0, NULL, '2018-12-27 20:31:49', NULL, '2019-07-11 21:39:00', NULL),
       (24, '查询图片', 'PICTURE_SELECT', 23, NULL, '2018-12-27 20:32:04', NULL, '2019-07-11 21:39:00', NULL),
       (25, '上传图片', 'PICTURE_UPLOAD', 23, NULL, '2018-12-27 20:32:24', NULL, '2019-07-11 21:39:00', NULL),
       (26, '删除图片', 'PICTURE_DELETE', 23, NULL, '2018-12-27 20:32:45', NULL, '2019-07-11 21:39:00', NULL),
       (29, '菜单管理', 'MENU_ALL', 0, NULL, '2018-12-28 17:34:31', NULL, '2019-07-11 21:39:00', NULL),
       (30, '菜单查询', 'MENU_SELECT', 29, NULL, '2018-12-28 17:34:41', NULL, '2019-07-11 21:39:00', NULL),
       (31, '菜单创建', 'MENU_CREATE', 29, NULL, '2018-12-28 17:34:52', NULL, '2019-07-11 21:39:00', NULL),
       (32, '菜单编辑', 'MENU_EDIT', 29, NULL, '2018-12-28 17:35:20', NULL, '2019-07-11 21:39:00', NULL),
       (33, '菜单删除', 'MENU_DELETE', 29, NULL, '2018-12-28 17:35:29', NULL, '2019-07-11 21:39:00', NULL),
       (35, '定时任务管理', 'JOB_ALL', 0, NULL, '2019-01-08 14:59:57', NULL, '2019-07-11 21:39:00', NULL),
       (36, '任务查询', 'JOB_SELECT', 35, NULL, '2019-01-08 15:00:09', NULL, '2019-07-11 21:39:00', NULL),
       (37, '任务创建', 'JOB_CREATE', 35, NULL, '2019-01-08 15:00:20', NULL, '2019-07-11 21:39:00', NULL),
       (38, '任务编辑', 'JOB_EDIT', 35, NULL, '2019-01-08 15:00:33', NULL, '2019-07-11 21:39:00', NULL),
       (39, '任务删除', 'JOB_DELETE', 35, NULL, '2019-01-08 15:01:13', NULL, '2019-07-11 21:39:00', NULL),
       (40, '部门管理', 'DEPT_ALL', 0, NULL, '2019-03-29 17:06:55', NULL, '2019-07-11 21:39:00', NULL),
       (41, '部门查询', 'DEPT_SELECT', 40, NULL, '2019-03-29 17:07:09', NULL, '2019-07-11 21:39:00', NULL),
       (42, '部门创建', 'DEPT_CREATE', 40, NULL, '2019-03-29 17:07:29', NULL, '2019-07-11 21:39:00', NULL),
       (43, '部门编辑', 'DEPT_EDIT', 40, NULL, '2019-03-29 17:07:52', NULL, '2019-07-11 21:39:00', NULL),
       (44, '部门删除', 'DEPT_DELETE', 40, NULL, '2019-03-29 17:08:14', NULL, '2019-07-11 21:39:00', NULL),
       (45, '岗位管理', 'USERJOB_ALL', 0, NULL, '2019-03-29 17:08:52', NULL, '2019-07-11 21:39:00', NULL),
       (46, '岗位查询', 'USERJOB_SELECT', 45, NULL, '2019-03-29 17:10:27', NULL, '2019-07-11 21:39:00', NULL),
       (47, '岗位创建', 'USERJOB_CREATE', 45, NULL, '2019-03-29 17:10:55', NULL, '2019-07-11 21:39:00', NULL),
       (48, '岗位编辑', 'USERJOB_EDIT', 45, NULL, '2019-03-29 17:11:08', NULL, '2019-07-11 21:39:00', NULL),
       (49, '岗位删除', 'USERJOB_DELETE', 45, NULL, '2019-03-29 17:11:19', NULL, '2019-07-11 21:39:00', NULL),
       (50, '字典管理', 'DICT_ALL', 0, NULL, '2019-04-10 16:24:51', NULL, '2019-07-11 21:39:00', NULL),
       (51, '字典查询', 'DICT_SELECT', 50, NULL, '2019-04-10 16:25:16', NULL, '2019-07-11 21:39:00', NULL),
       (52, '字典创建', 'DICT_CREATE', 50, NULL, '2019-04-10 16:25:29', NULL, '2019-07-11 21:39:00', NULL),
       (53, '字典编辑', 'DICT_EDIT', 50, NULL, '2019-04-10 16:27:19', NULL, '2019-07-11 21:39:00', NULL),
       (54, '字典删除', 'DICT_DELETE', 50, NULL, '2019-04-10 16:27:30', NULL, '2019-07-11 21:39:00', NULL);
/*!40000 ALTER TABLE `sys_permission`
    ENABLE KEYS */;

-- 导出  表 db_efadmin.sys_picture 结构
DROP TABLE IF EXISTS `sys_picture`;
CREATE TABLE IF NOT EXISTS `sys_picture`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `delete_url`  varchar(255)  DEFAULT NULL COMMENT '删除的URL',
    `filename`    varchar(255)  DEFAULT NULL COMMENT '图片名称',
    `height`      varchar(255)  DEFAULT NULL COMMENT '图片高度',
    `size`        varchar(255)  DEFAULT NULL COMMENT '图片大小',
    `url`         varchar(255)  DEFAULT NULL COMMENT '图片地址',
    `username`    varchar(255)  DEFAULT NULL COMMENT '用户名称',
    `width`       varchar(255)  DEFAULT NULL COMMENT '图片宽度',
    `remark`      varchar(1024) DEFAULT NULL COMMENT '备注',
    `create_time` datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '上传日期',
    `creator_num` varchar(32)   DEFAULT NULL COMMENT '创建人',
    `update_time` datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `updater_num` varchar(32)   DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='系统图床';

-- 正在导出表  db_efadmin.sys_picture 的数据：~0 rows (大约)
DELETE
FROM `sys_picture`;
/*!40000 ALTER TABLE `sys_picture`
    DISABLE KEYS */;
INSERT INTO `sys_picture` (`id`, `delete_url`, `filename`, `height`, `size`, `url`, `username`, `width`, `remark`,
                           `create_time`, `creator_num`, `update_time`, `updater_num`)
VALUES (2, 'https://sm.ms/delete/zTKqclhmYQ3Nw4L', '黄山.jpeg', '2592', '4.27MB   ',
        'https://i.loli.net/2019/07/14/5d2a656915beb45533.jpeg', 'admin', '4608', NULL, '2019-07-14 07:12:41', NULL,
        '2019-07-14 07:12:41', NULL);
/*!40000 ALTER TABLE `sys_picture`
    ENABLE KEYS */;

-- 导出  表 db_efadmin.sys_qiniu_config 结构
DROP TABLE IF EXISTS `sys_qiniu_config`;
CREATE TABLE IF NOT EXISTS `sys_qiniu_config`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `access_key`  text COMMENT 'accessKey',
    `bucket`      varchar(255)  DEFAULT NULL COMMENT 'Bucket 识别符',
    `host`        varchar(255) NOT NULL COMMENT '外链域名',
    `secret_key`  text COMMENT 'secretKey',
    `type`        varchar(255)  DEFAULT NULL COMMENT '空间类型',
    `zone`        varchar(255)  DEFAULT NULL COMMENT '机房',
    `remark`      varchar(1024) DEFAULT NULL COMMENT '备注',
    `create_time` datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `creator_num` varchar(32)   DEFAULT NULL COMMENT '创建人',
    `update_time` datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `updater_num` varchar(32)   DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='七牛云配置';

-- 正在导出表  db_efadmin.sys_qiniu_config 的数据：~0 rows (大约)
DELETE
FROM `sys_qiniu_config`;
/*!40000 ALTER TABLE `sys_qiniu_config`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_qiniu_config`
    ENABLE KEYS */;

-- 导出  表 db_efadmin.sys_qiniu_content 结构
DROP TABLE IF EXISTS `sys_qiniu_content`;
CREATE TABLE IF NOT EXISTS `sys_qiniu_content`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `bucket`      varchar(255)  DEFAULT NULL COMMENT 'Bucket 识别符',
    `name`        varchar(255)  DEFAULT NULL COMMENT '文件名称',
    `size`        varchar(255)  DEFAULT NULL COMMENT '文件大小',
    `type`        varchar(255)  DEFAULT NULL COMMENT '文件类型：私有或公开',
    `url`         varchar(255)  DEFAULT NULL COMMENT '文件url',
    `remark`      varchar(1024) DEFAULT NULL COMMENT '备注',
    `create_time` datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `creator_num` varchar(32)   DEFAULT NULL COMMENT '创建人',
    `update_time` datetime      DEFAULT NULL COMMENT '上传或同步的时间',
    `updater_num` varchar(32)   DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='七牛内容';

-- 正在导出表  db_efadmin.sys_qiniu_content 的数据：~0 rows (大约)
DELETE
FROM `sys_qiniu_content`;
/*!40000 ALTER TABLE `sys_qiniu_content`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_qiniu_content`
    ENABLE KEYS */;

-- 导出  表 db_efadmin.sys_quartz_job 结构
DROP TABLE IF EXISTS `sys_quartz_job`;
CREATE TABLE IF NOT EXISTS `sys_quartz_job`
(
    `id`              bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `bean_name`       varchar(255) DEFAULT NULL COMMENT 'Spring Bean名称',
    `cron_expression` varchar(255) DEFAULT NULL COMMENT 'cron 表达式',
    `is_pause`        bit(1)       DEFAULT NULL COMMENT '状态：1暂停、0启用',
    `job_name`        varchar(255) DEFAULT NULL COMMENT '任务名称',
    `method_name`     varchar(255) DEFAULT NULL COMMENT '方法名称',
    `params`          varchar(255) DEFAULT NULL COMMENT '参数',
    `remark`          varchar(255) DEFAULT NULL COMMENT '备注',
    `create_time`     datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `creator_num`     varchar(32)  DEFAULT NULL COMMENT '创建人',
    `update_time`     datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建或更新日期',
    `updater_num`     varchar(32)  DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='系统定时任务';

-- 正在导出表  db_efadmin.sys_quartz_job 的数据：~3 rows (大约)
DELETE
FROM `sys_quartz_job`;
/*!40000 ALTER TABLE `sys_quartz_job`
    DISABLE KEYS */;
INSERT INTO `sys_quartz_job` (`id`, `bean_name`, `cron_expression`, `is_pause`, `job_name`, `method_name`, `params`,
                              `remark`, `create_time`, `creator_num`, `update_time`, `updater_num`)
VALUES (1, 'visitsTask', '0 0 0 * * ?', b'0', '更新访客记录', 'run', NULL, '每日0点创建新的访客记录', '2019-07-11 21:39:01', NULL,
        '2019-01-08 14:53:31', NULL),
       (2, 'testTask', '0/5 * * * * ?', b'1', '测试1', 'run1', 'test', '带参测试，多参使用json', '2019-07-11 21:39:01', NULL,
        '2019-01-13 14:20:50', NULL),
       (3, 'testTask', '0/5 * * * * ?', b'1', '测试', 'run', '', '不带参测试', '2019-07-11 21:39:01', NULL,
        '2019-04-09 16:16:44', NULL);
/*!40000 ALTER TABLE `sys_quartz_job`
    ENABLE KEYS */;

-- 导出  表 db_efadmin.sys_quartz_log 结构
DROP TABLE IF EXISTS `sys_quartz_log`;
CREATE TABLE IF NOT EXISTS `sys_quartz_log`
(
    `id`               bigint(20) NOT NULL AUTO_INCREMENT,
    `baen_name`        varchar(255)  DEFAULT NULL COMMENT 'Bean名称',
    `cron_expression`  varchar(255)  DEFAULT NULL COMMENT '定时表达式',
    `exception_detail` text COMMENT '异常明细',
    `is_success`       bit(1)        DEFAULT NULL COMMENT '是否成功',
    `job_name`         varchar(255)  DEFAULT NULL COMMENT '任务名称',
    `method_name`      varchar(255)  DEFAULT NULL COMMENT '方法名称',
    `params`           varchar(255)  DEFAULT NULL COMMENT '参数',
    `time`             bigint(20)    DEFAULT NULL COMMENT '耗时',
    `remark`           varchar(1024) DEFAULT NULL COMMENT '备注',
    `create_time`      datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `creator_num`      varchar(32)   DEFAULT NULL COMMENT '创建人',
    `update_time`      datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `updater_num`      varchar(32)   DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='定时任务运行日志';

-- 正在导出表  db_efadmin.sys_quartz_log 的数据：~0 rows (大约)
DELETE
FROM `sys_quartz_log`;
/*!40000 ALTER TABLE `sys_quartz_log`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_quartz_log`
    ENABLE KEYS */;

-- 导出  表 db_efadmin.sys_role 结构
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE IF NOT EXISTS `sys_role`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`        varchar(255) NOT NULL COMMENT '名称',
    `data_scope`  varchar(255) DEFAULT NULL COMMENT '数据范围',
    `level`       int(255)     DEFAULT NULL COMMENT '级别',
    `remark`      varchar(255) DEFAULT NULL COMMENT '备注',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    `creator_num` varchar(32)  DEFAULT NULL COMMENT '创建人',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `updater_num` varchar(32)  DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='系统角色';

-- 正在导出表  db_efadmin.sys_role 的数据：~3 rows (大约)
DELETE
FROM `sys_role`;
/*!40000 ALTER TABLE `sys_role`
    DISABLE KEYS */;
INSERT INTO `sys_role` (`id`, `name`, `data_scope`, `level`, `remark`, `create_time`, `creator_num`, `update_time`,
                        `updater_num`)
VALUES (1, '超级管理员', '全部', 1, '系统所有权', '2018-11-23 11:04:37', NULL, '2019-07-11 21:39:01', NULL),
       (2, '普通用户', '自定义', 3, '用于测试菜单与权限', '2018-11-23 13:09:06', NULL, '2019-07-11 21:39:01', NULL),
       (4, '普通管理员', '自定义', 2, '普通管理员级别为2，使用该角色新增用户时只能赋予比普通管理员级别低的角色', '2019-05-13 14:16:15', NULL,
        '2019-07-11 21:39:01', NULL);
/*!40000 ALTER TABLE `sys_role`
    ENABLE KEYS */;

-- 导出  表 db_efadmin.sys_roles_depts 结构
DROP TABLE IF EXISTS `sys_roles_depts`;
CREATE TABLE IF NOT EXISTS `sys_roles_depts`
(
    `role_id` bigint(20) NOT NULL COMMENT '角色id',
    `dept_id` bigint(20) NOT NULL COMMENT '部门id',
    PRIMARY KEY (`role_id`, `dept_id`),
    KEY `FK7qg6itn5ajdoa9h9o78v9ksur` (`dept_id`),
    CONSTRAINT `FK7qg6itn5ajdoa9h9o78v9ksur` FOREIGN KEY (`dept_id`) REFERENCES `sys_dept` (`id`),
    CONSTRAINT `FKrg1ci4cxxfbja0sb0pddju7k` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='系统角色部门关联表';

-- 正在导出表  db_efadmin.sys_roles_depts 的数据：~4 rows (大约)
DELETE
FROM `sys_roles_depts`;
/*!40000 ALTER TABLE `sys_roles_depts`
    DISABLE KEYS */;
INSERT INTO `sys_roles_depts` (`role_id`, `dept_id`)
VALUES (2, 5),
       (4, 6),
       (4, 7),
       (2, 8);
/*!40000 ALTER TABLE `sys_roles_depts`
    ENABLE KEYS */;

-- 导出  表 db_efadmin.sys_roles_menus 结构
DROP TABLE IF EXISTS `sys_roles_menus`;
CREATE TABLE IF NOT EXISTS `sys_roles_menus`
(
    `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
    `role_id` bigint(20) NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`menu_id`, `role_id`) USING BTREE,
    KEY `FKcngg2qadojhi3a651a5adkvbq` (`role_id`) USING BTREE,
    CONSTRAINT `FKcngg2qadojhi3a651a5adkvbq` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`),
    CONSTRAINT `FKq1knxf8ykt26we8k331naabjx` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='角色和菜单关联';

-- 正在导出表  db_efadmin.sys_roles_menus 的数据：~64 rows (大约)
DELETE
FROM `sys_roles_menus`;
/*!40000 ALTER TABLE `sys_roles_menus`
    DISABLE KEYS */;
INSERT INTO `sys_roles_menus` (`menu_id`, `role_id`)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (4, 1),
       (5, 1),
       (6, 1),
       (7, 1),
       (8, 1),
       (9, 1),
       (10, 1),
       (11, 1),
       (14, 1),
       (15, 1),
       (16, 1),
       (17, 1),
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
       (1, 2),
       (2, 2),
       (3, 2),
       (4, 2),
       (5, 2),
       (6, 2),
       (8, 2),
       (9, 2),
       (10, 2),
       (11, 2),
       (14, 2),
       (15, 2),
       (16, 2),
       (17, 2),
       (18, 2),
       (19, 2),
       (21, 2),
       (22, 2),
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
       (1, 4),
       (2, 4);
/*!40000 ALTER TABLE `sys_roles_menus`
    ENABLE KEYS */;

-- 导出  表 db_efadmin.sys_roles_permissions 结构
DROP TABLE IF EXISTS `sys_roles_permissions`;
CREATE TABLE IF NOT EXISTS `sys_roles_permissions`
(
    `role_id`       bigint(20) NOT NULL COMMENT '角色ID',
    `permission_id` bigint(20) NOT NULL COMMENT '权限ID',
    PRIMARY KEY (`role_id`, `permission_id`) USING BTREE,
    KEY `FKboeuhl31go7wer3bpy6so7exi` (`permission_id`) USING BTREE,
    CONSTRAINT `FK4hrolwj4ned5i7qe8kyiaak6m` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`),
    CONSTRAINT `FKboeuhl31go7wer3bpy6so7exi` FOREIGN KEY (`permission_id`) REFERENCES `sys_permission` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='角色和权限关联';

-- 正在导出表  db_efadmin.sys_roles_permissions 的数据：~17 rows (大约)
DELETE
FROM `sys_roles_permissions`;
/*!40000 ALTER TABLE `sys_roles_permissions`
    DISABLE KEYS */;
INSERT INTO `sys_roles_permissions` (`role_id`, `permission_id`)
VALUES (1, 1),
       (2, 3),
       (4, 3),
       (4, 4),
       (4, 5),
       (2, 8),
       (2, 14),
       (2, 20),
       (2, 23),
       (2, 24),
       (2, 25),
       (2, 26),
       (2, 30),
       (2, 36),
       (2, 41),
       (2, 46),
       (2, 51);
/*!40000 ALTER TABLE `sys_roles_permissions`
    ENABLE KEYS */;

-- 导出  表 db_efadmin.sys_user 结构
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像地址',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `enabled` bigint(20) DEFAULT NULL COMMENT '状态：1启用、0禁用',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `usercode` varchar(255) DEFAULT NULL COMMENT '用户编号/工号',
  `last_password_reset_time` datetime DEFAULT NULL COMMENT '最后修改密码的日期',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '关联部门id',
  `phone` varchar(255) DEFAULT NULL COMMENT '手机号',
  `job_id` bigint(20) DEFAULT NULL COMMENT '岗位id',
  `remark` varchar(1024) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `creator_num` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater_num` varchar(32) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UK_kpubos9gc2cvtkb0thktkbkes` (`email`) USING BTREE,
  UNIQUE KEY `username` (`username`) USING BTREE,
  UNIQUE KEY `UK_ag19a4erqrlqvx40lk5j1aoll` (`usercode`),
  KEY `FK5rwmryny6jthaaxkogownknqp` (`dept_id`),
  KEY `FKfftoc2abhot8f2wu6cl9a5iky` (`job_id`),
  CONSTRAINT `FK5rwmryny6jthaaxkogownknqp` FOREIGN KEY (`dept_id`) REFERENCES `sys_dept` (`id`),
  CONSTRAINT `FKfftoc2abhot8f2wu6cl9a5iky` FOREIGN KEY (`job_id`) REFERENCES `sys_job` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统用户';

-- 正在导出表  db_efadmin.sys_user 的数据：~4 rows (大约)
DELETE FROM `sys_user`;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` (`id`, `avatar`, `email`, `enabled`, `password`, `username`, `usercode`, `last_password_reset_time`, `dept_id`, `phone`, `job_id`, `remark`, `create_time`, `creator_num`, `update_time`, `updater_num`) VALUES
	(1, 'https://i.loli.net/2019/07/14/5d2a656915beb45533.jpeg', 'admin@efadmin.net', 1, 'e10adc3949ba59abbe56e057f20f883e', 'admin', 'U001', '2019-05-18 17:34:21', 2, '18888888888', 11, NULL, '2018-08-23 09:11:56', NULL, '2019-07-16 06:14:29', NULL),
	(3, 'https://aurora-1255840532.cos.ap-chengdu.myqcloud.com/8918a306ea314404835a9196585c4b75.jpeg', 'test@efadmin.net', 1, 'e10adc3949ba59abbe56e057f20f883e', 'test', 'U002', '2019-04-01 09:15:24', 2, '17777777777', 12, NULL, '2018-12-27 20:05:26', NULL, '2019-07-16 06:14:37', NULL),
	(5, 'https://aurora-1255840532.cos.ap-chengdu.myqcloud.com/8918a306ea314404835a9196585c4b75.jpeg', 'hr@efadmin.net', 1, 'e10adc3949ba59abbe56e057f20f883e', 'hr', 'U003', NULL, 11, '15555555555', 8, NULL, '2019-04-02 10:07:12', NULL, '2019-07-16 06:14:44', NULL),
	(6, 'https://i.loli.net/2019/04/04/5ca5b971e1548.jpeg', 'dusuanyun@sina.com', 1, 'e10adc3949ba59abbe56e057f20f883e', 'dbdu', 'U004', NULL, 2, '13855119027', 11, NULL, '2019-07-14 08:54:48', 'admin', '2019-07-16 06:14:52', 'admin');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;



-- 导出  表 db_efadmin.sys_users_roles 结构
DROP TABLE IF EXISTS `sys_users_roles`;
CREATE TABLE IF NOT EXISTS `sys_users_roles`
(
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `role_id` bigint(20) NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
    KEY `FKq4eq273l04bpu4efj0jd0jb98` (`role_id`) USING BTREE,
    CONSTRAINT `sys_users_roles_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`),
    CONSTRAINT `sys_users_roles_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='用户和角色关联';

-- 正在导出表  db_efadmin.sys_users_roles 的数据：~3 rows (大约)
DELETE
FROM `sys_users_roles`;
/*!40000 ALTER TABLE `sys_users_roles`
    DISABLE KEYS */;
INSERT INTO `sys_users_roles` (`user_id`, `role_id`)
VALUES (1, 1),
       (3, 2),
       (5, 4);
/*!40000 ALTER TABLE `sys_users_roles`
    ENABLE KEYS */;

-- 导出  表 db_efadmin.sys_verification_code 结构
DROP TABLE IF EXISTS `sys_verification_code`;
CREATE TABLE IF NOT EXISTS `sys_verification_code`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `code`        varchar(255)  DEFAULT NULL COMMENT '验证码',
    `status`      bit(1)        DEFAULT NULL COMMENT '状态：1有效、0过期',
    `type`        varchar(255)  DEFAULT NULL COMMENT '验证码类型：email或者短信',
    `value`       varchar(255)  DEFAULT NULL COMMENT '接收邮箱或者手机号码',
    `scenes`      varchar(255)  DEFAULT NULL COMMENT '业务名称：如重置邮箱、重置密码等',
    `remark`      varchar(1024) DEFAULT NULL COMMENT '备注',
    `create_time` datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    `creator_num` varchar(32)   DEFAULT NULL COMMENT '创建人',
    `update_time` datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `updater_num` varchar(32)   DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='验证码';

-- 正在导出表  db_efadmin.sys_verification_code 的数据：~0 rows (大约)
DELETE
FROM `sys_verification_code`;
/*!40000 ALTER TABLE `sys_verification_code`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_verification_code`
    ENABLE KEYS */;

-- 导出  表 db_efadmin.sys_visits 结构
DROP TABLE IF EXISTS `sys_visits`;
CREATE TABLE IF NOT EXISTS `sys_visits`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `date`        varchar(255)  DEFAULT NULL COMMENT '日期',
    `ip_counts`   bigint(20)    DEFAULT NULL COMMENT 'IP数量',
    `pv_counts`   bigint(20)    DEFAULT NULL,
    `week_day`    varchar(255)  DEFAULT NULL,
    `remark`      varchar(1024) DEFAULT NULL COMMENT '备注',
    `create_time` datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `creator_num` varchar(32)   DEFAULT NULL COMMENT '创建人',
    `update_time` datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `updater_num` varchar(32)   DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_11aksgq87euk9bcyeesfs4vtp` (`date`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='系统访问统计';

-- 正在导出表  db_efadmin.sys_visits 的数据：~4 rows (大约)
DELETE
FROM `sys_visits`;
/*!40000 ALTER TABLE `sys_visits`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_visits`
    ENABLE KEYS */;

-- 员工权限 --
INSERT INTO `sys_permission` ( `alias`, `name`, `pid`, `remark`) VALUES
('员工管理', 'EMPLOYEE_ALL', 0,  'System Gen'),
('员工查询', 'EMPLOYEE_SELECT', 55,  'System Gen'),
('员工创建', 'EMPLOYEE_CREATE', 55,  'System Gen'),
('员工编辑', 'EMPLOYEE_EDIT', 55,  'System Gen'),
('员工删除', 'EMPLOYEE_DELETE', 55,  'System Gen');

-- 员工管理菜单 --
INSERT INTO `sys_menu` (`i_frame`, `name`, `component`, `pid`, `sort`, `icon`, `path`, `remark`) VALUES
(b'0', '员工管理', 'system/employee/index', 1, 9, 'employee', 'employee', 'System Gen');




/*!40101 SET SQL_MODE = IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS = IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
