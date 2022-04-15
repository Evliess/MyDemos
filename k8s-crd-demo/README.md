## CentOS

1. Download and setup
```
firewall-cmd --state
systemctl stop firewalld.service
systemctl disable firewalld.service
```
2. Set static ip

vi /etc/sysconfig/network-scripts/ifcfg-ens33

systemctl restart network
```
TYPE=Ethernet
PROXY_METHOD=none
BROWSER_ONLY=no
BOOTPROTO=static
DEFROUTE=yes
IPV4_FAILURE_FATAL=no
IPV6INIT=yes
IPV6_AUTOCONF=yes
IPV6_DEFROUTE=yes
IPV6_FAILURE_FATAL=no
IPV6_ADDR_GEN_MODE=stable-privacy
NAME=ens33
DEVICE=ens33
ONBOOT=yes
GATEWAY=192.168.112.2
IPADDR=192.168.112.200
NETMASK=255.255.255.0
DNS1=192.168.112.2
```

3. Update yun repo
- https://developer.aliyun.com/mirror/centos?spm=a2c6h.13651102.0.0.3e221b11qGawlj
```
mv /etc/yum.repos.d/CentOS-Base.repo /etc/yum.repos.d/CentOS-Base.repo.backup
curl -o /etc/yum.repos.d/CentOS-Base.repo https://mirrors.aliyun.com/repo/Centos-7.repo
```
4. Add a user
```
adduser jia
passwd jia
```
5. Add jia as sudo users
- https://cloud.tencent.com/developer/article/1721167
```
chmod -v u+w /etc/sudoers
vi /etc/sudoers

## Allow root to run any commands anywher 
root ALL=(ALL)  ALL 
jia ALL=(ALL)  ALL #这个是新增的用户
```

6. Add user to docker group
```
sudo usermod -aG docker $USER && newgrp docker
systemctl enable docker.service
```

7. Install kubectl on Linux
- https://kubernetes.io/docs/tasks/tools/install-kubectl-linux/
- https://kubernetes.io/docs/reference/kubectl/cheatsheet/
```
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
source <(kubectl completion bash)
alias k=kubectl
complete -F __start_kubectl k
```

8. Set default startup core
```
uname -r
rpm -qa |grep kernel
yum remove kernel-3.10.0-514.21.1.el7.x86_64
```

## Ubuntu

1. Download and setup
```
# 安装Vm Tools
https://blog.csdn.net/qq_34201858/article/details/108791068?spm=1001.2101.3001.6650.1&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1.pc_relevant_aa&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1.pc_relevant_aa&utm_relevant_index=2
https://www.jianshu.com/p/62e4bfa949e4
lsof -i
```
2. Setup firewall
```
sudo ufw disable
sudo systemctl status ufw
sudo systemctl start ufw
sudo ufw default allow outgoing
sudo ufw default deny incoming
```
3. Setup vim

```
sudo vi /etc/vim/vimrc.tiny
set nocompatible
set backspace=2

```

## Start minikube 
```
minikube delete
docker volume prune -f

minikube start
#Must enable --disable-filter=true
#Expose k8s api server
kubectl proxy --address='0.0.0.0' --accept-hosts='^*$' --disable-filter=true &

#Expose service
kubectl expose deployment nginx-deployment --type=LoadBalancer --name=nginx-service

#Generate ClusterIP
minikube tunnel --cleanup &
```

## Use Nginx proxy NodeIP:port
1. Restart nginx
```
sudo /etc/init.d/nginx reload
sudo systemctl restart nginx
```

2. sudo vi /etc/nginx/sites-enabled/default
```
server {
    #your host ip address
    listen 32196;
    client_max_body_size 20M;
      
    location / {
        #minikube ip:NodePort
        proxy_pass http://192.168.49.2:38905/;
        proxy_set_header Host      $host;
    }
}
```

