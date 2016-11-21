package com.fiveonechain.digitasset.exception;

import com.fiveonechain.digitasset.domain.AssetOrderStatusEnum;

/**
 * Created by fanjl on 16/11/21.
 */
public class AssetOrderException extends RuntimeException {
    public AssetOrderException() {
    }

    public AssetOrderException(int orderId) {
        super("Asset Order not found [orderId: " + orderId + "]");
    }

    public AssetOrderException(String message) {
        super(message);
    }

    public AssetOrderException(AssetOrderStatusEnum curStatus, AssetOrderStatusEnum newStatus) {
        super("Can not transfer from [" + curStatus + "] to [" + newStatus + "]");
    }
}
