pragma solidity>=0.6.10 <0.8.20;

contract TPM {
    string contract_name;

    struct reg_info {
        address tee_provider;
        string enc_ppid;
    }

    reg_info[] ris;

    constructor() {
        contract_name = "TEE Provider Manager Contract";
    }

    function get_all_reg_info() public view returns (reg_info[] memory) {
        return ris;
    }

    function get_latest_reg_info() public view returns (address, string memory) {
        return (ris[ris.length-1].tee_provider, ris[ris.length-1].enc_ppid);
    }

    function register_enc_ppid(string memory enc_ppid) public {
        ris.push(reg_info({tee_provider: msg.sender, enc_ppid: enc_ppid}));
    }
}