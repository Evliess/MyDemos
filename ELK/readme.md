# This is a demo for ELK v7.12.0

## Test env is windows 10 docker client.

## One elasticsearch node, one kibana node, one filebeat node and one metricbeat node. 

## One nginx node is used for test.

## Use filebeat to monitor nginx log and use metricbeat to monitor nginx stub status.

## Usage
```
docker-compose -f docker-compose.yml build

docker-compose -f docker-compose.yml up -d

docker-compose -f docker-compose.yml down -v

```