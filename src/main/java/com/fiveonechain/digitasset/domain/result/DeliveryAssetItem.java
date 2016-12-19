package com.fiveonechain.digitasset.domain.result;

import com.fiveonechain.digitasset.domain.UserInfo;

import java.util.List;

/**
 * Created by yuanshichao on 2016/12/8.
 */
public class DeliveryAssetItem {

    private int assetId;
    private String assetName;
    private int issuerId;
    private UserInfo issuerInfo;
    private int proposerId;
    private int unitPrices;//单价
    private String telephone;
    private String proposerName;
    private String endTimeStr;
    private CodeMessagePair status;
    private List<CodeMessagePair> operations;

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

    public int getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(int issuerId) {
        this.issuerId = issuerId;
    }

    public UserInfo getIssuerInfo() {
        return issuerInfo;
    }

    public void setIssuerInfo(UserInfo issuerInfo) {
        this.issuerInfo = issuerInfo;
    }

    public int getProposerId() {
        return proposerId;
    }

    public void setProposerId(int proposerId) {
        this.proposerId = proposerId;
    }

    public String getProposerName() {
        return proposerName;
    }

    public void setProposerName(String proposerName) {
        this.proposerName = proposerName;
    }

    public CodeMessagePair getStatus() {
        return status;
    }

    public void setStatus(CodeMessagePair status) {
        this.status = status;
    }

    public List<CodeMessagePair> getOperations() {
        return operations;
    }

    public void setOperations(List<CodeMessagePair> operations) {
        this.operations = operations;
    }

    public int getUnitPrices() {
        return unitPrices;
    }

    public void setUnitPrices(int unitPrices) {
        this.unitPrices = unitPrices;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }
}
