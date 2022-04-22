package org.fisco.smart;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.codec.datatypes.DynamicBytes;
import org.fisco.bcos.sdk.v3.codec.datatypes.Function;
import org.fisco.bcos.sdk.v3.codec.datatypes.Type;
import org.fisco.bcos.sdk.v3.codec.datatypes.TypeReference;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes32;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.v3.contract.Contract;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.CryptoType;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class ShaTest extends Contract {
    public static final String[] BINARY_ARRAY = {"60c0604052600e60808190526d12195b1b1bcb0814da1855195cdd60921b60a090815261002f9160009190610056565b5034801561003c57600080fd5b50600180546001600160a01b03191661100a17905561012a565b828054610062906100ef565b90600052602060002090601f01602090048101928261008457600085556100ca565b82601f1061009d57805160ff19168380011785556100ca565b828001600101855582156100ca579182015b828111156100ca5782518255916020019190600101906100af565b506100d69291506100da565b5090565b5b808211156100d657600081556001016100db565b600181811c9082168061010357607f821691505b6020821081141561012457634e487b7160e01b600052602260045260246000fd5b50919050565b610433806101396000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c80632d0ccea61461005c5780633bc5de30146100825780638b0537581461009757806393730bbe146100aa578063f246d346146100c3575b600080fd5b61006f61006a366004610279565b6100d6565b6040519081526020015b60405180910390f35b61008a61014e565b604051610079919061035a565b61006f6100a5366004610279565b6101e0565b61006f6100b8366004610279565b805160209091012090565b61006f6100d1366004610279565b610232565b60015460405163eb90f45960e01b81526000916001600160a01b03169063eb90f4599061010790859060040161035a565b602060405180830381865afa158015610124573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610148919061038d565b92915050565b60606000805461015d906103a6565b80601f0160208091040260200160405190810160405280929190818152602001828054610189906103a6565b80156101d65780601f106101ab576101008083540402835291602001916101d6565b820191906000526020600020905b8154815290600101906020018083116101b957829003601f168201915b5050505050905090565b60006002826040516101f291906103e1565b602060405180830381855afa15801561020f573d6000803e3d6000fd5b5050506040513d601f19601f82011682018060405250810190610148919061038d565b600154604051633ecd0d8f60e21b81526000916001600160a01b03169063fb34363c9061010790859060040161035a565b634e487b7160e01b600052604160045260246000fd5b60006020828403121561028b57600080fd5b813567ffffffffffffffff808211156102a357600080fd5b818401915084601f8301126102b757600080fd5b8135818111156102c9576102c9610263565b604051601f8201601f19908116603f011681019083821181831017156102f1576102f1610263565b8160405282815287602084870101111561030a57600080fd5b826020860160208301376000928101602001929092525095945050505050565b60005b8381101561034557818101518382015260200161032d565b83811115610354576000848401525b50505050565b602081526000825180602084015261037981604085016020870161032a565b601f01601f19169190910160400192915050565b60006020828403121561039f57600080fd5b5051919050565b600181811c908216806103ba57607f821691505b602082108114156103db57634e487b7160e01b600052602260045260246000fd5b50919050565b600082516103f381846020870161032a565b919091019291505056fea264697066735822122078ffdc594092fab35ff2caf722e7cebb835763136ceaf3492c3442baac2acc0964736f6c634300080b0033"};

    public static final String BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"60c0604052600e60808190526d12195b1b1bcb0814da1855195cdd60921b60a090815261002f9160009190610056565b5034801561003c57600080fd5b50600180546001600160a01b03191661100a17905561012a565b828054610062906100ef565b90600052602060002090601f01602090048101928261008457600085556100ca565b82601f1061009d57805160ff19168380011785556100ca565b828001600101855582156100ca579182015b828111156100ca5782518255916020019190600101906100af565b506100d69291506100da565b5090565b5b808211156100d657600081556001016100db565b600181811c9082168061010357607f821691505b602082108114156101245763b95aa35560e01b600052602260045260246000fd5b50919050565b610433806101396000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c80634cc1e5cc1461005c578063a91dffb514610082578063a935d28414610095578063d81443b4146100a8578063e211e0c1146100c1575b600080fd5b61006f61006a366004610279565b6100d6565b6040519081526020015b60405180910390f35b61006f610090366004610279565b61014e565b61006f6100a3366004610279565b61017f565b61006f6100b6366004610279565b805160209091012090565b6100c96101d1565b604051610079919061035a565b60015460405163f25611b560e01b81526000916001600160a01b03169063f25611b59061010790859060040161035a565b602060405180830381865afa158015610124573d6000803e3d6000fd5b505050506040513d601f19601f82011682018060405250810190610148919061038d565b92915050565b60015460405163b651010760e01b81526000916001600160a01b03169063b65101079061010790859060040161035a565b600060028260405161019191906103a6565b602060405180830381855afa1580156101ae573d6000803e3d6000fd5b5050506040513d601f19601f82011682018060405250810190610148919061038d565b6060600080546101e0906103c2565b80601f016020809104026020016040519081016040528092919081815260200182805461020c906103c2565b80156102595780601f1061022e57610100808354040283529160200191610259565b820191906000526020600020905b81548152906001019060200180831161023c57829003601f168201915b5050505050905090565b63b95aa35560e01b600052604160045260246000fd5b60006020828403121561028b57600080fd5b813567ffffffffffffffff808211156102a357600080fd5b818401915084601f8301126102b757600080fd5b8135818111156102c9576102c9610263565b604051601f8201601f19908116603f011681019083821181831017156102f1576102f1610263565b8160405282815287602084870101111561030a57600080fd5b826020860160208301376000928101602001929092525095945050505050565b60005b8381101561034557818101518382015260200161032d565b83811115610354576000848401525b50505050565b602081526000825180602084015261037981604085016020870161032a565b601f01601f19169190910160400192915050565b60006020828403121561039f57600080fd5b5051919050565b600082516103b881846020870161032a565b9190910192915050565b600181811c908216806103d657607f821691505b602082108114156103f75763b95aa35560e01b600052602260045260246000fd5b5091905056fea26469706673582212201cd79c27d4c57d8336af018e48f3e6fbbc4ecbc1a7598487d967c702569a35a764736f6c634300080b0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"inputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"bytes\",\"name\":\"_memory\",\"type\":\"bytes\"}],\"name\":\"calculateKeccak256\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"result\",\"type\":\"bytes32\"}],\"selector\":[755814054,2837315509],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"bytes\",\"name\":\"_memory\",\"type\":\"bytes\"}],\"name\":\"calculateSM3\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"result\",\"type\":\"bytes32\"}],\"selector\":[4064727878,1287775692],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":4,\"value\":[0]}],\"inputs\":[],\"name\":\"getData\",\"outputs\":[{\"internalType\":\"bytes\",\"name\":\"\",\"type\":\"bytes\"}],\"selector\":[1002823216,3792822465],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":5}],\"inputs\":[{\"internalType\":\"bytes\",\"name\":\"_memory\",\"type\":\"bytes\"}],\"name\":\"getKeccak256\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"result\",\"type\":\"bytes32\"}],\"selector\":[2473790398,3625206708],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"bytes\",\"name\":\"_memory\",\"type\":\"bytes\"}],\"name\":\"getSha256\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"result\",\"type\":\"bytes32\"}],\"selector\":[2332374872,2838876804],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_CALCULATEKECCAK256 = "calculateKeccak256";

    public static final String FUNC_CALCULATESM3 = "calculateSM3";

    public static final String FUNC_GETDATA = "getData";

    public static final String FUNC_GETKECCAK256 = "getKeccak256";

    public static final String FUNC_GETSHA256 = "getSha256";

    protected ShaTest(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public static String getABI() {
        return ABI;
    }

    public TransactionReceipt calculateKeccak256(byte[] _memory) {
        final Function function = new Function(
                FUNC_CALCULATEKECCAK256, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicBytes(_memory)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return executeTransaction(function);
    }

    public String calculateKeccak256(byte[] _memory, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_CALCULATEKECCAK256, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicBytes(_memory)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForCalculateKeccak256(byte[] _memory) {
        final Function function = new Function(
                FUNC_CALCULATEKECCAK256, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicBytes(_memory)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return createSignedTransaction(function);
    }

    public Tuple1<byte[]> getCalculateKeccak256Input(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_CALCULATEKECCAK256, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<byte[]>(

                (byte[]) results.get(0).getValue()
                );
    }

    public Tuple1<byte[]> getCalculateKeccak256Output(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_CALCULATEKECCAK256, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<byte[]>(

                (byte[]) results.get(0).getValue()
                );
    }

    public TransactionReceipt calculateSM3(byte[] _memory) {
        final Function function = new Function(
                FUNC_CALCULATESM3, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicBytes(_memory)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return executeTransaction(function);
    }

    public String calculateSM3(byte[] _memory, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_CALCULATESM3, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicBytes(_memory)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForCalculateSM3(byte[] _memory) {
        final Function function = new Function(
                FUNC_CALCULATESM3, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicBytes(_memory)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return createSignedTransaction(function);
    }

    public Tuple1<byte[]> getCalculateSM3Input(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_CALCULATESM3, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<byte[]>(

                (byte[]) results.get(0).getValue()
                );
    }

    public Tuple1<byte[]> getCalculateSM3Output(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_CALCULATESM3, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<byte[]>(

                (byte[]) results.get(0).getValue()
                );
    }

    public byte[] getData() throws ContractException {
        final Function function = new Function(FUNC_GETDATA, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeCallWithSingleValueReturn(function, byte[].class);
    }

    public TransactionReceipt getKeccak256(byte[] _memory) {
        final Function function = new Function(
                FUNC_GETKECCAK256, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicBytes(_memory)), 
                Collections.<TypeReference<?>>emptyList(), 4);
        return executeTransaction(function);
    }

    public String getKeccak256(byte[] _memory, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_GETKECCAK256, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicBytes(_memory)), 
                Collections.<TypeReference<?>>emptyList(), 4);
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForGetKeccak256(byte[] _memory) {
        final Function function = new Function(
                FUNC_GETKECCAK256, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicBytes(_memory)), 
                Collections.<TypeReference<?>>emptyList(), 4);
        return createSignedTransaction(function);
    }

    public Tuple1<byte[]> getGetKeccak256Input(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_GETKECCAK256, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<byte[]>(

                (byte[]) results.get(0).getValue()
                );
    }

    public Tuple1<byte[]> getGetKeccak256Output(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_GETKECCAK256, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<byte[]>(

                (byte[]) results.get(0).getValue()
                );
    }

    public TransactionReceipt getSha256(byte[] _memory) {
        final Function function = new Function(
                FUNC_GETSHA256, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicBytes(_memory)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return executeTransaction(function);
    }

    public String getSha256(byte[] _memory, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_GETSHA256, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicBytes(_memory)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForGetSha256(byte[] _memory) {
        final Function function = new Function(
                FUNC_GETSHA256, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicBytes(_memory)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return createSignedTransaction(function);
    }

    public Tuple1<byte[]> getGetSha256Input(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_GETSHA256, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<byte[]>(

                (byte[]) results.get(0).getValue()
                );
    }

    public Tuple1<byte[]> getGetSha256Output(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_GETSHA256, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<byte[]>(

                (byte[]) results.get(0).getValue()
                );
    }

    public static ShaTest load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new ShaTest(contractAddress, client, credential);
    }

    public static ShaTest deploy(Client client, CryptoKeyPair credential) throws ContractException {
        return deploy(ShaTest.class, client, credential, getBinary(client.getCryptoSuite()), getABI(), null, null);
    }
}
