```
docker pull redis
```

`--name` 指定名称

`-p` 主机端口:容器端口号

最后的`redis`是镜像名

```
docker run --name redis -d -p 6379:6370 redis
```

`-v $PWD/data:/data` 将主机中当前目录下的data挂载到容器的/data

`--appendonly yes` 在容器执行redis-server启动命令，并打开redis持久化配置


```
docker run -p 6379:6379 -v $PWD/data:/data  -d redis:3.2 redis-server --appendonly yes
```

