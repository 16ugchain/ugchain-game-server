package com.fiveonechain.digitasset.domain;

/**
 * Created by fanjl on 2016/12/16.
 */
public enum AssetOrderOperation {
    VIEW(6, "查看"),
    APPEAL(5, "申诉"),
    PAY(4, "支付"),
    COMPLETED(3, "完成"),
    REJECT_APPLY(2, "驳回申请"),
    AGREE_APPLY(1, "同意申请");

    private int code;
    private String message;

    AssetOrderOperation(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return message;
    }

    public static AssetOrderOperation fromCode(int code) {
        for (AssetOrderOperation status : AssetOrderOperation.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;
    }
}
