package com.ugc.gameserver.domain.contract;

import java.lang.Override;
import java.lang.String;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modify!</strong><br>
 * Please use {@link org.web3j.codegen.SolidityFunctionWrapperGenerator} to update.
 *
 * <p>Generated with web3j version none.
 */
public final class Trade extends Contract {
    private static final String BINARY = "60606040523461000057604051604080611198833981016040528080519060200190919080519060200190919050505b81600260086101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555080600360006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505b50505b6110d4806100c46000396000f30060606040526000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806302d05d3f1461005f57806386b94747146100ae578063c895868514610116578063cbc6172514610187575b610000565b346100005761006c6101be565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34610000576100fc600480803567ffffffffffffffff1690602001909190803567ffffffffffffffff16906020019091908035906020019091908035600019169060200190919050506101e4565b604051808215151515815260200191505060405180910390f35b346100005761016d600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803567ffffffffffffffff1690602001909190803567ffffffffffffffff1690602001909190505061078c565b604051808215151515815260200191505060405180910390f35b346100005761019461108e565b604051808267ffffffffffffffff1667ffffffffffffffff16815260200191505060405180910390f35b600060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60006000600260089054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663ff7e2be9336000604051602001526040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001915050602060405180830381600087803b156100005760325a03f11561000057505050604051805190509050600260089054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663bc0f3cad3388886000604051602001526040518463ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018367ffffffffffffffff1667ffffffffffffffff1681526020018267ffffffffffffffff1667ffffffffffffffff1681526020019350505050602060405180830381600087803b156100005760325a03f115610000575050506040518051905015156103d75760009150610783565b600260089054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166339b5ad53866000604051602001526040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808267ffffffffffffffff1667ffffffffffffffff168152602001915050602060405180830381600087803b156100005760325a03f1156100005750505060405180519050156104a05760009150610783565b6040604051908101604052808581526020018460001916815250600160008767ffffffffffffffff1667ffffffffffffffff1681526020019081526020016000206000820151816000015560208201518160010190600019169055905050600260089054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663670f59038660016040518363ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808367ffffffffffffffff1667ffffffffffffffff1681526020018215151515815260200192505050600060405180830381600087803b156100005760325a03f115610000575050506002600081819054906101000a900467ffffffffffffffff168092919060010191906101000a81548167ffffffffffffffff021916908367ffffffffffffffff160217905550507ff5b96081068a96a1fbe300cdded7aa491196e2dc1573e18f828511cd8cfbec60338288600260089054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166360e625028a6000604051602001526040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808267ffffffffffffffff1667ffffffffffffffff168152602001915050602060405180830381600087803b156100005760325a03f115610000575050506040518051905089604051808673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018567ffffffffffffffff1667ffffffffffffffff1681526020018467ffffffffffffffff1667ffffffffffffffff16815260200183600019166000191681526020018267ffffffffffffffff1667ffffffffffffffff1681526020019550505050505060405180910390a1600191505b50949350505050565b600060006000600260089054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663bc0f3cad8787876000604051602001526040518463ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018367ffffffffffffffff1667ffffffffffffffff1681526020018267ffffffffffffffff1667ffffffffffffffff1681526020019350505050602060405180830381600087803b156100005760325a03f115610000575050506040518051905015156108ac5760009250611085565b600260089054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166339b5ad53856000604051602001526040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808267ffffffffffffffff1667ffffffffffffffff168152602001915050602060405180830381600087803b156100005760325a03f115610000575050506040518051905015156109765760009250611085565b600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166323b872dd3388600160008967ffffffffffffffff1667ffffffffffffffff168152602001908152602001600020600001546000604051602001526040518463ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018281526020019350505050602060405180830381600087803b156100005760325a03f11561000057505050604051805190501515610abe5760009250611085565b600260089054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663670f59038560006040518363ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808367ffffffffffffffff1667ffffffffffffffff1681526020018215151515815260200192505050600060405180830381600087803b156100005760325a03f11561000057505050600160008567ffffffffffffffff1667ffffffffffffffff1681526020019081526020016000206000600082016000905560018201600090555050600260089054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663e0cb0ca7876000604051602001526040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001915050602060405180830381600087803b156100005760325a03f11561000057505050604051805190509150600260089054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663e0cb0ca7336000604051602001526040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001915050602060405180830381600087803b156100005760325a03f11561000057505050604051805190509050600260089054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663812cf01c838388886000604051602001526040518563ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808567ffffffffffffffff1667ffffffffffffffff1681526020018467ffffffffffffffff1667ffffffffffffffff1681526020018367ffffffffffffffff1667ffffffffffffffff1681526020018267ffffffffffffffff1667ffffffffffffffff168152602001945050505050602060405180830381600087803b156100005760325a03f1156100005750505060405180519050506002600081819054906101000a900467ffffffffffffffff16809291906001900391906101000a81548167ffffffffffffffff021916908367ffffffffffffffff160217905550507f7cb33caa9965b731601e035bccd30fc0ff072fedc3931fd3d3e7c4050a5db2748683338489600260089054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166360e625028b6000604051602001526040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808267ffffffffffffffff1667ffffffffffffffff168152602001915050602060405180830381600087803b156100005760325a03f11561000057505050604051805190508a604051808873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018767ffffffffffffffff1667ffffffffffffffff1681526020018673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018567ffffffffffffffff1667ffffffffffffffff1681526020018467ffffffffffffffff1667ffffffffffffffff16815260200183600019166000191681526020018267ffffffffffffffff1667ffffffffffffffff16815260200197505050505050505060405180910390a15b50509392505050565b600260009054906101000a900467ffffffffffffffff16815600a165627a7a723058201b26916daad01743cdaf08cfa25ed6eddc0d400b3a51d8bcca8a77306c7499710029";

