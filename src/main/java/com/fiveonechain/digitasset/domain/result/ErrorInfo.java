package com.fiveonechain.digitasset.domain.result;

public enum ErrorInfo {

    AUTHENTICATION(4001, "认证失败"),
    JWT_TOKEN_EXPIRED(4002, "TOKEN过期"),
    ASSET_NOT_FOUND(4003, "资产不存在");

    private int errorCode;
    private String errorMessage;

    private ErrorInfo(int code, String message) {
        this.errorCode = code;
        this.errorMessage = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
