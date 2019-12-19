#!/bin/bash

docker run --name=zzz_nacos \
    --restart=always \
    -p 8848:8848 \
    -e MODE=standalone \
    -e MYSQL_MASTER_SERVICE_HOST=172.0.0.1 \
    -e MYSQL_MASTER_SERVICE_PORT=3306 \
    -e MYSQL_MASTER_SERVICE_DB_NAME=nacos \
    -e MYSQL_MASTER_SERVICE_USER=root \
    -e MYSQL_MASTER_SERVICE_PASSWORD=123456 \
    -v /data/docker/nacos/logs:/home/nacos/logs \
    -d nacos/nacos-server:1.1.3
