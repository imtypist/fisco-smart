package org.fisco.smart;

import org.fisco.bcos.sdk.v3.BcosSDK;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.codec.ContractCodecException;
import org.fisco.bcos.sdk.v3.codec.datatypes.Type;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.ConstantConfig;
import org.fisco.bcos.sdk.v3.model.CryptoType;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.transaction.codec.decode.TransactionDecoderInterface;
import org.fisco.bcos.sdk.v3.transaction.codec.decode.TransactionDecoderService;
import org.fisco.bcos.sdk.v3.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.v3.transaction.manager.TransactionProcessorFactory;
import org.fisco.bcos.sdk.v3.transaction.model.dto.CallResponse;
import org.fisco.bcos.sdk.v3.transaction.model.dto.TransactionResponse;
import org.fisco.bcos.sdk.v3.transaction.model.exception.TransactionBaseException;

import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Integer.parseInt;

public class SmartKittyClient {

    public static String kittyCoreAddr = "0x0662b023D9F87A06ceCb053702e9c77510f1056f";
    public static String saleClockAuctionAddr = "0xA28AC30A792A59C3CD114A87a75193C6B8278D7E";
    public static String siringClockAuctionAddr = "0xaCE9eb31C30d607E91214b8C540D73F276D63656";
    public static String geneScienceAddr = "0xe46925CA51074d3b83Ba993a4e88d0156ECA6A06";

