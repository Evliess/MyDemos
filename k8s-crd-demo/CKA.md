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
```shell
set tabstop=2
set expandtab
set shiftwidth=2
```

## Questions

1. Get all context
```shell
k config get-contexts
k config get-contexts -o name

# Get current context
k config current-context
cat ~/.kube/config | grep current | sed -e "s/current-context: //"
```
2. Create a pod with image busybox named pod1
```shell
k run pod1 --image=busybox:1.31.1 $do
```
3. Check for the most common resources which manage Pods
```shell
k get deploy,ds,sts

k -n project-c13 scale sts o3db --replicas 1 --record
```

4. Create pod with label
```shell
k run am-i-ready --image=nginx:1.16.1-alpine --labels="id=cross-server-ready" $do
```

5. Kubectl sorting
```shell
k get pod -A --sort-by=.metadata.creationTimestamp
```

6. Storage, PV, PVC, Pod volume
- https://kubernetes.io/docs

7. Node and Pod Resource Usage

```shell
k top -h
k top node
```

8. Get Master Information
- https://cloud.tencent.com/developer/article/1357618
```shell
ps -aux | grep kubelet
```

9. Kill Scheduler, Manual Scheduling
```shell
cd /etc/kubernetes/manifests/
```

10. RBAC ServiceAccount Role RoleBinding

```shell
k create ns test

k -n test create sa processor $do
k -n test create role processor --verb=create --resource=secret --resource=configmap $do
k -n test create rolebinding processor --role processor --serviceaccount test:processor $do
k auth can-i -h
k -n test auth can-i create secret --as system:serviceaccount:test:processor
```

11. DaemonSet on all Nodes

```yaml
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

* Way1: Using podAntiAffinity*
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  labels:
    app: nginx
    id: very-important
  name: nginx
  namespace: test
spec:
  replicas: 3
  selector:
    matchLabels:
      app: nginx
      id: very-important
  template:
    metadata:
      labels:
        app: nginx
    spec:
      containers:
      - image: nginx
        name: nginx
      - image: busybox
        name: busybox
      affinity:
        podAntiAffinity:
          requiredDuringSchedulingIgnoredDuringExecution:
          - labelSelector:
              matchExpressions:
              - key: id
                operator: In
                values:
                - very-important
            topologyKey: topology.kubernetes.io/zone  #node labels
```
* Way2: Using topologySpreadConstraints*

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  labels:
    app: nginx
    id: very-important
  name: nginx
  namespace: test
spec:
  replicas: 3
  selector:
    matchLabels:
      app: nginx
      id: very-important
  template:
    metadata:
      labels:
        app: nginx
    spec:
      containers:
      - image: nginx
        name: nginx
      - image: busybox
        name: busybox
      topologySpreadConstraints:                 # add
      - maxSkew: 1                               # add
        topologyKey: kubernetes.io/hostname      # add
        whenUnsatisfiable: DoNotSchedule         # add
        labelSelector:                           # add
          matchLabels:                           # add
            id: very-important
```
13. Multi Containers and Pod shared Volume
- https://kubernetes.io/docs/concepts/storage/volumes/
```shell
k -n test create deploy --image=busybox:1.31.1 busybox $do
```

```yaml
apiVersion: v1
kind: Pod
metadata:
  creationTimestamp: null
  labels:
    run: multi-container-playground
  name: multi-container-playground
