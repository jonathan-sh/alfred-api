#!/bin/bash
#internal vars
deploy_path=~/alfred/
#external vars
key_file=$1
origin_file=$2
user_destiny=$3
host_destiny=$4
root_path=$5

function check-src(){
    ssh -i ${key_file} ${user_destiny}@${host_destiny}  mkdir -p ${root_path}/builds
    deploy_path=${root_path}/builds
}

function send(){
    scp -i ${key_file} ${origin_file} ${user_destiny}@${host_destiny}:${deploy_path}
}

check-src;
send;