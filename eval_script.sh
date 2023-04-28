models=(alexnet densenet161 resnet18 squeezenet1_0 vgg16)

for model_name in ${models[@]}
do
    echo $model_name

    echo "running 0.start_blockchain_network.sh"
    bash 0.start_blockchain_network.sh | grep SMART > eval_logs/$model_name.log

    echo "running 1.register_tee_provider.sh"
    bash 1.register_tee_provider.sh | grep SMART >> eval_logs/$model_name.log

    echo "running 2.client_deploy_and_call.sh"
    bash 2.client_deploy_and_call.sh $model_name | grep SMART >> eval_logs/$model_name.log

    echo "generating log file"
    cat nodes/127.0.0.1/node0/nohup.out | grep SMART >> eval_logs/$model_name.log
    cat nodes/127.0.0.1/node1/nohup.out | grep SMART >> eval_logs/$model_name.log
    cat tee-provider/sgx_gramine/encrypted_model/$model_name/secret_prov_pf/server_dcap.log | grep SMART >> eval_logs/$model_name.log

done