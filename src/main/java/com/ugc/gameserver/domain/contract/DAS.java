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
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicBytes;
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
public final class DAS extends Contract {
    private static final String BINARY = "60606040523462000000575b600160068181548183558181151162000053578183600052602060002091820191016200005291905b808211156200004e57600081600090555060010162000034565b5090565b5b505050506001600381815481835581811511620000fa57600302816003028360005260206000209182019101620000f991905b80821115620000f5576000600082016000905560018201600090556002820160006101000a81549067ffffffffffffffff02191690556002820160086101000a81549067ffffffffffffffff02191690556002820160106101000a81549060ff02191690555060030162000086565b5090565b5b5050505033600060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505b5b611ec680620001516000396000f3006060604052361561011b576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806302d05d3f146101295780631b21b2631461017857806329f1b3fb146101c75780633963b2ef1461029457806339b5ad531461031a5780635d8c605b1461035957806360e62502146103a2578063670f5903146103e55780636fc43bd114610417578063742978da14610456578063812cf01c14610510578063a7ef5ec314610588578063aacb1ebf146105bf578063affed0e01461061a578063b197b27c14610651578063b7fa270f146106af578063bc0f3cad1461070a578063d43902c91461077b578063e0cb0ca7146107d6578063fbd6215914610831578063ff7e2be91461088e575b34610000576101275b5b565b005b34610000576101366108e9565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b346100005761019d600480803567ffffffffffffffff1690602001909190505061090f565b604051808267ffffffffffffffff1667ffffffffffffffff16815260200191505060405180910390f35b346100005761020b600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803567ffffffffffffffff16906020019091905050610936565b604051808060200182810382528381815181526020019150805190602001908083836000831461025a575b80518252602083111561025a57602082019150602081019050602083039250610236565b505050905090810190601f1680156102865780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34610000576102d8600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803567ffffffffffffffff16906020019091905050610b21565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b346100005761033f600480803567ffffffffffffffff16906020019091905050610bfa565b604051808215151515815260200191505060405180910390f35b3461000057610378600480803560001916906020019091905050610c3d565b604051808267ffffffffffffffff1667ffffffffffffffff16815260200191505060405180910390f35b34610000576103c7600480803567ffffffffffffffff16906020019091905050610c77565b60405180826000191660001916815260200191505060405180910390f35b3461000057610415600480803567ffffffffffffffff169060200190919080351515906020019091905050610cad565b005b346100005761043c600480803567ffffffffffffffff16906020019091905050610cf5565b604051808215151515815260200191505060405180910390f35b3461000057610487600480803573ffffffffffffffffffffffffffffffffffffffff169060200190919050506111cb565b60405180806020018281038252838181518152602001915080519060200190808383600083146104d6575b8051825260208311156104d6576020820191506020810190506020830392506104b2565b505050905090810190601f1680156105025780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b346100005761056e600480803567ffffffffffffffff1690602001909190803567ffffffffffffffff1690602001909190803567ffffffffffffffff1690602001909190803567ffffffffffffffff1690602001909190505061130f565b604051808215151515815260200191505060405180910390f35b34610000576105956114e4565b604051808267ffffffffffffffff1667ffffffffffffffff16815260200191505060405180910390f35b34610000576105f0600480803573ffffffffffffffffffffffffffffffffffffffff169060200190919050506114fe565b604051808267ffffffffffffffff1667ffffffffffffffff16815260200191505060405180910390f35b346100005761062761153b565b604051808267ffffffffffffffff1667ffffffffffffffff16815260200191505060405180910390f35b3461000057610695600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803567ffffffffffffffff16906020019091905050611555565b604051808215151515815260200191505060405180910390f35b34610000576106e0600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050611663565b604051808267ffffffffffffffff1667ffffffffffffffff16815260200191505060405180910390f35b3461000057610761600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803567ffffffffffffffff1690602001909190803567ffffffffffffffff1690602001909190505061168a565b604051808215151515815260200191505060405180910390f35b34610000576107ac600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190505061176a565b604051808267ffffffffffffffff1667ffffffffffffffff16815260200191505060405180910390f35b3461000057610807600480803573ffffffffffffffffffffffffffffffffffffffff169060200190919050506117a7565b604051808267ffffffffffffffff1667ffffffffffffffff16815260200191505060405180910390f35b346100005761084c6004808035906020019091905050611934565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34610000576108bf600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050611971565b604051808267ffffffffffffffff1667ffffffffffffffff16815260200191505060405180910390f35b600060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60026020528060005260406000206000915054906101000a900467ffffffffffffffff1681565b602060405190810160405280600081525060006000600060206040519081016040528060008152506000600061096b89611971565b9550600560008767ffffffffffffffff1667ffffffffffffffff16815260200190815260200160002094506000935060646040518059106109a95750595b908082528060200260200182016040525b50925060009150600093505b8480549050841015610b0857848481548110156100005790600052602060002090600491828204019190066008025b9054906101000a900467ffffffffffffffff16905060008167ffffffffffffffff16118015610a7657508767ffffffffffffffff1660038267ffffffffffffffff16815481101561000057906000526020600020906003020160005b5060020160089054906101000a900467ffffffffffffffff1667ffffffffffffffff16145b8015610abe57506000151560038267ffffffffffffffff16815481101561000057906000526020600020906003020160005b5060020160109054906101000a900460ff161515145b15610afa578083838060010194508151811015610000579060200190602002019067ffffffffffffffff16908167ffffffffffffffff16815250505b5b83806001019450506109c6565b610b1283836119cf565b96505b50505050505092915050565b600060006000610b3085611971565b915060008267ffffffffffffffff161115610bf157600860008367ffffffffffffffff1667ffffffffffffffff16815260200190815260200160002060010160008567ffffffffffffffff1667ffffffffffffffff16815260200190815260200160002060009054906101000a900467ffffffffffffffff16905060068167ffffffffffffffff16815481101561000057906000526020600020900160005b9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1692505b5b505092915050565b600060038267ffffffffffffffff16815481101561000057906000526020600020906003020160005b5060020160109054906101000a900460ff1690505b919050565b600060046000836000191660001916815260200190815260200160002060009054906101000a900467ffffffffffffffff1690505b919050565b600060038267ffffffffffffffff16815481101561000057906000526020600020906003020160005b506001015490505b919050565b8060038367ffffffffffffffff16815481101561000057906000526020600020906003020160005b5060020160106101000a81548160ff0219169083151502179055505b5050565b6000600060006000600060006000601481819054906101000a900467ffffffffffffffff168092919060010191906101000a81548167ffffffffffffffff021916908367ffffffffffffffff16021790555033604051808367ffffffffffffffff1667ffffffffffffffff1678010000000000000000000000000000000000000000000000000281526008018273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166c01000000000000000000000000028152601401925050506040518091039020945060038054809190600101815481835581811511610e7457600302816003028360005260206000209182019101610e7391905b80821115610e6f576000600082016000905560018201600090556002820160006101000a81549067ffffffffffffffff02191690556002820160086101000a81549067ffffffffffffffff02191690556002820160106101000a81549060ff021916905550600301610e02565b5090565b5b505050935060038467ffffffffffffffff16815481101561000057906000526020600020906003020160005b509250848360000181600019169055508483600101816000191690555060008360020160106101000a81548160ff021916908315150217905550610ee3336117a7565b9150818360020160006101000a81548167ffffffffffffffff021916908367ffffffffffffffff160217905550868360020160086101000a81548167ffffffffffffffff021916908367ffffffffffffffff160217905550600560008367ffffffffffffffff1667ffffffffffffffff16815260200190815260200160002090508381828054809190600101815481835581811511610fbc576003016004900481600301600490048360005260206000209182019101610fbb91905b80821115610fb7576000816000905550600101610f9f565b5090565b5b50505081548110156100005790600052602060002090600491828204019190066008025b6101000a81548167ffffffffffffffff021916908367ffffffffffffffff1602179055506001600081819054906101000a900467ffffffffffffffff168092919060010191906101000a81548167ffffffffffffffff021916908367ffffffffffffffff160217905550506001600260008967ffffffffffffffff1667ffffffffffffffff16815260200190815260200160002060008282829054906101000a900467ffffffffffffffff160192506101000a81548167ffffffffffffffff021916908367ffffffffffffffff1602179055508360046000876000191660001916815260200190815260200160002060006101000a81548167ffffffffffffffff021916908367ffffffffffffffff1602179055507fdfef7e335ca3bf9d4892551016b59cbfd69ac51e0cc139928c4a0a3d75a5ac183383898888604051808673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018567ffffffffffffffff1667ffffffffffffffff1681526020018467ffffffffffffffff1667ffffffffffffffff16815260200183600019166000191681526020018267ffffffffffffffff1667ffffffffffffffff1681526020019550505050505060405180910390a1600195505b5050505050919050565b602060405190810160405280600081525060006000600060206040519081016040528060008152506000600061120088611971565b9550600560008767ffffffffffffffff1667ffffffffffffffff168152602001908152602001600020945060009350606460405180591061123e5750595b908082528060200260200182016040525b50925060009150600093505b84805490508410156112f757848481548110156100005790600052602060002090600491828204019190066008025b9054906101000a900467ffffffffffffffff16905060008167ffffffffffffffff1611156112e9578083838060010194508151811015610000579060200190602002019067ffffffffffffffff16908167ffffffffffffffff16815250505b5b838060010194505061125b565b61130183836119cf565b96505b505050505050919050565b600060006000600060009250600560008967ffffffffffffffff1667ffffffffffffffff1681526020019081526020016000209150600560008867ffffffffffffffff1667ffffffffffffffff1681526020019081526020016000209050600092505b81805490508367ffffffffffffffff161015611438578467ffffffffffffffff16828467ffffffffffffffff1681548110156100005790600052602060002090600491828204019190066008025b9054906101000a900467ffffffffffffffff1667ffffffffffffffff16141561142a57818367ffffffffffffffff1681548110156100005790600052602060002090600491828204019190066008025b6101000a81549067ffffffffffffffff0219169055611438565b5b8280600101935050611372565b848182805480919060010181548183558181151161149057600301600490048160030160049004836000526020600020918201910161148f91905b8082111561148b576000816000905550600101611473565b5090565b5b50505081548110156100005790600052602060002090600491828204019190066008025b6101000a81548167ffffffffffffffff021916908367ffffffffffffffff1602179055505b505050949350505050565b600160009054906101000a900467ffffffffffffffff1681565b6000600061150b83611971565b905060018167ffffffffffffffff1610156115295760009150611535565b61153281611b05565b91505b50919050565b600060149054906101000a900467ffffffffffffffff1681565b60006000600033846000611568836117a7565b90506115748183611b3d565b1561157e57610000565b611587886117a7565b9450611592336117a7565b935061159f848887611bb9565b7f21ca1003e8a86c14fc9c803dc1d967d65985325497800630575ed9ef6df1a298338989604051808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018267ffffffffffffffff1667ffffffffffffffff168152602001935050505060405180910390a1600195505b5b505050505092915050565b60076020528060005260406000206000915054906101000a900467ffffffffffffffff1681565b600060006000600061169b87611971565b925060018367ffffffffffffffff1610156116b95760009350611760565b600560008467ffffffffffffffff1667ffffffffffffffff1681526020019081526020016000209150600090505b818054905081101561175b578467ffffffffffffffff16828281548110156100005790600052602060002090600491828204019190066008025b9054906101000a900467ffffffffffffffff1667ffffffffffffffff16141561174d5760019350611760565b5b80806001019150506116e7565b600093505b5050509392505050565b6000600061177783611971565b905060018167ffffffffffffffff16101561179557600091506117a1565b61179e81611e51565b91505b50919050565b60006000600760008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900467ffffffffffffffff16905060018167ffffffffffffffff16101561192a576006805480919060010181548183558181151161185d5781836000526020600020918201910161185c91905b80821115611858576000816000905550600101611840565b5090565b5b50505090508260068267ffffffffffffffff16815481101561000057906000526020600020900160005b6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555080600760008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548167ffffffffffffffff021916908367ffffffffffffffff1602179055505b8091505b50919050565b600681815481101561000057906000526020600020900160005b915054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000600760008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900467ffffffffffffffff1690505b919050565b60206040519081016040528060008152506020604051908101604052806000815250600060006000600060006000600060688a02604051805910611a105750595b908082528060200260200182016040525b509750600096505b89871015611af35760038b888151811015610000579060200190602002015167ffffffffffffffff16815481101561000057906000526020600020906003020160005b50955085600101549450856000015493508560020160089054906101000a900467ffffffffffffffff16925060009150600115158660020160109054906101000a900460ff1615151415611abf57600191505b60688702602001905084818901528360208201890152826040820189015281606082018901535b8680600101975050611a29565b8798505b505050505050505092915050565b6000600860008367ffffffffffffffff1667ffffffffffffffff1681526020019081526020016000206000018054905090505b919050565b60006000600860008567ffffffffffffffff1667ffffffffffffffff16815260200190815260200160002060010160008467ffffffffffffffff1667ffffffffffffffff16815260200190815260200160002060009054906101000a900467ffffffffffffffff1667ffffffffffffffff161190505b92915050565b80600860008567ffffffffffffffff1667ffffffffffffffff16815260200190815260200160002060010160008467ffffffffffffffff1667ffffffffffffffff16815260200190815260200160002060006101000a81548167ffffffffffffffff021916908367ffffffffffffffff160217905550600860008467ffffffffffffffff1667ffffffffffffffff1681526020019081526020016000206000018054806001018281815481835581811511611cae576003016004900481600301600490048360005260206000209182019101611cad91905b80821115611ca9576000816000905550600101611c91565b5090565b5b50505091600052602060002090600491828204019190066008025b84909190916101000a81548167ffffffffffffffff021916908367ffffffffffffffff16021790555050600960008267ffffffffffffffff1667ffffffffffffffff16815260200190815260200160002060010160008367ffffffffffffffff1667ffffffffffffffff1681526020019081526020016000208054806001018281815481835581811511611d97576003016004900481600301600490048360005260206000209182019101611d9691905b80821115611d92576000816000905550600101611d7a565b5090565b5b50505091600052602060002090600491828204019190066008025b85909190916101000a81548167ffffffffffffffff021916908367ffffffffffffffff16021790555050600960008267ffffffffffffffff1667ffffffffffffffff168152602001908152602001600020600001600081819054906101000a900467ffffffffffffffff168092919060010191906101000a81548167ffffffffffffffff021916908367ffffffffffffffff160217905550505b505050565b6000600960008367ffffffffffffffff1667ffffffffffffffff16815260200190815260200160002060000160009054906101000a900467ffffffffffffffff1690505b9190505600a165627a7a72305820d241797c99f6718d183d3919fd74be6226daa199ea05a638dda86c669456370a0029";

