# Use Kaniko to build docker image

## Create a Dockerfile

```shell
FROM docker.io/library/alpine:3.18.3
RUN mkdir -p /opt/app
COPY README.md /opt/app/
ENTRYPOINT ["/bin/sh", "-c", "cat /opt/app/README.md"]
```

## create dockerhub secret

```shell
kubectl create secret generic docker-credentials --from-file=config.json=/home/jia/.docker/config.json
kubectl get secret docker-credentials --output="jsonpath={.data.config\.json}" | base64 --decode
```