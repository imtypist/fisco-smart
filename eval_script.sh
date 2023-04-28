model_name=$1

if [ -z $model_name ];then
    echo "please provide a model name!"
    exit -1
fi

echo "bash 0.start_blockchain_network.sh"
bash 0.start_blockchain_network.sh | grep SMART > eval_logs/$model_name.log

echo "bash 1.register_tee_provider.sh"
bash 1.register_tee_provider.sh | grep SMART >> eval_logs/$model_name.log

echo "bash 2.client_deploy_and_call.sh"
bash 2.client_deploy_and_call.sh $model_name | grep SMART >> eval_logs/$model_name.log

cat nodes/127.0.0.1/node0/nohup.out | grep SMART >> eval_logs/$model_name.log
cat nodes/127.0.0.1/node1/nohup.out | grep SMART >> eval_logs/$model_name.log
cat tee-provider/sgx_gramine/encrypted_model/$model_name/secret_prov_pf/server_dcap.log | grep SMART >> eval_logs/$model_name.log
