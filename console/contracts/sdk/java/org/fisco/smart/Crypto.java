package org.fisco.smart;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.codec.datatypes.Address;
import org.fisco.bcos.sdk.v3.codec.datatypes.Bool;
import org.fisco.bcos.sdk.v3.codec.datatypes.Function;
import org.fisco.bcos.sdk.v3.codec.datatypes.Type;
import org.fisco.bcos.sdk.v3.codec.datatypes.TypeReference;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes32;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.v3.contract.Contract;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.CryptoType;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class Crypto extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b506102d6806100206000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c806352ce50e814610051578063cbdb3a6714610086578063eb90f459146100bf578063fb34363c146100bf575b600080fd5b61006a61005f366004610184565b600080935093915050565b6040805192151583526020830191909152015b60405180910390f35b6100a061009436600461020c565b60008094509492505050565b6040805192151583526001600160a01b0390911660208301520161007d565b6100d36100cd366004610263565b50600090565b60405190815260200161007d565b634e487b7160e01b600052604160045260246000fd5b600082601f83011261010857600080fd5b813567ffffffffffffffff80821115610123576101236100e1565b604051601f8301601f19908116603f0116810190828211818310171561014b5761014b6100e1565b8160405283815286602085880101111561016457600080fd5b836020870160208301376000602085830101528094505050505092915050565b60008060006060848603121561019957600080fd5b833567ffffffffffffffff808211156101b157600080fd5b6101bd878388016100f7565b945060208601359150808211156101d357600080fd5b6101df878388016100f7565b935060408601359150808211156101f557600080fd5b50610202868287016100f7565b9150509250925092565b6000806000806080858703121561022257600080fd5b84359350602085013567ffffffffffffffff81111561024057600080fd5b61024c878288016100f7565b949794965050505060408301359260600135919050565b60006020828403121561027557600080fd5b813567ffffffffffffffff81111561028c57600080fd5b610298848285016100f7565b94935050505056fea26469706673582212209ed72878c0e1f4e5faef04a6938d173aa0adee74d79315e40e970df0aec9913d64736f6c634300080b0033"};

    public static final String BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b506102d6806100206000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c8063226f66e01461005157806322ede61e14610086578063b6510107146100bf578063f25611b5146100bf575b600080fd5b61006a61005f366004610184565b600080935093915050565b6040805192151583526020830191909152015b60405180910390f35b6100a061009436600461020c565b60008094509492505050565b6040805192151583526001600160a01b0390911660208301520161007d565b6100d36100cd366004610263565b50600090565b60405190815260200161007d565b63b95aa35560e01b600052604160045260246000fd5b600082601f83011261010857600080fd5b813567ffffffffffffffff80821115610123576101236100e1565b604051601f8301601f19908116603f0116810190828211818310171561014b5761014b6100e1565b8160405283815286602085880101111561016457600080fd5b836020870160208301376000602085830101528094505050505092915050565b60008060006060848603121561019957600080fd5b833567ffffffffffffffff808211156101b157600080fd5b6101bd878388016100f7565b945060208601359150808211156101d357600080fd5b6101df878388016100f7565b935060408601359150808211156101f557600080fd5b50610202868287016100f7565b9150509250925092565b6000806000806080858703121561022257600080fd5b84359350602085013567ffffffffffffffff81111561024057600080fd5b61024c878288016100f7565b949794965050505060408301359260600135919050565b60006020828403121561027557600080fd5b813567ffffffffffffffff81111561028c57600080fd5b610298848285016100f7565b94935050505056fea264697066735822122026554552e257d969c38ee7c06990439bc698b41a291124e1b902c1d53764432164736f6c634300080b0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"conflictFields\":[{\"kind\":5}],\"inputs\":[{\"internalType\":\"string\",\"name\":\"input\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"vrfPublicKey\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"vrfProof\",\"type\":\"string\"}],\"name\":\"curve25519VRFVerify\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"},{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"selector\":[1389252840,577726176],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":5}],\"inputs\":[{\"internalType\":\"bytes\",\"name\":\"data\",\"type\":\"bytes\"}],\"name\":\"keccak256Hash\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"selector\":[3952145497,3058761991],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":5}],\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"message\",\"type\":\"bytes32\"},{\"internalType\":\"bytes\",\"name\":\"publicKey\",\"type\":\"bytes\"},{\"internalType\":\"bytes32\",\"name\":\"r\",\"type\":\"bytes32\"},{\"internalType\":\"bytes32\",\"name\":\"s\",\"type\":\"bytes32\"}],\"name\":\"sm2Verify\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"},{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"selector\":[3420142183,586016286],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":5}],\"inputs\":[{\"internalType\":\"bytes\",\"name\":\"data\",\"type\":\"bytes\"}],\"name\":\"sm3\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"selector\":[4214502972,4065726901],\"stateMutability\":\"view\",\"type\":\"function\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_CURVE25519VRFVERIFY = "curve25519VRFVerify";

    public static final String FUNC_KECCAK256HASH = "keccak256Hash";

    public static final String FUNC_SM2VERIFY = "sm2Verify";

    public static final String FUNC_SM3 = "sm3";

    protected Crypto(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public static String getABI() {
        return ABI;
    }

    public Tuple2<Boolean, BigInteger> curve25519VRFVerify(String input, String vrfPublicKey,
            String vrfProof) throws ContractException {
        final Function function = new Function(FUNC_CURVE25519VRFVERIFY, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(input), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(vrfPublicKey), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(vrfProof)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = executeCallWithMultipleValueReturn(function);
        return new Tuple2<Boolean, BigInteger>(
                (Boolean) results.get(0).getValue(), 
                (BigInteger) results.get(1).getValue());
    }

    public byte[] keccak256Hash(byte[] data) throws ContractException {
        final Function function = new Function(FUNC_KECCAK256HASH, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicBytes(data)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeCallWithSingleValueReturn(function, byte[].class);
    }

    public Tuple2<Boolean, String> sm2Verify(byte[] message, byte[] publicKey, byte[] r, byte[] s)
            throws ContractException {
        final Function function = new Function(FUNC_SM2VERIFY, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes32(message), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicBytes(publicKey), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes32(r), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes32(s)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Address>() {}));
        List<Type> results = executeCallWithMultipleValueReturn(function);
        return new Tuple2<Boolean, String>(
                (Boolean) results.get(0).getValue(), 
                (String) results.get(1).getValue());
    }

    public byte[] sm3(byte[] data) throws ContractException {
        final Function function = new Function(FUNC_SM3, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicBytes(data)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeCallWithSingleValueReturn(function, byte[].class);
    }

    public static Crypto load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new Crypto(contractAddress, client, credential);
    }

    public static Crypto deploy(Client client, CryptoKeyPair credential) throws ContractException {
        return deploy(Crypto.class, client, credential, getBinary(client.getCryptoSuite()), getABI(), null, null);
    }
}
