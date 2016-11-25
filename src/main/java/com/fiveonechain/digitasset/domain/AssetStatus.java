package com.fiveonechain.digitasset.domain;

/**
 * Created by yuanshichao on 2016/11/16.
 */
public enum AssetStatus {

    WAIT_EVALUATE(1),
    PASS_EVALUATE(2),
    ISSUE(3),
    MATURITY(4),
    FROZEN(6),
    EXPIRE_TO_ISSUE(7),
    APPLY_DELIVERY(8),
    DELIVERY(9),
    CLEAR(10),
    REJECT_EVALUATE(5);


    private int code;

    AssetStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public static AssetStatus fromValue(int code) {
        for (AssetStatus status : AssetStatus.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;
    }
}
