package com.fiveonechain.digitasset.exception;

/**
 * Created by yuanshichao on 2016/11/22.
 */
public class NoEnoughDigitAssetException extends RuntimeException {

    public NoEnoughDigitAssetException() {
    }

    public NoEnoughDigitAssetException(int assetId, int userId) {
        super(String.format("No enough digit asset [assetId: %d, userId: %d]", assetId, userId));
    }
}
