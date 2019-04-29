
# docker 

docker 配置 centos JDK mysql

## cenetos

```bash
docker pull centos
```


查看images 根据不同需要替换 IMAGE ID

```bash
docker images 
```

cenos 的 ID `9f38484d220f`

```bash
docker run --name JavaWeb -itv /root/software/:/mnt/software/ 9f38484d220f /bin/bash
```

`-itv` flag 会使你直接进入 docker 容器

`exit` 退出当前容器  Ctrl+P+Q进行退出容器(不stop)

`docker start JavaWeb` 启动JavaWeb这个容器 

`docker attach JavaWeb` 进入JavaWeb这个容器 

```
修改images名字
docker tag dda290db6637 jss/springboot:0.0.2
```


## jdk

[downloads-jdk8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

### 下载安装

```bash
安装 wget
yum -y install wget

切换安装目录
cd /usr/local/src

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


### 检查JDK

```bash
java -v
```


## mysql

### 安装

docker中centos需要启动systemd进程

需要特权,解决方法：


- 运行时加入 `privileged

使用 `--privileged` 会导致CPU占用率增加


`/usr/sbin/init` 是为了docker能使用 `systemctl` 的命令

`/bin/bash` 是指定docker要运行的bash

```
# 创建 名为JSB 的容器 给予它root权限 并引入 /usr/sbin/init
docker run -tid --privileged --name JSB jss/springboot:0.0.2  /usr/sbin/init
# 016c5fda1faa是JSB容器的ID
docker exec -it 016c5fda1faa /bin/bash
```

- dockerfile

```
FROM centos:7
ENV container docker
RUN (cd /lib/systemd/system/sysinit.target.wants/; for i in *; do [ $i == \
systemd-tmpfiles-setup.service ] || rm -f $i; done); \
rm -f /lib/systemd/system/multi-user.target.wants/*;\
rm -f /etc/systemd/system/*.wants/*;\
rm -f /lib/systemd/system/local-fs.target.wants/*; \
rm -f /lib/systemd/system/sockets.target.wants/*udev*; \
rm -f /lib/systemd/system/sockets.target.wants/*initctl*; \
rm -f /lib/systemd/system/basic.target.wants/*;\
rm -f /lib/systemd/system/anaconda.target.wants/*;
VOLUME [ "/sys/fs/cgroup" ]
CMD ["/usr/sbin/init"]
```


[](https://blog.csdn.net/wanglei_storage/article/details/48602717)

[docker在centos7中运行systemctl命令](https://blog.csdn.net/ijijni/article/details/82218608)

>centos 7 将mysql移除了,现在改为mariadb. 可以完全兼容mysql

```bash
首先需要安装 mariadb-server
# yum install -y mariadb-server
启动服务
# systemctl start mariadb.service
添加到开机启动
# systemctl enable mariadb.service
登录
mysql -u root -p
```



### 设置密码

```bash
MariaDB [(none)]>  set password for 'root'@'localhost' =password('password');
```


### 配置

```bash
vim /etc/my.cnf
```

#### 加入编码格式


mysql中的字符集设置主要包括

客户端字符集，返回结果字符集，服务器接收字符集几种。

```
MariaDB [(none)]> show VARIABLES like 'character%';
```

```
```

```
default-character-set =utf8
```

#### 远程连接



```bash
MariaDB [(none)]> grant all privileges on *.* to root@'%'identified by 'password';
MariaDB [(none)]> create user 'username'@'%' identified by 'password';
```

参考文章

[JDK与Tomcat](https://www.jianshu.com/p/49c711bd424d)

[CentOS 7 巨大变动之 systemd 取代 SysV的Init](https://blog.csdn.net/smstong/article/details/39317491)

[[mysql]错误解决之"Failed to start MySQL Server"](https://blog.csdn.net/zll_0405/article/details/85637542)

[mariadb](https://www.cnblogs.com/starof/p/4680083.html)

[安装mysql](https://www.linuxidc.com/Linux/2019-03/157643.htm)


[Docker 清理命令](http://www.runoob.com/w3cnote/docker-clear-command.html)

[docker-image container 基本操作 -常用命令](https://www.cnblogs.com/xiadongqing/p/6144053.html)