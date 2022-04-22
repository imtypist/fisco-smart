package org.fisco.smart;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.codec.datatypes.Bool;
import org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray;
import org.fisco.bcos.sdk.v3.codec.datatypes.DynamicStruct;
import org.fisco.bcos.sdk.v3.codec.datatypes.Function;
import org.fisco.bcos.sdk.v3.codec.datatypes.Type;
import org.fisco.bcos.sdk.v3.codec.datatypes.TypeReference;
import org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.Int256;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple3;
import org.fisco.bcos.sdk.v3.contract.Contract;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.CryptoType;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class KVTable extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b50610604806100206000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c80633e10510b1461005157806356004b6a1461007b5780635d0d6d54146100a0578063649a8428146100c4575b600080fd5b61006461005f3660046101f4565b6100d2565b6040516100729291906102a5565b60405180910390f35b61009261008936600461033e565b60009392505050565b604051908152602001610072565b6100b66100ae3660046103c6565b606080915091565b604051610072929190610403565b610092610089366004610431565b60006100ea6040518060200160405280606081525090565b9250929050565b634e487b7160e01b600052604160045260246000fd5b6040516020810167ffffffffffffffff8111828210171561012a5761012a6100f1565b60405290565b6040805190810167ffffffffffffffff8111828210171561012a5761012a6100f1565b604051601f8201601f1916810167ffffffffffffffff8111828210171561017c5761017c6100f1565b604052919050565b600082601f83011261019557600080fd5b813567ffffffffffffffff8111156101af576101af6100f1565b6101c2601f8201601f1916602001610153565b8181528460208386010111156101d757600080fd5b816020850160208301376000918101602001919091529392505050565b6000806040838503121561020757600080fd5b823567ffffffffffffffff8082111561021f57600080fd5b61022b86838701610184565b9350602085013591508082111561024157600080fd5b5061024e85828601610184565b9150509250929050565b6000815180845260005b8181101561027e57602081850181015186830182015201610262565b81811115610290576000602083870101525b50601f01601f19169290920160200192915050565b60006040841515835260208181850152606084018551828487015281815180845260808801915060808160051b8901019350848301925060005b8181101561032f57888503607f190183528351805188875261030389880182610258565b91880151878303888a015291905061031b8183610258565b9650505092850192918501916001016102df565b50929998505050505050505050565b60008060006060848603121561035357600080fd5b833567ffffffffffffffff8082111561036b57600080fd5b61037787838801610184565b9450602086013591508082111561038d57600080fd5b61039987838801610184565b935060408601359150808211156103af57600080fd5b506103bc86828701610184565b9150509250925092565b6000602082840312156103d857600080fd5b813567ffffffffffffffff8111156103ef57600080fd5b6103fb84828501610184565b949350505050565b6040815260006104166040830185610258565b82810360208401526104288185610258565b95945050505050565b60008060006060848603121561044657600080fd5b67ffffffffffffffff808535111561045d57600080fd5b61046a8686358701610184565b935060208501358181111561047e57600080fd5b61048a87828801610184565b93505060408501358181111561049f57600080fd5b8501602081880312156104b157600080fd5b6104b9610107565b82823511156104c757600080fd5b81358201915087601f8301126104dc57600080fd5b8135838111156104ee576104ee6100f1565b6104fd60208260051b01610153565b8082825260208201915060208360051b86010192508a83111561051f57600080fd5b602085015b838110156105bb57868135111561053a57600080fd5b803586016040818e03601f1901121561055257600080fd5b61055a610130565b60208201358981111561056c57600080fd5b61057b8f602083860101610184565b82525060408201358981111561059057600080fd5b61059f8f602083860101610184565b6020830152508085525050602083019250602081019050610524565b508352509598949750955092935050505056fea2646970667358221220f59d2470b5f05fdff0d512217921c9b603dcd2158e34ff334bf76d969fba9f0664736f6c634300080b0033"};

    public static final String BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b50610600806100206000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c8063af50817414610051578063b885d5ac1461007b578063c92a78011461009f578063dcce5531146100ad575b600080fd5b61006861005f3660046101f0565b60009392505050565b6040519081526020015b60405180910390f35b61009161008936600461038d565b606080915091565b604051610072929190610417565b61006861005f366004610445565b6100c06100bb3660046104cd565b6100ce565b604051610072929190610531565b60006100e66040518060200160405280606081525090565b9250929050565b63b95aa35560e01b600052604160045260246000fd5b6040516020810167ffffffffffffffff81118282101715610126576101266100ed565b60405290565b6040805190810167ffffffffffffffff81118282101715610126576101266100ed565b604051601f8201601f1916810167ffffffffffffffff81118282101715610178576101786100ed565b604052919050565b600082601f83011261019157600080fd5b813567ffffffffffffffff8111156101ab576101ab6100ed565b6101be601f8201601f191660200161014f565b8181528460208386010111156101d357600080fd5b816020850160208301376000918101602001919091529392505050565b60008060006060848603121561020557600080fd5b67ffffffffffffffff808535111561021c57600080fd5b6102298686358701610180565b935060208501358181111561023d57600080fd5b61024987828801610180565b93505060408501358181111561025e57600080fd5b85016020818803121561027057600080fd5b610278610103565b828235111561028657600080fd5b81358201915087601f83011261029b57600080fd5b8135838111156102ad576102ad6100ed565b6102bc60208260051b0161014f565b8082825260208201915060208360051b86010192508a8311156102de57600080fd5b602085015b8381101561037a5786813511156102f957600080fd5b803586016040818e03601f1901121561031157600080fd5b61031961012c565b60208201358981111561032b57600080fd5b61033a8f602083860101610180565b82525060408201358981111561034f57600080fd5b61035e8f602083860101610180565b60208301525080855250506020830192506020810190506102e3565b5083525095989497509550929350505050565b60006020828403121561039f57600080fd5b813567ffffffffffffffff8111156103b657600080fd5b6103c284828501610180565b949350505050565b6000815180845260005b818110156103f0576020818501810151868301820152016103d4565b81811115610402576000602083870101525b50601f01601f19169290920160200192915050565b60408152600061042a60408301856103ca565b828103602084015261043c81856103ca565b95945050505050565b60008060006060848603121561045a57600080fd5b833567ffffffffffffffff8082111561047257600080fd5b61047e87838801610180565b9450602086013591508082111561049457600080fd5b6104a087838801610180565b935060408601359150808211156104b657600080fd5b506104c386828701610180565b9150509250925092565b600080604083850312156104e057600080fd5b823567ffffffffffffffff808211156104f857600080fd5b61050486838701610180565b9350602085013591508082111561051a57600080fd5b5061052785828601610180565b9150509250929050565b60006040841515835260208181850152606084018551828487015281815180845260808801915060808160051b8901019350848301925060005b818110156105bb57888503607f190183528351805188875261058f898801826103ca565b91880151878303888a01529190506105a781836103ca565b96505050928501929185019160010161056b565b5092999850505050505050505056fea264697066735822122034b03c8ca98cd54df7c8b288eccfc12a162a50d97e2a6c28338424552d51df7164736f6c634300080b0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"conflictFields\":[{\"kind\":5}],\"inputs\":[{\"internalType\":\"string\",\"name\":\"tableName\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"key\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"valueFields\",\"type\":\"string\"}],\"name\":\"createTable\",\"outputs\":[{\"internalType\":\"int256\",\"name\":\"\",\"type\":\"int256\"}],\"selector\":[1442859882,3375003649],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":5}],\"inputs\":[{\"internalType\":\"string\",\"name\":\"tableName\",\"type\":\"string\"}],\"name\":\"desc\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"selector\":[1561161044,3095778732],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":5}],\"inputs\":[{\"internalType\":\"string\",\"name\":\"tableName\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"key\",\"type\":\"string\"}],\"name\":\"get\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"},{\"components\":[{\"components\":[{\"internalType\":\"string\",\"name\":\"key\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"value\",\"type\":\"string\"}],\"internalType\":\"struct KVField[]\",\"name\":\"fields\",\"type\":\"tuple[]\"}],\"internalType\":\"struct Entry\",\"name\":\"entry\",\"type\":\"tuple\"}],\"selector\":[1041256715,3704509745],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":5}],\"inputs\":[{\"internalType\":\"string\",\"name\":\"tableName\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"key\",\"type\":\"string\"},{\"components\":[{\"components\":[{\"internalType\":\"string\",\"name\":\"key\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"value\",\"type\":\"string\"}],\"internalType\":\"struct KVField[]\",\"name\":\"fields\",\"type\":\"tuple[]\"}],\"internalType\":\"struct Entry\",\"name\":\"entry\",\"type\":\"tuple\"}],\"name\":\"set\",\"outputs\":[{\"internalType\":\"int256\",\"name\":\"\",\"type\":\"int256\"}],\"selector\":[1687847976,2941288820],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_CREATETABLE = "createTable";

    public static final String FUNC_DESC = "desc";

    public static final String FUNC_GET = "get";

    public static final String FUNC_SET = "set";

    protected KVTable(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public static String getABI() {
        return ABI;
    }

    public TransactionReceipt createTable(String tableName, String key, String valueFields) {
        final Function function = new Function(
                FUNC_CREATETABLE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(tableName), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(key), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(valueFields)), 
                Collections.<TypeReference<?>>emptyList(), 4);
        return executeTransaction(function);
    }

    public String createTable(String tableName, String key, String valueFields,
            TransactionCallback callback) {
        final Function function = new Function(
                FUNC_CREATETABLE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(tableName), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(key), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(valueFields)), 
                Collections.<TypeReference<?>>emptyList(), 4);
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForCreateTable(String tableName, String key,
            String valueFields) {
        final Function function = new Function(
                FUNC_CREATETABLE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(tableName), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(key), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(valueFields)), 
                Collections.<TypeReference<?>>emptyList(), 4);
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

    public TransactionReceipt desc(String tableName) {
        final Function function = new Function(
                FUNC_DESC, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(tableName)), 
                Collections.<TypeReference<?>>emptyList(), 4);
        return executeTransaction(function);
    }

    public String desc(String tableName, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_DESC, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(tableName)), 
                Collections.<TypeReference<?>>emptyList(), 4);
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForDesc(String tableName) {
        final Function function = new Function(
                FUNC_DESC, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(tableName)), 
                Collections.<TypeReference<?>>emptyList(), 4);
        return createSignedTransaction(function);
    }

    public Tuple1<String> getDescInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_DESC, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public Tuple2<String, String> getDescOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_DESC, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, String>(

                (String) results.get(0).getValue(), 
                (String) results.get(1).getValue()
                );
    }

    public Tuple2<Boolean, Entry> get(String tableName, String key) throws ContractException {
        final Function function = new Function(FUNC_GET, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(tableName), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Entry>() {}));
        List<Type> results = executeCallWithMultipleValueReturn(function);
        return new Tuple2<Boolean, Entry>(
                (Boolean) results.get(0).getValue(), 
                (Entry) results.get(1).getValue());
    }

    public TransactionReceipt set(String tableName, String key, Entry entry) {
        final Function function = new Function(
                FUNC_SET, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(tableName), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(key), 
                entry), 
                Collections.<TypeReference<?>>emptyList(), 4);
        return executeTransaction(function);
    }

    public String set(String tableName, String key, Entry entry, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_SET, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(tableName), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(key), 
                entry), 
                Collections.<TypeReference<?>>emptyList(), 4);
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForSet(String tableName, String key, Entry entry) {
        final Function function = new Function(
                FUNC_SET, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(tableName), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(key), 
                entry), 
                Collections.<TypeReference<?>>emptyList(), 4);
        return createSignedTransaction(function);
    }

    public Tuple3<String, String, Entry> getSetInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_SET, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Entry>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple3<String, String, Entry>(

                (String) results.get(0).getValue(), 
                (String) results.get(1).getValue(), 
                (Entry) results.get(2).getValue()
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

    public static KVTable load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new KVTable(contractAddress, client, credential);
    }

    public static KVTable deploy(Client client, CryptoKeyPair credential) throws ContractException {
        return deploy(KVTable.class, client, credential, getBinary(client.getCryptoSuite()), getABI(), null, null);
    }

    public static class KVField extends DynamicStruct {
        public String key;

        public String value;

        public KVField(Utf8String key, Utf8String value) {
            super(key,value);
            this.key = key.getValue();
            this.value = value.getValue();
        }

        public KVField(String key, String value) {
            super(new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(key),new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(value));
            this.key = key;
            this.value = value;
        }
    }

    public static class Entry extends DynamicStruct {
        public DynamicArray<KVField> fields;

        public Entry(DynamicArray<KVField> fields) {
            super(fields);
            this.fields = fields;
        }
    }
}
