#!/bin/bash

WORK_DIR=$(pwd)

# stop all nodes
bash nodes/127.0.0.1/stop_all.sh

# rm old nodes dir
rm -rf nodes/

# rm console log
rm -rf console/log/
rm console/deploylog.txt

# rm pck cache (need root privilege)
sudo rm /opt/intel/sgx-dcap-pccs/pckcache.db
sudo rm /opt/intel/sgx-pck-id-retrieval-tool/pckid_retrieval.csv

# restart pccs service (need root privilege)
sudo systemctl restart pccs

# generate nodes
bash build_chain.sh -l 127.0.0.1:4 -p 30300,20200

# start all nodes
bash nodes/127.0.0.1/start_all.sh

# update conf to console
cp -r nodes/127.0.0.1/sdk/* console/conf

# start console
# bash console/start.sh

# deploy TPM contract
echo -e "\033[34m Deploy TPM contract \033[0m"
cd console && bash console.sh deploy TPM