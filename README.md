# citizenstudy-admin
学习平台后台<br>


###平台说明 
在线学习平台，平台采用微服务架构，结合多住户管理模式向外提供服务。

###技术选型
* 框架 spring cloud<br>
* 持久化 MyBatis-Plus<br>
* 注册中心与配置中心  nacos<br>
* 队列 待定<br>
* 缓存 redis<br>
* 数据库 mysql<br>
* 安全 spring security<br>

###模块说明
* common:基础模块，提供常用的基类<br>
* auth:租户、用户、组织、区划、权限、角色、授权、认证<br>
* center:基于nacos提供注册中心与配置中心<br>
* gateway:基于spring cloud gateway提供网管，负责负载均衡、认证与鉴权<br>

### 端口列表
* 6888 网关入口
* 8848 nacos
* 5001 auth模块

### 开发计划
1. (auth)提供多租户下的业务模型与接口
2. (center)接入nacos的注册中心与配置中心
3. (gateway)接入spring cloud gateway

### 系统初始化配置说明
[点击这里] (citizenstudy-deploytool/README.MD).


















