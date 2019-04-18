# TCP/IP

## 四层

- 数据链路层
- 网络层
- 传输层
- 应用层

在应用层中隐藏所有的物理细节

IP是不可靠的传输协议

快递的例子

利用了超时重传,反馈,确认

TCP
- 建立在IP不可靠之上的可靠的传输层协议
- 全双工
- 

UDP 是不可靠的,丢包也不会重传(语音,视频)

从应用层的用户数据到以太网的数据并传输出去.每经过一层都会留下每一层的印记.

源端口是不确定的,目的端口是确定的(0~1024)


## 以太网

- 结构简单
- CSMA/CD
- 10Mb/S 地址为48bit
- 最少46bit,不足补齐

ethernet 头部

- 目的IP
- 源IP
- 协议类型

### ARP

从逻辑地址到mac地址


NAT  any?


MTU最大传输单元


## IP


无连接,不可靠.

IP发包时,很可能丢包,而且接包时也可能不是按照原来的顺序接受到

TCP就需要将IP传来的包,进行排序

linux 
```
ifconfig

netstat -an
```

win
```
ipconfig
```



### IP头部

- 长度4bit 表明最大长度为 15 行(一行4B,因此最大IP长度为60B)