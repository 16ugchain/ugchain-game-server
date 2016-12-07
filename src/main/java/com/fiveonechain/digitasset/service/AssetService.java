package com.fiveonechain.digitasset.service;

import com.fiveonechain.digitasset.auth.UserContext;
import com.fiveonechain.digitasset.domain.Asset;
import com.fiveonechain.digitasset.domain.AssetOperation;
import com.fiveonechain.digitasset.domain.AssetStatus;
import com.fiveonechain.digitasset.domain.UserRoleEnum;

import java.util.List;
import java.util.Optional;

/**
 * Created by yuanshichao on 2016/11/16.
 */
public interface AssetService {

    int nextAssetId();

    void createAsset(Asset asset, boolean needGuar);

    Asset getAsset(int assetId);

    Optional<Asset> getAssetOptional(int assetId);

    List<Asset> getAssetListByOwner(int userId);

    List<Asset> getAssetListByGuarantee(int guarId);

    List<Asset> getAssetListByStatus(AssetStatus status);

    List<Asset> getAssetListByGuarAndStatus(int guarId, AssetStatus status);

    List<Asset> getAssetListByGuarExcludeStatus(int guarId, AssetStatus notStatus);

    void updateAssetStatusStateMachine(UserContext host, int assetId, AssetStatus newStatus);

    void updateAssetEvalInfo(Asset asset);

    void issueAsset(int assetId, String payOrder);

    boolean isAssetGuaranteed(Asset asset);

    boolean checkAssetMaturity(Asset asset);

    void updateAssetStatusAsync(int assetId, AssetStatus newStatus);

    List<AssetOperation> getAvailableOperation(AssetStatus status, UserRoleEnum role);
}

