package com.fiveonechain.digitasset.domain.result;

public enum ErrorInfo {

    AUTHENTICATION(4001, "认证失败"),
    JWT_TOKEN_EXPIRED(4002, "TOKEN过期"),
    ASSET_NOT_FOUND(4003, "资产不存在"),
    IMAGETYPE_NOT_FOUND(5001,"图片类型不存在"),
    IMAGE_EXISTS(5002,"此类型图片已经上传过"),
    IMAGE_TOO_LARGE(5003,"图片太大"),
    IMAGE_UPLOAD_FAIL(5004,"图片上传失败"),
    USER_NOT_FOUND(6001,"用户不存在"),
    USER_NAME_EXITS(6002,"用户名已被注册"),
    PASSWORD_ERROR(6003,"密码错误"),
    IDENTITY_EXISTS(6004,"身份证已被注册"),
    CORP_EXISTS(6004,"公司已注册"),
    SERVER_ERROR(9001,"服务器错误");

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
