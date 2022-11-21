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

printLog "obtain TPM contract address"
contract_address=$(cat console/deploylog.txt | grep TPM | awk '{print $5}')

## retrieve enc_ppid from PCE (need root privilege) and request to attestation service
printLog "retrieve enc_ppid from PCE and request pck cache update"
cd /opt/intel/sgx-pck-id-retrieval-tool/ && sudo ./PCKIDRetrievalTool

printLog "obtain enc_ppid"
enc_ppid=$(cat /opt/intel/sgx-pck-id-retrieval-tool/pckid_retrieval.csv | awk -F, '{print $1}')

## register enc_ppid with tee_provider account in the TPM contract
printLog "register TEE provider in the TPM contract"
cd ${WORK_DIR}/console && bash console.sh call TPM $contract_address register_enc_ppid $enc_ppid

printLog "query the latest registered TEE provider"
bash console.sh call TPM $contract_address get_latest_reg_info

## since the same enc_ppid has been recorded before
printLog "query an exsited enc_ppid in the pck cache database"
cd /opt/intel/sgx-pck-id-retrieval-tool/ && sudo ./PCKIDRetrievalTool

printLog "TEE provider registration is completed"