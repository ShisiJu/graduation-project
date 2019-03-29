# git Bash ����Զ��linux


## ׼��

- �ͻ���
	- git bash 
- Զ�̷�����
	- ip �����ַ
	- username �û���
	- passphrase ����
	- ssh 

ssh (���û�а�װһ��)
����yum���ٰ�װssh

```
yum install shh

service sshd start
```

# ͨ������

```
ssh username@ip
```

����
```
ssh root@45.16.128.214
```
Ȼ���������뼴��


# ͨ��ssh


## �ڱ���������Կ

>.ssh/known_hosts �����֮ǰ��hostҪɾ��


![](ssh_files/2.jpg)


### ���ɹ�Կ��˽Կ

```
ssh-keygen -t rsa -C "xxxx@163.com"
```


֮��������ɵ��ļ�·��

```
Enter file in which to save the key (/c/Users/ASUS/.ssh/id_rsa):
```

- ���������,ֱ�ӻس���Ĭ�Ϸŵ�ǰ�û���.sshĿ¼�� (C:\Users\ASUS\.ssh)
- �������,��Ҫ����linux����·�� ( /e/vpn/ssh-key/public-key.txt )


```
Enter passphrase (empty for no passphrase):
Enter same passphrase again:
```

֮�����������������ظ�����

- �������ÿ�ζ���������,��ֱ�ӻس�(�ظ�����)
- �����ǿ�������,so ֱ������ͺ�

����ָ����Ŀ¼�»���2���ļ�
- id_rsa.pub ��Կ
- id_rsa ˽Կ


### Զ���������ù�Կ


- ����Ŀ¼ /root/.ssh ������Ȩ��

```
$ mkdir /root/.ssh
$ chmod 700 /root/.ssh
```


- �����ļ� / root/.ssh/authorized_keys������Ȩ��

authorized_keys ����Ź�Կ

```
$ vim /root/.ssh/authorized_keys
$ chmod 600 /root/.ssh/authized_keys
```

- vim û�еĻ�,��װһ��

```
yum -y install vim*
```

[vim���֪ʶ](http://www.runoob.com/linux/linux-vim.html)

vim ��I����༭ģʽ,��esc�˳�������ģʽ,������ģʽ������ `:` ���еײ������ʽ,
�ڵײ������ʽ�� ���� `:wq` ���ֲ��˳�

- ��id_rsa.pub�е����ݸ��Ƶ�authorized_keys��

�鿴һ��,�Ƿ���ȷ
```
cat /root/.ssh/authized_keys
```



### sshd_config

�п���Զ�̵�ssh���������ļ�
�����⵼��������sshҲ��ѯ������
������ȷ�����붼�޷���¼

vim /etc/ssh/sshd_config

/etc/ssh/ssh_host_rsa_key

�����е������޸�Ϊyes

```
PermitRootLogin yes
PubkeyAuthentication yes
ClientAliveCountMax 30
```

![sshd](ssh_files/1.jpg)

�ο�����
[ssh����linux](http://www.runoob.com/linux/linux-remote-login.html)
[Զ��linux](https://blog.csdn.net/zhougb3/article/details/78678135)
[root�û��޷�ͨ��ssh](https://help.aliyun.com/knowledge_detail/41487.html)


## ��װ���޸�ss

### ��װ
```
wget �Cno-check-certificate -O shadowsocks.sh https://raw.githubusercontent.com/teddysun/shadowsocks_install/master/shadowsocks.sh

chmod +x shadowsocks.sh

./shadowsocks.sh 2>&1 | tee shadowsocks.log

```


### �޸�
```
vim /etc/init.d/shadowsocks restart
```

#### ���û�

```
{   "server":"0.0.0.0",
    "server_port":8088,
    "local_address":"127.0.0.1",
    "local_port":1080,
    "password":"=k1MSma7jU?e=)$5",
    "timeout":300,
    "method":"aes-256-cfb",
    "fast_open":false
}
```

#### ���û�

```
{   "server":"0.0.0.0",
     "port_password": {
        "8080":"=k1MSma7jU?e=)$5",
        "8081": "k1MSma7jU"
    },
    "local_address":"127.0.0.1",
    "local_port":1080,
    "timeout":300,
    "method":"aes-256-cfb",
    "fast_open":false
}
```

Ϊʲôgit bash ����ֱ�������¼?
git bash ��¼���ǲ���putty����..



## scp
scp source target

scp root@45.76.168.244:/etc/ssh/sshd_config  /e/vpn/ssh-key

����Windows��ȡԶ��Linux�ļ�����Ҫ����Windows��bash����ߣ�ִ��

```
scp -P 8868 username@www.domain.com:/data/1.sh /d/data  
-P(ע��P�Ǵ�д)��Զ��ssh�˿�(һ��Ĭ����22)

���ش��ļ��е�Զ��Linux��
scp -rp /d/data username@www.domain.com:/data
-r �����ļ���  -p����ԭ�ļ��޸ģ�����ʱ���Ȩ�ޣ�modes����Ϣ
```
