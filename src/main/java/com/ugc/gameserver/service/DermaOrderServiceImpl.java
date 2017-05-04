package com.ugc.gameserver.service;

import com.ugc.gameserver.config.AppConfig;
import com.ugc.gameserver.domain.Derma;
import com.ugc.gameserver.domain.DermaOrder;
import com.ugc.gameserver.domain.OrderStatusEnum;
import com.ugc.gameserver.domain.UserToken;
import com.ugc.gameserver.domain.contract.Recharge;
import com.ugc.gameserver.mapper.DermaOrderMapper;
import com.ugc.gameserver.mapper.SequenceMapper;
import com.ugc.gameserver.util.Keccak;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.abi.datatypes.Address;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.web3j.tx.Contract.GAS_LIMIT;
import static org.web3j.tx.ManagedTransaction.GAS_PRICE;

/**
 * Created by fanjl on 2017/4/27.
 */
@Service
public class DermaOrderServiceImpl implements DermaOrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DermaOrderServiceImpl.class);

    @Autowired
    private SequenceMapper sequenceMapper;
    @Autowired
    private UserTokenService userTokenService;
    @Autowired
    private DermaOrderMapper dermaOrderMapper;
    @Autowired
    private AppConfig appConfig;
    private Web3j web3;

    private Recharge contract;

    private Keccak keccak = new Keccak();

    @Override
    public DermaOrder createOrder(String token, Derma derma) {
        DermaOrder dermaOrder = new DermaOrder();
        dermaOrder.setToken(token);
        dermaOrder.setDerma(derma);
        dermaOrder.setOrderId(nextId());
        dermaOrder.setStatus(OrderStatusEnum.PENDING.getId());
        dermaOrder.setSeller(appConfig.getUgAddress());
        dermaOrder.setGameId(1);
        dermaOrderMapper.insertDermaOrder(dermaOrder);
        return dermaOrder;
    }

    @Override
    public void updateOrder(int orderId, OrderStatusEnum orderStatusEnum) {
        dermaOrderMapper.updateDermaOrder(orderStatusEnum.getId(),orderId);
    }

    @Override
    public void listenContract() {
        initContract();
        LOGGER.info("init web3j end,start listen contract");
        Observable<Recharge.PayEventResponse> observable = contract.payEventObservable();
        observable.subscribe(new Action1<Recharge.PayEventResponse>() {
            @Override
            @Transactional(rollbackFor=Exception.class)
            public void call(Recharge.PayEventResponse payEventResponse) {
                Uint256 value = payEventResponse._amount;
                Address fromAddress = payEventResponse._payer;
                Address toAddress = payEventResponse._seller;
                Uint64 gameId = payEventResponse._gameId;
                Uint64 tradeId = payEventResponse._tradeId;
                DermaOrder order = getOrderById(tradeId.getValue().intValue());

                if(order.getDerma().getPrices()!=value.getValue().intValue()
                        ||order.getGameId()!=gameId.getValue().intValue()){
                    throw new RuntimeException("order amount is not equal");
                }
                updateOrder(tradeId.getValue().intValue(),OrderStatusEnum.SUCCESS);
                Optional<UserToken> userToken = userTokenService.getUserTokenByToken(fromAddress.toString());
                if(!userToken.isPresent()){
                    LOGGER.error("the token which pay order is not exists,please check");
                }
                List<String> dermas = userToken.get().getDerma();
                List<String> newDermas = new LinkedList<String>();
                newDermas.addAll(dermas);
                newDermas.add(String.valueOf(order.getDerma().getId()));
                boolean result = userTokenService.updateDerma(fromAddress.toString(),newDermas);
                LOGGER.info("update order and user derma end,result is: "+ result);
            }

        });
    }

    @Override
    public DermaOrder getOrderById(int orderId) {
        return  dermaOrderMapper.getOrderById(orderId);
    }

    @Override
    public void initContract() {
        web3 = Web3j.build(new HttpService(appConfig.getGethHttpURL()));
        Credentials credentials = null;
        try {
            credentials = WalletUtils.loadCredentials(appConfig.getWalletPassword(), appConfig.getWalletPath());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        } catch (CipherException e) {
            LOGGER.error(e.getMessage());
        }
        contract = Recharge.load(
                appConfig.getUgAddress(), web3, credentials, GAS_PRICE, GAS_LIMIT);
    }


    @Override
    public int nextId() {
        return sequenceMapper.nextId(sequenceMapper.DERMA_ORDER);
    }
}
