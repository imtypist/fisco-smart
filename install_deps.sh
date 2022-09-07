#!/bin/bash

# deps for fisco-bcos
# 注意：cmake可以用snap安装较高版本，apt默认包不满足最低版本要求
#      gcc/g++-9编译可能会报错，可以安装gcc/g++-10，配合update-alternatives使用
#      重编译删除build, deps/src/*stamp, deps/src/*build文件夹后再编译：mkdir -p build && cd build; cmake ..; make BuildInfo.h; make fisco-bcos -j4
sudo apt install -y cmake g++ git curl build-essential autoconf texinfo flex bison libzstd-dev libpython3-dev python-dev wget libgmp-dev

curl https://sh.rustup.rs -sSf | bash -s -- -y

source $HOME/.cargo/env

# deps for fisco console

sudo apt install -y curl openssl wget

# deps for intel sgx sdk/psw
sudo apt-get install -y libcurl4-openssl-dev libprotobuf-c-dev \
    protobuf-c-compiler python3-pip python3-protobuf libssl-dev libprotobuf-dev

# https://download.01.org/intel-sgx/latest/dcap-latest/linux/docs/Intel_SGX_SW_Installation_Guide_for_Linux.pdf
# install sgx psw and dcap
echo 'deb [arch=amd64] https://download.01.org/intel-sgx/sgx_repo/ubuntu focal main' | sudo tee /etc/apt/sources.list.d/intel-sgx.list
wget -qO - https://download.01.org/intel-sgx/sgx_repo/ubuntu/intel-sgx-deb.key | sudo apt-key add
sudo apt-get update
sudo apt-get install libsgx-epid libsgx-quote-ex libsgx-dcap-ql

sudo usermod -aG sgx_prv junqin

# setup Provisioning Certificate Caching Service (PCCS) on local machine
curl -sL https://deb.nodesource.com/setup_14.x | sudo -E bash -
sudo apt-get install -y nodejs
sudo apt-get install sgx-dcap-pccs
sudo systemctl start pccs
# install the DCAP QPL package
sudo apt-get install libsgx-dcap-default-qpl
sudo apt-get install libsgx-dcap-quote-verify-dev

# install sgx sdk
wget - https://download.01.org/intel-sgx/latest/linux-latest/distro/ubuntu20.04-server/sgx_linux_x64_sdk_2.16.100.4.bin
chmod +x sgx_linux_x64_sdk_2.16.100.4.bin
sudo ./sgx_linux_x64_sdk_2.16.100.4.bin --prefix /opt/
source /opt/sgxsdk/environment

sudo apt-get install libsgx-enclave-common-dev libsgx-dcap-ql-dev libsgx-dcap-default-qpl-dev

# (strongly recommend to build from source https://gramine.readthedocs.io/en/latest/devel/building.html#id2)
sudo pip3 install meson jinja2 toml
sudo apt-get install nasm gawk
git clone https://github.com/gramineproject/gramine.git && cd gramine
sudo meson setup build/ --buildtype=release -Ddirect=enabled -Dsgx=enabled -Ddcap=enabled

sudo ninja -C build/
sudo ninja -C build/ install

# install gramine-sgx from package
# sudo curl -fsSLo /usr/share/keyrings/gramine-keyring.gpg https://packages.gramineproject.io/gramine-keyring.gpg
# echo 'deb [arch=amd64 signed-by=/usr/share/keyrings/gramine-keyring.gpg] https://packages.gramineproject.io/ stable main' | sudo tee /etc/apt/sources.list.d/gramine.list
# sudo apt-get update

# sudo apt-get install gramine      # for 5.11+ upstream, in-kernel driver

# install torchvision
sudo pip3 install torchvision