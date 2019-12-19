#!/bin/bash

docker run --name=zzz_mysql \
    --restart=always \
    -p 10000:3306 \
    -v ./conf:/etc/mysql/conf.d \
    -v /data/docker/mysql/data:/var/lib/mysql \
    -v /data/docker/mysql/logs:/logs \
    -e MYSQL_ROOT_PASSWORD=123456 \
    -e MYSQL_DATABASE=nacos \
    -e MYSQL_USER=nacos \
    -e MYSQL_PASSWORD=123456 \
    -d mysql:5.7
