# efadmin
- A project based on eladmin back-end project transformation and enhancement
    The earliest based on the [2.1 版本优化，修复定时任务删除后还继续执行的bug] node of 2019-07-04!
- The source code of the eladmin project will be followed up periodically;

# The difference between this project and eladmin
- Use ejpa framework;
- Do not use JPA's ResponseEntity and paging, too cumbersome, use R instead of ResponseEntity, and use EPage to simplify paging data return;
- Multi-tenant supporting independent database and redis database mode;  // 2.1.0+
- Because of the use of the ejpa framework, there are the following new features on the original basis: see more：https://github.com/vip-efactory/ejpa-example
    - The basic CRUD template, that is, add, delete, modify, and check operations. The check here refers to normal paging, sorting and id query;
    - More complex advanced query with multiple conditions; --Advanced query more flexible than eladmin
    - Provides the function of checking the operation of persistent entity attributes;-Advanced query more flexible than eladmin
    - Provide interface internationalization function;
    - Automatic tracking records: creation time, update time, creator, updater;
    - Add employee component management;
    - Support Java 8 date and time type;   //2.2.0+  
    - Multi-table joint check cache linkage consistency based on observer mode; //2.2.0+  

<h1 style="text-align: center">EF-ADMIN Management System</h1>
<div style="text-align: center">

[![AUR](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](https://github.com/vip-efactory/efadmin/blob/master/LICENSE)
[![GitHub stars](https://img.shields.io/github/stars/vip-efactory/efadmin.svg?style=social&label=Stars)](https://github.com/vip-efactory/efadmin)
[![GitHub forks](https://img.shields.io/github/forks/vip-efactory/efadmin.svg?style=social&label=Fork)](https://github.com/vip-efactory/efadmin)

</div>

#### Project Description
A back-end management system based on Spring Boot 2.3.1, eJpa, JWT, Spring Security, Redis, Vue, separation of front and backends

#### Source (Github priority update)

|        | Backend source code                                 | Front-end source code                                    |
|:-------|:----------------------------------------|:-------------------------------------------|
| github | https://github.com/vip-efactory/efadmin | https://github.com/vip-efactory/efadmin-ui |
| gitee  | https://gitee.com/vip-efactory/efadmin  | https://gitee.com/vip-efactory/efadmin-ui  |

#### Experience address
##### Manage multi-tenant, you can manage the data sources of other tenants  
Because the bandwidth of the Alibaba Cloud server is only 1 M, that is, the theoretical maximum file transfer speed is 128 K/s, the login may be slower, please be aware.  
<https://efadmin.ddbin.com:1443/>  
Username Password：root/123456

Note: Because the https certificates of the following two tenants use efadmin.ddbin.com, when visiting, the browser will say that the certificate is invalid, just trust it!
##### Tenant 1
<https://t1.ddbin.com:1443/>  
Username Password：admin1/123456
##### Tenant 2
<https://t2.ddbin.com:1443/>  
Username Password：admin2/123456

#### Document
<https://docs.efactory.vip:1443/>

#### TODO
Dynamic configuration interface permissions;  
Code generation supports more constraints;  
Package the file upload and download module of OSS!  

#### Template syntax description for code generation   
FreeMarker Official documents:  
https://freemarker.apache.org/docs/index.html 

#### mapstruct Usage
mapstruct Official:https://mapstruct.org/

#### Main features
- Using the latest technology stack, the community is rich in resources.
- The project is modularized by function to improve development and testing efficiency.
- High-efficiency development, using the code generator to generate front-end and back-end code with one click
- Support data dictionary, can easily manage some states
- Support interface current limiting to avoid excessive pressure on the service layer caused by malicious requests
- Support interface-level function permissions and data permissions, and can customize operations
- Custom permission annotations and anonymous interface annotations can quickly intercept and release an interface
- Encapsulate some common front-end components: table data request, data dictionary, etc.
- Unified exception interception processing at the front and back ends, unified output exceptions, avoiding tedious judgments
####  System functions
- User management: provide user related configuration, after adding a user, the default password is 123456
- Role management: assign permissions and menus, and set data permissions for roles according to departments
- Menu management: dynamic menu routing has been implemented, the back-end can be configured, and multi-level menus are supported
- Department management: configurable system organization structure, tree form display
- Position management: configure the positions of each department
- Dictionary management: can maintain some commonly used fixed data, such as: status, gender, etc.
- Operation log: record user operation log
- Exception log: record the exception log to facilitate developers to locate errors
- SQL monitoring: use druid to monitor database access performance, the default user name is admin, and the password is 123456
- Timed tasks: Integrate Quartz to do timed tasks, add task logs, and the task running status is clear at a glance
- Code generation: One-click generation of front-end and back-end codes with high flexibility, reducing work tasks by about 80%
- Mail tool: with rich text, send html format mail
- Free picture bed: use sm.ms picture bed for uploading public pictures, the picture bed is not very stable, so it is not recommended to use it
- Qiniu Cloud Storage: Synchronize the data stored in Qiniu Cloud to the system, and directly operate cloud data without logging in to Qiniu Cloud
- Alipay payment: integrated Alipay payment and provided a test account, which can be tested by yourself

#### Project structure
The project adopts the development method of sub-modules by function, and puts the common configuration in the common module，```system```The module is the core module of the system and the entry module of the project.```logging``` The module is the log module of the system,```tools``` It is a third-party tool module, including image bed, email, Qiniu cloud, Alipay,```generator``` Generate modules for the system's code

- efadmin-common **Common module**
    - annotation **Custom annotations for the system**
    - aspect **Custom annotation aspect**
    - base **Provides general mapper for Entity, DTO base class and mapstruct**
    - config **Custom permission implementation, redis configuration, swagger configuration**
    - exception **Project unified exception handling**
    - utils **System general tools**
- efadmin-system **System core module (system startup entry)**
	- config **Configure cross-domain and static resources, and data permissions**
	    - thread **Thread pool related**
	- modules **System related modules (login authorization, system monitoring, timing tasks, etc.)**
- efadmin-logging **System log module**
- efadmin-tools **System third-party tool module**
- efadmin-generator **System code generation module**
    

#### Preview
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

#### Donation


#### Feedback
- QQ：601693868



