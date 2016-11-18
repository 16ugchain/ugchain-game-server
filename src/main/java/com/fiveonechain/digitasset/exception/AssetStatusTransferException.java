package com.fiveonechain.digitasset.exception;

import com.fiveonechain.digitasset.domain.AssetStatus;

/**
 * Created by yuanshichao on 2016/11/18.
 */
public class AssetStatusTransferException extends RuntimeException {

    public AssetStatusTransferException(AssetStatus curStatus, AssetStatus newStatus) {
        super("Can not transfer from [" + curStatus + "] to [" + newStatus + "]");
    }
}
