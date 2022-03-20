## Start minikube 
```
minikube delete
docker volume prune -f

minikube start
kubectl proxy --address='0.0.0.0' --accept-hosts='^*$'


"C:\\Program Files\\jdk11\\bin\\keytool.exe" -import -alias minikube -file client.crt -keystore "C:\\Program Files\\jdk11\\lib\\security\\cacerts"


```


```
curl --request POST http://192.168.88.128:8001/api/v1/namespaces/default/pods -s -w "resp_code:%{http_code}\n" -o /dev/null -H 'Content-Type: application/yaml' --data 'apiVersion: v1
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


