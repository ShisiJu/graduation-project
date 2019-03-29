## STS spring boot app 启动报错

### 问题描述

控制台报错
```
错误: 代理抛出异常错误: java.net.MalformedURLException: Local host name unknown: 
java.net.UnknownHostException: 鐞氫笘鎬�: 琚世æ
```



### 收集方案

- 百度到的答案是

`C:\Windows\System32\drivers\etc` 中的host文件中加入解析的主机名 `鐞氫笘鎬�: 琚世æ` 

例如这样
```
127.0.0.1    鐞氫笘鎬�: 琚世æ
::1          鐞氫笘鎬�: 琚世æ
```

**但是对我没有效果**

这种情况,如果不是中文的话,网上有人就解决了


- 我怀疑主机名是不是中文乱码的问题

对我计算机的用户名和计算机名进行修改(都修改为 StevenJu),修改后成功


### 问题分析

在启动之后,看到日志中会打印

```
2019-03-28 09:06:10.045  INFO 9872 --- [           main] com.jss.app.PostTutorApplication         
: Starting PostTutorApplication on 
StevenJu with PID 9872 
(E:\IDE\git-workspace\graduation\graduation-project\code\java\post-tutor\target\classes
started by ASUS in 
E:\IDE\git-workspace\graduation\graduation-project\code\java\post-tutor)
```

是因为,tomcat启动时会尝试绑定你的 hostname , 而非 localhost

所以,hostname是中文,或者数字型的都会出现这种问题.修改一下用户名就行

或者在host文件中添加解析地址

参考:

[stackoverflow-malformedurlexception](https://stackoverflow.com/questions/20093854/jmx-agent-throws-java-net-malformedurlexception-when-host-name-is-set-to-all-num)