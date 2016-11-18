package com.fiveonechain.digitasset.exception;

/**
 * Created by yuanshichao on 2016/11/18.
 */
public class AssetNotFoundException extends RuntimeException {

    public AssetNotFoundException() {
    }

    public AssetNotFoundException(int assetId) {
        super("Asset not found [assetId: " + assetId + "]");
    }

    public AssetNotFoundException(String message) {
        super(message);
    }
}
