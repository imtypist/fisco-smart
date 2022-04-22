package org.fisco.smart;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.codec.datatypes.Bool;
import org.fisco.bcos.sdk.v3.codec.datatypes.Function;
import org.fisco.bcos.sdk.v3.codec.datatypes.Type;
import org.fisco.bcos.sdk.v3.codec.datatypes.TypeReference;
import org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.Int256;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple3;
import org.fisco.bcos.sdk.v3.contract.Contract;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.CryptoType;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class KVTableTest extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b50600080546001600160a01b031916611009908117909155604051632b0025b560e11b81526060600482015260096064820152681d17dadd97dd195cdd60ba1b608482015260a06024820152600260a4820152611a5960f21b60c482015260e06044820152601460e48201527f6974656d5f70726963652c6974656d5f6e616d6500000000000000000000000061010482015281906356004b6a90610124016020604051808303816000875af11580156100ce573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906100f291906100f9565b5050610112565b60006020828403121561010b57600080fd5b5051919050565b61093e806101216000396000f3fe608060405234801561001057600080fd5b50600436106100415760003560e01c806356004b6a14610046578063693ec85e1461006c578063da465d741461008e575b600080fd5b610059610054366004610495565b6100a1565b6040519081526020015b60405180910390f35b61007f61007a36600461051d565b610122565b604051610063939291906105b6565b61005961009c366004610495565b610218565b60008054604051632b0025b560e11b815282916001600160a01b0316906356004b6a906100d6908890889088906004016105ed565b6020604051808303816000875af11580156100f5573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906101199190610626565b95945050505050565b6000606080600061013f6040518060200160405280606081525090565b600054604051633e10510b60e01b81526001600160a01b0390911690633e10510b9061016f90899060040161063f565b600060405180830381865afa15801561018c573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f191682016040526101b491908101906106bf565b9092509050606080831561020957825180516000906101d5576101d5610826565b602002602001015160200151915082600001516001815181106101fa576101fa610826565b60200260200101516020015190505b92979096509194509092505050565b6040805160808082018352600a828401908152696974656d5f707269636560b01b6060808501919091529083526020808401879052845192830185526009838601908152686974656d5f6e616d6560b81b8484015283528201859052835160028082529181019094526000938491816020015b604080518082019091526060808252602082015281526020019060019003908161028b57905050905082816000815181106102c8576102c8610826565b602002602001018190525081816001815181106102e7576102e7610826565b602090810291909101810191909152604080519182018152828252600080549151630c93508560e31b815290916001600160a01b03169063649a842890610334908c90869060040161083c565b6020604051808303816000875af1158015610353573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906103779190610626565b9998505050505050505050565b634e487b7160e01b600052604160045260246000fd5b6040516020810167ffffffffffffffff811182821017156103bd576103bd610384565b60405290565b6040805190810167ffffffffffffffff811182821017156103bd576103bd610384565b604051601f8201601f1916810167ffffffffffffffff8111828210171561040f5761040f610384565b604052919050565b600067ffffffffffffffff82111561043157610431610384565b50601f01601f191660200190565b600082601f83011261045057600080fd5b813561046361045e82610417565b6103e6565b81815284602083860101111561047857600080fd5b816020850160208301376000918101602001919091529392505050565b6000806000606084860312156104aa57600080fd5b833567ffffffffffffffff808211156104c257600080fd5b6104ce8783880161043f565b945060208601359150808211156104e457600080fd5b6104f08783880161043f565b9350604086013591508082111561050657600080fd5b506105138682870161043f565b9150509250925092565b60006020828403121561052f57600080fd5b813567ffffffffffffffff81111561054657600080fd5b6105528482850161043f565b949350505050565b60005b8381101561057557818101518382015260200161055d565b83811115610584576000848401525b50505050565b600081518084526105a281602086016020860161055a565b601f01601f19169290920160200192915050565b83151581526060602082015260006105d1606083018561058a565b82810360408401526105e3818561058a565b9695505050505050565b606081526000610600606083018661058a565b8281036020840152610612818661058a565b905082810360408401526105e3818561058a565b60006020828403121561063857600080fd5b5051919050565b6040815260006106686040830160098152681d17dadd97dd195cdd60ba1b602082015260400190565b8281036020840152610552818561058a565b600082601f83011261068b57600080fd5b815161069961045e82610417565b8181528460208386010111156106ae57600080fd5b61055282602083016020870161055a565b600080604083850312156106d257600080fd5b825180151581146106e257600080fd5b8092505060208084015167ffffffffffffffff8082111561070257600080fd5b818601915082828803121561071657600080fd5b61071e61039a565b82518281111561072d57600080fd5b80840193505087601f84011261074257600080fd5b82518281111561075457610754610384565b8060051b6107638682016103e6565b918252848101860191868101908b84111561077d57600080fd5b87870192505b838310156108135782518681111561079b5760008081fd5b87016040818e03601f190112156107b25760008081fd5b6107ba6103c3565b89820151888111156107cc5760008081fd5b6107da8f8c8386010161067a565b8252506040820151888111156107f05760008081fd5b6107fe8f8c8386010161067a565b828c0152508352509187019190870190610783565b8452509699919850909650505050505050565b634e487b7160e01b600052603260045260246000fd5b6060815260006108656060830160098152681d17dadd97dd195cdd60ba1b602082015260400190565b602083820381850152610878828761058a565b915060408483038186015281830186518385528181518084528487019150848160051b8801019350858301925060005b818110156108f857878503603f19018352835180518787526108cc8888018261058a565b91890151878303888b01529190506108e4818361058a565b9650505092860192918601916001016108a8565b50929a995050505050505050505056fea264697066735822122050e68950687fc202078a915d57c13e4e797992258c077a1fd272e5617555853664736f6c634300080b0033"};

    public static final String BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b50600080546001600160a01b03191661100990811790915560405163c92a780160e01b81526060600482015260096064820152681d17dadd97dd195cdd60ba1b608482015260a06024820152600260a4820152611a5960f21b60c482015260e06044820152601460e48201527f6974656d5f70726963652c6974656d5f6e616d65000000000000000000000000610104820152819063c92a780190610124016020604051808303816000875af11580156100ce573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052508101906100f291906100f9565b5050610112565b60006020828403121561010b57600080fd5b5051919050565b61093e806101216000396000f3fe608060405234801561001057600080fd5b50600436106100415760003560e01c8063095bf8fa146100465780637b1b8e031461006c578063c92a78011461008e575b600080fd5b610059610054366004610495565b6100a1565b6040519081526020015b60405180910390f35b61007f61007a36600461051d565b61020d565b604051610063939291906105b6565b61005961009c366004610495565b610303565b6040805160808082018352600a828401908152696974656d5f707269636560b01b6060808501919091529083526020808401879052845192830185526009838601908152686974656d5f6e616d6560b81b8484015283528201859052835160028082529181019094526000938491816020015b60408051808201909152606080825260208201528152602001906001900390816101145790505090508281600081518110610151576101516105ed565b60200260200101819052508181600181518110610170576101706105ed565b602090810291909101810191909152604080519182018152828252600080549151632bd4205d60e21b815290916001600160a01b03169063af508174906101bd908c908690600401610603565b6020604051808303816000875af11580156101dc573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061020091906106cf565b9998505050505050505050565b6000606080600061022a6040518060200160405280606081525090565b60005460405163dcce553160e01b81526001600160a01b039091169063dcce55319061025a9089906004016106e8565b600060405180830381865afa158015610277573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f1916820160405261029f9190810190610768565b909250905060608083156102f457825180516000906102c0576102c06105ed565b602002602001015160200151915082600001516001815181106102e5576102e56105ed565b60200260200101516020015190505b92979096509194509092505050565b6000805460405163c92a780160e01b815282916001600160a01b03169063c92a780190610338908890889088906004016108cf565b6020604051808303816000875af1158015610357573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061037b91906106cf565b95945050505050565b63b95aa35560e01b600052604160045260246000fd5b6040516020810167ffffffffffffffff811182821017156103bd576103bd610384565b60405290565b6040805190810167ffffffffffffffff811182821017156103bd576103bd610384565b604051601f8201601f1916810167ffffffffffffffff8111828210171561040f5761040f610384565b604052919050565b600067ffffffffffffffff82111561043157610431610384565b50601f01601f191660200190565b600082601f83011261045057600080fd5b813561046361045e82610417565b6103e6565b81815284602083860101111561047857600080fd5b816020850160208301376000918101602001919091529392505050565b6000806000606084860312156104aa57600080fd5b833567ffffffffffffffff808211156104c257600080fd5b6104ce8783880161043f565b945060208601359150808211156104e457600080fd5b6104f08783880161043f565b9350604086013591508082111561050657600080fd5b506105138682870161043f565b9150509250925092565b60006020828403121561052f57600080fd5b813567ffffffffffffffff81111561054657600080fd5b6105528482850161043f565b949350505050565b60005b8381101561057557818101518382015260200161055d565b83811115610584576000848401525b50505050565b600081518084526105a281602086016020860161055a565b601f01601f19169290920160200192915050565b83151581526060602082015260006105d1606083018561058a565b82810360408401526105e3818561058a565b9695505050505050565b63b95aa35560e01b600052603260045260246000fd5b60608152600061062c6060830160098152681d17dadd97dd195cdd60ba1b602082015260400190565b60208382038185015261063f828761058a565b915060408483038186015281830186518385528181518084528487019150848160051b8801019350858301925060005b818110156106bf57878503603f19018352835180518787526106938888018261058a565b91890151878303888b01529190506106ab818361058a565b96505050928601929186019160010161066f565b50929a9950505050505050505050565b6000602082840312156106e157600080fd5b5051919050565b6040815260006107116040830160098152681d17dadd97dd195cdd60ba1b602082015260400190565b8281036020840152610552818561058a565b600082601f83011261073457600080fd5b815161074261045e82610417565b81815284602083860101111561075757600080fd5b61055282602083016020870161055a565b6000806040838503121561077b57600080fd5b8251801515811461078b57600080fd5b8092505060208084015167ffffffffffffffff808211156107ab57600080fd5b81860191508282880312156107bf57600080fd5b6107c761039a565b8251828111156107d657600080fd5b80840193505087601f8401126107eb57600080fd5b8251828111156107fd576107fd610384565b8060051b61080c8682016103e6565b918252848101860191868101908b84111561082657600080fd5b87870192505b838310156108bc578251868111156108445760008081fd5b87016040818e03601f1901121561085b5760008081fd5b6108636103c3565b89820151888111156108755760008081fd5b6108838f8c83860101610723565b8252506040820151888111156108995760008081fd5b6108a78f8c83860101610723565b828c015250835250918701919087019061082c565b8452509699919850909650505050505050565b6060815260006108e2606083018661058a565b82810360208401526108f4818661058a565b905082810360408401526105e3818561058a56fea2646970667358221220c39848febe59ca139a126918bc82c0ff2e8d34e1be2348e964b6feab8b8d41a664736f6c634300080b0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"inputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"string\",\"name\":\"tableName\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"key\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"fields\",\"type\":\"string\"}],\"name\":\"createTable\",\"outputs\":[{\"internalType\":\"int256\",\"name\":\"\",\"type\":\"int256\"}],\"selector\":[1442859882,3375003649],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"string\",\"name\":\"id\",\"type\":\"string\"}],\"name\":\"get\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"selector\":[1765722206,2065403395],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"string\",\"name\":\"id\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"item_price\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"item_name\",\"type\":\"string\"}],\"name\":\"set\",\"outputs\":[{\"internalType\":\"int256\",\"name\":\"\",\"type\":\"int256\"}],\"selector\":[3662044532,157022458],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_CREATETABLE = "createTable";

    public static final String FUNC_GET = "get";

    public static final String FUNC_SET = "set";

    protected KVTableTest(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public static String getABI() {
        return ABI;
    }

    public TransactionReceipt createTable(String tableName, String key, String fields) {
        final Function function = new Function(
                FUNC_CREATETABLE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(tableName), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(key), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(fields)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return executeTransaction(function);
    }

    public String createTable(String tableName, String key, String fields,
            TransactionCallback callback) {
        final Function function = new Function(
                FUNC_CREATETABLE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(tableName), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(key), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(fields)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForCreateTable(String tableName, String key, String fields) {
        final Function function = new Function(
                FUNC_CREATETABLE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(tableName), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(key), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(fields)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return createSignedTransaction(function);
    }

    public Tuple3<String, String, String> getCreateTableInput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_CREATETABLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple3<String, String, String>(

                (String) results.get(0).getValue(), 
                (String) results.get(1).getValue(), 
                (String) results.get(2).getValue()
                );
    }

    public Tuple1<BigInteger> getCreateTableOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_CREATETABLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public Tuple3<Boolean, String, String> get(String id) throws ContractException {
        final Function function = new Function(FUNC_GET, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = executeCallWithMultipleValueReturn(function);
        return new Tuple3<Boolean, String, String>(
                (Boolean) results.get(0).getValue(), 
                (String) results.get(1).getValue(), 
                (String) results.get(2).getValue());
    }

    public TransactionReceipt set(String id, String item_price, String item_name) {
        final Function function = new Function(
                FUNC_SET, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(id), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(item_price), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(item_name)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return executeTransaction(function);
    }

    public String set(String id, String item_price, String item_name,
            TransactionCallback callback) {
        final Function function = new Function(
                FUNC_SET, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(id), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(item_price), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(item_name)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForSet(String id, String item_price, String item_name) {
        final Function function = new Function(
                FUNC_SET, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(id), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(item_price), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(item_name)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return createSignedTransaction(function);
    }

    public Tuple3<String, String, String> getSetInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_SET, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple3<String, String, String>(

                (String) results.get(0).getValue(), 
                (String) results.get(1).getValue(), 
                (String) results.get(2).getValue()
                );
    }

    public Tuple1<BigInteger> getSetOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_SET, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public static KVTableTest load(String contractAddress, Client client,
            CryptoKeyPair credential) {
        return new KVTableTest(contractAddress, client, credential);
    }

    public static KVTableTest deploy(Client client, CryptoKeyPair credential) throws
            ContractException {
        return deploy(KVTableTest.class, client, credential, getBinary(client.getCryptoSuite()), getABI(), null, null);
    }
}
