package com.fiveonechain.digitasset.domain.result;

import com.fiveonechain.digitasset.domain.UserInfo;

/**
 * Created by yuanshichao on 2016/12/5.
 */
public class DigitAssetItem {

    private int assetId;
    private int ownerId;
    private String ownerName;
    private int availShare;
    private String percent;
    private String telephone;
    private UserInfo userInfo;

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

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
