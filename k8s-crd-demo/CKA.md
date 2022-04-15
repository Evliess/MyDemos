#CKA
- https://kubernetes.io/docs/reference/kubectl/cheatsheet/#bash
## Pre Setup
```
alias k=kubectl                         # will already be pre-configured
export do="--dry-run=client -o yaml"    # k get pod x $do
export now="--force --grace-period 0"   # k delete pod x $now
```

## Vim Setup

vi ~/vimrc
```
set tabstop=2
set expandtab
set shiftwidth=2
```

## Questions

1. Get all context
```
k config get-contexts
k config get-contexts -o name

# Get current context
k config current-context
cat ~/.kube/config | grep current | sed -e "s/current-context: //"
```
2. Create a pod with image busybox named pod1
```
k run pod1 --image=busybox:1.31.1 $do
```
3. Check for the most common resources which manage Pods
```
k get deploy,ds,sts

k -n project-c13 scale sts o3db --replicas 1 --record
```

4. Create pod with label
```
k run am-i-ready --image=nginx:1.16.1-alpine --labels="id=cross-server-ready" $do
```

5. Kubectl sorting
```
k get pod -A --sort-by=.metadata.creationTimestamp
```

6. Storage, PV, PVC, Pod volume
- https://kubernetes.io/docs

7. Node and Pod Resource Usage

```
k top -h
k top node
```

8. Get Master Information
- https://cloud.tencent.com/developer/article/1357618
```
ps -aux | grep kubelet
```

9. Kill Scheduler, Manual Scheduling
```
cd /etc/kubernetes/manifests/
```

10. RBAC ServiceAccount Role RoleBinding

```
k create ns test

k -n test create sa processor $do
k -n test create role processor --verb=create --resource=secret --resource=configmap $do
k -n test create rolebinding processor --role processor --serviceaccount test:processor $do
k auth can-i -h
k -n test auth can-i create secret --as system:serviceaccount:test:processor
```

11. DaemonSet on all Nodes

```
k -n test create deployment --image=busybox:latest ds-busybox $do

apiVersion: apps/v1
kind: Deployment
metadata:
  creationTimestamp: null
  labels:
    app: ds-busybox
  name: ds-busybox
  namespace: test
spec:
  replicas: 1
  selector:
    matchLabels:
      app: ds-busybox
  template:
    metadata:
      creationTimestamp: null
      labels:
        app: ds-busybox
    spec:
      containers:
      - image: busybox:latest
        name: busybox
        resources:
          cpu: 10m   #add
          memory: 10mi #add
          
      toleration:
      - effect: NoSchedule   #add
        key: node-role.kubernetes.io/master #add  why????
```

12. Deployment on all Nodes

```

```