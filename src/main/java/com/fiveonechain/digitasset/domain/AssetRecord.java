package com.fiveonechain.digitasset.domain;

import java.util.Date;

/**
 * Created by yuanshichao on 2016/11/25.
 */
public class AssetRecord {

    private int assetId;
    private int userId;
    private int status;
    private Date createTime;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
