package com.ugc.gameserver.domain.contract;

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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modify!</strong><br>
 * Please use {@link org.web3j.codegen.SolidityFunctionWrapperGenerator} to update.
 *
 * <p>Generated with web3j version none.
 */
public final class DAS extends Contract {
    private static final String BINARY = "60606040523462000000575b600160068181548183558181151162000053578183600052602060002091820191016200005291905b808211156200004e57600081600090555060010162000034565b5090565b5b505050506001600381815481835581811511620000fa57600302816003028360005260206000209182019101620000f991905b80821115620000f5576000600082016000905560018201600090556002820160006101000a81549067ffffffffffffffff02191690556002820160086101000a81549067ffffffffffffffff02191690556002820160106101000a81549060ff02191690555060030162000086565b5090565b5b505050506001600b8181548183558181151162000146578183600052602060002091820191016200014591905b808211156200014157600081600090555060010162000127565b5090565b5b5050505033600060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505b5b612380806200019d6000396000f30060606040523615610152576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806302d05d3f146101605780631b21b263146101af57806329f1b3fb146101fe5780633963b2ef146102cb57806339b5ad53146103515780634fea5ffd146103905780635d8c605b146103f757806360e6250214610440578063670f590314610483578063742978da146104b5578063812cf01c1461056f57806396e736d5146105e7578063a4dec4401461062a578063a7ef5ec314610639578063aacb1ebf14610670578063abfd6da5146106cb578063affed0e014610726578063b197b27c1461075d578063b510c535146107bb578063b7fa270f14610807578063bc0f3cad14610862578063c08b6fb4146108d3578063d43902c91461093a578063e0cb0ca714610995578063fbd62159146109f0578063ff7e2be914610a4d575b346100005761015e5b5b565b005b346100005761016d610aa8565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34610000576101d4600480803567ffffffffffffffff16906020019091905050610ace565b604051808267ffffffffffffffff1667ffffffffffffffff16815260200191505060405180910390f35b3461000057610242600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803567ffffffffffffffff16906020019091905050610af5565b6040518080602001828103825283818151815260200191508051906020019080838360008314610291575b8051825260208311156102915760208201915060208101905060208303925061026d565b505050905090810190601f1680156102bd5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b346100005761030f600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803567ffffffffffffffff16906020019091905050610ce0565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3461000057610376600480803567ffffffffffffffff16906020019091905050610db9565b604051808215151515815260200191505060405180910390f35b34610000576103b5600480803567ffffffffffffffff16906020019091905050610dfc565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3461000057610416600480803560001916906020019091905050610e49565b604051808267ffffffffffffffff1667ffffffffffffffff16815260200191505060405180910390f35b3461000057610465600480803567ffffffffffffffff16906020019091905050610e83565b60405180826000191660001916815260200191505060405180910390f35b34610000576104b3600480803567ffffffffffffffff169060200190919080351515906020019091905050610eb9565b005b34610000576104e6600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610f01565b6040518080602001828103825283818151815260200191508051906020019080838360008314610535575b80518252602083111561053557602082019150602081019050602083039250610511565b505050905090810190601f1680156105615780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34610000576105cd600480803567ffffffffffffffff1690602001909190803567ffffffffffffffff1690602001909190803567ffffffffffffffff1690602001909190803567ffffffffffffffff16906020019091905050611045565b604051808215151515815260200191505060405180910390f35b346100005761060c600480803567ffffffffffffffff1690602001909190505061121a565b60405180826000191660001916815260200191505060405180910390f35b3461000057610637611250565b005b34610000576106466113d7565b604051808267ffffffffffffffff1667ffffffffffffffff16815260200191505060405180910390f35b34610000576106a1600480803573ffffffffffffffffffffffffffffffffffffffff169060200190919050506113f1565b604051808267ffffffffffffffff1667ffffffffffffffff16815260200191505060405180910390f35b34610000576106fc600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190505061142e565b604051808267ffffffffffffffff1667ffffffffffffffff16815260200191505060405180910390f35b346100005761073361148c565b604051808267ffffffffffffffff1667ffffffffffffffff16815260200191505060405180910390f35b34610000576107a1600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803567ffffffffffffffff169060200190919050506114a6565b604051808215151515815260200191505060405180910390f35b34610000576107ed600480803567ffffffffffffffff16906020019091908035600019169060200190919050506115b4565b604051808215151515815260200191505060405180910390f35b3461000057610838600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050611a8b565b604051808267ffffffffffffffff1667ffffffffffffffff16815260200191505060405180910390f35b34610000576108b9600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803567ffffffffffffffff1690602001909190803567ffffffffffffffff16906020019091905050611ab2565b604051808215151515815260200191505060405180910390f35b34610000576108f8600480803567ffffffffffffffff16906020019091905050611b92565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b346100005761096b600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050611c24565b604051808267ffffffffffffffff1667ffffffffffffffff16815260200191505060405180910390f35b34610000576109c6600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050611c61565b604051808267ffffffffffffffff1667ffffffffffffffff16815260200191505060405180910390f35b3461000057610a0b6004808035906020019091905050611dee565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3461000057610a7e600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050611e2b565b604051808267ffffffffffffffff1667ffffffffffffffff16815260200191505060405180910390f35b600060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60026020528060005260406000206000915054906101000a900467ffffffffffffffff1681565b6020604051908101604052806000815250600060006000602060405190810160405280600081525060006000610b2a89611e2b565b9550600560008767ffffffffffffffff1667ffffffffffffffff1681526020019081526020016000209450600093506064604051805910610b685750595b908082528060200260200182016040525b50925060009150600093505b8480549050841015610cc757848481548110156100005790600052602060002090600491828204019190066008025b9054906101000a900467ffffffffffffffff16905060008167ffffffffffffffff16118015610c3557508767ffffffffffffffff1660038267ffffffffffffffff16815481101561000057906000526020600020906003020160005b5060020160089054906101000a900467ffffffffffffffff1667ffffffffffffffff16145b8015610c7d57506000151560038267ffffffffffffffff16815481101561000057906000526020600020906003020160005b5060020160109054906101000a900460ff161515145b15610cb9578083838060010194508151811015610000579060200190602002019067ffffffffffffffff16908167ffffffffffffffff16815250505b5b8380600101945050610b85565b610cd18383611e89565b96505b50505050505092915050565b600060006000610cef85611e2b565b915060008267ffffffffffffffff161115610db057600860008367ffffffffffffffff1667ffffffffffffffff16815260200190815260200160002060010160008567ffffffffffffffff1667ffffffffffffffff16815260200190815260200160002060009054906101000a900467ffffffffffffffff16905060068167ffffffffffffffff16815481101561000057906000526020600020900160005b9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1692505b5b505092915050565b600060038267ffffffffffffffff16815481101561000057906000526020600020906003020160005b5060020160109054906101000a900460ff1690505b919050565b6000600b8267ffffffffffffffff16815481101561000057906000526020600020900160005b9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690505b919050565b600060046000836000191660001916815260200190815260200160002060009054906101000a900467ffffffffffffffff1690505b919050565b600060038267ffffffffffffffff16815481101561000057906000526020600020906003020160005b506001015490505b919050565b8060038367ffffffffffffffff16815481101561000057906000526020600020906003020160005b5060020160106101000a81548160ff0219169083151502179055505b5050565b6020604051908101604052806000815250600060006000602060405190810160405280600081525060006000610f3688611e2b565b9550600560008767ffffffffffffffff1667ffffffffffffffff1681526020019081526020016000209450600093506064604051805910610f745750595b908082528060200260200182016040525b50925060009150600093505b848054905084101561102d57848481548110156100005790600052602060002090600491828204019190066008025b9054906101000a900467ffffffffffffffff16905060008167ffffffffffffffff16111561101f578083838060010194508151811015610000579060200190602002019067ffffffffffffffff16908167ffffffffffffffff16815250505b5b8380600101945050610f91565b6110378383611e89565b96505b505050505050919050565b600060006000600060009250600560008967ffffffffffffffff1667ffffffffffffffff1681526020019081526020016000209150600560008867ffffffffffffffff1667ffffffffffffffff1681526020019081526020016000209050600092505b81805490508367ffffffffffffffff16101561116e578467ffffffffffffffff16828467ffffffffffffffff1681548110156100005790600052602060002090600491828204019190066008025b9054906101000a900467ffffffffffffffff1667ffffffffffffffff16141561116057818367ffffffffffffffff1681548110156100005790600052602060002090600491828204019190066008025b6101000a81549067ffffffffffffffff021916905561116e565b5b82806001019350506110a8565b84818280548091906001018154818355818115116111c65760030160049004816003016004900483600052602060002091820191016111c591905b808211156111c15760008160009055506001016111a9565b5090565b5b50505081548110156100005790600052602060002090600491828204019190066008025b6101000a81548167ffffffffffffffff021916908367ffffffffffffffff1602179055505b505050949350505050565b600060038267ffffffffffffffff16815481101561000057906000526020600020906003020160005b506000015490505b919050565b60006000600a60003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900467ffffffffffffffff1667ffffffffffffffff1611156112bd57610000565b600b80548091906001018154818355818115116113065781836000526020600020918201910161130591905b808211156113015760008160009055506001016112e9565b5090565b5b505050905033600b8267ffffffffffffffff16815481101561000057906000526020600020900160005b6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555080600a60003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548167ffffffffffffffff021916908367ffffffffffffffff1602179055505b5b50565b600160009054906101000a900467ffffffffffffffff1681565b600060006113fe83611e2b565b905060018167ffffffffffffffff16101561141c5760009150611428565b61142581611fbf565b91505b50919050565b6000600a60008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900467ffffffffffffffff1690505b919050565b600060149054906101000a900467ffffffffffffffff1681565b600060006000338460006114b983611c61565b90506114c58183611ff7565b156114cf57610000565b6114d888611c61565b94506114e333611c61565b93506114f0848887612073565b7f21ca1003e8a86c14fc9c803dc1d967d65985325497800630575ed9ef6df1a298338989604051808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018267ffffffffffffffff1667ffffffffffffffff168152602001935050505060405180910390a1600195505b5b505050505092915050565b6000600060006000600060006000601481819054906101000a900467ffffffffffffffff168092919060010191906101000a81548167ffffffffffffffff021916908367ffffffffffffffff16021790555033604051808367ffffffffffffffff1667ffffffffffffffff1678010000000000000000000000000000000000000000000000000281526008018273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166c010000000000000000000000000281526014019250505060405180910390209450600380548091906001018154818355818115116117335760030281600302836000526020600020918201910161173291905b8082111561172e576000600082016000905560018201600090556002820160006101000a81549067ffffffffffffffff02191690556002820160086101000a81549067ffffffffffffffff02191690556002820160106101000a81549060ff0219169055506003016116c1565b5090565b5b505050935060038467ffffffffffffffff16815481101561000057906000526020600020906003020160005b509250868360000181600019169055508483600101816000191690555060008360020160106101000a81548160ff0219169083151502179055506117a233611c61565b9150818360020160006101000a81548167ffffffffffffffff021916908367ffffffffffffffff160217905550878360020160086101000a81548167ffffffffffffffff021916908367ffffffffffffffff160217905550600560008367ffffffffffffffff1667ffffffffffffffff1681526020019081526020016000209050838182805480919060010181548183558181151161187b57600301600490048160030160049004836000526020600020918201910161187a91905b8082111561187657600081600090555060010161185e565b5090565b5b50505081548110156100005790600052602060002090600491828204019190066008025b6101000a81548167ffffffffffffffff021916908367ffffffffffffffff1602179055506001600081819054906101000a900467ffffffffffffffff168092919060010191906101000a81548167ffffffffffffffff021916908367ffffffffffffffff160217905550506001600260008a67ffffffffffffffff1667ffffffffffffffff16815260200190815260200160002060008282829054906101000a900467ffffffffffffffff160192506101000a81548167ffffffffffffffff021916908367ffffffffffffffff1602179055508360046000876000191660001916815260200190815260200160002060006101000a81548167ffffffffffffffff021916908367ffffffffffffffff1602179055507fdfef7e335ca3bf9d4892551016b59cbfd69ac51e0cc139928c4a0a3d75a5ac1833838a8888604051808673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018567ffffffffffffffff1667ffffffffffffffff1681526020018467ffffffffffffffff1667ffffffffffffffff16815260200183600019166000191681526020018267ffffffffffffffff1667ffffffffffffffff1681526020019550505050505060405180910390a1600195505b505050505092915050565b60076020528060005260406000206000915054906101000a900467ffffffffffffffff1681565b6000600060006000611ac387611e2b565b925060018367ffffffffffffffff161015611ae15760009350611b88565b600560008467ffffffffffffffff1667ffffffffffffffff1681526020019081526020016000209150600090505b8180549050811015611b83578467ffffffffffffffff16828281548110156100005790600052602060002090600491828204019190066008025b9054906101000a900467ffffffffffffffff1667ffffffffffffffff161415611b755760019350611b88565b5b8080600101915050611b0f565b600093505b5050509392505050565b6000600060038367ffffffffffffffff16815481101561000057906000526020600020906003020160005b5060020160009054906101000a900467ffffffffffffffff16905060068167ffffffffffffffff16815481101561000057906000526020600020900160005b9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1691505b50919050565b60006000611c3183611e2b565b905060018167ffffffffffffffff161015611c4f5760009150611c5b565b611c588161230b565b91505b50919050565b60006000600760008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900467ffffffffffffffff16905060018167ffffffffffffffff161015611de45760068054809190600101815481835581811511611d1757818360005260206000209182019101611d1691905b80821115611d12576000816000905550600101611cfa565b5090565b5b50505090508260068267ffffffffffffffff16815481101561000057906000526020600020900160005b6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555080600760008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548167ffffffffffffffff021916908367ffffffffffffffff1602179055505b8091505b50919050565b600681815481101561000057906000526020600020900160005b915054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000600760008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900467ffffffffffffffff1690505b919050565b60206040519081016040528060008152506020604051908101604052806000815250600060006000600060006000600060688a02604051805910611eca5750595b908082528060200260200182016040525b509750600096505b89871015611fad5760038b888151811015610000579060200190602002015167ffffffffffffffff16815481101561000057906000526020600020906003020160005b50955085600101549450856000015493508560020160089054906101000a900467ffffffffffffffff16925060009150600115158660020160109054906101000a900460ff1615151415611f7957600191505b60688702602001905084818901528360208201890152826040820189015281606082018901535b8680600101975050611ee3565b8798505b505050505050505092915050565b6000600860008367ffffffffffffffff1667ffffffffffffffff1681526020019081526020016000206000018054905090505b919050565b60006000600860008567ffffffffffffffff1667ffffffffffffffff16815260200190815260200160002060010160008467ffffffffffffffff1667ffffffffffffffff16815260200190815260200160002060009054906101000a900467ffffffffffffffff1667ffffffffffffffff161190505b92915050565b80600860008567ffffffffffffffff1667ffffffffffffffff16815260200190815260200160002060010160008467ffffffffffffffff1667ffffffffffffffff16815260200190815260200160002060006101000a81548167ffffffffffffffff021916908367ffffffffffffffff160217905550600860008467ffffffffffffffff1667ffffffffffffffff168152602001908152602001600020600001805480600101828181548183558181151161216857600301600490048160030160049004836000526020600020918201910161216791905b8082111561216357600081600090555060010161214b565b5090565b5b50505091600052602060002090600491828204019190066008025b84909190916101000a81548167ffffffffffffffff021916908367ffffffffffffffff16021790555050600960008267ffffffffffffffff1667ffffffffffffffff16815260200190815260200160002060010160008367ffffffffffffffff1667ffffffffffffffff168152602001908152602001600020805480600101828181548183558181151161225157600301600490048160030160049004836000526020600020918201910161225091905b8082111561224c576000816000905550600101612234565b5090565b5b50505091600052602060002090600491828204019190066008025b85909190916101000a81548167ffffffffffffffff021916908367ffffffffffffffff16021790555050600960008267ffffffffffffffff1667ffffffffffffffff168152602001908152602001600020600001600081819054906101000a900467ffffffffffffffff168092919060010191906101000a81548167ffffffffffffffff021916908367ffffffffffffffff160217905550505b505050565b6000600960008367ffffffffffffffff1667ffffffffffffffff16815260200190815260200160002060000160009054906101000a900467ffffffffffffffff1690505b9190505600a165627a7a72305820bd2f38c62ba4c1f6a8477d9c8a722ebb814ad54f520fdb5c580c25954f586c530029";

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

    public Future<Address> getAddressByGameId(Uint64 _gameId) {
        Function function = new Function("getAddressByGameId", 
                Arrays.<Type>asList(_gameId), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
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

    public Future<Bytes32> getAliasNameByAssetId(Uint64 _assetId) {
        Function function = new Function("getAliasNameByAssetId", 
                Arrays.<Type>asList(_assetId), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> initGameId() {
        Function function = new Function("initGameId", Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
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

    public Future<Uint64> getGameId(Address gameProvider) {
        Function function = new Function("getGameId", 
                Arrays.<Type>asList(gameProvider), 
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

    public Future<TransactionReceipt> initGameToken(Uint64 _gameId, Bytes32 alias) {
        Function function = new Function("initGameToken", Arrays.<Type>asList(_gameId, alias), Collections.<TypeReference<?>>emptyList());
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

    public Future<Address> getPlyerByAssetId(Uint64 _assetId) {
        Function function = new Function("getPlyerByAssetId", 
                Arrays.<Type>asList(_assetId), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
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
