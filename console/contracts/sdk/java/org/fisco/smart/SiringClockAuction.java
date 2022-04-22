package org.fisco.smart;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.codec.abi.FunctionEncoder;
import org.fisco.bcos.sdk.v3.codec.datatypes.Address;
import org.fisco.bcos.sdk.v3.codec.datatypes.Bool;
import org.fisco.bcos.sdk.v3.codec.datatypes.Event;
import org.fisco.bcos.sdk.v3.codec.datatypes.Function;
import org.fisco.bcos.sdk.v3.codec.datatypes.Type;
import org.fisco.bcos.sdk.v3.codec.datatypes.TypeReference;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple5;
import org.fisco.bcos.sdk.v3.contract.Contract;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.CryptoType;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class SiringClockAuction extends Contract {
    public static final String[] BINARY_ARRAY = {"60806040526000805460ff60a01b191690556004805460ff1916600117905534801561002a57600080fd5b50604051610f44380380610f4483398101604081905261004991610115565b600080546001600160a01b03191633179055818161271081111561006c57600080fd5b60028190556040516301ffc9a760e01b8152639a20483d60e01b600482015282906001600160a01b038216906301ffc9a790602401602060405180830381865afa1580156100be573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906100e2919061014f565b6100eb57600080fd5b600180546001600160a01b0319166001600160a01b03929092169190911790555061017892505050565b6000806040838503121561012857600080fd5b82516001600160a01b038116811461013f57600080fd5b6020939093015192949293505050565b60006020828403121561016157600080fd5b8151801515811461017157600080fd5b9392505050565b610dbd806101876000396000f3fe6080604052600436106100e85760003560e01c806383b5ff8b1161008a57806396b5a7551161005957806396b5a7551461027f578063c55d0f561461029f578063dd1b7a0f146102bf578063f2fde38b146102df57600080fd5b806383b5ff8b146101ee5780638456cb5914610212578063878eb368146102275780638da5cb5b1461024757600080fd5b80635c975abb116100c65780635c975abb1461014c5780635fd8c7101461016d57806376190f8f1461018257806378bd79351461019c57600080fd5b806327ebe40a146100ed5780633f4ba83a1461010f578063454a2ab314610139575b600080fd5b3480156100f957600080fd5b5061010d610108366004610b61565b6102ff565b005b34801561011b57600080fd5b506101246103b7565b60405190151581526020015b60405180910390f35b61010d610147366004610baa565b61041f565b34801561015857600080fd5b5060005461012490600160a01b900460ff1681565b34801561017957600080fd5b5061010d610467565b34801561018e57600080fd5b506004546101249060ff1681565b3480156101a857600080fd5b506101bc6101b7366004610baa565b6104cd565b604080516001600160a01b0390961686526020860194909452928401919091526060830152608082015260a001610130565b3480156101fa57600080fd5b5061020460025481565b604051908152602001610130565b34801561021e57600080fd5b50610124610551565b34801561023357600080fd5b5061010d610242366004610baa565b6105c0565b34801561025357600080fd5b50600054610267906001600160a01b031681565b6040516001600160a01b039091168152602001610130565b34801561028b57600080fd5b5061010d61029a366004610baa565b61062f565b3480156102ab57600080fd5b506102046102ba366004610baa565b610681565b3480156102cb57600080fd5b50600154610267906001600160a01b031681565b3480156102eb57600080fd5b5061010d6102fa366004610bc3565b6106bd565b836001600160801b0316841461031457600080fd5b826001600160801b0316831461032957600080fd5b816001600160401b0316821461033e57600080fd5b6001546001600160a01b0316331461035557600080fd5b61035f8186610702565b6040805160a0810182526001600160a01b03831681526001600160801b0380871660208301528516918101919091526001600160401b038084166060830152421660808201526103af868261076b565b505050505050565b600080546001600160a01b031633146103cf57600080fd5b600054600160a01b900460ff166103e557600080fd5b6000805460ff60a01b191681556040517f7805862f689e2f13df9f062ff482ad3ad112aca9e0847911ed832e158c525b339190a150600190565b6001546001600160a01b0316331461043657600080fd5b6000818152600360205260409020546001600160a01b03166104588234610866565b5061046381836109a3565b5050565b6001546000546001600160a01b03918216911633148061048f5750336001600160a01b038216145b61049857600080fd5b6040516001600160a01b038216904780156108fc02916000818181858888f19350505050158015610463573d6000803e3d6000fd5b600081815260036020526040812060028101548291829182918291600160401b90046001600160401b031661050157600080fd5b805460018201546002909201546001600160a01b03909116986001600160801b038084169950600160801b90930490921696506001600160401b038082169650600160401b909104169350915050565b600080546001600160a01b0316331461056957600080fd5b600054600160a01b900460ff161561058057600080fd5b6000805460ff60a01b1916600160a01b1781556040517f6985a02210a168e66602d3235cb6db0e70f92b3ba4d376a33c0f3d9434bff6259190a150600190565b600054600160a01b900460ff166105d657600080fd5b6000546001600160a01b031633146105ed57600080fd5b60008181526003602052604090206002810154600160401b90046001600160401b031661061957600080fd5b80546104639083906001600160a01b03166109dc565b60008181526003602052604090206002810154600160401b90046001600160401b031661065b57600080fd5b80546001600160a01b031633811461067257600080fd5b61067c83826109dc565b505050565b60008181526003602052604081206002810154600160401b90046001600160401b03166106ad57600080fd5b6106b681610a1f565b9392505050565b6000546001600160a01b031633146106d457600080fd5b6001600160a01b038116156106ff57600080546001600160a01b0319166001600160a01b0383161790555b50565b6001546040516323b872dd60e01b81526001600160a01b03848116600483015230602483015260448201849052909116906323b872dd906064015b600060405180830381600087803b15801561075757600080fd5b505af11580156103af573d6000803e3d6000fd5b603c81606001516001600160401b0316101561078657600080fd5b600082815260036020908152604091829020835181546001600160a01b039091166001600160a01b031990911617815590830151828401516001600160801b03908116600160801b810291909216908117600184015560608501516002909301805460808701516001600160401b03908116600160401b026001600160801b031992909216951694851717905592517fa9c8dfcda5664a5a124c713e386da27de87432d5b668e79458501eb296389ba79361085a938793919293845260208401929092526040830152606082015260800190565b60405180910390a15050565b60008281526003602052604081206002810154600160401b90046001600160401b031661089257600080fd5b600061089d82610a1f565b9050808410156108ac57600080fd5b81546001600160a01b03166108c086610a95565b811561091c5760006108d183610acc565b905060006108df8285610bf4565b6040519091506001600160a01b0384169082156108fc029083906000818181858888f19350505050158015610918573d6000803e3d6000fd5b5050505b60006109288387610bf4565b604051909150339082156108fc029083906000818181858888f19350505050158015610958573d6000803e3d6000fd5b506040805188815260208101859052338183015290517f4fcc30d90a842164dd58501ab874a101a3749c3d4747139cefe7c876f4ccebd29181900360600190a1509095945050505050565b60015460405163a9059cbb60e01b81526001600160a01b038481166004830152602482018490529091169063a9059cbb9060440161073d565b6109e582610a95565b6109ef81836109a3565b6040518281527f2809c7e17bf978fbc7194c0a694b638c4215e9140cacc6c38ca36010b45697df9060200161085a565b60028101546000908190600160401b90046001600160401b0316421115610a62576002830154610a5f90600160401b90046001600160401b031642610bf4565b90505b600183015460028401546106b6916001600160801b0380821692600160801b90920416906001600160401b031684610aef565b600090815260036020526040812080546001600160a01b0319168155600181019190915560020180546001600160801b0319169055565b600061271060025483610adf9190610c0b565b610ae99190610c40565b92915050565b6000828210610aff575082610b3d565b6000610b0b8686610c54565b9050600084610b1a8584610c93565b610b249190610d18565b90506000610b328289610d46565b9350610b3d92505050565b949350505050565b80356001600160a01b0381168114610b5c57600080fd5b919050565b600080600080600060a08688031215610b7957600080fd5b85359450602086013593506040860135925060608601359150610b9e60808701610b45565b90509295509295909350565b600060208284031215610bbc57600080fd5b5035919050565b600060208284031215610bd557600080fd5b6106b682610b45565b634e487b7160e01b600052601160045260246000fd5b600082821015610c0657610c06610bde565b500390565b6000816000190483118215151615610c2557610c25610bde565b500290565b634e487b7160e01b600052601260045260246000fd5b600082610c4f57610c4f610c2a565b500490565b60008083128015600160ff1b850184121615610c7257610c72610bde565b6001600160ff1b0384018313811615610c8d57610c8d610bde565b50500390565b60006001600160ff1b0381841382841380821686840486111615610cb957610cb9610bde565b600160ff1b6000871282811687830589121615610cd857610cd8610bde565b60008712925087820587128484161615610cf457610cf4610bde565b87850587128184161615610d0a57610d0a610bde565b505050929093029392505050565b600082610d2757610d27610c2a565b600160ff1b821460001984141615610d4157610d41610bde565b500590565b600080821280156001600160ff1b0384900385131615610d6857610d68610bde565b600160ff1b8390038412811615610d8157610d81610bde565b5050019056fea26469706673582212202375bd974ae50fc233ce7ea59756f5417fb69e8c34ef19f74ea63928964d0b3664736f6c634300080b0033"};

    public static final String BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"60806040526000805460ff60a01b191690556004805460ff1916600117905534801561002a57600080fd5b50604051610f40380380610f4083398101604081905261004991610115565b600080546001600160a01b03191633179055818161271081111561006c57600080fd5b6002819055604051631d4fd6f360e31b8152639a20483d60e01b600482015282906001600160a01b0382169063ea7eb79890602401602060405180830381865afa1580156100be573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906100e2919061014f565b6100eb57600080fd5b600180546001600160a01b0319166001600160a01b03929092169190911790555061017892505050565b6000806040838503121561012857600080fd5b82516001600160a01b038116811461013f57600080fd5b6020939093015192949293505050565b60006020828403121561016157600080fd5b8151801515811461017157600080fd5b9392505050565b610db9806101876000396000f3fe6080604052600436106100e85760003560e01c8063892e6f951161008a578063caa7d1f311610059578063caa7d1f314610293578063d4bc9601146102b3578063e39643a9146102d4578063f4fbb0c8146102ea57600080fd5b8063892e6f95146102315780639498fbdc1461024657806398981d4f14610266578063b44f5bd91461028057600080fd5b806316cad12a116100c657806316cad12a14610194578063297be052146101b45780635089e2c8146101d457806363c375261461020c57600080fd5b8063011a4bba146100ed5780630a0379a1146101205780630e34a02f14610172575b600080fd5b3480156100f957600080fd5b5061010d610108366004610b41565b6102ff565b6040519081526020015b60405180910390f35b34801561012c57600080fd5b5061014061013b366004610b41565b61033b565b604080516001600160a01b0390961686526020860194909452928401919091526060830152608082015260a001610117565b34801561017e57600080fd5b5061019261018d366004610b41565b6103bf565b005b3480156101a057600080fd5b506101926101af366004610b76565b610432565b3480156101c057600080fd5b506101926101cf366004610b91565b610477565b3480156101e057600080fd5b506000546101f4906001600160a01b031681565b6040516001600160a01b039091168152602001610117565b34801561021857600080fd5b5061022161052f565b6040519015158152602001610117565b34801561023d57600080fd5b50610192610597565b34801561025257600080fd5b506001546101f4906001600160a01b031681565b34801561027257600080fd5b506004546102219060ff1681565b61019261028e366004610b41565b6105fd565b34801561029f57600080fd5b506101926102ae366004610b41565b610641565b3480156102bf57600080fd5b5060005461022190600160a01b900460ff1681565b3480156102e057600080fd5b5061010d60025481565b3480156102f657600080fd5b50610221610693565b60008181526003602052604081206002810154600160401b90046001600160401b031661032b57600080fd5b61033481610702565b9392505050565b600081815260036020526040812060028101548291829182918291600160401b90046001600160401b031661036f57600080fd5b805460018201546002909201546001600160a01b03909116986001600160801b038084169950600160801b90930490921696506001600160401b038082169650600160401b909104169350915050565b600054600160a01b900460ff166103d557600080fd5b6000546001600160a01b031633146103ec57600080fd5b60008181526003602052604090206002810154600160401b90046001600160401b031661041857600080fd5b805461042e9083906001600160a01b0316610778565b5050565b6000546001600160a01b0316331461044957600080fd5b6001600160a01b0381161561047457600080546001600160a01b0319166001600160a01b0383161790555b50565b836001600160801b0316841461048c57600080fd5b826001600160801b031683146104a157600080fd5b816001600160401b031682146104b657600080fd5b6001546001600160a01b031633146104cd57600080fd5b6104d781866107c3565b6040805160a0810182526001600160a01b03831681526001600160801b0380871660208301528516918101919091526001600160401b03808416606083015242166080820152610527868261082c565b505050505050565b600080546001600160a01b0316331461054757600080fd5b600054600160a01b900460ff1661055d57600080fd5b6000805460ff60a01b191681556040517fe6d87ee340ec45ca4fde177223d81612516ef4e9027eb2ec3190948d3e22d06b9190a150600190565b6001546000546001600160a01b0391821691163314806105bf5750336001600160a01b038216145b6105c857600080fd5b6040516001600160a01b038216904780156108fc02916000818181858888f1935050505015801561042e573d6000803e3d6000fd5b6001546001600160a01b0316331461061457600080fd5b6000818152600360205260409020546001600160a01b0316610636823461091b565b5061042e8183610a58565b60008181526003602052604090206002810154600160401b90046001600160401b031661066d57600080fd5b80546001600160a01b031633811461068457600080fd5b61068e8382610778565b505050565b600080546001600160a01b031633146106ab57600080fd5b600054600160a01b900460ff16156106c257600080fd5b6000805460ff60a01b1916600160a01b1781556040517f82c2a22cb62f9fcb667c82b15e864fd9106fc6e8bdf191e1940c2c0518b000d99190a150600190565b60028101546000908190600160401b90046001600160401b031642111561074557600283015461074290600160401b90046001600160401b031642610bf0565b90505b60018301546002840154610334916001600160801b0380821692600160801b90920416906001600160401b031684610a91565b61078182610ae7565b61078b8183610a58565b6040518281527f6a1c1c1116704b877f29b2850527f2b920dab58cac0b59089e93cba921151319906020015b60405180910390a15050565b60015460405163ad8a973160e01b81526001600160a01b038481166004830152306024830152604482018490529091169063ad8a9731906064015b600060405180830381600087803b15801561081857600080fd5b505af1158015610527573d6000803e3d6000fd5b603c81606001516001600160401b0316101561084757600080fd5b600082815260036020908152604091829020835181546001600160a01b039091166001600160a01b031990911617815590830151828401516001600160801b03908116600160801b810291909216908117600184015560608501516002909301805460808701516001600160401b03908116600160401b026001600160801b031992909216951694851717905592517f2eb78c1a81ae6e7facaa29286c9fd12939ed047a83a30a760c15958bc7b324ed936107b7938793919293845260208401929092526040830152606082015260800190565b60008281526003602052604081206002810154600160401b90046001600160401b031661094757600080fd5b600061095282610702565b90508084101561096157600080fd5b81546001600160a01b031661097586610ae7565b81156109d157600061098683610b1e565b905060006109948285610bf0565b6040519091506001600160a01b0384169082156108fc029083906000818181858888f193505050501580156109cd573d6000803e3d6000fd5b5050505b60006109dd8387610bf0565b604051909150339082156108fc029083906000818181858888f19350505050158015610a0d573d6000803e3d6000fd5b506040805188815260208101859052338183015290517f8323c29c2c7ea62f686500ae944bc3d91129cc0a6b1bb38f32d5d8fb43ba2afd9181900360600190a1509095945050505050565b600154604051636904e96560e01b81526001600160a01b0384811660048301526024820184905290911690636904e965906044016107fe565b6000828210610aa1575082610adf565b6000610aad8686610c07565b9050600084610abc8584610c46565b610ac69190610ce1565b90506000610ad48289610d0f565b9350610adf92505050565b949350505050565b600090815260036020526040812080546001600160a01b0319168155600181019190915560020180546001600160801b0319169055565b600061271060025483610b319190610d50565b610b3b9190610d6f565b92915050565b600060208284031215610b5357600080fd5b5035919050565b80356001600160a01b0381168114610b7157600080fd5b919050565b600060208284031215610b8857600080fd5b61033482610b5a565b600080600080600060a08688031215610ba957600080fd5b85359450602086013593506040860135925060608601359150610bce60808701610b5a565b90509295509295909350565b63b95aa35560e01b600052601160045260246000fd5b600082821015610c0257610c02610bda565b500390565b60008083128015600160ff1b850184121615610c2557610c25610bda565b6001600160ff1b0384018313811615610c4057610c40610bda565b50500390565b60006001600160ff1b0381841382841380821686840486111615610c6c57610c6c610bda565b600160ff1b6000871282811687830589121615610c8b57610c8b610bda565b60008712925087820587128484161615610ca757610ca7610bda565b87850587128184161615610cbd57610cbd610bda565b505050929093029392505050565b63b95aa35560e01b600052601260045260246000fd5b600082610cf057610cf0610ccb565b600160ff1b821460001984141615610d0a57610d0a610bda565b500590565b600080821280156001600160ff1b0384900385131615610d3157610d31610bda565b600160ff1b8390038412811615610d4a57610d4a610bda565b50500190565b6000816000190483118215151615610d6a57610d6a610bda565b500290565b600082610d7e57610d7e610ccb565b50049056fea26469706673582212203d90c48d103fbd48e185d949b1b1c3410ee6aa06fd598ce3744ea91450182f6b64736f6c634300080b0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_nftAddr\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_cut\",\"type\":\"uint256\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"tokenId\",\"type\":\"uint256\"}],\"name\":\"AuctionCancelled\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"tokenId\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"startingPrice\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"endingPrice\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"duration\",\"type\":\"uint256\"}],\"name\":\"AuctionCreated\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"tokenId\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"totalPrice\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"winner\",\"type\":\"address\"}],\"name\":\"AuctionSuccessful\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[],\"name\":\"Pause\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[],\"name\":\"Unpause\",\"type\":\"event\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_tokenId\",\"type\":\"uint256\"}],\"name\":\"bid\",\"outputs\":[],\"selector\":[1162488499,3025099737],\"stateMutability\":\"payable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":3,\"slot\":3,\"value\":[0]}],\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_tokenId\",\"type\":\"uint256\"}],\"name\":\"cancelAuction\",\"outputs\":[],\"selector\":[2528487253,3399995891],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":3,\"slot\":3,\"value\":[0]},{\"kind\":4,\"value\":[0]}],\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_tokenId\",\"type\":\"uint256\"}],\"name\":\"cancelAuctionWhenPaused\",\"outputs\":[],\"selector\":[2274276200,238329903],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_tokenId\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"_startingPrice\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"_endingPrice\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"_duration\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"_seller\",\"type\":\"address\"}],\"name\":\"createAuction\",\"outputs\":[],\"selector\":[669770762,695984210],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":3,\"slot\":3,\"value\":[0]}],\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_tokenId\",\"type\":\"uint256\"}],\"name\":\"getAuction\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"seller\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"startingPrice\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"endingPrice\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"duration\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"startedAt\",\"type\":\"uint256\"}],\"selector\":[2025683253,167999905],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":3,\"slot\":3,\"value\":[0]}],\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_tokenId\",\"type\":\"uint256\"}],\"name\":\"getCurrentPrice\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"selector\":[3311210326,18500538],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":4,\"value\":[4]}],\"inputs\":[],\"name\":\"isSiringClockAuction\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"selector\":[1981353871,2560105807],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":4,\"value\":[1]}],\"inputs\":[],\"name\":\"nonFungibleContract\",\"outputs\":[{\"internalType\":\"contract ERC721\",\"name\":\"\",\"type\":\"address\"}],\"selector\":[3709565455,2493053916],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":4,\"value\":[0]}],\"inputs\":[],\"name\":\"owner\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"selector\":[2376452955,1351213768],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":4,\"value\":[2]}],\"inputs\":[],\"name\":\"ownerCut\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"selector\":[2209742731,3818275753],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":4,\"value\":[0]}],\"inputs\":[],\"name\":\"pause\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"selector\":[2220280665,4110135496],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":4,\"value\":[0]}],\"inputs\":[],\"name\":\"paused\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"selector\":[1553423035,3569128961],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":4,\"value\":[0]}],\"inputs\":[{\"internalType\":\"address\",\"name\":\"newOwner\",\"type\":\"address\"}],\"name\":\"transferOwnership\",\"outputs\":[],\"selector\":[4076725131,382390570],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":4,\"value\":[0]}],\"inputs\":[],\"name\":\"unpause\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"selector\":[1061922874,1673753894],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[],\"name\":\"withdrawBalance\",\"outputs\":[],\"selector\":[1608042256,2301521813],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_BID = "bid";

    public static final String FUNC_CANCELAUCTION = "cancelAuction";

    public static final String FUNC_CANCELAUCTIONWHENPAUSED = "cancelAuctionWhenPaused";

    public static final String FUNC_CREATEAUCTION = "createAuction";

    public static final String FUNC_GETAUCTION = "getAuction";

    public static final String FUNC_GETCURRENTPRICE = "getCurrentPrice";

    public static final String FUNC_ISSIRINGCLOCKAUCTION = "isSiringClockAuction";

    public static final String FUNC_NONFUNGIBLECONTRACT = "nonFungibleContract";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_OWNERCUT = "ownerCut";

    public static final String FUNC_PAUSE = "pause";

    public static final String FUNC_PAUSED = "paused";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_UNPAUSE = "unpause";

    public static final String FUNC_WITHDRAWBALANCE = "withdrawBalance";

    public static final Event AUCTIONCANCELLED_EVENT = new Event("AuctionCancelled", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event AUCTIONCREATED_EVENT = new Event("AuctionCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event AUCTIONSUCCESSFUL_EVENT = new Event("AuctionSuccessful", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event PAUSE_EVENT = new Event("Pause", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event UNPAUSE_EVENT = new Event("Unpause", 
            Arrays.<TypeReference<?>>asList());
    ;

    protected SiringClockAuction(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public static String getABI() {
        return ABI;
    }

    public List<AuctionCancelledEventResponse> getAuctionCancelledEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(AUCTIONCANCELLED_EVENT, transactionReceipt);
        ArrayList<AuctionCancelledEventResponse> responses = new ArrayList<AuctionCancelledEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AuctionCancelledEventResponse typedResponse = new AuctionCancelledEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public List<AuctionCreatedEventResponse> getAuctionCreatedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(AUCTIONCREATED_EVENT, transactionReceipt);
        ArrayList<AuctionCreatedEventResponse> responses = new ArrayList<AuctionCreatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AuctionCreatedEventResponse typedResponse = new AuctionCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.startingPrice = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.endingPrice = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.duration = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public List<AuctionSuccessfulEventResponse> getAuctionSuccessfulEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(AUCTIONSUCCESSFUL_EVENT, transactionReceipt);
        ArrayList<AuctionSuccessfulEventResponse> responses = new ArrayList<AuctionSuccessfulEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AuctionSuccessfulEventResponse typedResponse = new AuctionSuccessfulEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.totalPrice = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.winner = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public List<PauseEventResponse> getPauseEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PAUSE_EVENT, transactionReceipt);
        ArrayList<PauseEventResponse> responses = new ArrayList<PauseEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PauseEventResponse typedResponse = new PauseEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public List<UnpauseEventResponse> getUnpauseEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(UNPAUSE_EVENT, transactionReceipt);
        ArrayList<UnpauseEventResponse> responses = new ArrayList<UnpauseEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UnpauseEventResponse typedResponse = new UnpauseEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public TransactionReceipt bid(BigInteger _tokenId) {
        final Function function = new Function(
                FUNC_BID, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(_tokenId)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return executeTransaction(function);
    }

    public String bid(BigInteger _tokenId, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_BID, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(_tokenId)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForBid(BigInteger _tokenId) {
        final Function function = new Function(
                FUNC_BID, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(_tokenId)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return createSignedTransaction(function);
    }

    public Tuple1<BigInteger> getBidInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_BID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public TransactionReceipt cancelAuction(BigInteger _tokenId) {
        final Function function = new Function(
                FUNC_CANCELAUCTION, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(_tokenId)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return executeTransaction(function);
    }

    public String cancelAuction(BigInteger _tokenId, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_CANCELAUCTION, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(_tokenId)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForCancelAuction(BigInteger _tokenId) {
        final Function function = new Function(
                FUNC_CANCELAUCTION, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(_tokenId)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return createSignedTransaction(function);
    }

    public Tuple1<BigInteger> getCancelAuctionInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_CANCELAUCTION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public TransactionReceipt cancelAuctionWhenPaused(BigInteger _tokenId) {
        final Function function = new Function(
                FUNC_CANCELAUCTIONWHENPAUSED, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(_tokenId)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return executeTransaction(function);
    }

    public String cancelAuctionWhenPaused(BigInteger _tokenId, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_CANCELAUCTIONWHENPAUSED, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(_tokenId)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForCancelAuctionWhenPaused(BigInteger _tokenId) {
        final Function function = new Function(
                FUNC_CANCELAUCTIONWHENPAUSED, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(_tokenId)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return createSignedTransaction(function);
    }

    public Tuple1<BigInteger> getCancelAuctionWhenPausedInput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_CANCELAUCTIONWHENPAUSED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public TransactionReceipt createAuction(BigInteger _tokenId, BigInteger _startingPrice,
            BigInteger _endingPrice, BigInteger _duration, String _seller) {
        final Function function = new Function(
                FUNC_CREATEAUCTION, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(_tokenId), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(_startingPrice), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(_endingPrice), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(_duration), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(_seller)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return executeTransaction(function);
    }

    public String createAuction(BigInteger _tokenId, BigInteger _startingPrice,
            BigInteger _endingPrice, BigInteger _duration, String _seller,
            TransactionCallback callback) {
        final Function function = new Function(
                FUNC_CREATEAUCTION, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(_tokenId), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(_startingPrice), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(_endingPrice), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(_duration), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(_seller)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForCreateAuction(BigInteger _tokenId,
            BigInteger _startingPrice, BigInteger _endingPrice, BigInteger _duration,
            String _seller) {
        final Function function = new Function(
                FUNC_CREATEAUCTION, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(_tokenId), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(_startingPrice), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(_endingPrice), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(_duration), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(_seller)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return createSignedTransaction(function);
    }

    public Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, String> getCreateAuctionInput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_CREATEAUCTION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, String>(

                (BigInteger) results.get(0).getValue(), 
                (BigInteger) results.get(1).getValue(), 
                (BigInteger) results.get(2).getValue(), 
                (BigInteger) results.get(3).getValue(), 
                (String) results.get(4).getValue()
                );
    }

    public Tuple5<String, BigInteger, BigInteger, BigInteger, BigInteger> getAuction(
            BigInteger _tokenId) throws ContractException {
        final Function function = new Function(FUNC_GETAUCTION, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(_tokenId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = executeCallWithMultipleValueReturn(function);
        return new Tuple5<String, BigInteger, BigInteger, BigInteger, BigInteger>(
                (String) results.get(0).getValue(), 
                (BigInteger) results.get(1).getValue(), 
                (BigInteger) results.get(2).getValue(), 
                (BigInteger) results.get(3).getValue(), 
                (BigInteger) results.get(4).getValue());
    }

    public BigInteger getCurrentPrice(BigInteger _tokenId) throws ContractException {
        final Function function = new Function(FUNC_GETCURRENTPRICE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(_tokenId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public Boolean isSiringClockAuction() throws ContractException {
        final Function function = new Function(FUNC_ISSIRINGCLOCKAUCTION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallWithSingleValueReturn(function, Boolean.class);
    }

    public String nonFungibleContract() throws ContractException {
        final Function function = new Function(FUNC_NONFUNGIBLECONTRACT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public String owner() throws ContractException {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public BigInteger ownerCut() throws ContractException {
        final Function function = new Function(FUNC_OWNERCUT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public TransactionReceipt pause() {
        final Function function = new Function(
                FUNC_PAUSE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return executeTransaction(function);
    }

    public String pause(TransactionCallback callback) {
        final Function function = new Function(
                FUNC_PAUSE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForPause() {
        final Function function = new Function(
                FUNC_PAUSE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return createSignedTransaction(function);
    }

    public Tuple1<Boolean> getPauseOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_PAUSE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<Boolean>(

                (Boolean) results.get(0).getValue()
                );
    }

    public Boolean paused() throws ContractException {
        final Function function = new Function(FUNC_PAUSED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallWithSingleValueReturn(function, Boolean.class);
    }

    public TransactionReceipt transferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return executeTransaction(function);
    }

    public String transferOwnership(String newOwner, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForTransferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return createSignedTransaction(function);
    }

    public Tuple1<String> getTransferOwnershipInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public TransactionReceipt unpause() {
        final Function function = new Function(
                FUNC_UNPAUSE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return executeTransaction(function);
    }

    public String unpause(TransactionCallback callback) {
        final Function function = new Function(
                FUNC_UNPAUSE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForUnpause() {
        final Function function = new Function(
                FUNC_UNPAUSE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return createSignedTransaction(function);
    }

    public Tuple1<Boolean> getUnpauseOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_UNPAUSE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<Boolean>(

                (Boolean) results.get(0).getValue()
                );
    }

    public TransactionReceipt withdrawBalance() {
        final Function function = new Function(
                FUNC_WITHDRAWBALANCE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return executeTransaction(function);
    }

    public String withdrawBalance(TransactionCallback callback) {
        final Function function = new Function(
                FUNC_WITHDRAWBALANCE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForWithdrawBalance() {
        final Function function = new Function(
                FUNC_WITHDRAWBALANCE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return createSignedTransaction(function);
    }

    public static SiringClockAuction load(String contractAddress, Client client,
            CryptoKeyPair credential) {
        return new SiringClockAuction(contractAddress, client, credential);
    }

    public static SiringClockAuction deploy(Client client, CryptoKeyPair credential,
            String _nftAddr, BigInteger _cut) throws ContractException {
        byte[] encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Address(_nftAddr), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(_cut)));
        return deploy(SiringClockAuction.class, client, credential, getBinary(client.getCryptoSuite()), getABI(), encodedConstructor, null);
    }

    public static class AuctionCancelledEventResponse {
        public TransactionReceipt.Logs log;

        public BigInteger tokenId;
    }

    public static class AuctionCreatedEventResponse {
        public TransactionReceipt.Logs log;

        public BigInteger tokenId;

        public BigInteger startingPrice;

        public BigInteger endingPrice;

        public BigInteger duration;
    }

    public static class AuctionSuccessfulEventResponse {
        public TransactionReceipt.Logs log;

        public BigInteger tokenId;

        public BigInteger totalPrice;

        public String winner;
    }

    public static class PauseEventResponse {
        public TransactionReceipt.Logs log;
    }

    public static class UnpauseEventResponse {
        public TransactionReceipt.Logs log;
    }
}
