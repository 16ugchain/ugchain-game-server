package com.fiveonechain.digitasset.domain;

import java.util.Date;

/**
 * Created by fanjl on 2016/11/24.
 */
public class Contract {
    private int contractId;
    private Date createTime;
    private byte[] content;

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
