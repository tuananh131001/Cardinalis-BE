#!/bin/bash

set -e # stop shell when a command fails with status other than 0

r=`pwd`
echo $r

docker-compose up -d --build
minikube addons enable ingress
minikube addons enable ingress-dns

