package com.fiveonechain.digitasset.domain.result;

import java.util.Collections;
import java.util.List;

/**
 * Created by yuanshichao on 2016/12/8.
 */
public class DeliveryAssetItem {

    private int assetId;
    private String assetName;
    private int issuerId;
    private String issuerName;
    private int proposerId;
    private String proposerName;
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

    public String getIssuerName() {
        return issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
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
}
