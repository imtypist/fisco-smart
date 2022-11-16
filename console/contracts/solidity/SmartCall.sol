pragma solidity >=0.6.10 <0.8.20;
import "./InferencePrecompiled.sol";

contract SmartCall
{
    address contract_owner;
    InferencePrecompiled TEE;

    constructor(){
        contract_owner = msg.sender;
        TEE = InferencePrecompiled(0x0000000000000000000000000000000000006000);
    }

    function inference(string memory cmd) public view returns (string memory){
        return TEE.predict(cmd);
    }
}