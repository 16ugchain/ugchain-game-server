package com.fiveonechain.digitasset.domain;

import java.util.Date;

/**
 * Created by fanjl on 2016/11/24.
 */
public class Contract {
    private int contract_id;
    private Date create_time;
    private byte[] content;

    public int getContract_id() {
        return contract_id;
    }

    public void setContract_id(int contract_id) {
        this.contract_id = contract_id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
