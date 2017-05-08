package com.ugc.gameserver.service;

import java.math.BigDecimal;

/**
 * Created by fanjl on 2017/5/8.
 */
public interface Web3jService {
    void init();

    void listenContract();

    int queryAssetIdByToken(String token);

    boolean isOnSell(int assetId);

    void sell(int gameId,String proveHash, BigDecimal prices,int assetId);
}
