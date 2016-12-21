package com.fiveonechain.digitasset.domain.result;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by yuanshichao on 2016/12/8.
 */
public class MaturityAssetItem {

    private int assetId;
    private String assetName;
    private CodeMessagePair status;
    private Date endTime;
    private String endTimeStr;
    private int issuerId;
    private String issuerName;
    private List<CodeMessagePair> operations = Collections.emptyList();

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

    public CodeMessagePair getStatus() {
        return status;
    }

    public void setStatus(CodeMessagePair status) {
        this.status = status;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public List<CodeMessagePair> getOperations() {
        return operations;
    }

    public void setOperations(List<CodeMessagePair> operations) {
        this.operations = operations;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }
}

