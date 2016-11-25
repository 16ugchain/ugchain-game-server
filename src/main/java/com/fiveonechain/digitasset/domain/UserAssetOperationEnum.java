package com.fiveonechain.digitasset.domain;

/**
 * Created by yuanshichao on 2016/11/23.
 */
public enum UserAssetOperationEnum {

    TRANSFER_IN(3),
    TRANSFER_OUT(2),
    ISSUE(1);

    private int code;

    UserAssetOperationEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static UserAssetOperationEnum fromCode(int code) {
        for (UserAssetOperationEnum oper : UserAssetOperationEnum.values()) {
            if (oper.getCode() == code) {
                return oper;
            }
        }
        throw new IllegalArgumentException(
                String.format("UserAssetOperation not found [code: %d]", code));
    }
}
