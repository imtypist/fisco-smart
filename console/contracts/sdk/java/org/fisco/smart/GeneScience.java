package org.fisco.smart;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.codec.abi.FunctionEncoder;
import org.fisco.bcos.sdk.v3.codec.datatypes.Address;
import org.fisco.bcos.sdk.v3.codec.datatypes.Bool;
import org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray;
import org.fisco.bcos.sdk.v3.codec.datatypes.Function;
import org.fisco.bcos.sdk.v3.codec.datatypes.Type;
import org.fisco.bcos.sdk.v3.codec.datatypes.TypeReference;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.StaticArray12;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint8;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.v3.contract.Contract;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.CryptoType;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class GeneScience extends Contract {
    public static final String[] BINARY_ARRAY = {"60806040526000805460ff191660019081179091556005905534801561002457600080fd5b50604051610e31380380610e31833981016040819052610043916100b0565b6001600160a01b03811661005657600080fd5b600280546001600160a01b0319166001600160a01b0392831617905560008054610100600160a81b03191661010093909216929092021790556100e3565b80516001600160a01b03811681146100ab57600080fd5b919050565b600080604083850312156100c357600080fd5b6100cc83610094565b91506100da60208401610094565b90509250929050565b610d3f806100f26000396000f3fe608060405234801561001057600080fd5b506004361061007d5760003560e01c806354c15b821161005b57806354c15b82146100c457806361a76900146100e157806377a74a2014610101578063fe2787e81461012157600080fd5b80630d9f5aed146100825780631597ee44146100a857806333695c59146100bb575b600080fd5b6100956100903660046108ac565b610136565b6040519081526020015b60405180910390f35b6100956100b6366004610904565b6105c6565b61009560015481565b6000546100d19060ff1681565b604051901515815260200161009f565b6100f46100ef3660046109c9565b61061e565b60405161009f91906109e2565b61011461010f3660046109c9565b61069a565b60405161009f9190610a29565b61013461012f366004610a76565b6106fb565b005b6000805461010090046001600160a01b03161580610163575060005461010090046001600160a01b031632145b156101795781431161017457600080fd5b610191565b6001546101869083610ab0565b431161019157600080fd5b8140806101c5576101a960ff84164360ff1916610ab0565b92504383106101c1576101be61010084610ac8565b92505b5081405b6040805160208101839052908101869052606081018590526080810184905260a0016040516020818303038152906040528051906020012060001c905060008061020e8761061e565b9050600061021b8761061e565b604080516030808252610620820190925291925060009190602082016106008036833701905050905060008060005b600c81101561040f57600360005b600182106103fa578161026c846004610adf565b6102769190610ab0565b94506102848a60028b6107b9565b905061029160028a610ab0565b985080610330578785815181106102aa576102aa610afe565b60200260200101519350876001866102c29190610ac8565b815181106102d2576102d2610afe565b60200260200101518886815181106102ec576102ec610afe565b60ff90921660209283029190910190910152838861030b600188610ac8565b8151811061031b5761031b610afe565b602002602001019060ff16908160ff16815250505b61033c8a60028b6107b9565b905061034960028a610ab0565b9850806103e85786858151811061036257610362610afe565b602002602001015193508660018661037a9190610ac8565b8151811061038a5761038a610afe565b60200260200101518786815181106103a4576103a4610afe565b60ff9092166020928302919091019091015283876103c3600188610ac8565b815181106103d3576103d3610afe565b602002602001019060ff16908160ff16815250505b816103f281610b14565b925050610258565b5050808061040790610b2b565b91505061024a565b50600091505b60308210156105ae5760008061042c600485610b5c565b158015610477575085848151811061044657610446610afe565b602002602001015160011660ff1687858151811061046657610466610afe565b602002602001015160011660ff1614155b156104d7576104888960038a6107b9565b9050610495600389610ab0565b97506104d48785815181106104ac576104ac610afe565b60200260200101518786815181106104c6576104c6610afe565b6020026020010151836107e4565b91505b60ff82161561050c57818585815181106104f3576104f3610afe565b602002602001019060ff16908160ff1681525050610599565b6105188960018a6107b9565b9050610525600189610ab0565b9750806105585786848151811061053e5761053e610afe565b60200260200101518585815181106104f3576104f3610afe565b85848151811061056a5761056a610afe565b602002602001015185858151811061058457610584610afe565b602002602001019060ff16908160ff16815250505b505081806105a690610b2b565b925050610415565b6105b7836105c6565b9b9a5050505050505050505050565b6000805b60308110156106185760059190911b90826105e682602f610ac8565b815181106105f6576105f6610afe565b602002602001015160ff1682179150808061061090610b2b565b9150506105ca565b50919050565b604080516030808252610620820190925260609160009190602082016106008036833701905050905060005b60308110156106935761065d848261086d565b82828151811061066f5761066f610afe565b60ff909216602092830291909101909101528061068b81610b2b565b91505061064a565b5092915050565b6106a261088d565b6106aa61088d565b60005b600c811015610693576106ca846106c5836004610adf565b61086d565b8282600c81106106dc576106dc610afe565b60ff9092166020929092020152806106f381610b2b565b9150506106ad565b600260009054906101000a90046001600160a01b03166001600160a01b031663b047fb506040518163ffffffff1660e01b81526004016020604051808303816000875af1158015610750573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906107749190610b70565b6001600160a01b0316336001600160a01b03161461079157600080fd5b600080546001600160a01b0390921661010002610100600160a81b0319909216919091179055565b6000808260016107ca866002610c71565b6107d49190610ac8565b901b8516831c9150509392505050565b6000838360ff80821690831611156107fc5750839050845b6108068282610c7d565b60ff166001148015610823575061081e600283610ca0565b60ff16155b1561086457600060178360ff16101561083e57506001610842565b5060005b80851161086257610854600284610cc2565b61085f906010610ce4565b93505b505b50509392505050565b600061088483600561087f8582610adf565b6107b9565b90505b92915050565b604051806101800160405280600c906020820280368337509192915050565b6000806000606084860312156108c157600080fd5b505081359360208301359350604090920135919050565b634e487b7160e01b600052604160045260246000fd5b803560ff811681146108ff57600080fd5b919050565b6000602080838503121561091757600080fd5b823567ffffffffffffffff8082111561092f57600080fd5b818501915085601f83011261094357600080fd5b813581811115610955576109556108d8565b8060051b604051601f19603f8301168101818110858211171561097a5761097a6108d8565b60405291825284820192508381018501918883111561099857600080fd5b938501935b828510156109bd576109ae856108ee565b8452938501939285019261099d565b98975050505050505050565b6000602082840312156109db57600080fd5b5035919050565b6020808252825182820181905260009190848201906040850190845b81811015610a1d57835160ff16835292840192918401916001016109fe565b50909695505050505050565b6101808101818360005b600c811015610a5557815160ff16835260209283019290910190600101610a33565b50505092915050565b6001600160a01b0381168114610a7357600080fd5b50565b600060208284031215610a8857600080fd5b8135610a9381610a5e565b9392505050565b634e487b7160e01b600052601160045260246000fd5b60008219821115610ac357610ac3610a9a565b500190565b600082821015610ada57610ada610a9a565b500390565b6000816000190483118215151615610af957610af9610a9a565b500290565b634e487b7160e01b600052603260045260246000fd5b600081610b2357610b23610a9a565b506000190190565b6000600019821415610b3f57610b3f610a9a565b5060010190565b634e487b7160e01b600052601260045260246000fd5b600082610b6b57610b6b610b46565b500690565b600060208284031215610b8257600080fd5b8151610a9381610a5e565b600181815b80851115610bc8578160001904821115610bae57610bae610a9a565b80851615610bbb57918102915b93841c9390800290610b92565b509250929050565b600082610bdf57506001610887565b81610bec57506000610887565b8160018114610c025760028114610c0c57610c28565b6001915050610887565b60ff841115610c1d57610c1d610a9a565b50506001821b610887565b5060208310610133831016604e8410600b8410161715610c4b575081810a610887565b610c558383610b8d565b8060001904821115610c6957610c69610a9a565b029392505050565b60006108848383610bd0565b600060ff821660ff841680821015610c9757610c97610a9a565b90039392505050565b600060ff831680610cb357610cb3610b46565b8060ff84160691505092915050565b600060ff831680610cd557610cd5610b46565b8060ff84160491505092915050565b600060ff821660ff84168060ff03821115610d0157610d01610a9a565b01939250505056fea26469706673582212203f2e7411b7a1e339d0fe20f3dc4c343d47288a999defca1c265912b44bb266dd64736f6c634300080b0033"};

    public static final String BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"60806040526000805460ff191660019081179091556005905534801561002457600080fd5b50604051610e35380380610e35833981016040819052610043916100b0565b6001600160a01b03811661005657600080fd5b600280546001600160a01b0319166001600160a01b0392831617905560008054610100600160a81b03191661010093909216929092021790556100e3565b80516001600160a01b03811681146100ab57600080fd5b919050565b600080604083850312156100c357600080fd5b6100cc83610094565b91506100da60208401610094565b90509250929050565b610d43806100f26000396000f3fe608060405234801561001057600080fd5b506004361061007d5760003560e01c80638287bb9f1161005b5780638287bb9f146100e15780638de54498146100fe578063cf0d2e3314610107578063e1a790141461012757600080fd5b806301869e6b146100825780633abfe8f6146100ab5780637b29942d146100c0575b600080fd5b6100956100903660046108b0565b61013a565b6040516100a291906108c9565b60405180910390f35b6100be6100b9366004610928565b6101b6565b005b6100d36100ce366004610978565b610274565b6040519081526020016100a2565b6000546100ee9060ff1681565b60405190151581526020016100a2565b6100d360015481565b61011a6101153660046108b0565b6102cc565b6040516100a29190610a3d565b6100d3610135366004610a72565b61032d565b604080516030808252610620820190925260609160009190602082016106008036833701905050905060005b60308110156101af5761017984826107bd565b82828151811061018b5761018b610a9e565b60ff90921660209283029190910190910152806101a781610aca565b915050610166565b5092915050565b600260009054906101000a90046001600160a01b03166001600160a01b03166392bfa75c6040518163ffffffff1660e01b81526004016020604051808303816000875af115801561020b573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061022f9190610ae5565b6001600160a01b0316336001600160a01b03161461024c57600080fd5b600080546001600160a01b0390921661010002610100600160a81b0319909216919091179055565b6000805b60308110156102c65760059190911b908261029482602f610b02565b815181106102a4576102a4610a9e565b602002602001015160ff168217915080806102be90610aca565b915050610278565b50919050565b6102d4610891565b6102dc610891565b60005b600c8110156101af576102fc846102f7836004610b19565b6107bd565b8282600c811061030e5761030e610a9e565b60ff90921660209290920201528061032581610aca565b9150506102df565b6000805461010090046001600160a01b0316158061035a575060005461010090046001600160a01b031632145b156103705781431161036b57600080fd5b610388565b60015461037d9083610b38565b431161038857600080fd5b8140806103bc576103a060ff84164360ff1916610b38565b92504383106103b8576103b561010084610b02565b92505b5081405b6040805160208101839052908101869052606081018590526080810184905260a0016040516020818303038152906040528051906020012060001c90506000806104058761013a565b905060006104128761013a565b604080516030808252610620820190925291925060009190602082016106008036833701905050905060008060005b600c81101561060657600360005b600182106105f15781610463846004610b19565b61046d9190610b38565b945061047b8a60028b6107dd565b905061048860028a610b38565b985080610527578785815181106104a1576104a1610a9e565b60200260200101519350876001866104b99190610b02565b815181106104c9576104c9610a9e565b60200260200101518886815181106104e3576104e3610a9e565b60ff909216602092830291909101909101528388610502600188610b02565b8151811061051257610512610a9e565b602002602001019060ff16908160ff16815250505b6105338a60028b6107dd565b905061054060028a610b38565b9850806105df5786858151811061055957610559610a9e565b60200260200101519350866001866105719190610b02565b8151811061058157610581610a9e565b602002602001015187868151811061059b5761059b610a9e565b60ff9092166020928302919091019091015283876105ba600188610b02565b815181106105ca576105ca610a9e565b602002602001019060ff16908160ff16815250505b816105e981610b50565b92505061044f565b505080806105fe90610aca565b915050610441565b50600091505b60308210156107a557600080610623600485610b7d565b15801561066e575085848151811061063d5761063d610a9e565b602002602001015160011660ff1687858151811061065d5761065d610a9e565b602002602001015160011660ff1614155b156106ce5761067f8960038a6107dd565b905061068c600389610b38565b97506106cb8785815181106106a3576106a3610a9e565b60200260200101518786815181106106bd576106bd610a9e565b602002602001015183610808565b91505b60ff82161561070357818585815181106106ea576106ea610a9e565b602002602001019060ff16908160ff1681525050610790565b61070f8960018a6107dd565b905061071c600189610b38565b97508061074f5786848151811061073557610735610a9e565b60200260200101518585815181106106ea576106ea610a9e565b85848151811061076157610761610a9e565b602002602001015185858151811061077b5761077b610a9e565b602002602001019060ff16908160ff16815250505b5050818061079d90610aca565b92505061060c565b6107ae83610274565b9b9a5050505050505050505050565b60006107d48360056107cf8582610b19565b6107dd565b90505b92915050565b6000808260016107ee866002610c75565b6107f89190610b02565b901b8516831c9150509392505050565b6000838360ff80821690831611156108205750839050845b61082a8282610c81565b60ff1660011480156108475750610842600283610ca4565b60ff16155b1561088857600060178360ff16101561086257506001610866565b5060005b80851161088657610878600284610cc6565b610883906010610ce8565b93505b505b50509392505050565b604051806101800160405280600c906020820280368337509192915050565b6000602082840312156108c257600080fd5b5035919050565b6020808252825182820181905260009190848201906040850190845b8181101561090457835160ff16835292840192918401916001016108e5565b50909695505050505050565b6001600160a01b038116811461092557600080fd5b50565b60006020828403121561093a57600080fd5b813561094581610910565b9392505050565b63b95aa35560e01b600052604160045260246000fd5b803560ff8116811461097357600080fd5b919050565b6000602080838503121561098b57600080fd5b823567ffffffffffffffff808211156109a357600080fd5b818501915085601f8301126109b757600080fd5b8135818111156109c9576109c961094c565b8060051b604051601f19603f830116810181811085821117156109ee576109ee61094c565b604052918252848201925083810185019188831115610a0c57600080fd5b938501935b82851015610a3157610a2285610962565b84529385019392850192610a11565b98975050505050505050565b6101808101818360005b600c811015610a6957815160ff16835260209283019290910190600101610a47565b50505092915050565b600080600060608486031215610a8757600080fd5b505081359360208301359350604090920135919050565b63b95aa35560e01b600052603260045260246000fd5b63b95aa35560e01b600052601160045260246000fd5b6000600019821415610ade57610ade610ab4565b5060010190565b600060208284031215610af757600080fd5b815161094581610910565b600082821015610b1457610b14610ab4565b500390565b6000816000190483118215151615610b3357610b33610ab4565b500290565b60008219821115610b4b57610b4b610ab4565b500190565b600081610b5f57610b5f610ab4565b506000190190565b63b95aa35560e01b600052601260045260246000fd5b600082610b8c57610b8c610b67565b500690565b600181815b80851115610bcc578160001904821115610bb257610bb2610ab4565b80851615610bbf57918102915b93841c9390800290610b96565b509250929050565b600082610be3575060016107d7565b81610bf0575060006107d7565b8160018114610c065760028114610c1057610c2c565b60019150506107d7565b60ff841115610c2157610c21610ab4565b50506001821b6107d7565b5060208310610133831016604e8410600b8410161715610c4f575081810a6107d7565b610c598383610b91565b8060001904821115610c6d57610c6d610ab4565b029392505050565b60006107d48383610bd4565b600060ff821660ff841680821015610c9b57610c9b610ab4565b90039392505050565b600060ff831680610cb757610cb7610b67565b8060ff84160691505092915050565b600060ff831680610cd957610cd9610b67565b8060ff84160491505092915050565b600060ff821660ff84168060ff03821115610d0557610d05610ab4565b01939250505056fea2646970667358221220800fbe86ff21cfb4bdfd0b91529de767ab7f833b694b73f8fe40f305f7fa292c64736f6c634300080b0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_privilegedBirtherAddress\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"_kittyCoreAddress\",\"type\":\"address\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"conflictFields\":[{\"kind\":5}],\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_genes\",\"type\":\"uint256\"}],\"name\":\"decode\",\"outputs\":[{\"internalType\":\"uint8[]\",\"name\":\"\",\"type\":\"uint8[]\"}],\"selector\":[1638361344,25599595],\"stateMutability\":\"pure\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":5}],\"inputs\":[{\"internalType\":\"uint8[]\",\"name\":\"_traits\",\"type\":\"uint8[]\"}],\"name\":\"encode\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"_genes\",\"type\":\"uint256\"}],\"selector\":[362278468,2066322477],\"stateMutability\":\"pure\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":5}],\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_genes\",\"type\":\"uint256\"}],\"name\":\"expressingTraits\",\"outputs\":[{\"internalType\":\"uint8[12]\",\"name\":\"\",\"type\":\"uint8[12]\"}],\"selector\":[2007452192,3473747507],\"stateMutability\":\"pure\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":4,\"value\":[0]}],\"inputs\":[],\"name\":\"isGeneScience\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"selector\":[1421958018,2189933471],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":4,\"value\":[0]},{\"kind\":4,\"value\":[1]}],\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_genes1\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"_genes2\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"_targetBlock\",\"type\":\"uint256\"}],\"name\":\"mixGenes\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"selector\":[228547309,3785854996],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":4,\"value\":[1]}],\"inputs\":[],\"name\":\"privilegedBirtherWindowSize\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"selector\":[862542937,2380612760],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"address\",\"name\":\"_birtherAddress\",\"type\":\"address\"}],\"name\":\"setPrivilegedBirther\",\"outputs\":[],\"selector\":[4264003560,985655542],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_DECODE = "decode";

    public static final String FUNC_ENCODE = "encode";

    public static final String FUNC_EXPRESSINGTRAITS = "expressingTraits";

    public static final String FUNC_ISGENESCIENCE = "isGeneScience";

    public static final String FUNC_MIXGENES = "mixGenes";

    public static final String FUNC_PRIVILEGEDBIRTHERWINDOWSIZE = "privilegedBirtherWindowSize";

    public static final String FUNC_SETPRIVILEGEDBIRTHER = "setPrivilegedBirther";

    protected GeneScience(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public static String getABI() {
        return ABI;
    }

    public TransactionReceipt decode(BigInteger _genes) {
        final Function function = new Function(
                FUNC_DECODE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(_genes)), 
                Collections.<TypeReference<?>>emptyList(), 4);
        return executeTransaction(function);
    }

    public String decode(BigInteger _genes, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_DECODE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(_genes)), 
                Collections.<TypeReference<?>>emptyList(), 4);
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForDecode(BigInteger _genes) {
        final Function function = new Function(
                FUNC_DECODE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(_genes)), 
                Collections.<TypeReference<?>>emptyList(), 4);
        return createSignedTransaction(function);
    }

    public Tuple1<BigInteger> getDecodeInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_DECODE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public Tuple1<List<BigInteger>> getDecodeOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_DECODE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint8>>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<List<BigInteger>>(

                convertToNative((List<Uint8>) results.get(0).getValue())
                );
    }

    public TransactionReceipt encode(List<BigInteger> _traits) {
        final Function function = new Function(
                FUNC_ENCODE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint8>(
                        org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint8.class,
                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(_traits, org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint8.class))), 
                Collections.<TypeReference<?>>emptyList(), 4);
        return executeTransaction(function);
    }

    public String encode(List<BigInteger> _traits, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_ENCODE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint8>(
                        org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint8.class,
                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(_traits, org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint8.class))), 
                Collections.<TypeReference<?>>emptyList(), 4);
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForEncode(List<BigInteger> _traits) {
        final Function function = new Function(
                FUNC_ENCODE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint8>(
                        org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint8.class,
                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(_traits, org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint8.class))), 
                Collections.<TypeReference<?>>emptyList(), 4);
        return createSignedTransaction(function);
    }

    public Tuple1<List<BigInteger>> getEncodeInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_ENCODE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint8>>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<List<BigInteger>>(

                convertToNative((List<Uint8>) results.get(0).getValue())
                );
    }

    public Tuple1<BigInteger> getEncodeOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_ENCODE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public TransactionReceipt expressingTraits(BigInteger _genes) {
        final Function function = new Function(
                FUNC_EXPRESSINGTRAITS, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(_genes)), 
                Collections.<TypeReference<?>>emptyList(), 4);
        return executeTransaction(function);
    }

    public String expressingTraits(BigInteger _genes, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_EXPRESSINGTRAITS, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(_genes)), 
                Collections.<TypeReference<?>>emptyList(), 4);
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForExpressingTraits(BigInteger _genes) {
        final Function function = new Function(
                FUNC_EXPRESSINGTRAITS, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(_genes)), 
                Collections.<TypeReference<?>>emptyList(), 4);
        return createSignedTransaction(function);
    }

    public Tuple1<BigInteger> getExpressingTraitsInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_EXPRESSINGTRAITS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public Tuple1<List<BigInteger>> getExpressingTraitsOutput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_EXPRESSINGTRAITS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<StaticArray12<Uint8>>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<List<BigInteger>>(

                convertToNative((List<Uint8>) results.get(0).getValue())
                );
    }

    public Boolean isGeneScience() throws ContractException {
        final Function function = new Function(FUNC_ISGENESCIENCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallWithSingleValueReturn(function, Boolean.class);
    }

    public BigInteger mixGenes(BigInteger _genes1, BigInteger _genes2, BigInteger _targetBlock)
            throws ContractException {
        final Function function = new Function(FUNC_MIXGENES, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(_genes1), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(_genes2), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(_targetBlock)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public BigInteger privilegedBirtherWindowSize() throws ContractException {
        final Function function = new Function(FUNC_PRIVILEGEDBIRTHERWINDOWSIZE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public TransactionReceipt setPrivilegedBirther(String _birtherAddress) {
        final Function function = new Function(
                FUNC_SETPRIVILEGEDBIRTHER, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Address(_birtherAddress)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return executeTransaction(function);
    }

    public String setPrivilegedBirther(String _birtherAddress, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_SETPRIVILEGEDBIRTHER, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Address(_birtherAddress)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForSetPrivilegedBirther(String _birtherAddress) {
        final Function function = new Function(
                FUNC_SETPRIVILEGEDBIRTHER, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Address(_birtherAddress)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return createSignedTransaction(function);
    }

    public Tuple1<String> getSetPrivilegedBirtherInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_SETPRIVILEGEDBIRTHER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public static GeneScience load(String contractAddress, Client client,
            CryptoKeyPair credential) {
        return new GeneScience(contractAddress, client, credential);
    }

    public static GeneScience deploy(Client client, CryptoKeyPair credential,
            String _privilegedBirtherAddress, String _kittyCoreAddress) throws ContractException {
        byte[] encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Address(_privilegedBirtherAddress), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(_kittyCoreAddress)));
        return deploy(GeneScience.class, client, credential, getBinary(client.getCryptoSuite()), getABI(), encodedConstructor, null);
    }
}
