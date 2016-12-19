package com.fiveonechain.digitasset.service;

import com.fiveonechain.digitasset.domain.AssetOrder;
import com.fiveonechain.digitasset.domain.AssetOrderOperation;
import com.fiveonechain.digitasset.domain.AssetOrderStatusEnum;
import com.fiveonechain.digitasset.domain.UserRoleEnum;
import com.fiveonechain.digitasset.domain.result.OrderCenterCmd;
import com.fiveonechain.digitasset.exception.AssetOrderException;
import com.fiveonechain.digitasset.mapper.AssetOrderMapper;
import com.fiveonechain.digitasset.mapper.SequenceMapper;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Created by fanjl on 16/11/21.
 */
@Component
public class AssetOrderServiceImpl implements AssetOrderService {
    @Autowired
    private SequenceMapper sequenceMapper;
    @Autowired
    private AssetOrderMapper assetOrderMapper;
    @Override
    public int nextOrderId() {
        return sequenceMapper.nextId(sequenceMapper.ASSET_ORDER);
    }

    @Override
    public int createAssetOrder(AssetOrder assetOrder) {
        int id = nextOrderId();
        assetOrder.setOrderId(id);
        return assetOrderMapper.insertOrder(assetOrder);
    }

    @Override
    public AssetOrder getAssetOrder(int orderId) {
        return assetOrderMapper.findByOrderId(orderId);
    }

    @Override
    public List<AssetOrder> getAssetOrderListByOwner(int userId) {
        return assetOrderMapper.findListByUsreId(userId);
    }

    @Override
    public List<AssetOrder> getAssetOrderListByBuyerId(int buyerId) {
        return assetOrderMapper.getAssetOrderListByBuyerId(buyerId);
    }

    @Override
    public List<AssetOrder> getAssetOrderListByAssetId(int assetId) {
        return assetOrderMapper.findListByAssetId(assetId);
    }

    @Override
    public List<AssetOrder> getAssetOrderListByStatus(AssetOrderStatusEnum status) {
        return assetOrderMapper.findListByStatus(status.getId());
    }

    @Override
    public List<AssetOrderOperation> getOperationByStatusAndRole( AssetOrderStatusEnum status, UserRoleEnum userRoleEnum) {
        List<AssetOrderOperation> assetOrderOperations = Collections.emptyList();
        if(userRoleEnum.getId()==UserRoleEnum.USER_ASSIGNEE.getId()){
            if(status.getId() == AssetOrderStatusEnum.APPLY.getId()){
                assetOrderOperations = Lists.newArrayList(AssetOrderOperation.VIEW);
                return assetOrderOperations;
            }else if(status.getId() == AssetOrderStatusEnum.OBLIGATIONS.getId()){
                assetOrderOperations = Lists.newArrayList(AssetOrderOperation.PAY);
                return assetOrderOperations;
            }else if(status.getId() == AssetOrderStatusEnum.COMPLETE_OUT_TIME.getId()){
                assetOrderOperations = Lists.newArrayList(AssetOrderOperation.APPEAL);
                return assetOrderOperations;
            }
        }else if(userRoleEnum.getId()==UserRoleEnum.USER_PUBLISHER.getId()){
            if(status.getId() == AssetOrderStatusEnum.APPLY.getId()){
                assetOrderOperations = Lists.newArrayList(AssetOrderOperation.AGREE_APPLY,AssetOrderOperation.REJECT_APPLY);
                return assetOrderOperations;
            }else if(status.getId() == AssetOrderStatusEnum.OBLIGATIONS_DONE.getId()){
                assetOrderOperations = Lists.newArrayList(AssetOrderOperation.COMPLETED);
                return assetOrderOperations;
            }else {
                assetOrderOperations = Lists.newArrayList(AssetOrderOperation.VIEW);
                return assetOrderOperations;
            }
        }
        return assetOrderOperations;

    }

    @Override
    public void updateAssetOrderStatus(int orderId, AssetOrderStatusEnum newStatus) {
        Integer curStatusCode = assetOrderMapper.selectStatusForUpdate(orderId);
        if (curStatusCode == null) {
            throw new AssetOrderException(orderId);
        }

        AssetOrderStatusEnum curStatus = AssetOrderStatusEnum.fromValue(curStatusCode);
        if (newStatus == AssetOrderStatusEnum.OBLIGATIONS) {
            // TODO check user role
            // TODO check amount from user_asset
            if (curStatus != AssetOrderStatusEnum.APPLY) {
                throw new AssetOrderException(curStatus, newStatus);
            }
        } else if (newStatus == AssetOrderStatusEnum.APPLY_REJECT) {
            // TODO check user role
            if (curStatus != AssetOrderStatusEnum.APPLY) {
                throw new AssetOrderException(curStatus, newStatus);
            }
        } else if (newStatus == AssetOrderStatusEnum.APPLY_OUT_TIME) {
            // TODO check user role
            if (curStatus != AssetOrderStatusEnum.APPLY) {
                throw new AssetOrderException(curStatus, newStatus);
            }
        } else if (newStatus == AssetOrderStatusEnum.OBLIGATIONS_OUT_TIME) {
            if (curStatus != AssetOrderStatusEnum.OBLIGATIONS) {
                throw new AssetOrderException(curStatus, newStatus);
            }
        } else if (newStatus == AssetOrderStatusEnum.OBLIGATIONS_DONE) {
            // TODO check user role
            if (curStatus != AssetOrderStatusEnum.OBLIGATIONS) {
                throw new AssetOrderException(curStatus, newStatus);
            }
        } else if (newStatus == AssetOrderStatusEnum.COMPLETE_OUT_TIME) {
            // TODO check user role
            if (curStatus != AssetOrderStatusEnum.OBLIGATIONS_DONE) {
                throw new AssetOrderException(curStatus, newStatus);
            }
        } else if (newStatus == AssetOrderStatusEnum.TRADE_FAILED) {
            // TODO check user role
            if (curStatus != AssetOrderStatusEnum.COMPLETE_OUT_TIME) {
                throw new AssetOrderException(curStatus, newStatus);
            }
        }else if (newStatus == AssetOrderStatusEnum.TRADE_SUCCESS) {
            // TODO check user role
            if (curStatus != AssetOrderStatusEnum.COMPLETE_OUT_TIME
                    && curStatus != AssetOrderStatusEnum.OBLIGATIONS_DONE) {
                throw new AssetOrderException(curStatus, newStatus);
            }
        } else if (newStatus == curStatus) {
            //nothing
            return;
        } else {
            throw new AssetOrderException(curStatus, newStatus);
        }

        assetOrderMapper.updateStatusByOrderId(newStatus.getId(), orderId);
    }

}
