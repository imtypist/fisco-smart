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
public class SaleClockAuction extends Contract {
    public static final String[] BINARY_ARRAY = {"60806040526000805460ff60a01b191690556004805460ff1916600117905534801561002a57600080fd5b506040516110b13803806110b183398101604081905261004991610115565b600080546001600160a01b03191633179055818161271081111561006c57600080fd5b60028190556040516301ffc9a760e01b8152639a20483d60e01b600482015282906001600160a01b038216906301ffc9a790602401602060405180830381865afa1580156100be573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906100e2919061014f565b6100eb57600080fd5b600180546001600160a01b0319166001600160a01b03929092169190911790555061017892505050565b6000806040838503121561012857600080fd5b82516001600160a01b038116811461013f57600080fd5b6020939093015192949293505050565b60006020828403121561016157600080fd5b8151801515811461017157600080fd5b9392505050565b610f2a806101876000396000f3fe6080604052600436106101095760003560e01c806385b861881161009557806396b5a7551161006457806396b5a755146102d6578063c55d0f56146102f6578063dd1b7a0f14610316578063eac9d94c14610336578063f2fde38b1461034b57600080fd5b806385b861881461024e578063878eb368146102685780638a98a9cc146102885780638da5cb5b1461029e57600080fd5b80635c975abb116100dc5780635c975abb1461019b5780635fd8c710146101bc57806378bd7935146101d157806383b5ff8b146102235780638456cb591461023957600080fd5b806327ebe40a1461010e5780633f4ba83a14610130578063454a2ab31461015a578063484eccb41461016d575b600080fd5b34801561011a57600080fd5b5061012e610129366004610c71565b61036b565b005b34801561013c57600080fd5b50610145610423565b60405190151581526020015b60405180910390f35b61012e610168366004610cba565b61048b565b34801561017957600080fd5b5061018d610188366004610cba565b61050e565b604051908152602001610151565b3480156101a757600080fd5b5060005461014590600160a01b900460ff1681565b3480156101c857600080fd5b5061012e610525565b3480156101dd57600080fd5b506101f16101ec366004610cba565b61058f565b604080516001600160a01b0390961686526020860194909452928401919091526060830152608082015260a001610151565b34801561022f57600080fd5b5061018d60025481565b34801561024557600080fd5b50610145610613565b34801561025a57600080fd5b506004546101459060ff1681565b34801561027457600080fd5b5061012e610283366004610cba565b610682565b34801561029457600080fd5b5061018d60055481565b3480156102aa57600080fd5b506000546102be906001600160a01b031681565b6040516001600160a01b039091168152602001610151565b3480156102e257600080fd5b5061012e6102f1366004610cba565b6106f1565b34801561030257600080fd5b5061018d610311366004610cba565b61073e565b34801561032257600080fd5b506001546102be906001600160a01b031681565b34801561034257600080fd5b5061018d61077a565b34801561035757600080fd5b5061012e610366366004610cd3565b6107cd565b836001600160801b0316841461038057600080fd5b826001600160801b0316831461039557600080fd5b816001600160401b031682146103aa57600080fd5b6001546001600160a01b031633146103c157600080fd5b6103cb8186610812565b6040805160a0810182526001600160a01b03831681526001600160801b0380871660208301528516918101919091526001600160401b0380841660608301524216608082015261041b868261087b565b505050505050565b600080546001600160a01b0316331461043b57600080fd5b600054600160a01b900460ff1661045157600080fd5b6000805460ff60a01b191681556040517f7805862f689e2f13df9f062ff482ad3ad112aca9e0847911ed832e158c525b339190a150600190565b6000818152600360205260408120546001600160a01b0316906104ae8334610976565b90506104ba3384610ab3565b6001546001600160a01b038381169116141561050957806006600580546104e19190610d04565b600581106104f1576104f1610d18565b01556005805490600061050383610d44565b91905055505b505050565b6006816005811061051e57600080fd5b0154905081565b6001546000546001600160a01b03918216911633148061054d5750336001600160a01b038216145b61055657600080fd5b6040516001600160a01b038216904780156108fc02916000818181858888f1935050505015801561058b573d6000803e3d6000fd5b5050565b600081815260036020526040812060028101548291829182918291600160401b90046001600160401b03166105c357600080fd5b805460018201546002909201546001600160a01b03909116986001600160801b038084169950600160801b90930490921696506001600160401b038082169650600160401b909104169350915050565b600080546001600160a01b0316331461062b57600080fd5b600054600160a01b900460ff161561064257600080fd5b6000805460ff60a01b1916600160a01b1781556040517f6985a02210a168e66602d3235cb6db0e70f92b3ba4d376a33c0f3d9434bff6259190a150600190565b600054600160a01b900460ff1661069857600080fd5b6000546001600160a01b031633146106af57600080fd5b60008181526003602052604090206002810154600160401b90046001600160401b03166106db57600080fd5b805461058b9083906001600160a01b0316610aec565b60008181526003602052604090206002810154600160401b90046001600160401b031661071d57600080fd5b80546001600160a01b031633811461073457600080fd5b6105098382610aec565b60008181526003602052604081206002810154600160401b90046001600160401b031661076a57600080fd5b61077381610b2f565b9392505050565b600080805b60058110156107bb576006816005811061079b5761079b610d18565b01546107a79083610d5f565b9150806107b381610d44565b91505061077f565b506107c7600582610d77565b91505090565b6000546001600160a01b031633146107e457600080fd5b6001600160a01b0381161561080f57600080546001600160a01b0319166001600160a01b0383161790555b50565b6001546040516323b872dd60e01b81526001600160a01b03848116600483015230602483015260448201849052909116906323b872dd906064015b600060405180830381600087803b15801561086757600080fd5b505af115801561041b573d6000803e3d6000fd5b603c81606001516001600160401b0316101561089657600080fd5b600082815260036020908152604091829020835181546001600160a01b039091166001600160a01b031990911617815590830151828401516001600160801b03908116600160801b810291909216908117600184015560608501516002909301805460808701516001600160401b03908116600160401b026001600160801b031992909216951694851717905592517fa9c8dfcda5664a5a124c713e386da27de87432d5b668e79458501eb296389ba79361096a938793919293845260208401929092526040830152606082015260800190565b60405180910390a15050565b60008281526003602052604081206002810154600160401b90046001600160401b03166109a257600080fd5b60006109ad82610b2f565b9050808410156109bc57600080fd5b81546001600160a01b03166109d086610ba5565b8115610a2c5760006109e183610bdc565b905060006109ef8285610d8b565b6040519091506001600160a01b0384169082156108fc029083906000818181858888f19350505050158015610a28573d6000803e3d6000fd5b5050505b6000610a388387610d8b565b604051909150339082156108fc029083906000818181858888f19350505050158015610a68573d6000803e3d6000fd5b506040805188815260208101859052338183015290517f4fcc30d90a842164dd58501ab874a101a3749c3d4747139cefe7c876f4ccebd29181900360600190a1509095945050505050565b60015460405163a9059cbb60e01b81526001600160a01b038481166004830152602482018490529091169063a9059cbb9060440161084d565b610af582610ba5565b610aff8183610ab3565b6040518281527f2809c7e17bf978fbc7194c0a694b638c4215e9140cacc6c38ca36010b45697df9060200161096a565b60028101546000908190600160401b90046001600160401b0316421115610b72576002830154610b6f90600160401b90046001600160401b031642610d8b565b90505b60018301546002840154610773916001600160801b0380821692600160801b90920416906001600160401b031684610bff565b600090815260036020526040812080546001600160a01b0319168155600181019190915560020180546001600160801b0319169055565b600061271060025483610bef9190610da2565b610bf99190610d77565b92915050565b6000828210610c0f575082610c4d565b6000610c1b8686610dc1565b9050600084610c2a8584610e00565b610c349190610e85565b90506000610c428289610eb3565b9350610c4d92505050565b949350505050565b80356001600160a01b0381168114610c6c57600080fd5b919050565b600080600080600060a08688031215610c8957600080fd5b85359450602086013593506040860135925060608601359150610cae60808701610c55565b90509295509295909350565b600060208284031215610ccc57600080fd5b5035919050565b600060208284031215610ce557600080fd5b61077382610c55565b634e487b7160e01b600052601260045260246000fd5b600082610d1357610d13610cee565b500690565b634e487b7160e01b600052603260045260246000fd5b634e487b7160e01b600052601160045260246000fd5b6000600019821415610d5857610d58610d2e565b5060010190565b60008219821115610d7257610d72610d2e565b500190565b600082610d8657610d86610cee565b500490565b600082821015610d9d57610d9d610d2e565b500390565b6000816000190483118215151615610dbc57610dbc610d2e565b500290565b60008083128015600160ff1b850184121615610ddf57610ddf610d2e565b6001600160ff1b0384018313811615610dfa57610dfa610d2e565b50500390565b60006001600160ff1b0381841382841380821686840486111615610e2657610e26610d2e565b600160ff1b6000871282811687830589121615610e4557610e45610d2e565b60008712925087820587128484161615610e6157610e61610d2e565b87850587128184161615610e7757610e77610d2e565b50","5050929093029392505050565b600082610e9457610e94610cee565b600160ff1b821460001984141615610eae57610eae610d2e565b500590565b600080821280156001600160ff1b0384900385131615610ed557610ed5610d2e565b600160ff1b8390038412811615610eee57610eee610d2e565b5050019056fea264697066735822122050de7c2d45771c01ac58cde9581e0e84b8865454031c2450dd13ab00e572a7ba64736f6c634300080b0033"};

