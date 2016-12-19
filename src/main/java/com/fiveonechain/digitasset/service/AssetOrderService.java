package com.fiveonechain.digitasset.service;

import com.fiveonechain.digitasset.auth.UserContext;
import com.fiveonechain.digitasset.domain.AssetOrder;
import com.fiveonechain.digitasset.domain.AssetOrderOperation;
import com.fiveonechain.digitasset.domain.AssetOrderStatusEnum;
import com.fiveonechain.digitasset.domain.UserRoleEnum;

import java.util.List;

/**
 * Created by fanjl on 16/11/21.
 */
public interface AssetOrderService {
    int nextOrderId();

    void createAssetOrder(AssetOrder assetOrder);

    AssetOrder getAssetOrder(int orderId);

    List<AssetOrder> getAssetOrderListByOwner(int userId);

    List<AssetOrder> getAssetOrderListByBuyerId(int buyerId);

    List<AssetOrder> getAssetOrderListByAssetId(int assetId);

    List<AssetOrder> getAssetOrderListByStatus(AssetOrderStatusEnum status);

    List<AssetOrderOperation> getOperationByStatusAndRole(AssetOrderStatusEnum status, UserRoleEnum userRoleEnum);

    void confirmOrderApply(UserContext host, AssetOrder order);

    void setOrderPayExpiration(UserContext host, AssetOrder order);

    void finishOrderSuccess(UserContext host, AssetOrder order);

    void finishOrderFailed(UserContext host, AssetOrder order);

    void updateAssetOrderStatus(int orderId, AssetOrderStatusEnum assetOrderStatusEnum);

    boolean checkOrderApplyExpired(AssetOrder order);

    boolean checkOrderPayExpired(AssetOrder order);

    boolean checkOrderPayConfirmExpired(AssetOrder order);

}
