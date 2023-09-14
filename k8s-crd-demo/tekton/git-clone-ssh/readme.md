# git clone with ssh

## create a kubernetes secret
```
kubectl create secret generic git-credentials \
    --from-file=id_rsa=./id_rsa
```