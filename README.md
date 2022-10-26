# SuperChen_API 平台

#### 介绍
这是一个提供API 的平台


### 项目在线地址
http://hh2022.cn


#### 软件架构
软件架构说明:

后端
Mysql
redis
springboot + mybatis-plus+thymeleaf
nginx

前端
bootstrap
jquery
ajax



#### 安装准备

1.  准备一台服务器
2.  配置java环境
3.  安装Mysql
4.  安装redis
5.  安装nginx
#### 使用说明

1.  修改配置文件中的mysql数据库配置
2.  修改配置文件中的redis数据库配置
3.  修改配置文件中的邮件配置
4.  配置nginx的动静分离和反向代理(8080端口)

#### 参与贡献

1.  Fork 本仓库
2.  新建 guest_API  分支
3.  提交代码
4.  新建 Pull Request

### 功能介绍
定位服务 \
调用第三方接口封装，提供一部分API \
基于数据库的API数据访问 \
天气查询\
生成二维码\
VIP视频解析\
个人网站访问量统计\
音乐获取\
随机图片\
随机笑话 

项目图片：
![主页](https://s1.ax1x.com/2022/07/23/jXa8Og.png "主页")
![登录](https://s1.ax1x.com/2022/07/23/jXaUkn.png "登录")
![解析](https://s1.ax1x.com/2022/07/23/jXadf0.png "解析")

#### 更新日志

2022/7/16 增加了登录和注册功能 \
2022/7/16 增加了网站提取文本功能API\
2022/7/17 对注册功能增加了 输入用户名后判断是否已被注册 \
2022/7/18 对登录注册的增加了加载动画 \
2022/7/18 对各大API 接口 进行了调用频率的限制 \
2022/7/19 登录后功能 增加了随机获取MP4短视频的API \
2022/7/19 增加了随机获取图片的API \
2022/7/20 增加了token 来认证 开放的API \
2022/7/20 开放了视频解析功能 \
2022/7/21 开放了获取音乐功能 \
2022/7/22 增加了登录滑动验证码功能 \
2022/7/22 增加了进入用户登录页面，判断是否登录，若已登录则自动跳转主页 \
2022/7/23 增加了临时token 可用临时token 调用API \
2022/7/30 修复了多用户不能正确判断用户名重复bug \
2022/8/10 更新了地位位置接口 \
2022/8/17 更新了访问量接口 \
2022/8/25 更新了图床接口 \
2022/8/27 增加了权限校验 \
2022/8/28 增加了接口限流 \
2022/8/31 增加了用户主页的公告 \
2022/9/2 新增忘记密码功能 \
2022/9/17 新增网站跳转链接生成api  \
2022/10/6 新增分布式锁，保证数据正确 \
2022/10/26 优化了代码 (判空) 
### 后续更新

集成SpringSecurity
用户登录限制
集成springCloud
集成 RabbitMq
注册邮箱验证码(为方便用户注册暂不考虑)
后台管理(正在开发)
微服务重构
。。。

### 使用api
1.登录后在用户主页获取token \
2.用获取到的token覆盖接口地址最后的token 即可

接口返回的JSON 中msg 是返回的主要数据

### 部署
目前有两种方法部署：\
1.docker \
2. jar 包

## jar包部署
1.安装java环境 (推荐jdk11) \
2.maven打包后，上传到服务器 \
3.执行 nohup java  -jar jar包名字 & 

## docker 部署
(后续更新)



### 留言
欢迎大家学习借鉴，觉得好的话，就给我一个star
谢谢了