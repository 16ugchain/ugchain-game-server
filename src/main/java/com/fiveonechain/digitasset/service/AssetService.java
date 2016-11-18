package com.fiveonechain.digitasset.service;

import com.fiveonechain.digitasset.domain.Asset;
import com.fiveonechain.digitasset.domain.AssetStatus;

import java.util.List;

/**
 * Created by yuanshichao on 2016/11/16.
 */
public interface AssetService {

    int nextAssetId();

    void createAsset(Asset asset, boolean needGuar);

    Asset getAsset(int assetId);

    List<Asset> getAssetListByOwner(int userId);

    List<Asset> getAssetListByGuarantee(int guarId);

    List<Asset> getAssetListByStatus(AssetStatus status);

    void updateAssetStatusStateMachine(int assetId, AssetStatus newStatus);

    void updateAssetEvalInfo(Asset asset);

    void issueAsset(int assetId, String payOrder);

}

