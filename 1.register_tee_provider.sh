#!/bin/bash

WORK_DIR=$(pwd)

function getTiming(){ 
    start=$1
    end=$2
  
    start_s=$(echo $start | cut -d '.' -f 1)
    start_ns=$(echo $start | cut -d '.' -f 2)
    end_s=$(echo $end | cut -d '.' -f 1)
    end_ns=$(echo $end | cut -d '.' -f 2)


    take=$(( ( 10#$end_s - 10#$start_s ) * 1000 + ( 10#$end_ns / 1000000 - 10#$start_ns / 1000000 ) ))
    
    echo $take
}

contract_address=$(cat console/deploylog.txt | grep TPM | awk '{print $5}')

# retrieve enc_ppid from PCE (need root privilege) and request to attestation service
start=$(date +%s.%N)

cd /opt/intel/sgx-pck-id-retrieval-tool/ && sudo ./PCKIDRetrievalTool

end=$(date +%s.%N)
take=$(getTiming $start $end) 
echo -e "\033[34m [TIME] It takes ${take} ms for blockchain node to update PCK database \033[0m" # 7722, 6809

enc_ppid=$(cat /opt/intel/sgx-pck-id-retrieval-tool/pckid_retrieval.csv | awk -F, '{print $1}')

# register enc_ppid with tee_provider account in the TPM contract
echo -e "\033[34m Register TEE provider in the TPM \033[0m"

start=$(date +%s.%N)

cd ${WORK_DIR}/console && bash console.sh call TPM $contract_address register_enc_ppid $enc_ppid

end=$(date +%s.%N)
take=$(getTiming $start $end) 

echo -e "\033[34m [TIME] It takes ${take} ms for TEE provider registration \033[0m" # 2426, 2486

echo -e "\033[34m Query registered TEE provider \033[0m"
bash console.sh call TPM $contract_address get_latest_reg_info