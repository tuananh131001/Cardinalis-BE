#!/bin/bash

set -e # stop shell when a command fails with status other than 0

r=`pwd`
echo $r

# Eureka
cd "$r/eureka-server"
mvn clean install &

# Gateway
cd "$r/gateway"
mvn clean install &

# Tweet Service
cd "$r/tweet-service"
mvn clean install &

# User Service
cd "$r/user-service"
mvn clean install &
