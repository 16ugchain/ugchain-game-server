package com.fiveonechain.digitasset.domain;

/**
 * Created by yuanshichao on 2016/12/7.
 */
public enum AssetOperation {

    CLEAR(9, "完成赎回"),
    UNFREEZE(8, "解冻"),
    FREEZE(7, "冻结"),
    DELIVERY(6, "完成交割"),
    REJECT_DELIVERY(5, "驳回交割"),
    APPLY_DELIVERY(4, "申请交割"),
    ISSUE(3, "发行"),
    REJECT_EVALUATE(2, "驳回评估"),
    PASS_EVALUATE(1, "通过评估");

    private int code;
    private String message;

    AssetOperation(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return message;
    }

    public static AssetStatus fromCode(int code) {
        for (AssetStatus status : AssetStatus.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;
    }

}
