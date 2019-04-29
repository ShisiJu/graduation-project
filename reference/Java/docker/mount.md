[详解Docker挂载本地目录及实现文件共享](https://blog.csdn.net/magerguo/article/details/72514813)
[Docker：常用命令 与 挂载目录](https://www.jianshu.com/p/9fd2f77001a3)
`-v ` 进行挂载


```bash
docker inspect 容器名
```


```json
"Mounts":[
        {
            "Name": "test1",
            "Source": "/var/lib/docker/volumes/test1/_data",
            "Destination": "/soft",
            "Driver": "local",
            "Mode": "z",
            "RW": true
        }
    ]
```


数据卷

docker run -it --volumes-from dataVol ubuntu64 /bin/bash
