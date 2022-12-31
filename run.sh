#!/bin/bash

set -e # stop shell when a command fails with status other than 0

r=`pwd`
echo $r

# Gateway
cd "$r/gateway"
mvn clean install &

# Tweet Service
cd "$r/tweet-service"
mvn clean install &

# User Service
cd "$r/user-service"
mvn clean install &

# Timeline Service
cd "$r/timeline-service"
mvn clean install &

cd "$r"

docker-compose up -d --build

