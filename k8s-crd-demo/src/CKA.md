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
k run pod1 --image=busybox:latest $do
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