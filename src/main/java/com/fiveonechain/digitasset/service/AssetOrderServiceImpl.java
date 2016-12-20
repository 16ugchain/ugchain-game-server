package com.fiveonechain.digitasset.service;

import com.fiveonechain.digitasset.auth.UserContext;
import com.fiveonechain.digitasset.config.AppConfig;
import com.fiveonechain.digitasset.domain.AssetOrder;
import com.fiveonechain.digitasset.domain.AssetOrderOperation;
import com.fiveonechain.digitasset.domain.AssetOrderStatusEnum;
import com.fiveonechain.digitasset.domain.UserRoleEnum;
import com.fiveonechain.digitasset.domain.result.OrderCenterCmd;
import com.fiveonechain.digitasset.exception.AssetOrderException;
import com.fiveonechain.digitasset.mapper.AssetOrderMapper;
import com.fiveonechain.digitasset.mapper.SequenceMapper;
import com.google.common.collect.Lists;
import com.fiveonechain.digitasset.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.time.LocalDateTime;
import java.util.Date;
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

    @Autowired
    private UserAssetService userAssetService;

    @Autowired
    private AppConfig appConfig;

    @Override
    public int nextOrderId() {
        return sequenceMapper.nextId(sequenceMapper.ASSET_ORDER);
    }

    @Override
    public void createAssetOrder(AssetOrder assetOrder) {
        assetOrderMapper.insertOrder(assetOrder);
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
    public List<AssetOrderOperation> getOperationByStatusAndRole(AssetOrderStatusEnum status, boolean isBuyer) {
        List<AssetOrderOperation> assetOrderOperations = Collections.emptyList();
        if(isBuyer){
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
        } else {
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
    @Transactional
    public void confirmOrderApply(UserContext host, AssetOrder order) {
        userAssetService.lockDigitAssetShare(order.getUserId(), order.getAssetId(), order.getAmount());
        userAssetService.createUserAsset(order.getAssetId(), order.getBuyerId());
        updateAssetOrderStatus(order.getOrderId(), AssetOrderStatusEnum.OBLIGATIONS);
    }

    @Override
    @Transactional
    public void setOrderPayExpiration(UserContext host, AssetOrder order) {
        userAssetService.unLockDigitAssetShare(order.getUserId(), order.getAssetId(), order.getAmount());
        updateAssetOrderStatus(order.getOrderId(), AssetOrderStatusEnum.OBLIGATIONS_OUT_TIME);
    }

    @Override
    @Transactional
    public void finishOrderSuccess(UserContext host, AssetOrder order) {
        userAssetService.transferDigitAssetShare(
                order.getUserId(), order.getBuyerId(), order.getAssetId(), order.getAmount(), order.getOrderId());
        updateAssetOrderStatus(order.getOrderId(), AssetOrderStatusEnum.TRADE_SUCCESS);
    }

    @Override
    @Transactional
    public void finishOrderFailed(UserContext host, AssetOrder order) {
        userAssetService.unLockDigitAssetShare(order.getUserId(), order.getAssetId(), order.getAmount());
        updateAssetOrderStatus(order.getOrderId(), AssetOrderStatusEnum.TRADE_FAILED);
    }

    @Override
    @Transactional
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

    @Override
    public boolean checkOrderApplyExpired(AssetOrder order) {
        return isAfter(order.getUpdateTime(), appConfig.getOrderApplyExpireTime());
    }

    @Override
    public boolean checkOrderPayExpired(AssetOrder order) {
        return isAfter(order.getUpdateTime(), appConfig.getOrderPayExpireTime());
    }

    @Override
    public boolean checkOrderPayConfirmExpired(AssetOrder order) {
        return isAfter(order.getUpdateTime(), appConfig.getOrderPayConfirmExpireTime());
    }

    private boolean isAfter(Date lastDate, int deltaInDays) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime last = DateUtil.toLocalDateTime(lastDate);
        return now.isAfter(last.plusDays(deltaInDays));
    }
}
