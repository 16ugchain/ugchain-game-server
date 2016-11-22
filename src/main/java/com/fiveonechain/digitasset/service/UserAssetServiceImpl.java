package com.fiveonechain.digitasset.service;

import com.fiveonechain.digitasset.domain.Asset;
import com.fiveonechain.digitasset.domain.UserAsset;
import com.fiveonechain.digitasset.exception.DigitAssetNotFoundException;
import com.fiveonechain.digitasset.exception.DigitAssetTransferException;
import com.fiveonechain.digitasset.exception.NoEnoughBalanceException;
import com.fiveonechain.digitasset.exception.NoEnoughTradeBalanceException;
import com.fiveonechain.digitasset.mapper.UserAssetMapper;
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

    @Override
    public void createDigitAsset(Asset asset) {

        UserAsset userAsset = new UserAsset();
        userAsset.setAssetId(asset.getAssetId());
        userAsset.setUserId(asset.getUserId());
        userAsset.setBalance(asset.getEvalValue());
        userAsset.setTradeBalance(0);
        userAsset.setLockBalance(0);

        // TODO contract
        userAsset.setContractId(0);


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

        userAssetMapper.withdrawBalance(amount, assetId, fromId, 1);
        userAssetMapper.depositBalance(amount, assetId, toId, 1);

        // TODO write record table

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
    public List<UserAsset> getDigitAssetListByAsset(int assetId) {
        return userAssetMapper.selectByAssetId(assetId);
    }
}