## Create pod against k8s server API
```
curl --request POST http://172.31.81.91:8001/api/v1/namespaces/default/pods -s -w "resp_code:%{http_code}\n" -o /dev/null -H 'Content-Type: application/yaml' --data 'apiVersion: v1
kind: Pod
metadata:
  name: pod-example
spec:
  containers:
  - name: busybox
    image: busybox:latest
    command: ["/bin/sh", "-c"]
    args: ["sleep 10; touch /tmp/healthy; sleep 30000"]'
```

## Package a executable jar
```
mvn package -DskitTests
```

## Deploy
1. Build and Push docker image
```
docker build -t operator-demo:1.0 .
docker login -u waitplay
docker tag operator-demo:1.0 waitplay/operator-demo:1.0.2
docker push waitplay/operator-demo:1.0.2
```
2. Create service and clusterrole binding
```
kubectl apply dockerhub-secret.yaml
kubectl apply sa.yaml
kubectl clusterrolebinding.yaml
```
3. Deploy application

```
kubectl apply -f operator-deploy.yaml
```
4. Verify

```
kubectl get crd 
```

## Using nginx

> OS: Ubuntu 20.04.4

1. Download http://nginx.org/en/CHANGES-1.18
- http://nginx.org/download/nginx-1.18.0.tar.gz
```
curl -LO http://nginx.org/download/nginx-1.18.0.tar.gz
sudo tar zxvf nginx-1.18.0.tar.gz
```
2. Compile and Install
```
sudo ./configure --prefix=/usr/app/nginx
checking for OS
 + Linux 5.4.0-107-generic x86_64
checking for C compiler ... not found

./configure: error: C compiler cc is not found

./configure: error: the HTTP rewrite module requires the PCRE library.
You can either disable the module by using --without-http_rewrite_module
option, or install the PCRE library into the system, or build the PCRE library
statically from the source with nginx by using --with-pcre=<path> option.

sudo apt-get install gcc
sudo apt-get install libpcre3
sudo apt-get install libpcre3-dev
sudo apt-get install zlib1g
sudo apt-get install zlib1g-dev && sudo apt-get install make

sudo ./configure --prefix=/usr/app/nginx
sudo make && sudo make install
```

3. Setup firewall
```
sudo ufw disable
sudo systemctl status ufw
sudo systemctl start ufw
sudo ufw default allow outgoing
sudo ufw default deny incoming
```
4. Configure nginx system service

```
sudo vi /usr/lib/systemd/system/nginx.service

[Unit]
Description=nginx - web server After=network.target 
remote-fs.target nss-lookup.target 

[Service]

Type=forking
PIDFile=/usr/app/nginx/logs/nginx.pid
ExecStartPre=/usr/app/nginx/sbin/nginx -t -c /usr/app/nginx/conf/nginx.conf 
ExecStart=/usr/app/nginx/sbin/nginx -c /usr/app/nginx/conf/nginx.conf 
ExecReload=/usr/app/nginx/sbin/nginx -s reload 
ExecStop=/usr/app/nginx/sbin/nginx -s stop 
ExecQuit=/usr/app/nginx/sbin/nginx -s quit 
PrivateTmp=true 

[Install] 
WantedBy=multi-user.target 
```
5. Reload system service
```
sudo systemctl start nginx
sudo systemctl reload nginx
sudo systemctl enable nginx.service
```

## Reference links
- https://cloud.tencent.com/developer/article/1877046
- https://kubernetes.io/docs/tasks/administer-cluster/access-cluster-api/
- https://jamesdefabia.github.io/docs/user-guide/kubectl/kubectl_proxy/
- https://minikube.sigs.k8s.io/docs/handbook/accessing/
- https://digitalocean.com/community/tutorials/how-to-install-nginx-on-ubuntu-20-04
- https://docs.microsoft.com/en-us/troubleshoot/developer/webapps/aspnetcore/practice-troubleshoot-linux/2-2-install-nginx-configure-it-reverse-proxy
- https://www.linode.com/docs/guides/configure-firewall-with-ufw/
- https://blog.csdn.net/puhaiyang/article/details/105949613
- https://www.ibm.com/docs/en/cloud-paks/cp-management/1.2.0?topic=kubectl-using-service-account-tokens-connect-api-server
- https://mirrors.aliyun.com/centos/7/isos/x86_64/
- 