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

cd console


echo -e "\033[34m Deploy SmartCall contract \033[0m"
start=$(date +%s.%N)

contract_address=$(bash console.sh deploy SmartCall | awk 'NR==2' | awk '{print $3}')

end=$(date +%s.%N)
take=$(getTiming $start $end) 
echo -e "\033[34m [TIME] It takes ${take} ms to deploy SmartCall contract \033[0m" # 2566 2630 2585



echo -e "\033[34m Call inference function \033[0m"
start=$(date +%s.%N)

bash console.sh call SmartCall $contract_address inference "cd /home/junqin/fisco-smart/tee-provider && python3 pytorchexample.py"

end=$(date +%s.%N)
take=$(getTiming $start $end) 
echo -e "\033[34m [TIME] It takes ${take} ms to execute model inference \033[0m" # w/o TEE alexnet 3294 3161