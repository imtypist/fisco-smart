#!/bin/bash

model_name=$1

if [ -z $model_name ];then
    echo "please provide a model name!"
    exit -1
fi

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

cd console

## deploy client smart contract
printLog "deploy smart contract" 
contract_address=$(bash console.sh deploy SmartCall | awk 'NR==2' | awk '{print $3}')

# feh $WORK_DIR/tee-provider/non_sgx_gramine/$model_name/input.jpg

## execute model inference with non-SGX gramine
printLog "call inference function (non-sgx-gramine, public $model_name model)"
bash console.sh call SmartCall $contract_address inference "cd $WORK_DIR/tee-provider/non_sgx_gramine/$model_name && gramine-direct ./pytorch pytorchexample.py && cp result.txt ../../"

## execute model inference with Gramine in SGX
printLog "call inference function (sgx-gramine, public $model_name model)"
bash console.sh call SmartCall $contract_address inference "cd $WORK_DIR/tee-provider/sgx_gramine/plaintext_model/$model_name && gramine-sgx ./pytorch pytorchexample.py && python3 $WORK_DIR/quote-verification/quote-extractor.py result.quote"

## execute private model inference with Gramine in SGX
printLog "client or TEE provider starts secret key provisioning server"
cd $WORK_DIR/tee-provider/sgx_gramine/encrypted_model/$model_name/secret_prov_pf

RA_TLS_ALLOW_DEBUG_ENCLAVE_INSECURE=1 RA_TLS_ALLOW_OUTDATED_TCB_INSECURE=1 ./server_dcap wrap_key > server_dcap.log 2>&1 &

cd $WORK_DIR/console

printLog "call inference function (sgx-gramine, private $model_name model)"
bash console.sh call SmartCall $contract_address inference "cd $WORK_DIR/tee-provider/sgx_gramine/encrypted_model/$model_name && gramine-sgx ./pytorch pytorchexample.py && python3 $WORK_DIR/quote-verification/quote-extractor.py result.quote"

printLog "close server_dcap"
killall server_dcap

## verify execution result
printLog "verify execution result (sgx-gramine, public $model_name model)"
$WORK_DIR/quote-verification/app -quote $WORK_DIR/tee-provider/sgx_gramine/plaintext_model/$model_name/result.quote

printLog "verify execution result (sgx-gramine, private $model_name model)"
$WORK_DIR/quote-verification/app -quote $WORK_DIR/tee-provider/sgx_gramine/encrypted_model/$model_name/result.quote

printLog "on-chain and off-chain execution model test is completed"