# blog

#### 介绍

基于java的个人博客后台服务系统,该服务只向前端提供请求接口，不对UI层进行实现，该系统的所有的接口，需要AJAX进行请求，返回的数据类型为JSON数据类型，该系统已经开放了跨域功能，前台可以不用配置跨域

#### 软件架构

  主要技术栈 ：spring  +  springMVC +  spirngboot  +  mysql  +  redis  +  JWT  +  mail  +  mybatis-plus  +  mongoDB  +  swagger

#### 安装教程

**第一步：**

```bash
git clone https://gitee.com/joinhanren/blog.git
```

**第二步：**

​	打开idea

**第三步：**

​	导入项目

**第四步：**

​	启动项目

#### 使用说明

​	1.尽量采用 mysql 8.0以上版本

​	2.maven 使用 Apache Maven 3.8.4 以上版本

​	3. JDK 需要使用 java version "13.0.2"  以上版本，（温馨提示，JDK尽量要高于13.0.2，防止项目启动时报不必要的异常）

​	4.要想使用 Mail 邮箱发送服务，需要在项目中 application.yaml  文件中配置自己邮箱的key 

```yaml
    username: 123456789@qq.com//自己的邮箱
    password: dadfggedfadgnk//自己邮箱的key
```

**注：password 这个配置中，并不是自己邮箱的密码，是邮箱生成的一个key**

5.静态文件服务器的使用（**更换为自己的密钥和域名**）

```java
public static final String url =""//个人域名，或者服务分发的域名
private static final String accessKey =//AS key
 private static final String secretKey //个人密钥
```



### 更新日志

v1.0.1

- 实现登录功能
- 实现了注册功能
- 邮箱验证码发送功能
- 使用JWT技术对用户的验证
- 新增用户注册时对用户名的验证功能



v1.0.2

- 新增文章列表的显示，用户在登录和未登录的情况下可以查看文章列表

- 新增文章查看文章功能，用户点击文章列表可以对文章进行查看

  

v1.0.3

- 添加了 swagger 技术，添加了swagger配置文档
- 优化了在高并发的情况下查看文章，数据库会存在写入数据的压力，会对浏览文章造成影响
- 新增了文章评论模块
- 新增查看文章评论列表
- 新增添加评论
- 新增删除评论
- 新增查看子评论（还未测试）
- 新增评论点赞（还未测试）

v1.0.4

- 新增日志功能

- 新增静态文件服务，存储静态文件，降低服务器压力

- 新增用户头像上传修改功能

  

### 版权声明

当前项目是后台完整功能代码 , 但是仍然仅支持个人演示测试 , 不包含线上使用 ，仅供于学习交流，禁止一切商用行为！ 使用本软件时,请遵守当地法律法规,任何违法用途一切后果请自行承担！！！