    private Trade(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private Trade(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<SellEventResponse> getSellEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Sell", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint64>() {}, new TypeReference<Uint64>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint64>() {}));
        List<EventValues> valueList = extractEventParameters(event,transactionReceipt);
        ArrayList<SellEventResponse> responses = new ArrayList<SellEventResponse>(valueList.size());
        for(EventValues eventValues : valueList) {
            SellEventResponse typedResponse = new SellEventResponse();
            typedResponse._seller = (Address)eventValues.getNonIndexedValues().get(0);
            typedResponse._sellerIndex = (Uint64)eventValues.getNonIndexedValues().get(1);
            typedResponse._gameId = (Uint64)eventValues.getNonIndexedValues().get(2);
            typedResponse._asset = (Bytes32)eventValues.getNonIndexedValues().get(3);
            typedResponse._assetIndex = (Uint64)eventValues.getNonIndexedValues().get(4);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<SellEventResponse> sellEventObservable() {
        final Event event = new Event("Sell", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint64>() {}, new TypeReference<Uint64>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint64>() {}));
        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST,DefaultBlockParameterName.LATEST, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, SellEventResponse>() {
            @Override
            public SellEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                SellEventResponse typedResponse = new SellEventResponse();
                typedResponse._seller = (Address)eventValues.getNonIndexedValues().get(0);
                typedResponse._sellerIndex = (Uint64)eventValues.getNonIndexedValues().get(1);
                typedResponse._gameId = (Uint64)eventValues.getNonIndexedValues().get(2);
                typedResponse._asset = (Bytes32)eventValues.getNonIndexedValues().get(3);
                typedResponse._assetIndex = (Uint64)eventValues.getNonIndexedValues().get(4);
                return typedResponse;
            }
        });
    }

    public List<BuyEventResponse> getBuyEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Buy", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint64>() {}, new TypeReference<Address>() {}, new TypeReference<Uint64>() {}, new TypeReference<Uint64>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint64>() {}));
        List<EventValues> valueList = extractEventParameters(event,transactionReceipt);
        ArrayList<BuyEventResponse> responses = new ArrayList<BuyEventResponse>(valueList.size());
        for(EventValues eventValues : valueList) {
            BuyEventResponse typedResponse = new BuyEventResponse();
            typedResponse._owner = (Address)eventValues.getNonIndexedValues().get(0);
            typedResponse._ownerIndex = (Uint64)eventValues.getNonIndexedValues().get(1);
            typedResponse.__buyer = (Address)eventValues.getNonIndexedValues().get(2);
            typedResponse._buyerIndex = (Uint64)eventValues.getNonIndexedValues().get(3);
            typedResponse._gameId = (Uint64)eventValues.getNonIndexedValues().get(4);
            typedResponse._asset = (Bytes32)eventValues.getNonIndexedValues().get(5);
            typedResponse._assetIndex = (Uint64)eventValues.getNonIndexedValues().get(6);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<BuyEventResponse> buyEventObservable() {
        final Event event = new Event("Buy", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint64>() {}, new TypeReference<Address>() {}, new TypeReference<Uint64>() {}, new TypeReference<Uint64>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint64>() {}));
        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST,DefaultBlockParameterName.LATEST, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, BuyEventResponse>() {
            @Override
            public BuyEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                BuyEventResponse typedResponse = new BuyEventResponse();
                typedResponse._owner = (Address)eventValues.getNonIndexedValues().get(0);
                typedResponse._ownerIndex = (Uint64)eventValues.getNonIndexedValues().get(1);
                typedResponse.__buyer = (Address)eventValues.getNonIndexedValues().get(2);
                typedResponse._buyerIndex = (Uint64)eventValues.getNonIndexedValues().get(3);
                typedResponse._gameId = (Uint64)eventValues.getNonIndexedValues().get(4);
                typedResponse._asset = (Bytes32)eventValues.getNonIndexedValues().get(5);
                typedResponse._assetIndex = (Uint64)eventValues.getNonIndexedValues().get(6);
                return typedResponse;
            }
        });
    }

    public Future<Address> creator() {
        Function function = new Function("creator", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> sell(Uint64 _gameId, Uint64 _assetId, Uint256 price, Bytes32 proveHash) {
        Function function = new Function("sell", Arrays.<Type>asList(_gameId, _assetId, price, proveHash), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<TransactionReceipt> buy(Address _sellerPlayer, Uint64 _gameId, Uint64 _assetId) {
        Function function = new Function("buy", Arrays.<Type>asList(_sellerPlayer, _gameId, _assetId), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<Uint64> onSellingCount() {
        Function function = new Function("onSellingCount", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public static Future<Trade> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialValue, Address _dasAddr, Address _ugToken) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_dasAddr, _ugToken));
        return deployAsync(Trade.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor, initialValue);
    }

    public static Future<Trade> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialValue, Address _dasAddr, Address _ugToken) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(_dasAddr, _ugToken));
        return deployAsync(Trade.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor, initialValue);
    }

    public static Trade load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Trade(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Trade load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Trade(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class SellEventResponse {
        public Address _seller;

        public Uint64 _sellerIndex;

        public Uint64 _gameId;

        public Bytes32 _asset;

        public Uint64 _assetIndex;
    }

    public static class BuyEventResponse {
        public Address _owner;

        public Uint64 _ownerIndex;

        public Address __buyer;

        public Uint64 _buyerIndex;

        public Uint64 _gameId;

        public Bytes32 _asset;

        public Uint64 _assetIndex;
    }
}
