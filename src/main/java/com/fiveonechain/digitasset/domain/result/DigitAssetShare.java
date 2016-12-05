package com.fiveonechain.digitasset.domain.result;

/**
 * Created by yuanshichao on 2016/12/5.
 */
public class DigitAssetShare {

    private int assetId;
    private int ownerId;
    private String ownerName;
    private int availShare;
    private int percent;

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public int getAvailShare() {
        return availShare;
    }

    public void setAvailShare(int availShare) {
        this.availShare = availShare;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