    public static final String BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"60806040526000805460ff60a01b191690556004805460ff1916600117905534801561002a57600080fd5b506040516110ad3803806110ad83398101604081905261004991610115565b600080546001600160a01b03191633179055818161271081111561006c57600080fd5b6002819055604051631d4fd6f360e31b8152639a20483d60e01b600482015282906001600160a01b0382169063ea7eb79890602401602060405180830381865afa1580156100be573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906100e2919061014f565b6100eb57600080fd5b600180546001600160a01b0319166001600160a01b03929092169190911790555061017892505050565b6000806040838503121561012857600080fd5b82516001600160a01b038116811461013f57600080fd5b6020939093015192949293505050565b60006020828403121561016157600080fd5b8151801515811461017157600080fd5b9392505050565b610f26806101876000396000f3fe6080604052600436106101095760003560e01c8063892e6f9511610095578063caa7d1f311610064578063caa7d1f3146102df578063d4bc9601146102ff578063e39643a914610320578063ecd0c17d14610336578063f4fbb0c81461035657600080fd5b8063892e6f951461027d5780639498fbdc146102925780639adc3370146102b2578063b44f5bd9146102cc57600080fd5b80632053747d116100dc5780632053747d146101d5578063297be052146101ea5780632f6175041461020a5780635089e2c81461022057806363c375261461025857600080fd5b8063011a4bba1461010e5780630a0379a1146101415780630e34a02f1461019357806316cad12a146101b5575b600080fd5b34801561011a57600080fd5b5061012e610129366004610c51565b61036b565b6040519081526020015b60405180910390f35b34801561014d57600080fd5b5061016161015c366004610c51565b6103a7565b604080516001600160a01b0390961686526020860194909452928401919091526060830152608082015260a001610138565b34801561019f57600080fd5b506101b36101ae366004610c51565b61042b565b005b3480156101c157600080fd5b506101b36101d0366004610c86565b61049e565b3480156101e157600080fd5b5061012e6104e3565b3480156101f657600080fd5b506101b3610205366004610ca1565b610536565b34801561021657600080fd5b5061012e60055481565b34801561022c57600080fd5b50600054610240906001600160a01b031681565b6040516001600160a01b039091168152602001610138565b34801561026457600080fd5b5061026d6105ee565b6040519015158152602001610138565b34801561028957600080fd5b506101b3610656565b34801561029e57600080fd5b50600154610240906001600160a01b031681565b3480156102be57600080fd5b5060045461026d9060ff1681565b6101b36102da366004610c51565b6106bc565b3480156102eb57600080fd5b506101b36102fa366004610c51565b61073f565b34801561030b57600080fd5b5060005461026d90600160a01b900460ff1681565b34801561032c57600080fd5b5061012e60025481565b34801561034257600080fd5b5061012e610351366004610c51565b61078c565b34801561036257600080fd5b5061026d6107a3565b60008181526003602052604081206002810154600160401b90046001600160401b031661039757600080fd5b6103a081610812565b9392505050565b600081815260036020526040812060028101548291829182918291600160401b90046001600160401b03166103db57600080fd5b805460018201546002909201546001600160a01b03909116986001600160801b038084169950600160801b90930490921696506001600160401b038082169650600160401b909104169350915050565b600054600160a01b900460ff1661044157600080fd5b6000546001600160a01b0316331461045857600080fd5b60008181526003602052604090206002810154600160401b90046001600160401b031661048457600080fd5b805461049a9083906001600160a01b0316610888565b5050565b6000546001600160a01b031633146104b557600080fd5b6001600160a01b038116156104e057600080546001600160a01b0319166001600160a01b0383161790555b50565b600080805b6005811015610524576006816005811061050457610504610cea565b01546105109083610d16565b91508061051c81610d2e565b9150506104e8565b50610530600582610d5f565b91505090565b836001600160801b0316841461054b57600080fd5b826001600160801b0316831461056057600080fd5b816001600160401b0316821461057557600080fd5b6001546001600160a01b0316331461058c57600080fd5b61059681866108d3565b6040805160a0810182526001600160a01b03831681526001600160801b0380871660208301528516918101919091526001600160401b038084166060830152421660808201526105e6868261093c565b505050505050565b600080546001600160a01b0316331461060657600080fd5b600054600160a01b900460ff1661061c57600080fd5b6000805460ff60a01b191681556040517fe6d87ee340ec45ca4fde177223d81612516ef4e9027eb2ec3190948d3e22d06b9190a150600190565b6001546000546001600160a01b03918216911633148061067e5750336001600160a01b038216145b61068757600080fd5b6040516001600160a01b038216904780156108fc02916000818181858888f1935050505015801561049a573d6000803e3d6000fd5b6000818152600360205260408120546001600160a01b0316906106df8334610a2b565b90506106eb3384610b68565b6001546001600160a01b038381169116141561073a57806006600580546107129190610d73565b6005811061072257610722610cea565b01556005805490600061073483610d2e565b91905055505b505050565b60008181526003602052604090206002810154600160401b90046001600160401b031661076b57600080fd5b80546001600160a01b031633811461078257600080fd5b61073a8382610888565b6006816005811061079c57600080fd5b0154905081565b600080546001600160a01b031633146107bb57600080fd5b600054600160a01b900460ff16156107d257600080fd5b6000805460ff60a01b1916600160a01b1781556040517f82c2a22cb62f9fcb667c82b15e864fd9106fc6e8bdf191e1940c2c0518b000d99190a150600190565b60028101546000908190600160401b90046001600160401b031642111561085557600283015461085290600160401b90046001600160401b031642610d87565b90505b600183015460028401546103a0916001600160801b0380821692600160801b90920416906001600160401b031684610ba1565b61089182610bf7565b61089b8183610b68565b6040518281527f6a1c1c1116704b877f29b2850527f2b920dab58cac0b59089e93cba921151319906020015b60405180910390a15050565b60015460405163ad8a973160e01b81526001600160a01b038481166004830152306024830152604482018490529091169063ad8a9731906064015b600060405180830381600087803b15801561092857600080fd5b505af11580156105e6573d6000803e3d6000fd5b603c81606001516001600160401b0316101561095757600080fd5b600082815260036020908152604091829020835181546001600160a01b039091166001600160a01b031990911617815590830151828401516001600160801b03908116600160801b810291909216908117600184015560608501516002909301805460808701516001600160401b03908116600160401b026001600160801b031992909216951694851717905592517f2eb78c1a81ae6e7facaa29286c9fd12939ed047a83a30a760c15958bc7b324ed936108c7938793919293845260208401929092526040830152606082015260800190565b60008281526003602052604081206002810154600160401b90046001600160401b0316610a5757600080fd5b6000610a6282610812565b905080841015610a7157600080fd5b81546001600160a01b0316610a8586610bf7565b8115610ae1576000610a9683610c2e565b90506000610aa48285610d87565b6040519091506001600160a01b0384169082156108fc029083906000818181858888f19350505050158015610add573d6000803e3d6000fd5b5050505b6000610aed8387610d87565b604051909150339082156108fc029083906000818181858888f19350505050158015610b1d573d6000803e3d6000fd5b506040805188815260208101859052338183015290517f8323c29c2c7ea62f686500ae944bc3d91129cc0a6b1bb38f32d5d8fb43ba2afd9181900360600190a1509095945050505050565b600154604051636904e96560e01b81526001600160a01b0384811660048301526024820184905290911690636904e9659060440161090e565b6000828210610bb1575082610bef565b6000610bbd8686610d9e565b9050600084610bcc8584610ddd565b610bd69190610e62565b90506000610be48289610e90565b9350610bef92505050565b949350505050565b600090815260036020526040812080546001600160a01b0319168155600181019190915560020180546001600160801b0319169055565b600061271060025483610c419190610ed1565b610c4b9190610d5f565b92915050565b600060208284031215610c6357600080fd5b5035919050565b80356001600160a01b0381168114610c8157600080fd5b919050565b600060208284031215610c9857600080fd5b6103a082610c6a565b600080600080600060a08688031215610cb957600080fd5b85359450602086013593506040860135925060608601359150610cde60808701610c6a565b90509295509295909350565b63b95aa35560e01b600052603260045260246000fd5b63b95aa35560e01b600052601160045260246000fd5b60008219821115610d2957610d29610d00565b500190565b6000600019821415610d4257610d42610d00565b5060010190565b63b95aa35560e01b600052601260045260246000fd5b600082610d6e57610d6e610d49565b500490565b600082610d8257610d82610d49565b500690565b600082821015610d9957610d99610d00565b500390565b60008083128015600160ff1b850184121615610dbc57610dbc610d00565b6001600160ff1b0384018313811615610dd757610dd7610d00565b50500390565b60006001600160ff1b0381841382841380821686840486111615610e0357610e03610d00565b600160ff1b6000871282811687830589121615610e2257610e22610d00565b60008712925087820587128484161615610e3e57610e3e610d00565b87850587128184161615610e5457610e54610d00565b505050929093029392505050565b600082610e7157610e71610d49565b600160ff1b8214","60001984141615610e8b57610e8b610d00565b500590565b600080821280156001600160ff1b0384900385131615610eb257610eb2610d00565b600160ff1b8390038412811615610ecb57610ecb610d00565b50500190565b6000816000190483118215151615610eeb57610eeb610d00565b50029056fea26469706673582212200a9375b14fabde70d409a761f87d9ca7df87725e9dd8b91264c4258770f2e9c264736f6c634300080b0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_nftAddr\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_cut\",\"type\":\"uint256\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"tokenId\",\"type\":\"uint256\"}],\"name\":\"AuctionCancelled\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"tokenId\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"startingPrice\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"endingPrice\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"duration\",\"type\":\"uint256\"}],\"name\":\"AuctionCreated\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"tokenId\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"totalPrice\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"winner\",\"type\":\"address\"}],\"name\":\"AuctionSuccessful\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[],\"name\":\"Pause\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[],\"name\":\"Unpause\",\"type\":\"event\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[],\"name\":\"averageGen0SalePrice\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"selector\":[3939096908,542340221],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_tokenId\",\"type\":\"uint256\"}],\"name\":\"bid\",\"outputs\":[],\"selector\":[1162488499,3025099737],\"stateMutability\":\"payable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":3,\"slot\":3,\"value\":[0]}],\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_tokenId\",\"type\":\"uint256\"}],\"name\":\"cancelAuction\",\"outputs\":[],\"selector\":[2528487253,3399995891],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":3,\"slot\":3,\"value\":[0]},{\"kind\":4,\"value\":[0]}],\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_tokenId\",\"type\":\"uint256\"}],\"name\":\"cancelAuctionWhenPaused\",\"outputs\":[],\"selector\":[2274276200,238329903],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_tokenId\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"_startingPrice\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"_endingPrice\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"_duration\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"_seller\",\"type\":\"address\"}],\"name\":\"createAuction\",\"outputs\":[],\"selector\":[669770762,695984210],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":4,\"value\":[5]}],\"inputs\":[],\"name\":\"gen0SaleCount\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"selector\":[2325260748,794916100],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":3,\"slot\":3,\"value\":[0]}],\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_tokenId\",\"type\":\"uint256\"}],\"name\":\"getAuction\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"seller\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"startingPrice\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"endingPrice\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"duration\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"startedAt\",\"type\":\"uint256\"}],\"selector\":[2025683253,167999905],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":3,\"slot\":3,\"value\":[0]}],\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_tokenId\",\"type\":\"uint256\"}],\"name\":\"getCurrentPrice\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"selector\":[3311210326,18500538],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":4,\"value\":[4]}],\"inputs\":[],\"name\":\"isSaleClockAuction\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"selector\":[2243453320,2598122352],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"name\":\"lastGen0SalePrices\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"selector\":[1213123764,3973103997],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":4,\"value\":[1]}],\"inputs\":[],\"name\":\"nonFungibleContract\",\"outputs\":[{\"internalType\":\"contract ERC721\",\"name\":\"\",\"type\":\"address\"}],\"selector\":[3709565455,2493053916],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":4,\"value\":[0]}],\"inputs\":[],\"name\":\"owner\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"selector\":[2376452955,1351213768],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":4,\"value\":[2]}],\"inputs\":[],\"name\":\"ownerCut\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"selector\":[2209742731,3818275753],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":4,\"value\":[0]}],\"inputs\":[],\"name\":\"pause\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"selector\":[2220280665,4110135496],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":4,\"value\":[0]}],\"inputs\":[],\"name\":\"paused\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"selector\":[1553423035,3569128961],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":4,\"value\":[0]}],\"inputs\":[{\"internalType\":\"address\",\"name\":\"newOwner\",\"type\":\"address\"}],\"name\":\"transferOwnership\",\"outputs\":[],\"selector\":[4076725131,382390570],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":4,\"value\":[0]}],\"inputs\":[],\"name\":\"unpause\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"selector\":[1061922874,1673753894],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[],\"name\":\"withdrawBalance\",\"outputs\":[],\"selector\":[1608042256,2301521813],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_AVERAGEGEN0SALEPRICE = "averageGen0SalePrice";

