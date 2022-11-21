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

cd console

## deploy client smart contract
printLog "deploy smart contract" 
contract_address=$(bash console.sh deploy SmartCall | awk 'NR==2' | awk '{print $3}')


## execute model inference with non-SGX gramine
printLog "call inference function (non-sgx-gramine, public alexnet model)"
bash console.sh call SmartCall $contract_address inference "cd $WORK_DIR/tee-provider/non_sgx_gramine/alexnet && gramine-direct ./pytorch pytorchexample.py && cp result.txt ../../"

## execute model inference with Gramine in SGX
printLog "call inference function (sgx-gramine, public alexnet model)"
bash console.sh call SmartCall $contract_address inference "cd $WORK_DIR/tee-provider/sgx_gramine/plaintext_model/alexnet && gramine-sgx ./pytorch pytorchexample.py && python3 $WORK_DIR/quote-verification/quote-extractor.py result.quote"

## execute private model inference with Gramine in SGX
printLog "client or TEE provider starts secret key provisioning server"
cd $WORK_DIR/tee-provider/sgx_gramine/encrypted_model/alexnet/secret_prov_pf && RA_TLS_ALLOW_DEBUG_ENCLAVE_INSECURE=1 RA_TLS_ALLOW_OUTDATED_TCB_INSECURE=1 ./server_dcap wrap_key &

cd $WORK_DIR/console

printLog "call inference function (sgx-gramine, private alexnet model)"
bash console.sh call SmartCall $contract_address inference "cd $WORK_DIR/tee-provider/sgx_gramine/encrypted_model/alexnet && gramine-sgx ./pytorch pytorchexample.py && python3 $WORK_DIR/quote-verification/quote-extractor.py result.quote"

printLog "close server_dcap"
killall server_dcap

## verify execution result
printLog "verify execution result (sgx-gramine, public alexnet model)"
$WORK_DIR/quote-verification/app -quote $WORK_DIR/tee-provider/sgx_gramine/plaintext_model/alexnet/result.quote

printLog "verify execution result (sgx-gramine, private alexnet model)"
$WORK_DIR/quote-verification/app -quote $WORK_DIR/tee-provider/sgx_gramine/encrypted_model/alexnet/result.quote

printLog "on-chain and off-chain execution model test is completed"