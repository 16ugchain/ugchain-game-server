package com.fiveonechain.digitasset.exception;

/**
 * Created by yuanshichao on 2016/11/18.
 */
public class DigitAssetNotFoundException extends RuntimeException {

    public DigitAssetNotFoundException() {
    }

    public DigitAssetNotFoundException(int assetId, int userId) {
        super(String.format("Digit Asset not found [assetId: %d, userId: %d]", assetId, userId));
    }

}
