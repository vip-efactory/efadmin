# efactory

电子化工厂的后端项目:本项目最初基于eladmin,后参考其他开源项目,进行了大量的优化修改!

<h1 style="text-align: center">ef-admin 后台管理系统</h1>
<div style="text-align: center">

[![AUR](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](https://github.com/elunez/efadmin/blob/master/LICENSE)
[![star](https://gitee.com/elunez/efadmin/badge/star.svg?theme=white)](https://gitee.com/elunez/efadmin)
[![GitHub stars](https://img.shields.io/github/stars/elunez/efadmin.svg?style=social&label=Stars)](https://github.com/elunez/efadmin)
[![GitHub forks](https://img.shields.io/github/forks/elunez/efadmin.svg?style=social&label=Fork)](https://github.com/elunez/efadmin)

</div>

#### 项目简介
efadmin基于 Spring Boot 2.1.6 、 Jpa、 Spring Security、redis、Vue的前后端分离的后台管理系统， 权限控制的方式为RBAC，项目支持数据字典与数据权限管理，支持一键生成前后端代码，支持前端菜单动态路由

**开发文档**  [https://docs.auauz.net/](https://docs.auauz.net)

**体验地址**  [https://auauz.net/](https://auauz.net/)

**账号密码** ```admin/123456```(默认密码都是123456)


####  系统功能
- 用户管理：提供用户的相关配置，新增用户后，默认密码为123456
- 角色管理：对权限与菜单进行分配，可根据部门设置角色的数据权限
- 权限管理：权限细化到接口，可以理解成按钮权限
- 菜单管理：已实现菜单动态路由，后端可配置化，支持多级菜单
- 部门管理：可配置系统组织架构，树形表格展示
- 岗位管理：配置各个部门的职位
- 字典管理：应广大码友的要求加入字典管理，可维护常用一些固定的数据，如：状态，性别等
- 操作日志：记录用户操作的日志
- 异常日志：记录异常日志，方便开发人员定位错误
- 系统缓存：使用jedis将缓存操作可视化，并提供对redis的基本操作，可根据需求自行扩展
- SQL监控：采用druid 监控数据库访问性能，默认用户名admin，密码123456
- 定时任务：整合Quartz做定时任务，加入任务日志，任务运行情况一目了然
- 代码生成：高灵活度一键生成前后端代码，减少百分之80左右的工作任务
- 邮件工具：配合富文本，发送html格式的邮件
- 免费图床：使用sm.ms图床，用作公共图片上传使用
- 七牛云存储：可同步七牛云存储的数据到系统，无需登录七牛云直接操作云数据
- 支付宝支付：整合了支付宝支付并且提供了测试账号，可自行测试

#### 项目结构
项目采用分模块开发方式，将通用的配置放在公共模块，```system```模块为系统核心模块也是项目入口模块，```logging``` 模块为系统的日志模块，```tools``` 为第三方工具模块，包含了图床、邮件、七牛云、支付宝，```generator``` 为系统的代码生成模块
- efadmin-common 公共模块
    - exception 项目统一异常的处理
    - mapper mapstruct的通用mapper
    - redis redis缓存相关配置
    - swagger2 接口文档配置
    - utils 系统通用工具类
- efadmin-system 系统核心模块（系统启动入口）
	- config 配置跨域与静态资源，与数据权限
	- modules 系统相关模块(登录授权、定时任务等)
- efadmin-logging 系统日志模块
- efadmin-tools 系统第三方工具模块
- efadmin-generator 系统代码生成模块

#### 系统预览
<table>
    <tr>
        <td><img src="https://i.loli.net/2019/05/18/5cdf77fa8144d68788.png"/></td>
        <td><img src="https://i.loli.net/2019/05/18/5cdf7763993e361778.png"/></td>
    </tr>
    <tr>
        <td><img src="https://i.loli.net/2019/05/18/5cdf7763971d453615.png"/></td>
        <td><img src="https://i.loli.net/2019/05/18/5cdf77632e85a60423.png"/></td>
    </tr>
    <tr>
        <td><img src="https://i.loli.net/2019/05/18/5cdf77632b4b090165.png"/></td>
        <td><img src="https://i.loli.net/2019/05/18/5cdf77639929277783.png"/></td>
    </tr>
    <tr>   
 <td><img src="https://i.loli.net/2019/05/18/5cdf78969adc389599.png"/></td>
    </tr>
</table>

#表说明:
   sys_        开头都为系统模块表;
   tbl_        都为业务模块表

# 代码生成器使用
    1.生成的SQL需要调整后再执行;
    2.配置中的模块名,不要包含efadmin-开头;
    3.不同的用户来使用需要注意前端项目的路径可能不同!
    4.

# 系统模块期望实现的效果:
    1.支持多条件高级搜索;
    2.支持列表头点击升降序及取消排序条件,支持列字段拖拽前后位置.支持可配置显示的字段;
    3.支持不同客户的OEM配置:比如logo,网址,背景,电话等信息;
    4.支持更换项目主题;
    5.
    
# 系统数据录入:
    1.尽量支持excel导入,手工录入太慢了
    2.
    3.