spec:
  containers:
  - image: nginx:1.17.6-alpine
    name: c1                                                                      # change
    resources: {}
    env:                                                                          # add
    - name: MY_NODE_NAME                                                          # add
      valueFrom:                                                                  # add
        fieldRef:                                                                 # add
          fieldPath: spec.nodeName                                                # add
    volumeMounts:                                                                 # add
    - name: vol                                                                   # add
      mountPath: /vol                                                             # add
  - image: busybox:1.31.1                                                         # add
    name: c2                                                                      # add
    command: ["sh", "-c", "while true; do date >> /vol/date.log; sleep 1; done"]  # add
    volumeMounts:                                                                 # add
    - name: vol                                                                   # add
      mountPath: /vol                                                             # add
  - image: busybox:1.31.1                                                         # add
    name: c3                                                                      # add
    command: ["sh", "-c", "tail -f /vol/date.log"]                                # add
    volumeMounts:                                                                 # add
    - name: vol                                                                   # add
      mountPath: /vol                                                             # add
  dnsPolicy: ClusterFirst
  restartPolicy: Always
  volumes:                                                                        # add
    - name: vol                                                                   # add
      emptyDir: {}                                                                # add
status: {}
```

14. Find out Cluster Information

```shell
# Which Networking (or CNI Plugin) is configured and where is its config file?
find /etc/cni/net.d/

```

15. Cluster Event Logging

```shell
kubectl get events -A --sort-by=.metadata.creationTimestamp
```

16. Namespaces and Api Resources

```shell
k api-resources
k api-resources --namespaced -o name
```

17. Find Container of Pod and check info

```shell
k -n test run busybox --image=busybox:1.31.1 --labels "pod=container,container=pod"
k get pod nginx -o jsonpath="{.spec.nodeName}"
```

18. Fix Kubelet

```shell
/etc/systemd/system/kubelet.service.d
```

19. Create Secret and mount into Pod
- https://kubernetes.io/docs/tasks/configmap-secret/managing-secret-using-kubectl/
- https://kubernetes.io/docs/concepts/configuration/secret/

```shell
# create
k -n test create secret generic db-user-pass --from-literal=username=dev --from-literal=password=passw0rd
# decode
k -n test get secret db-user-pass -o yaml
echo cGFzc3cwcmQ= | base64 --decode
```

20. Update Kubernetes Version and join cluster
21. Create a Static Pod and Service
- https://kubernetes.io/docs/reference/generated/kubectl/kubectl-commands#expose
```shell
cd /etc/kubernetes/manifests/
```

22. Check how long certificates are valid

```shell
openssl x509  -noout -text -in /etc/kubernetes/pki/apiserver.crt | grep Validity -A2
```

23. Kubelet client/server cert info

```shell
# check kubelet client
openssl x509  -noout -text -in /var/lib/kubelet/pki/kubelet-client-current.pem | grep Issuer
openssl x509  -noout -text -in /var/lib/kubelet/pki/kubelet-client-current.pem | grep "Extended Key Usage" -A1

# check kubelet server
openssl x509  -noout -text -in /var/lib/kubelet/pki/kubelet.crt | grep Issuer
openssl x509  -noout -text -in /var/lib/kubelet/pki/kubelet.crt | grep "Extended Key Usage" -A1
```

24. NetworkPolicy
- https://kubernetes.io/docs/concepts/services-networking/network-policies/

25. Etcd Snapshot Save and Restore

```shell
vim /etc/kubernetes/manifests/etcd.yaml
cat /etc/kubernetes/manifests/kube-apiserver.yaml | grep etcd

#save
ETCDCTL_API=3 etcdctl snapshot save /tmp/etcd-backup.db \
--cacert /etc/kubernetes/pki/etcd/ca.crt \
--cert /etc/kubernetes/pki/etcd/server.crt \
--key /etc/kubernetes/pki/etcd/server.key

#restore
ETCDCTL_API=3 etcdctl snapshot restore /tmp/etcd-backup.db \
--data-dir /var/lib/etcd-backup \
--cacert /etc/kubernetes/pki/etcd/ca.crt \
--cert /etc/kubernetes/pki/etcd/server.crt \
--key /etc/kubernetes/pki/etcd/server.key
```

### references
- https://mp.weixin.qq.com/s/SxmitpMMu6a91d3NqD5adQ
- https://minikube.sigs.k8s.io/docs/commands/addons/
- https://minikube.sigs.k8s.io/docs/tutorials/volume_snapshots_and_csi/

