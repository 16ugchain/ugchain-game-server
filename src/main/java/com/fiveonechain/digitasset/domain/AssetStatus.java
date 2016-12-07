package com.fiveonechain.digitasset.domain;

/**
 * Created by yuanshichao on 2016/11/16.
 */
public enum AssetStatus {

    CLEAR(10, "赎回完成"),
    DELIVERY(9, "交割完成"),
    APPLY_DELIVERY(8, "申请交割"),
    EXPIRE_TO_ISSUE(7, "发行超时"),
    FROZEN(6, "冻结"),
    REJECT_EVALUATE(5, "驳回评估"),
    MATURITY(4, "到期"),
    ISSUE(3, "流通中"),
    PASS_EVALUATE(2, "通过评估"),
    WAIT_EVALUATE(1, "等待评估");


    private int code;
    private String message;

    AssetStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return message;
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
