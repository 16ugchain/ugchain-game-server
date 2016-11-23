package com.fiveonechain.digitasset.exception;

/**
 * Created by yuanshichao on 2016/11/22.
 */
public class NoEnoughBalanceException extends RuntimeException {

    public NoEnoughBalanceException() {
    }

    public NoEnoughBalanceException(int assetId, int userId) {
        super(String.format("No enough balance [assetId: %d, userId: %d]", assetId, userId));
    }
}
