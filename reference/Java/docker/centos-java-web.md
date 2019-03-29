# docker centos下配置JDK

参考文章 但是有许多细节有误 

[JDK与Tomcat](https://www.jianshu.com/p/49c711bd424d)

## cenetos

```
docker pull centos
```


查看images 根据不同需要替换 IMAGE ID

```
docker images 
```

cenos 的 ID `9f38484d220f`

```
docker run --name JavaWeb -itv /root/software/:/mnt/software/ 9f38484d220f /bin/bash
```

`-itv` flag 会使你直接进入 docker 容器

`exit` 退出当前容器 
`docker start JavaWeb` 启动JavaWeb这个容器 
`docker attach JavaWeb` 进入JavaWeb这个容器 



## jdk下载地址

[downloads-jdk8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)


```
安装 wget
yum -y install wget

下载 JDK8
wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" https://download.oracle.com/otn-pub/java/jdk/8u201-b09/42970487e3af4f5aa5bca3f542482c60/jdk-8u201-linux-x64.tar.gz

解压
tar -zxvf jdk-8u201-linux-x64.tar.gz

重命名JDK
mv jdk1.8.0_201 jdk8

安装vim
yum  -y install vim

查看profile 并加入
vim /etc/profile

增加下面内容到该profile文件最后
export JAVA_HOME=/usr/local/src/jdk8
export PATH=$JAVA_HOME/bin:$PATH  
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar

使环境生效
source /etc/profile
```


## 检查JDK

```
java -v
```





