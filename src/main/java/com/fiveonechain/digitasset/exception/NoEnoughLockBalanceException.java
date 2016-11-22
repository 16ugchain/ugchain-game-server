package com.fiveonechain.digitasset.exception;

/**
 * Created by yuanshichao on 2016/11/22.
 */
public class NoEnoughLockBalanceException extends RuntimeException {

    public NoEnoughLockBalanceException() {
    }

    public NoEnoughLockBalanceException(int assetId, int userId) {
        super(String.format("No enough locked balance [assetId: %d, userId: %d]", assetId, userId));
    }
}
