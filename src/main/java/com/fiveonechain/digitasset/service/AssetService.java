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

    void issueAsset(UserContext host, Asset asset);

    Optional<Asset> getAssetOptional(int assetId);

    List<Asset> getAssetListByOwner(int userId);

    List<Asset> getAssetListByStatus(AssetStatus status);

    List<Asset> getAssetListByGuarAndStatus(int guarId, AssetStatus status);

    List<Asset> getAssetListByUserIdAndStatus(int userId, AssetStatus status);

    List<Asset> getAssetListByGuarAndStatusList(int guarId, List<AssetStatus> status);

    List<Asset> getNoGuarAssetListByIssuerAndStatusList(int userId, List<AssetStatus> status);

    List<Asset> getAssetListByGuarExcludeStatus(int guarId, AssetStatus notStatus);

    void updateAssetStatusStateMachine(UserContext host, int assetId, AssetStatus newStatus);

    void updateAssetEvalInfo(Asset asset);

    void updateAssetIssueInfo(Asset asset);

    boolean isAssetGuaranteed(Asset asset);

    boolean isAssetGuaranteed(Asset asset, int guarId);

    boolean isAssetIssuedByUser(Asset asset, int userId);

    boolean checkAssetMaturity(Asset asset);

    boolean checkAssetExpireToIssue(Asset asset);

    void updateAssetStatusAsync(int assetId, AssetStatus newStatus);

    List<AssetOperation> getAvailableOperation(AssetStatus status, UserRoleEnum role);

    void applyAssetDelivery(UserContext host, Asset asset);
}
