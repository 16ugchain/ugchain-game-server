package com.fiveonechain.digitasset.service;

import com.fiveonechain.digitasset.domain.AssetOrder;
import com.fiveonechain.digitasset.domain.AssetOrderStatusEnum;

import java.util.List;

/**
 * Created by fanjl on 16/11/21.
 */
public interface AssetOrderService {
    int nextOrderId();

    int createAssetOrder(AssetOrder assetOrder);

    AssetOrder getAssetOrder(int orderId);

    List<AssetOrder> getAssetOrderListByOwner(int userId);

    List<AssetOrder> getAssetOrderListByAssetId(int assetId);

    List<AssetOrder> getAssetOrderListByStatus(AssetOrderStatusEnum status);

    void updateAssetOrderStatus(int orderId,AssetOrderStatusEnum assetOrderStatusEnum);
}
