# efadmin
- 一个基于eladmin后端项目改造增强的项目
    最早基于2019-07-04的[2.1 版本优化，修复定时任务删除后还继续执行的bug]节点！
- 会阶段性跟进efadmin项目的源码;
- 本项目会尽可能兼容原项目的功能.

# 本项目与eladmin的差异
- 使用ejpa框架
- 不使用JPA的ResponseEntity及分页，太繁琐了，用R代替了ResponseEntity，分页数据返回使用EPage简化
- 支持独立数据库及redis数据库模式的多租户;  2.1.0+
- 因为使用ejpa框架，在原来的基础上有如下的新特性：更多请参见：https://github.com/vip-efactory/ejpa-example
    - 基本的CRUD模板，即增删改查操作，此处的查是指正常的分页、排序及id查询；
    - 较复杂的多条件的高级查询； --比eladmin更加灵活的高级查询
    - 提供对持久化实体属性操作检查功能；--比eladmin更加灵活的高级查询
    - 提供接口的国际化功能；
    - 自动跟踪记录的：创建时间、更新时间、创建人、更新人
    - 增加员工组件管理

<h1 style="text-align: center">EF-ADMIN 后台管理系统</h1>
<div style="text-align: center">

[![AUR](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](https://github.com/vip-efactory/efadmin/blob/master/LICENSE)
[![GitHub stars](https://img.shields.io/github/stars/vip-efactory/efadmin.svg?style=social&label=Stars)](https://github.com/vip-efactory/efadmin)
[![GitHub forks](https://img.shields.io/github/forks/vip-efactory/efadmin.svg?style=social&label=Fork)](https://github.com/vip-efactory/efadmin)

</div>

#### 项目简介
一个基于 Spring Boot 2.2.6 、 eJpa、 JWT、Spring Security、Redis、Vue、的前后端分离的后台管理系统

**账号密码：** `admin/123456` (默认密码都是123456)

#### 项目源码

|        | 后端源码                                 | 前端源码                                    |
|:-------|:----------------------------------------|:-------------------------------------------|
| github | https://github.com/vip-efactory/efadmin | https://github.com/vip-efactory/efadmin-ui |
| gitee  | https://gitee.com/vip-efactory/efadmin  | https://gitee.com/vip-efactory/efadmin-ui  |

#### 体验地址
##### 管理多租户,可以管理其他租户的数据源  
因阿里云服务器宽带仅1M,即理论最大128K/s文件传输速度,因此登录可能会慢一些，请知悉。  
<https://efadmin.ddbin.com/>  
用户名密码：root/123456

注意:因为下面两个租户的https证书使用的是efadmin.ddbin.com的，所以访问时，浏览器会说证书无效，信任即可!
##### 租户1
<https://t1.ddbin.com/>  
用户名密码：admin1/123456
##### 租户2
<https://t2.ddbin.com/>  
用户名密码：admin2/123456

#### 文档说明
<http://docs.efactory.vip/>

#### TODO
动态配置接口权限
基于观察者模式的多表连查缓存的一致性处理
代码生成支持更多的约束条件

#### 主要特性
- 使用最新技术栈，社区资源丰富。
- 项目按功能模块化，提升开发，测试效率。
- 高效率开发，使用代码生成器可以一键生成前后端代码
- 支持数据字典，可方便的对一些状态进行管理
- 支持接口限流，避免恶意请求导致服务层压力过大
- 支持接口级别的功能权限与数据权限，可自定义操作
- 自定义权限注解与匿名接口注解，可快速对某一接口拦截与放行
- 对一些常用的前端组件封装：表格数据请求、数据字典等
- 前后端统一异常拦截处理，统一输出异常，避免繁琐的判断
####  系统功能
- 用户管理：提供用户的相关配置，新增用户后，默认密码为123456
- 角色管理：对权限与菜单进行分配，可根据部门设置角色的数据权限
- 菜单管理：已实现菜单动态路由，后端可配置化，支持多级菜单
- 部门管理：可配置系统组织架构，树形表格展示
- 岗位管理：配置各个部门的职位
- 字典管理：可维护常用一些固定的数据，如：状态，性别等
- 操作日志：记录用户操作的日志
- 异常日志：记录异常日志，方便开发人员定位错误
- SQL监控：采用druid 监控数据库访问性能，默认用户名admin，密码123456
- 定时任务：整合Quartz做定时任务，加入任务日志，任务运行情况一目了然
- 代码生成：高灵活度一键生成前后端代码，减少百分之80左右的工作任务
- 邮件工具：配合富文本，发送html格式的邮件
- 免费图床：使用sm.ms图床，用作公共图片上传使用，该图床不怎么稳定，不太建议使用
- 七牛云存储：可同步七牛云存储的数据到系统，无需登录七牛云直接操作云数据
- 支付宝支付：整合了支付宝支付并且提供了测试账号，可自行测试

#### 项目结构
项目采用按功能分模块开发方式，将通用的配置放在公共模块，```system```模块为系统核心模块也是项目入口模块，```logging``` 模块为系统的日志模块，```tools``` 为第三方工具模块，包含了图床、邮件、七牛云、支付宝，```generator``` 为系统的代码生成模块

- efadmin-common 公共模块
    - annotation 为系统自定义注解
    - aspect 自定义注解的切面
    - base 提供了Entity、DTO基类和mapstruct的通用mapper
    - config 自定义权限实现、redis配置、swagger配置
    - exception 项目统一异常的处理
    - utils 系统通用工具类
- efadmin-system 系统核心模块（系统启动入口）
	- config 配置跨域与静态资源，与数据权限
	    - thread 线程池相关
	- modules 系统相关模块(登录授权、系统监控、定时任务等)
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

#### 项目捐赠


#### 反馈交流
- QQ交流群：601693868



