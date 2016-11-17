package com.fiveonechain.digitasset.domain;

/**
 * Created by yuanshichao on 2016/11/16.
 */
public enum AssetStatus {

    WAIT_EVALUATE(1),
    PASS_EVALUATE(2),
    ISSUING(3),
    EXPIRE(4),
    FROZEN(6),
    REJECT_EVALUATE(5);


    private int code;

    AssetStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
