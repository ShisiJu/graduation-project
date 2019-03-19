# win10 家庭版

## 下载

建议去镜像下载

注意,个人使用下载社区版(以ce为后缀的)

[docker-mirrors-地址](http://mirrors.aliyun.com/docker-toolbox/windows/docker-toolbox/)

![docker-list](install_files/3.jpg)


## 安装时

如果自己电脑上安装了`git`就不要再选了

![注意git](install_files/1.jpg)

安装时,会向你请求一些权限,恩,肯定选同意

## 安装后

安装后,会有三个图标在桌面上


![installed-icons](install_files/4.jpg)

点击`Docker Quickstart Terminal`运行

---

如果你的git没有在`C:\Program Files\Git`的话,点击后会让你选择bash.exe,这个文件在Git\bin下面

![](install_files/5.jpg)

需要把目标的引用改成你本地的bash.exe

```
E:\IDE\git\Git\bin\bash.exe --login -i "E:\IDE\docker\DockerToolbox\start.sh"
```


## 配置

### CPU开启虚拟化

确定CPU开启了虚拟化,CPU的虚拟化功能在开机时进入BIOS中设置开启

![CPU的虚拟化](install_files/6.jpg)


### iso 文件

如果在docker里面加载iso文件(boot2docker.iso)的话,会很慢.不如去github上下载,再放到
`C:\Users\ASUS\.docker\machine\cache` 里面

当然也可以修改一下配置文件(这个配置文件夹和文件**可能没有新建即可**)
另外ProgramData文件夹是隐藏的,选择查看隐藏文件即可

![mirror-config](install_files/2.jpg)

window 在 `%programdata%\docker\config\daemon.json` 里加入

`registry-mirrors`是docker镜像地址,graph是存放iso镜像的地址

```json
{
  "registry-mirrors": ["http://hub-mirror.c.163.com"],
  "graph":"E:\\IDE\\docker\\iso"
}
```


--config-file
```
docker --config "E:\\IDE\\docker\\iso"
```

## 成功

成功之后会出现小鲸鱼

![成功小鲸鱼](install_files/2.jpg)

查看 docker的信息

```bash
docker info
```


参考文章

[windows-docker-install](http://www.runoob.com/docker/windows-docker-install.html)
[CPU虚拟化功能](https://blog.csdn.net/hao_kkkkk/article/details/79853752)
[docker下载镜像](https://blog.csdn.net/u013948858/article/details/80811986)