# mongo

以mongo为例,在win10下运行docker

## 下载和安装

1.下载：

```
docker pull mongo 
```

2.查看：（会看到自己下载的镜像）

```
docker images 
```

3.去创建一个docker卷 ：

```
docker volume create mongodata
```

4.查看一下你的卷路径（确保创建成功）：

```
docker volume inspect mongodata
```

如果成功会出现以下内容：

```json
[
    {
        "CreatedAt": "2019-02-28T09:12:08Z",
        "Driver": "local",
        "Labels": {},
        "Mountpoint": "/mnt/sda1/var/lib/docker/volumes/mongodata/_data",
        "Name": "mongodata",
        "Options": {},
        "Scope": "local"
    }
]
```


5.这个时候就可以运行

```
docker run --name mongo -p 27017:27017 -v mongodata:/data/db -e MONGO_INITDB_ROOT_USERNAME=root -e MONGO_INITDB_ROOT_PASSWORD=root -d mongo
```

结果：

```
1500417691d8f0ea71c2ddbe589f27e2b38cbf92e5fcece6683e5bf47e8cdf0c
```

6.确认一下是否启动：

```
docker ps
```

结果：

```
CONTAINER ID IMAGE COMMAND CREATED
     STATUS PORTS NAMES
1500417691d8 mongo "docker-entrypoint.s" 10 minutes ago
    Up 10 minutes 0.0.0.0:27017->27017/tcp mongo
```



## 运行

```
docker exec -it mongo bash
```


连接mongo

```
mongo -u root -p root
```


使用`graduation`数据库
```
use graduation;
```


创建用户
注意 createUser是函数要加上 `()` 最后别忘记 `;` 结束
```
db.createUser(
	{
	user:"jss",
	pwd:"jss",
	roles:[
	 {role:"readWrite",db:"graduation"}
	 ]
	}
);
```
