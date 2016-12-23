package com.fiveonechain.digitasset.domain.result;

import com.fiveonechain.digitasset.domain.UserInfo;

/**
 * Created by yuanshichao on 2016/12/7.
 */
public class WaitEvaluateAssetItem {

    private int assetId;
    private String assetName;
    private String desc;
    private int cycle;
    private int issuerId;
    private String issuerName;
    private UserInfo issuerInfo;
    private String telephone;


    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCycle() {
        return cycle;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }

    public int getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(int issuerId) {
        this.issuerId = issuerId;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public UserInfo getIssuerInfo() {
        return issuerInfo;
    }

    public void setIssuerInfo(UserInfo issuerInfo) {
        this.issuerInfo = issuerInfo;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
