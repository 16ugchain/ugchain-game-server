package com.ugc.gameserver.service;

import com.ugc.gameserver.config.AppConfig;
import com.ugc.gameserver.domain.DermaOrder;
import com.ugc.gameserver.domain.OrderStatusEnum;
import com.ugc.gameserver.domain.UserToken;
import com.ugc.gameserver.domain.contract.DAS;
import com.ugc.gameserver.domain.contract.Recharge;
import com.ugc.gameserver.domain.contract.Trade;
import com.ugc.gameserver.util.Web3Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import rx.Observable;
import rx.functions.Action1;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.web3j.tx.ManagedTransaction.GAS_PRICE;

/**
 * Created by fanjl on 2017/5/8.
 */
@Service
public class Web3jServiceImpl implements Web3jService {
    private static final Logger LOGGER = LoggerFactory.getLogger(Web3jServiceImpl.class);

    @Autowired
    private UserTokenService userTokenService;
    @Autowired
    private DermaOrderService dermaOrderService;
    @Autowired
    private AppConfig appConfig;

    private Web3j web3;

    private Recharge recharge;

    private Trade trade;

    private DAS das;

    private int gameId = 0;

    @Override
    public void init() {
        web3 = Web3j.build(new HttpService(appConfig.getGethHttpURL()));
        Credentials credentials = null;
        try {
            credentials = WalletUtils.loadCredentials(appConfig.getWalletPassword(), appConfig.getWalletPath());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        } catch (CipherException e) {
            LOGGER.error(e.getMessage());
        }
        recharge = Recharge.load(
                appConfig.getRechargeAddress(), web3, credentials, GAS_PRICE, BigInteger.valueOf(4_700_000));
        trade = Trade.load(appConfig.getTradeAddress(),web3,credentials,GAS_PRICE, BigInteger.valueOf(4_700_000));
        das = DAS.load(appConfig.getDasAddress(),web3,credentials,GAS_PRICE,BigInteger.valueOf(4_700_000));

        if(credentials!=null){
            LOGGER.info("init web3j and load contract end, use address :"+credentials.getAddress());
        }else{
            LOGGER.warn("credentials is null ! contract can only do query operation");
        }
        das.initGameId();
        int i = 0;
        while (true){
            try {
                Address sellerAddress = new Address(appConfig.getSellerAddress());
                gameId = das.getGameId(sellerAddress).get().getValue().intValue();
                if(gameId !=0 ){
                    LOGGER.info("get gameId success gameId:"+gameId);
                    break;
                }
                LOGGER.info("wait for register gameId ,try get gameId times:"+i);
                i++;
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                LOGGER.error("can't get game id");
            } catch (ExecutionException e) {
                LOGGER.error("can't get game id");
            }

        }
    }

    @Override
    public int getGameId() {
        return this.gameId;
    }

    @Override
    public void listenContract() {
        LOGGER.info("init web3j end,start listen contract--> address:"+appConfig.getRechargeAddress());
        Observable<Recharge.PayEventResponse> observable = recharge.payEventObservable();
        observable.subscribe(new Action1<Recharge.PayEventResponse>() {
            @Override
            @Transactional(rollbackFor=Exception.class)
            public void call(Recharge.PayEventResponse payEventResponse) {
                Uint256 value = payEventResponse._amount;
                Address fromAddress = payEventResponse._payer;
                Address toAddress = payEventResponse._seller;
                Uint64 gameId = payEventResponse._gameId;
                Uint64 tradeId = payEventResponse._tradeId;
                DermaOrder order = dermaOrderService.getOrderById(tradeId.getValue().intValue());

                if(order.getDerma().getPrices()!=value.getValue().intValue()
                        ||order.getGameId()!=gameId.getValue().intValue()){
                    throw new RuntimeException("order amount is not equal");
                }
                dermaOrderService.updateOrder(tradeId.getValue().intValue(), OrderStatusEnum.SUCCESS);
                Optional<UserToken> userToken = userTokenService.getUserTokenByToken(order.getToken());
                if(!userToken.isPresent()){
                    throw new RuntimeException("the token which pay order is not exists,please check");
                }
                if(!toAddress.toString().equals(appConfig.getSellerAddress())){
                    throw new RuntimeException("seller address is not equal: "+toAddress.toString());
                }
                List<String> dermas = userToken.get().getDerma();
                List<String> newDermas = new LinkedList<String>();
                newDermas.addAll(dermas);
                newDermas.add(String.valueOf(order.getDerma().getId()));
                boolean result = userTokenService.updateDerma(order.getToken(),newDermas);
                LOGGER.info("update order and user derma end,order token:"+order.getToken()+"result is: "+ result);
            }

        });
    }

    @Override
    public int queryAssetIdByToken(String token) {
        Future<Uint64> assetIdFuture = das.getIndexByToken(Web3Util.toBytes32(token));
        int assetId = 0;
        try {
            assetId = assetIdFuture.get().getValue().intValue();
        } catch (InterruptedException e) {
            LOGGER.error("InterruptedException occured while query asset id",e);
        } catch (ExecutionException e) {
            LOGGER.error("ExecutionException occured while query asset id",e);
        }
        return assetId;
    }

    @Override
    public String queryTokenByAssetId(int assetId) {
        Future<Bytes32> tokenFuture = das.getTokenByAssetId(Web3Util.toUint64(assetId));
        String token = "0x";
        try {
            token += Web3Util.bytes32ToHexString(tokenFuture.get());
        } catch (InterruptedException e) {
            LOGGER.error("InterruptedException occured while query token ",e);
        } catch (ExecutionException e) {
            LOGGER.error("ExecutionException occured while query token ",e);
        }
        return token.toLowerCase();
    }

    @Override
    public boolean isOnSell(int assetId) {
        boolean result = false;
        try {
             result = das.getSellingStatusByAssetId(Web3Util.toUint64(assetId)).get().getValue();
        } catch (InterruptedException e) {
            LOGGER.error("InterruptedException occured while query is on sell,assetId"+assetId);
        } catch (ExecutionException e) {
            LOGGER.error("ExecutionException occured while query is on sell,assetId"+assetId);
        }
        return result;
    }

    @Override
    public void sell(int gameId,String proveHash, BigDecimal prices, int assetId) {
        trade.sell(Web3Util.toUint64(gameId),Web3Util.toUint64(assetId),
                Web3Util.toUint256(prices.intValue()), Web3Util.toBytes32(proveHash));
        LOGGER.info("send transcation end , assetId:"+assetId);
    }

}
