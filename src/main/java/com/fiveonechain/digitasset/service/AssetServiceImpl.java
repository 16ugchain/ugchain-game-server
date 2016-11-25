package com.fiveonechain.digitasset.service;

import com.fiveonechain.digitasset.auth.UserContext;
import com.fiveonechain.digitasset.domain.Asset;
import com.fiveonechain.digitasset.domain.AssetRecord;
import com.fiveonechain.digitasset.domain.AssetStatus;
import com.fiveonechain.digitasset.exception.AssetNotFoundException;
import com.fiveonechain.digitasset.exception.AssetStatusTransferException;
import com.fiveonechain.digitasset.mapper.AssetMapper;
import com.fiveonechain.digitasset.mapper.AssetRecordMapper;
import com.fiveonechain.digitasset.mapper.SequenceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by yuanshichao on 2016/11/17.
 */

@Component
public class AssetServiceImpl implements AssetService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssetServiceImpl.class);

    private static final int DUMMY_GUAR_ID = -1;

    private static final BigDecimal FEE_RATE = new BigDecimal("0.001");

    @Autowired
    private SequenceMapper seqMapper;

    @Autowired
    private AssetMapper assetMapper;

    @Autowired
    private AssetRecordMapper assetRecordMapper;

    @Override
    public int nextAssetId() {
        return seqMapper.nextId(SequenceMapper.ASSET);
    }

    @Override
    public void createAsset(Asset asset, boolean needGuar) {
        int assetId = nextAssetId();
        asset.setAssetId(assetId);
        if (needGuar) {
            asset.setStatus(AssetStatus.WAIT_EVALUATE.getCode());
        } else {
            asset.setGuarId(DUMMY_GUAR_ID);
            asset.setFee(calculateFee(asset.getValue()));
            asset.setStatus(AssetStatus.PASS_EVALUATE.getCode());
        }
        assetMapper.insert(asset);
    }

    private int calculateFee(int value) {
        return FEE_RATE.multiply(new BigDecimal(value)).intValue();
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
    @Transactional
    public void updateAssetStatusStateMachine(UserContext host, int assetId, AssetStatus newStatus) {
        Integer curStatusCode = assetMapper.selectStatusForUpdate(assetId);
        if (curStatusCode == null) {
            throw new AssetNotFoundException(assetId);
        }

        AssetStatus curStatus = AssetStatus.fromValue(curStatusCode);
        if (newStatus == AssetStatus.PASS_EVALUATE) {
            // TODO check user role
            if (curStatus != AssetStatus.WAIT_EVALUATE) {
                throw new AssetStatusTransferException(curStatus, newStatus);
            }
        } else if (newStatus == AssetStatus.REJECT_EVALUATE) {
            // TODO check user role
            if (curStatus != AssetStatus.WAIT_EVALUATE) {
                throw new AssetStatusTransferException(curStatus, newStatus);
            }
        } else if (newStatus == AssetStatus.ISSUE) {
            // TODO check user role
            // TODO check pay order
            if ((curStatus != AssetStatus.PASS_EVALUATE)
                    && (curStatus != AssetStatus.APPLY_DELIVERY)
                    && (curStatus != AssetStatus.FROZEN)) {
                throw new AssetStatusTransferException(curStatus, newStatus);
            }
        } else if (newStatus == AssetStatus.EXPIRE_TO_ISSUE) {
            if (curStatus != AssetStatus.PASS_EVALUATE) {
                throw new AssetStatusTransferException(curStatus, newStatus);
            }
        } else if (newStatus == AssetStatus.APPLY_DELIVERY) {
            // TODO check user role
            if (curStatus != AssetStatus.ISSUE) {
                throw new AssetStatusTransferException(curStatus, newStatus);
            }
        } else if (newStatus == AssetStatus.DELIVERY) {
            if (curStatus != AssetStatus.APPLY_DELIVERY) {
                throw new AssetStatusTransferException(curStatus, newStatus);
            }
        } else if (newStatus == AssetStatus.FROZEN) {
            // TODO check user role
            if (curStatus != AssetStatus.ISSUE) {
                throw new AssetStatusTransferException(curStatus, newStatus);
            }
        } else if (newStatus == AssetStatus.MATURITY) {
            // TODO check user role
            if (curStatus != AssetStatus.ISSUE) {
                throw new AssetStatusTransferException(curStatus, newStatus);
            }
        } else if (newStatus == AssetStatus.CLEAR) {
            if (curStatus != AssetStatus.MATURITY) {
                throw new AssetStatusTransferException(curStatus, newStatus);
            }
        } else if (newStatus == curStatus) {
            //nothing
            return;
        } else {
            throw new AssetStatusTransferException(curStatus, newStatus);
        }

        assetMapper.updateStatusByAssetId(newStatus.getCode(), assetId);
        AssetRecord record = new AssetRecord();
        record.setUserId(host.getUserId());
        record.setAssetId(assetId);
        record.setStatus(newStatus.getCode());
        assetRecordMapper.insert(record);
    }

    @Override
    public List<Asset> getAssetListByStatus(AssetStatus status) {
        return null;
    }

    @Override
    public void updateAssetEvalInfo(Asset asset) {
        assetMapper.updateAssetEvalInfoByAssetId(asset);
    }

    @Override
    public void issueAsset(int assetId, String payOrder) {
        Asset asset = getAsset(assetId);
        if (asset == null) {
            throw new AssetNotFoundException(assetId);
        }

        // TODO check pay order



    }
}

