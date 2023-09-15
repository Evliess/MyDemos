# Tekton learning

## Prerequisite

- Ubuntu 20.04
- minikube v1.31.2
- Kubernetes 1.27.4
- Docker 24.0.4

## Installation

```
curl -k https://raw.githubusercontent.com/drriguz/knative-dockerhub-mirror/master/output/tektoncd-pipeline-v0.47.3-release.yaml -o tektoncd-pipeline-v0.47.3-release.yaml
curl -k https://raw.githubusercontent.com/drriguz/knative-dockerhub-mirror/master/output/tektoncd-dashboard-v0.37.0-release.yaml -o tektoncd-dashboard-v0.37.0-release.yaml

# triggers
curl -k https://raw.githubusercontent.com/drriguz/knative-dockerhub-mirror/master/output/tektoncd-triggers-v0.24.1-release.yaml -o tektoncd-triggers-v0.24.1-release.yaml
curl -k https://raw.githubusercontent.com/drriguz/knative-dockerhub-mirror/master/output/tektoncd-triggers-v0.24.1-interceptors.yaml -o tektoncd-triggers-v0.24.1-interceptors.yaml

kubectl apply -f tektoncd-pipeline-v0.47.3-release.yaml
kubectl apply -f tektoncd-dashboard-v0.37.0-release.yaml
kubectl apply -f tektoncd-triggers-v0.24.1-release.yaml
kubectl apply -f tektoncd-triggers-v0.24.1-interceptors.yaml
```

## Proxy minikube dashboard and tekton dashboard

```
kubectl proxy --port=8001 --address='0.0.0.0' --accept-hosts='^.*' &

#minikube dashboard
http://192.168.0.222:8001/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/#/customresourcedefinition?namespace=_all

#tekton dashboard
http://192.168.0.222:8001/api/v1/namespaces/tekton-pipelines/services/tekton-dashboard:http/proxy/ 

#stop kubectl proxy
ps -ef | grep kubectl
kill -9

kubectl port-forward service/el-hello-listener 8080

export all_proxy=http://192.168.0.105:7890
```


## Usage

### Create a git clone task
### Create a git clone task run
### Create a show readme task
### Create a pipeline
### Create a pipeline run


## Commands

```
alias k=kubectl
k delete taskrun $(k get taskrun | awk '{print$1}')
k delete pipelinerun $(k get pipelinerun | awk '{print$1}')

# https://kubernetes.io/docs/reference/kubectl/cheatsheet/#creating-objects
kubectl create -f - <<EOF
apiVersion: tekton.dev/v1beta1
kind: TaskRun
metadata:
  generateName: hello-task-run-
spec:
  taskRef:
    name: hello
  params:
  - name: username
    value: "Tekton"
EOF
```

### Send an event
```
curl -v -H 'content-Type: application/json' -d '{"username": "Tekton123"}' http://localhost:8080
```

## References
- [knative-dockerhub-mirror](https://github.com/drriguz/knative-dockerhub-mirror/tree/master/output)
- https://mp.weixin.qq.com/s/0STtKrlgYqpoqwMgb0QnoA
- https://developer.ibm.com/tutorials/build-and-deploy-a-docker-image-on-kubernetes-using-tekton-pipelines/
- https://github.com/drriguz/knative-dockerhub-mirror/blob/master/releases.txt
- https://zhuanlan.zhihu.com/p/562869904