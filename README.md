# Vue-SpringBoot-Template
## 简介

基于Vue + Spring Boot&Mybatis + Maven分模块 搭建一个快速开发模板，前后端分离，采用JWT做用户验证，参考了其他开源项目，特此感谢，侵删

参考：

前端Vue模板：[vue-element-admin](https://github.com/PanJiaChen/vue-element-admin)

后端模板：[spring-boot-api-project-seed](https://github.com/lihengming/spring-boot-api-project-seed)，权限验证方案：[Spring Boot Vue Admin](https://github.com/Zoctan/spring-boot-vue-admin)

这里先简单描述一下，后期再补齐相关说明

在此基础上按自己的意愿进行了修改，到时会把改进的部分描述出来

MyBatis自动生成、数据库的表、包括改进的时候查到的一些资料、其他文档等后续都会贴上来...

现在项目可以正常使用，正在逐步改进中，欢迎一起讨论



## 快速开始

```java
这里先默认node和jdk环境已经配好
1.下载或clone该repo
2.进入appAdminView，打开cmd，运行命令 npm install, 下载相关依赖模块
3.完了之后npm run dev启动前端服务
4.数据库创建相应的表
5.idea导入appServer，配置相关环境之后，运行Application中的main方法即可

前端入口：http://localhost:9999
```



