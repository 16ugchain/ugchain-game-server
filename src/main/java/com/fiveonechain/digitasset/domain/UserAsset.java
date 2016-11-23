package com.fiveonechain.digitasset.domain;

/**
 * Created by yuanshichao on 2016/11/21.
 */
public class UserAsset {

    private int assetId;
    private int userId;
    private int contractId;
    private int balance;
    private int tradeBalance;
    private int lockBalance;


    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getTradeBalance() {
        return tradeBalance;
    }

    public void setTradeBalance(int tradeBalance) {
        this.tradeBalance = tradeBalance;
    }

    public int getLockBalance() {
        return lockBalance;
    }

    public void setLockBalance(int lockBalance) {
        this.lockBalance = lockBalance;
    }

}