    private DAS(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private DAS(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<InitGameTokenEventResponse> getInitGameTokenEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("InitGameToken", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint64>() {}, new TypeReference<Uint64>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint64>() {}));
        List<EventValues> valueList = extractEventParameters(event,transactionReceipt);
        ArrayList<InitGameTokenEventResponse> responses = new ArrayList<InitGameTokenEventResponse>(valueList.size());
        for(EventValues eventValues : valueList) {
            InitGameTokenEventResponse typedResponse = new InitGameTokenEventResponse();
            typedResponse._seller = (Address)eventValues.getNonIndexedValues().get(0);
            typedResponse._sellerIndex = (Uint64)eventValues.getNonIndexedValues().get(1);
            typedResponse._gameId = (Uint64)eventValues.getNonIndexedValues().get(2);
            typedResponse._asset = (Bytes32)eventValues.getNonIndexedValues().get(3);
            typedResponse._assetIndex = (Uint64)eventValues.getNonIndexedValues().get(4);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<InitGameTokenEventResponse> initGameTokenEventObservable() {
        final Event event = new Event("InitGameToken", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint64>() {}, new TypeReference<Uint64>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint64>() {}));
        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST,DefaultBlockParameterName.LATEST, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, InitGameTokenEventResponse>() {
            @Override
            public InitGameTokenEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                InitGameTokenEventResponse typedResponse = new InitGameTokenEventResponse();
                typedResponse._seller = (Address)eventValues.getNonIndexedValues().get(0);
                typedResponse._sellerIndex = (Uint64)eventValues.getNonIndexedValues().get(1);
                typedResponse._gameId = (Uint64)eventValues.getNonIndexedValues().get(2);
                typedResponse._asset = (Bytes32)eventValues.getNonIndexedValues().get(3);
                typedResponse._assetIndex = (Uint64)eventValues.getNonIndexedValues().get(4);
                return typedResponse;
            }
        });
    }

    public List<ChannelEventResponse> getChannelEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Channel", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint64>() {}));
        List<EventValues> valueList = extractEventParameters(event,transactionReceipt);
        ArrayList<ChannelEventResponse> responses = new ArrayList<ChannelEventResponse>(valueList.size());
        for(EventValues eventValues : valueList) {
            ChannelEventResponse typedResponse = new ChannelEventResponse();
            typedResponse._player = (Address)eventValues.getNonIndexedValues().get(0);
            typedResponse._channel = (Address)eventValues.getNonIndexedValues().get(1);
            typedResponse._gameId = (Uint64)eventValues.getNonIndexedValues().get(2);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ChannelEventResponse> channelEventObservable() {
        final Event event = new Event("Channel", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint64>() {}));
        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST,DefaultBlockParameterName.LATEST, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, ChannelEventResponse>() {
            @Override
            public ChannelEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                ChannelEventResponse typedResponse = new ChannelEventResponse();
                typedResponse._player = (Address)eventValues.getNonIndexedValues().get(0);
                typedResponse._channel = (Address)eventValues.getNonIndexedValues().get(1);
                typedResponse._gameId = (Uint64)eventValues.getNonIndexedValues().get(2);
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

    public Future<Uint64> totalOfAssetMapping(Uint64 param0) {
        Function function = new Function("totalOfAssetMapping", 
                Arrays.<Type>asList(param0), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<DynamicBytes> getPlayerToken(Address _player, Uint64 _gameId) {
        Function function = new Function("getPlayerToken", 
                Arrays.<Type>asList(_player, _gameId), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Address> getChannelByPlayer(Address _player, Uint64 _gameId) {
        Function function = new Function("getChannelByPlayer", 
                Arrays.<Type>asList(_player, _gameId), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Bool> getSellingStatusByAssetId(Uint64 _assetId) {
        Function function = new Function("getSellingStatusByAssetId", 
                Arrays.<Type>asList(_assetId), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Uint64> getIndexByToken(Bytes32 token) {
        Function function = new Function("getIndexByToken", 
                Arrays.<Type>asList(token), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Bytes32> getTokenByAssetId(Uint64 _assetId) {
        Function function = new Function("getTokenByAssetId", 
                Arrays.<Type>asList(_assetId), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> setSellingStatus(Uint64 _assetId, Bool status) {
        Function function = new Function("setSellingStatus", Arrays.<Type>asList(_assetId, status), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<TransactionReceipt> initGameToken(Uint64 _gameId) {
        Function function = new Function("initGameToken", Arrays.<Type>asList(_gameId), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<DynamicBytes> getAssets(Address _player) {
        Function function = new Function("getAssets", 
                Arrays.<Type>asList(_player), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> transferAsset(Uint64 _from, Uint64 _to, Uint64 _gameId, Uint64 _assetId) {
        Function function = new Function("transferAsset", Arrays.<Type>asList(_from, _to, _gameId, _assetId), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<Uint64> totalOfAsset() {
        Function function = new Function("totalOfAsset", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Uint64> getChannelNumberByPlayer(Address _player) {
        Function function = new Function("getChannelNumberByPlayer", 
                Arrays.<Type>asList(_player), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Uint64> nonce() {
        Function function = new Function("nonce", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> channel(Address _channel, Uint64 _gameId) {
        Function function = new Function("channel", Arrays.<Type>asList(_channel, _gameId), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<Uint64> addressIndexes(Address param0) {
        Function function = new Function("addressIndexes", 
                Arrays.<Type>asList(param0), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Bool> isPlayerContainAsset(Address _player, Uint64 _gameId, Uint64 _assetId) {
        Function function = new Function("isPlayerContainAsset", 
                Arrays.<Type>asList(_player, _gameId, _assetId), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Uint64> getPlayerNumberByChannel(Address _channel) {
        Function function = new Function("getPlayerNumberByChannel", 
                Arrays.<Type>asList(_channel), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> getAddressIndexOrCreate(Address _addr) {
        Function function = new Function("getAddressIndexOrCreate", Arrays.<Type>asList(_addr), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<Address> playerOfAddress(Uint256 param0) {
        Function function = new Function("playerOfAddress", 
                Arrays.<Type>asList(param0), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Uint64> getAddressIndex(Address _addr) {
        Function function = new Function("getAddressIndex", 
                Arrays.<Type>asList(_addr), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public static Future<DAS> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialValue) {
        return deployAsync(DAS.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialValue);
    }

    public static Future<DAS> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialValue) {
        return deployAsync(DAS.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialValue);
    }

    public static DAS load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new DAS(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static DAS load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new DAS(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class InitGameTokenEventResponse {
        public Address _seller;

        public Uint64 _sellerIndex;

        public Uint64 _gameId;

        public Bytes32 _asset;

        public Uint64 _assetIndex;
    }

    public static class ChannelEventResponse {
        public Address _player;

        public Address _channel;

        public Uint64 _gameId;
    }
}