    public static void main(String[] args) {
        try {
            String configFileName = ConstantConfig.CONFIG_FILE_NAME;
            URL configUrl = SmartKittyClient.class.getClassLoader().getResource(configFileName);

            if (configUrl == null) {
                System.out.println("The configFile " + configFileName + " doesn't exist!");
                return;
            }

            String configFile = configUrl.getPath();
            BcosSDK sdk = BcosSDK.build(configFile);
            Client client = sdk.getClient("group0");
            // this is ceo account
            loadPemAccount(client, "src/main/resources/account/0xd9a2182618d3cb7b7a0a610eaee5e1a0d3f440b3.pem");
            CryptoKeyPair cryptoKeyPair = client.getCryptoSuite().getCryptoKeyPair();
            System.out.println(cryptoKeyPair.getAddress());

            AssembleTransactionProcessor transactionProcessor = TransactionProcessorFactory.createAssembleTransactionProcessor(client, cryptoKeyPair, "src/main/resources/abi/", "");
            TransactionDecoderInterface decoder = new TransactionDecoderService(client.getCryptoSuite(), client.isWASM());

            /*
             * KittyCore
             */
            Object cooAddress = sendCallKittyCore(transactionProcessor, "cooAddress").getResults().get(0);
            System.out.println("cooAddress: " + cooAddress);
            // set cfo
            Object cfoAddress = sendCallKittyCore(transactionProcessor, "cfoAddress").getResults().get(0);
            System.out.println("cfoAddress: " + cfoAddress);
            if(cfoAddress.toString().equals("0x0000000000000000000000000000000000000000")){
                List<Object> params = new ArrayList<>();
                params.add(cryptoKeyPair.getAddress());
                TransactionReceipt transactionReceipt = sendTxnKittyCore(transactionProcessor, "setCFO", params).getTransactionReceipt();
                System.out.println(transactionReceipt);
            }
            // set saleAuction
            Object saleAuctionAddress = sendCallKittyCore(transactionProcessor, "saleAuction").getResults().get(0);
            System.out.println("saleAuctionAddress: " + saleAuctionAddress);
            if (saleAuctionAddress.toString().equals("0x0000000000000000000000000000000000000000")){
                List<Object> params = new ArrayList<>();
                params.add(saleClockAuctionAddr);
                TransactionReceipt transactionReceipt = sendTxnKittyCore(transactionProcessor, "setSaleAuctionAddress", params).getTransactionReceipt();
                System.out.println(transactionReceipt);
            }
            // set siringAuction
            Object siringAuctionAddress = sendCallKittyCore(transactionProcessor, "siringAuction").getResults().get(0);
            System.out.println("siringAuctionAddress: " + siringAuctionAddress);
            if (siringAuctionAddress.toString().equals("0x0000000000000000000000000000000000000000")){
                List<Object> params = new ArrayList<>();
                params.add(siringClockAuctionAddr);
                TransactionReceipt transactionReceipt = sendTxnKittyCore(transactionProcessor, "setSiringAuctionAddress", params).getTransactionReceipt();
                System.out.println(transactionReceipt);
            }
            // set geneScience
            Object geneScienceAddress = sendCallKittyCore(transactionProcessor, "geneScience").getResults().get(0);
            System.out.println("geneScienceAddress: " + geneScienceAddress);
            if (geneScienceAddress.toString().equals("0x0000000000000000000000000000000000000000")){
                List<Object> params = new ArrayList<>();
                params.add(geneScienceAddr);
                TransactionReceipt transactionReceipt = sendTxnKittyCore(transactionProcessor, "setGeneScienceAddress", params).getTransactionReceipt();
                System.out.println(transactionReceipt);
            }
            // randomly create 10 accounts for further use, only run once time
            if (false) createAccountAndSaveWithPem(10);

            // unpause kittycore (must do it before call other functions)
            Type pausedStat = sendCallKittyCore(transactionProcessor, "paused").getResults().get(0);
            if(!pausedStat.getValue().toString().equals("false")){
                TransactionReceipt transactionReceipt = sendTxnKittyCore(transactionProcessor, "unpause").getTransactionReceipt();
                System.out.println("unpause: " + transactionReceipt);
            }

            // create gen0 kitty
            Type gen0Num = sendCallKittyCore(transactionProcessor, "gen0CreatedCount").getResults().get(0);
            System.out.println("gen0 count: " + gen0Num.getValue());
            if (parseInt(gen0Num.getValue().toString()) < 45000){
                List<Object> params = new ArrayList<>();
                params.add(new BigInteger(256, new Random()).toString()); // rand 0~2^256-1
                TransactionReceipt transactionReceipt = sendTxnKittyCore(transactionProcessor, "createGen0Auction", params).getTransactionReceipt();
                TransactionResponse transactionResponse = decoder.decodeReceiptWithValues("[{\"inputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"address\",\"name\":\"owner\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"approved\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"tokenId\",\"type\":\"uint256\"}],\"name\":\"Approval\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"address\",\"name\":\"owner\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"kittyId\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"matronId\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"sireId\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"genes\",\"type\":\"uint256\"}],\"name\":\"Birth\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"address\",\"name\":\"newContract\",\"type\":\"address\"}],\"name\":\"ContractUpgrade\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"address\",\"name\":\"owner\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"matronId\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"sireId\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"cooldownEndBlock\",\"type\":\"uint256\"}],\"name\":\"Pregnant\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"address\",\"name\":\"from\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"to\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"tokenId\",\"type\":\"uint256\"}],\"name\":\"Transfer\",\"type\":\"event\"},{\"inputs\":[],\"name\":\"GEN0_AUCTION_DURATION\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"GEN0_CREATION_LIMIT\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"GEN0_STARTING_PRICE\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"PROMO_CREATION_LIMIT\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_to\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_tokenId\",\"type\":\"uint256\"}],\"name\":\"approve\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_addr\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_sireId\",\"type\":\"uint256\"}],\"name\":\"approveSiring\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"autoBirthFee\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_owner\",\"type\":\"address\"}],\"name\":\"balanceOf\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"count\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_sireId\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"_matronId\",\"type\":\"uint256\"}],\"name\":\"bidOnSiringAuction\",\"outputs\":[],\"stateMutability\":\"payable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_matronId\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"_sireId\",\"type\":\"uint256\"}],\"name\":\"breedWithAuto\",\"outputs\":[],\"stateMutability\":\"payable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_matronId\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"_sireId\",\"type\":\"uint256\"}],\"name\":\"canBreedWith\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"ceoAddress\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"cfoAddress\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"cooAddress\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"name\":\"cooldowns\",\"outputs\":[{\"internalType\":\"uint32\",\"name\":\"\",\"type\":\"uint32\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_genes\",\"type\":\"uint256\"}],\"name\":\"createGen0Auction\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_genes\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"_owner\",\"type\":\"address\"}],\"name\":\"createPromoKitty\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_kittyId\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"_startingPrice\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"_endingPrice\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"_duration\",\"type\":\"uint256\"}],\"name\":\"createSaleAuction\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_kittyId\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"_startingPrice\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"_endingPrice\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"_duration\",\"type\":\"uint256\"}],\"name\":\"createSiringAuction\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"erc721Metadata\",\"outputs\":[{\"internalType\":\"contract ERC721Metadata\",\"name\":\"\",\"type\":\"address\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"gen0CreatedCount\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"geneScience\",\"outputs\":[{\"internalType\":\"contract GeneScienceInterface\",\"name\":\"\",\"type\":\"address\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_id\",\"type\":\"uint256\"}],\"name\":\"getKitty\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"isGestating\",\"type\":\"bool\"},{\"internalType\":\"bool\",\"name\":\"isReady\",\"type\":\"bool\"},{\"internalType\":\"uint256\",\"name\":\"cooldownIndex\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"nextActionAt\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"siringWithId\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"birthTime\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"matronId\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"sireId\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"generation\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"genes\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_matronId\",\"type\":\"uint256\"}],\"name\":\"giveBirth\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_kittyId\",\"type\":\"uint256\"}],\"name\":\"isPregnant\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_kittyId\",\"type\":\"uint256\"}],\"name\":\"isReadyToBreed\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"name\":\"kittyIndexToApproved\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"name\":\"kittyIndexToOwner\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"name\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"newContractAddress\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_tokenId\",\"type\":\"uint256\"}],\"name\":\"ownerOf\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"owner\",\"type\":\"address\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"pause\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"paused\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"pregnantKitties\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"promoCreatedCount\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"saleAuction\",\"outputs\":[{\"internalType\":\"contract SaleClockAuction\",\"name\":\"\",\"type\":\"address\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"secondsPerBlock\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"val\",\"type\":\"uint256\"}],\"name\":\"setAutoBirthFee\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_newCEO\",\"type\":\"address\"}],\"name\":\"setCEO\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_newCFO\",\"type\":\"address\"}],\"name\":\"setCFO\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_newCOO\",\"type\":\"address\"}],\"name\":\"setCOO\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_address\",\"type\":\"address\"}],\"name\":\"setGeneScienceAddress\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_contractAddress\",\"type\":\"address\"}],\"name\":\"setMetadataAddress\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_v2Address\",\"type\":\"address\"}],\"name\":\"setNewAddress\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_address\",\"type\":\"address\"}],\"name\":\"setSaleAuctionAddress\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"secs\",\"type\":\"uint256\"}],\"name\":\"setSecondsPerBlock\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_address\",\"type\":\"address\"}],\"name\":\"setSiringAuctionAddress\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"name\":\"sireAllowedToAddress\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"siringAuction\",\"outputs\":[{\"internalType\":\"contract SiringClockAuction\",\"name\":\"\",\"type\":\"address\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes4\",\"name\":\"_interfaceID\",\"type\":\"bytes4\"}],\"name\":\"supportsInterface\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"pure\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"symbol\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"_tokenId\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"_preferredTransport\",\"type\":\"string\"}],\"name\":\"tokenMetadata\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"infoUrl\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_owner\",\"type\":\"address\"}],\"name\":\"tokensOfOwner\",\"outputs\":[{\"internalType\":\"uint256[]\",\"name\":\"ownerTokens\",\"type\":\"uint256[]\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"totalSupply\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_to\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_tokenId\",\"type\":\"uint256\"}],\"name\":\"transfer\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_from\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"_to\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"_tokenId\",\"type\":\"uint256\"}],\"name\":\"transferFrom\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"unpause\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"withdrawAuctionBalances\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"withdrawBalance\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"stateMutability\":\"payable\",\"type\":\"receive\"}]", "createGen0Auction", transactionReceipt);
                System.out.println("create new Gen0 " + transactionResponse.getReturnMessage());
            }

            // get kitty through id
            List<Object> params = new ArrayList<>();
            params.add(gen0Num.getValue());
            List<Type> kitty = sendCallKittyCore(transactionProcessor, "getKitty", params).getResults();
            System.out.println("kitty " + gen0Num.getValue() + ":");
            for (Type type : kitty) {
                System.out.println("         " + type.getValue());
            }

            /*
             * SaleAuction
             */

            // get auction
            params = new ArrayList<>();
            params.add(gen0Num.getValue());
            List<Type> getAuction = sendCallSaleAuction(transactionProcessor, "getAuction", params).getResults();
            System.out.println("getAuction " + gen0Num.getValue() + ":");
            for (Type type : getAuction){
                System.out.println("         " + type.getValue());
            }

            /*
             * SiringAuction
             */

            /*
             * GeneScience
             */
            params = new ArrayList<>();
            params.add(new BigInteger(256, new Random()).toString());
            params.add(new BigInteger(256, new Random()).toString());
            params.add(50);
            Type mixedGene = sendCallGeneScience(transactionProcessor, "mixGenes", params).getResults().get(0);
            System.out.println("mixedGene: " + mixedGene.getValue());

            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void loadPemAccount(Client client, String pemAccountFilePath){
        CryptoSuite cryptoSuite = client.getCryptoSuite();
        cryptoSuite.loadAccount("pem", pemAccountFilePath, null);
    }

    public static CallResponse sendCallKittyCore(AssembleTransactionProcessor transactionProcessor, String functionName) throws TransactionBaseException, ContractCodecException {
        return transactionProcessor.sendCallByContractLoader("KittyCore", kittyCoreAddr, functionName, new ArrayList<>());
    }

    public static CallResponse sendCallKittyCore(AssembleTransactionProcessor transactionProcessor, String functionName, List<Object> params) throws TransactionBaseException, ContractCodecException {
        return transactionProcessor.sendCallByContractLoader("KittyCore", kittyCoreAddr, functionName, params);
    }

    public static TransactionResponse sendTxnKittyCore(AssembleTransactionProcessor transactionProcessor, String functionName) throws TransactionBaseException, ContractCodecException {
        return transactionProcessor.sendTransactionAndGetResponseByContractLoader("KittyCore", kittyCoreAddr, functionName, new ArrayList<>());
    }

    public static TransactionResponse sendTxnKittyCore(AssembleTransactionProcessor transactionProcessor, String functionName, List<Object> params) throws TransactionBaseException, ContractCodecException {
        return transactionProcessor.sendTransactionAndGetResponseByContractLoader("KittyCore", kittyCoreAddr, functionName, params);
    }

    public static CallResponse sendCallSaleAuction(AssembleTransactionProcessor transactionProcessor, String functionName, List<Object> params) throws TransactionBaseException, ContractCodecException {
        return transactionProcessor.sendCallByContractLoader("SaleClockAuction", saleClockAuctionAddr, functionName, params);
    }

    public static CallResponse sendCallGeneScience(AssembleTransactionProcessor transactionProcessor, String functionName, List<Object> params) throws TransactionBaseException, ContractCodecException {
        return transactionProcessor.sendCallByContractLoader("GeneScience", geneScienceAddr, functionName, params);
    }

    public static void createAccountAndSaveWithPem(int num){
        for (int i = 0; i < num; i++){
            CryptoSuite cryptoSuite = new CryptoSuite(CryptoType.ECDSA_TYPE);
            CryptoKeyPair cryptoKeyPair = cryptoSuite.generateRandomKeyPair();
            String accountAddress = cryptoKeyPair.getAddress();
            cryptoKeyPair.storeKeyPairWithPem("src/main/resources/account/" + accountAddress + ".pem");
        }
    }
}