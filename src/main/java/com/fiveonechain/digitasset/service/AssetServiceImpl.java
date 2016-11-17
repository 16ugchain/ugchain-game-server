package com.fiveonechain.digitasset.service;

import com.fiveonechain.digitasset.domain.Asset;
import com.fiveonechain.digitasset.domain.AssetStatus;
import com.fiveonechain.digitasset.mapper.AssetMapper;
import com.fiveonechain.digitasset.mapper.SequenceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by yuanshichao on 2016/11/17.
 */

@Component
public class AssetServiceImpl implements AssetService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssetServiceImpl.class);

    private static final int DUMMY_GUAR_ID = -1;

    @Autowired
    private SequenceMapper seqMapper;

    @Autowired
    private AssetMapper assetMapper;

    @Override
    public int nextAssetId() {
        return seqMapper.nextId(SequenceMapper.ASSET);
    }

    @Override
    public void createAsset(Asset asset, boolean needGuar) {
        int assetId = nextAssetId();
        asset.setAssetId(assetId);
        asset.setStatus(AssetStatus.WAIT_EVALUATE.getCode());
        if (!needGuar) {
            asset.setGuarId(DUMMY_GUAR_ID);
            asset.setFee(asset.getValue());
        }
        assetMapper.insert(asset);
    }

    @Override
    public Asset getAsset(int assetId) {
        return assetMapper.select(assetId);
    }

    @Override
    public List<Asset> getAssetListByOwner(int userId) {
        return null;
    }

    @Override
    public List<Asset> getAssetListByGuarantee(int guarId) {
        return null;
    }

    @Override
    public void updateAssetStatus(AssetStatus status) {

    }

    @Override
    public List<Asset> getAssetListByStatus(AssetStatus status) {
        return null;
    }
}

