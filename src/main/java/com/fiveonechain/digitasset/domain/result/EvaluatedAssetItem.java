package com.fiveonechain.digitasset.domain.result;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yuanshichao on 2016/12/7.
 */
public class EvaluatedAssetItem {

    private int assetId;
    private String assetName;
    private String desc;
    private CodeMessagePair status;
    private Date updateTime;
    private List<CodeMessagePair> operations = Collections.emptyList();

    public void addOperation(CodeMessagePair operation) {
        if (operations == null) {
            operations = new LinkedList<>();
        }
        operations.add(operation);
    }

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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