    public static final String FUNC_BID = "bid";

    public static final String FUNC_CANCELAUCTION = "cancelAuction";

    public static final String FUNC_CANCELAUCTIONWHENPAUSED = "cancelAuctionWhenPaused";

    public static final String FUNC_CREATEAUCTION = "createAuction";

    public static final String FUNC_GEN0SALECOUNT = "gen0SaleCount";

    public static final String FUNC_GETAUCTION = "getAuction";

    public static final String FUNC_GETCURRENTPRICE = "getCurrentPrice";

    public static final String FUNC_ISSALECLOCKAUCTION = "isSaleClockAuction";

    public static final String FUNC_LASTGEN0SALEPRICES = "lastGen0SalePrices";

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

    protected SaleClockAuction(String contractAddress, Client client, CryptoKeyPair credential) {
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

    public BigInteger averageGen0SalePrice() throws ContractException {
        final Function function = new Function(FUNC_AVERAGEGEN0SALEPRICE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
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

    public BigInteger gen0SaleCount() throws ContractException {
        final Function function = new Function(FUNC_GEN0SALECOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
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

    public Boolean isSaleClockAuction() throws ContractException {
        final Function function = new Function(FUNC_ISSALECLOCKAUCTION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallWithSingleValueReturn(function, Boolean.class);
    }

    public BigInteger lastGen0SalePrices(BigInteger param0) throws ContractException {
        final Function function = new Function(FUNC_LASTGEN0SALEPRICES, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
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

    public static SaleClockAuction load(String contractAddress, Client client,
            CryptoKeyPair credential) {
        return new SaleClockAuction(contractAddress, client, credential);
    }

    public static SaleClockAuction deploy(Client client, CryptoKeyPair credential, String _nftAddr,
            BigInteger _cut) throws ContractException {
        byte[] encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Address(_nftAddr), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256(_cut)));
        return deploy(SaleClockAuction.class, client, credential, getBinary(client.getCryptoSuite()), getABI(), encodedConstructor, null);
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
