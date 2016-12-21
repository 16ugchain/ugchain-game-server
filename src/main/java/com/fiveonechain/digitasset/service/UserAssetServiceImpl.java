package com.fiveonechain.digitasset.service;

import com.fiveonechain.digitasset.auth.UserContext;
import com.fiveonechain.digitasset.domain.*;
import com.fiveonechain.digitasset.domain.result.ErrorInfo;
import com.fiveonechain.digitasset.exception.DigitAssetNotFoundException;
import com.fiveonechain.digitasset.exception.DigitAssetTransferException;
import com.fiveonechain.digitasset.exception.NoEnoughBalanceException;
import com.fiveonechain.digitasset.exception.NoEnoughTradeBalanceException;
import com.fiveonechain.digitasset.mapper.UserAssetMapper;
import com.fiveonechain.digitasset.mapper.UserAssetRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by yuanshichao on 2016/11/21.
 */

@Component
public class UserAssetServiceImpl implements UserAssetService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAssetServiceImpl.class);

    @Autowired
    private UserAssetMapper userAssetMapper;

    @Autowired
    private UserAssetRecordMapper userAssetRecordMapper;

    @Override
    @Transactional
    public void createDigitAsset(Asset asset) {
        // TODO contract
        int contractId = 1;

        UserAsset userAsset = new UserAsset();
        userAsset.setAssetId(asset.getAssetId());
        userAsset.setUserId(asset.getUserId());
        userAsset.setBalance(asset.getEvalValue());
        userAsset.setTradeBalance(0);
        userAsset.setLockBalance(0);
        userAsset.setContractId(1);
        userAssetMapper.insert(userAsset);

        UserAssetRecord record = new UserAssetRecord();
        record.setAssetId(asset.getAssetId());
        record.setUserId(asset.getUserId());
        record.setPeerId(asset.getGuarId());
        record.setAmount(asset.getEvalValue());
        record.setContractId(1);
        record.setOperation(UserAssetOperationEnum.ISSUE.getCode());
        userAssetRecordMapper.insert(record);
    }

    @Override
    public void createUserAsset(int assetId, int userId) {
        UserAsset userAsset = new UserAsset();
        userAsset.setAssetId(assetId);
        userAsset.setUserId(userId);
        userAsset.setBalance(0);
        userAsset.setTradeBalance(0);
        userAsset.setLockBalance(0);
        userAsset.setContractId(1);
        userAssetMapper.insert(userAsset);
    }

    @Override
    @Transactional
    public void lockDigitAssetShare(int ownerId, int assetId, int amount) {
        UserAsset userAsset = userAssetMapper.selectForUpdate(assetId, ownerId);
        if (userAsset == null) {
            throw new DigitAssetNotFoundException(assetId, ownerId);
        }
        if (userAsset.getTradeBalance() < amount) {
            throw new NoEnoughTradeBalanceException(assetId, ownerId);
        }
        userAssetMapper.lockTradeBalance(amount, assetId, ownerId);
    }

    @Override
    @Transactional
    public void unLockDigitAssetShare(int ownerId, int assetId, int amount) {
        UserAsset userAsset = userAssetMapper.selectForUpdate(assetId, ownerId);
        if (userAsset == null) {
            throw new DigitAssetNotFoundException(assetId, ownerId);
        }
        if (userAsset.getLockBalance() < amount) {
            throw new NoEnoughTradeBalanceException(assetId, ownerId);
        }
        userAssetMapper.unLockTradeBalance(amount, assetId, ownerId);
    }

    @Override
    @Transactional
    public void transferDigitAssetShare(int fromId, int toId, int assetId, int amount, int orderId) {
        if (fromId == toId) {
            throw new DigitAssetTransferException("Can not transfer to self");
        }
        UserAsset from, to;
        if (fromId < toId) {
            from = userAssetMapper.selectForUpdate(assetId, fromId);
            to = userAssetMapper.selectForUpdate(assetId, toId);
        } else {
            to = userAssetMapper.selectForUpdate(assetId, toId);
            from = userAssetMapper.selectForUpdate(assetId, fromId);
        }

        if (from == null) {
            throw new DigitAssetNotFoundException(assetId, fromId);
        }
        if (to == null) {
            throw new DigitAssetNotFoundException(assetId, toId);
        }

        if (from.getBalance() < amount) {
            throw new NoEnoughBalanceException(assetId, fromId);
        }

        // TODO check orderId
        // TODO contract

        int contractId = 1;

        userAssetMapper.withdrawBalance(amount, assetId, fromId, 1);
        userAssetMapper.depositBalance(amount, assetId, toId, 1);

        UserAssetRecord record = new UserAssetRecord();
        record.setAssetId(assetId);
        record.setUserId(fromId);
        record.setPeerId(toId);
        record.setAmount(amount);
        record.setContractId(contractId);
        record.setOperation(UserAssetOperationEnum.TRANSFER_OUT.getCode());
        userAssetRecordMapper.insert(record);

        record.setUserId(toId);
        record.setPeerId(fromId);
        record.setAmount(amount);
        record.setContractId(contractId);
        record.setOperation(UserAssetOperationEnum.TRANSFER_IN.getCode());
        userAssetRecordMapper.insert(record);

    }

    @Override
    @Transactional
    public void setTradeBalance(int ownerId, int assetId, int amount) {
        UserAsset userAsset = userAssetMapper.selectForUpdate(assetId, ownerId);
        if (userAsset == null) {
            throw new DigitAssetNotFoundException(assetId, ownerId);
        }

        if (userAsset.getBalance() - userAsset.getLockBalance() < amount) {
            throw new NoEnoughTradeBalanceException(assetId, ownerId);
        }

        userAssetMapper.updateTradeBalance(amount, assetId, ownerId);

    }

    @Override
    public UserAsset getDigitAsset(int ownerId, int assetId) {
        UserAsset userAsset = userAssetMapper.select(assetId, ownerId);
        if (userAsset == null) {
            throw new DigitAssetNotFoundException(assetId, ownerId);
        }
        return userAsset;
    }

    @Override
    public Optional<UserAsset> getDigitAssetOptional(int ownerId, int assetId) {
        UserAsset userAsset = userAssetMapper.select(assetId, ownerId);
        return Optional.ofNullable(userAsset);
    }

    @Override
    public List<UserAsset> getDigitAssetListByOwner(int ownerId) {
        return userAssetMapper.selectByUserId(ownerId);
    }

    @Override
    public List<UserAsset> getAvailDigitAssetListByAsset(int assetId) {
        return userAssetMapper.selectAvailByAssetIdOrderByTrade(assetId);
    }

    @Override
    public int sumTradeBalanceByAsset(int assetId) {
        Integer sum = userAssetMapper.sumTradeBalanceByAssetId(assetId);
        if (sum == null) {
            LOGGER.error("{} Asset {} NO Digit Asset",ErrorInfo.SERVER_ERROR, assetId);
            sum = 0;
        }
        return sum;
    }

    @Override
    public List<UserAsset> getDigitAssetListByAsset(int assetId) {
        return userAssetMapper.selectByAssetId(assetId);
    }

    @Override
    public Optional<UserAsset> getWhollyOwnerOfAsset(int assetId, int totalAmount) {
        UserAsset userAsset = userAssetMapper.selectByAssetIdAndBalance(assetId, totalAmount);
        return Optional.ofNullable(userAsset);
    }

    @Override
    public boolean hasEnoughDigitAsset(int ownerId, int assetId, int needAmount) {
        UserAsset digitAsset = userAssetMapper.select(assetId, ownerId);
        return hasEnoughDigitAsset(digitAsset, needAmount);
    }

    @Override
    public boolean hasEnoughDigitAsset(UserAsset digitAsset, int needAmount) {
        if (digitAsset.getBalance() - digitAsset.getLockBalance() >= needAmount) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hasEnoughTradeBalance(UserAsset digitAsset, int needAmount) {
        if (digitAsset.getTradeBalance() >= needAmount) {
            return true;
        }
        return false;
    }
}
