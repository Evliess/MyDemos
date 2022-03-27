## Ubuntu
```
# 安装Vm Tools
https://blog.csdn.net/qq_34201858/article/details/108791068?spm=1001.2101.3001.6650.1&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1.pc_relevant_aa&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1.pc_relevant_aa&utm_relevant_index=2
https://www.jianshu.com/p/62e4bfa949e4

lsof -i
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
```

sudo /etc/init.d/nginx reload
sudo systemctl restart nginx

sudo systemctl status ufw
sudo systemctl start ufw
sudo ufw default allow outgoing
sudo ufw default deny incoming

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

## Reference links
- https://cloud.tencent.com/developer/article/1877046
- https://kubernetes.io/docs/tasks/administer-cluster/access-cluster-api/
- https://jamesdefabia.github.io/docs/user-guide/kubectl/kubectl_proxy/
- https://minikube.sigs.k8s.io/docs/handbook/accessing/
- https://digitalocean.com/community/tutorials/how-to-install-nginx-on-ubuntu-20-04
- https://docs.microsoft.com/en-us/troubleshoot/developer/webapps/aspnetcore/practice-troubleshoot-linux/2-2-install-nginx-configure-it-reverse-proxy
- https://www.linode.com/docs/guides/configure-firewall-with-ufw/
- https://blog.csdn.net/puhaiyang/article/details/105949613