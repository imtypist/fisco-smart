#!/bin/bash

# rm old nodes dir
rm -rf nodes/

# generate nodes
bash build_chain.sh -l 127.0.0.1:4 -p 30300,20200

# start all nodes
bash nodes/127.0.0.1/start_all.sh

# update conf to console
cp -r nodes/127.0.0.1/sdk/* console/conf

# start console
cd console && bash start.sh