package org.fisco.smart;

import java.util.Arrays;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.codec.datatypes.Function;
import org.fisco.bcos.sdk.v3.codec.datatypes.Type;
import org.fisco.bcos.sdk.v3.codec.datatypes.TypeReference;
import org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String;
import org.fisco.bcos.sdk.v3.contract.Contract;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.CryptoType;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class Inference extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b506101ac806100206000396000f3fe608060405234801561001057600080fd5b506004361061002b5760003560e01c8063dcf4343514610030575b600080fd5b61004461003e366004610070565b50606090565b6040516100519190610121565b60405180910390f35b634e487b7160e01b600052604160045260246000fd5b60006020828403121561008257600080fd5b813567ffffffffffffffff8082111561009a57600080fd5b818401915084601f8301126100ae57600080fd5b8135818111156100c0576100c061005a565b604051601f8201601f19908116603f011681019083821181831017156100e8576100e861005a565b8160405282815287602084870101111561010157600080fd5b826020860160208301376000928101602001929092525095945050505050565b600060208083528351808285015260005b8181101561014e57858101830151858201604001528201610132565b81811115610160576000604083870101525b50601f01601f191692909201604001939250505056fea26469706673582212203e2819ab6ec6021fd6b35bd40741eb8c4e615d41df0b73f981658dc44c750b3964736f6c634300080b0033"};

    public static final String BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b506101ac806100206000396000f3fe608060405234801561001057600080fd5b506004361061002b5760003560e01c806391fc805814610030575b600080fd5b61004461003e366004610070565b50606090565b6040516100519190610121565b60405180910390f35b63b95aa35560e01b600052604160045260246000fd5b60006020828403121561008257600080fd5b813567ffffffffffffffff8082111561009a57600080fd5b818401915084601f8301126100ae57600080fd5b8135818111156100c0576100c061005a565b604051601f8201601f19908116603f011681019083821181831017156100e8576100e861005a565b8160405282815287602084870101111561010157600080fd5b826020860160208301376000928101602001929092525095945050505050565b600060208083528351808285015260005b8181101561014e57858101830151858201604001528201610132565b81811115610160576000604083870101525b50601f01601f191692909201604001939250505056fea26469706673582212208ac242adfd6e9c4338646cd29057c0688c4cafdb040748459d3ce42affed236b64736f6c634300080b0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"conflictFields\":[{\"kind\":5}],\"inputs\":[{\"internalType\":\"string\",\"name\":\"cmd\",\"type\":\"string\"}],\"name\":\"predict\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"selector\":[3706991669,2449244248],\"stateMutability\":\"view\",\"type\":\"function\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_PREDICT = "predict";

    protected Inference(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public static String getABI() {
        return ABI;
    }

    public String predict(String cmd) throws ContractException {
        final Function function = new Function(FUNC_PREDICT, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(cmd)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public static Inference load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new Inference(contractAddress, client, credential);
    }

    public static Inference deploy(Client client, CryptoKeyPair credential) throws
            ContractException {
        return deploy(Inference.class, client, credential, getBinary(client.getCryptoSuite()), getABI(), null, null);
    }
}
