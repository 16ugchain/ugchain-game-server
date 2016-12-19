package com.fiveonechain.digitasset.domain.result;

import java.util.Date;

/**
 * Created by fanjl on 2016/12/8.
 */
public class DigitalAssetCmd {
    private String assetName;
    private int assetId;
    private String publishName;
    private String holderName;
    private int tradeShare;//流通份额
    private int holdShare;//持有份额
    private int lockedShare;
    private Date endTime;
    private String guarName;
    private String statusStr;
    private boolean isWhollyOwner;

    private String percent;//占资比例  持有份额／资产估价

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    public String getPublishName() {
        return publishName;
    }

    public void setPublishName(String publishName) {
        this.publishName = publishName;
    }

    public int getHoldShare() {
        return holdShare;
    }

    public void setHoldShare(int holdShare) {
        this.holdShare = holdShare;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getGuarName() {
        return guarName;
    }

    public void setGuarName(String guarName) {
        this.guarName = guarName;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public int getTradeShare() {
        return tradeShare;
    }

    public void setTradeShare(int tradeShare) {
        this.tradeShare = tradeShare;
    }

    public boolean isWhollyOwner() {
        return isWhollyOwner;
    }

    public void setWhollyOwner(boolean whollyOwner) {
        isWhollyOwner = whollyOwner;
    }

    public int getLockedShare() {
        return lockedShare;
    }

    public void setLockedShare(int lockedShare) {
        this.lockedShare = lockedShare;
    }
}

