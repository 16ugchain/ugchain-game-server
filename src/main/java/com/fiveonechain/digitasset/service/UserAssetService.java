package com.fiveonechain.digitasset.service;

import com.fiveonechain.digitasset.domain.Asset;
import com.fiveonechain.digitasset.domain.UserAsset;

import java.util.List;
import java.util.Optional;

/**
 * Created by yuanshichao on 2016/11/21.
 */
public interface UserAssetService {

    void createDigitAsset(Asset asset);

    void lockDigitAssetShare(int ownerId, int assetId, int amount);

    void unLockDigitAssetShare(int ownerId, int assetId, int amount);

    void transferDigitAssetShare(int fromId, int toId, int assetId, int amount, int orderId);

    void setTradeBalance(int ownerId, int assetId, int amount);

    UserAsset getDigitAsset(int ownerId, int assetId);

    Optional<UserAsset> getDigitAssetOptional(int ownerId, int assetId);

    List<UserAsset> getDigitAssetListByOwner(int ownerId);

    List<UserAsset> getDigitAssetListByAsset(int assetId);

    List<UserAsset> getAvailDigitAssetListByAsset(int assetId);

    int sumTradeBalanceByAsset(int assetId);

    Optional<UserAsset> getWhollyOwnerOfAsset(int assetId, int totalAmount);

    boolean hasEnoughDigitAsset(int ownerId, int assetId, int needAmount);

    boolean hasEnoughDigitAsset(UserAsset digitAsset, int needAmount);

    boolean hasEnoughTradeBalance(UserAsset digitAsset, int needAmount);
}
