#!/bin/bash

WORK_DIR=$(pwd)

function getCurTs(){ 
    cur=$(date +%s.%N)
  
    cur_s=$(echo $cur | cut -d '.' -f 1)
    cur_ns=$(echo $cur | cut -d '.' -f 2)

    cur_ms=$(( 10#$cur_s * 1000 + 10#$cur_ns / 1000000 ))
    
    echo $cur_ms
}

function printLog(){
    log_info=$1

    echo -e "\033[34m [SMART][$(getCurTs)] $log_info \033[0m"
}

## stop all nodes
printLog "stop existing blockchain nodes"
bash nodes/127.0.0.1/stop_all.sh

## rm old nodes dir
printLog "remove existing blockchain data"
rm -rf nodes/

## rm console log
printLog "remove existing console log"
rm -rf console/log/
rm console/deploylog.txt

## generate nodes
printLog "generate 4 new blockchain nodes"
bash build_chain.sh -l 127.0.0.1:4 -p 30300,20200

## start all nodes
printLog "start 4 new blockchain nodes"
bash nodes/127.0.0.1/start_all.sh

## update conf to console
printLog "sync blockchain nodes config to console"
cp -r nodes/127.0.0.1/sdk/* console/conf

## deploy TPM contract
printLog "deploy TEE provider management contract"
cd console && bash console.sh deploy TPM

printLog "system initialization is completed"