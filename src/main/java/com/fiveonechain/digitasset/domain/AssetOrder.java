package com.fiveonechain.digitasset.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by fanjl on 16/11/21.
 */
public class AssetOrder {
    private int orderId;
    private int assetId;
    private int userId;
    private int amount;
    private BigDecimal unitPrices;
    private int buyerId;
    private Date endTime;
    private Date createTime;
    private Date updateTime;
    private int status;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BigDecimal getUnitPrices() {
        return unitPrices;
    }

    public void setUnitPrices(BigDecimal unitPrices) {
        this.unitPrices = unitPrices;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
