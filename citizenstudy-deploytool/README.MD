## 提供一个工具，方便进行配置与部署。<br>
将依赖软件放入到depsoftware后，执行build.xml中的对应任务。减少手工配置环境的时间。<br>

## 提取依赖软件
所有依赖软件都会放入百度网盘，可以下载后，放入对应文件夹，然后运行build对应的指令即可。<br>
链接: https://pan.baidu.com/s/1gKk5cFzWc7Ej6vCScrMbnA<br> 
提取码: ch72<br>

## 执行自动解压与软件配置
自动执行采用ant实现，可查看citizenstudy-deploytool\build.xml了解自动化过程中的处理
* ant REALL       重新生成mysql与nacos
* ant reNacos     重新生成nacos
* ant reMysql     重新生成mysql
* ant initdb      重新生成所需要的数据库（需要保证数据库已启动，端口3306，用户/密码：root/root）

#### 应用启动
* 启动mysql<br>
  进入 citizenstudy-deploytool\deploy\mysql  目录（必须在该目录运行），执行mysqlStart.bat<br>
  启动后，就可以使用ant initdb进行数据库初始化了。
  
* 启动nacos<br>
   nacos默认配置为基于数据库启动，故需要先启动数据库，并初始化<br>
   然后进入 citizenstudy-deploytool\deploy\nacosstand\bin 目录（必须在该目录运行），执行startup.cmd

* 注入应用配置到nacos<br>
   nacos启动完成后，执行NacosClientPushConfig.java，自动发布当前所有后台工程的配置到nacos的配置中心。<br>
   因为nacos上维护配置文件比较麻烦，而且不支持检索。
   



