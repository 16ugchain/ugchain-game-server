package com.fiveonechain.digitasset.exception;

/**
 * Created by yuanshichao on 2016/11/22.
 */
public class NoEnoughTradeBalanceException extends RuntimeException {

    public NoEnoughTradeBalanceException() {
    }

    public NoEnoughTradeBalanceException(int assetId, int userId) {
        super(String.format("No enough trade balance [assetId: %d, userId: %d]", assetId, userId));
    }
}
