package com.fiveonechain.digitasset.domain.result;

public enum ErrorInfo {

    AUTHENTICATION(4001, "认证失败"),
    JWT_TOKEN_EXPIRED(4002, "TOKEN过期"),
    ASSET_NOT_FOUND(4003, "资产不存在"),
    ASSET_STATUS_TRANSFER_ERROR(4004, "资产状态异常"),
    DIGIT_ASSET_NOT_FOUND(4005, "数字资产不存在"),
    IMAGETYPE_NOT_FOUND(5001,"图片类型不存在"),
    IMAGE_EXISTS(5002,"此类型图片已经上传过"),
    IMAGE_TOO_LARGE(5003,"图片太大"),
    IMAGE_UPLOAD_FAIL(5004,"图片上传失败"),
    USER_NOT_FOUND(6001,"用户不存在"),
    USER_NAME_EXITS(6002,"用户名已被注册"),
    PASSWORD_ERROR(6003,"密码错误"),
    IDENTITY_EXISTS(6004,"身份证已被注册"),
    CORP_EXISTS(6005,"公司已注册"),
    CORP_NOT_FOUND(6006,"找不到公司"),
    CORP_CERTIFICATE_NOT_FOUND(6007,"找不到证书"),
    CORP_KEYPAIR_NOT_FOUND(6008,"找不到公钥私钥"),
    CONTRACT_NOT_FOUND(6009,"找不到合同"),
    ORDER_EXCEPTION(7001,"订单异常"),
    ORDER_NOT_FOUND(7002,"订单不存在"),
    ORDER_APPLY_OUTTIME(7003,"申请操作超时"),
    ORDER_OBLIGATION_OUTTIME(7004,"支付操作超时"),
    ORDER_COMEPLETE_OUTTIME(7005,"完成操作超时，进入仲裁"),
    PDF_SIGN_ERROR(8001,"pdf签名失败"),
    PDF_VERIFY_ERROR(8002,"pdf验证失败"),
    DOWNLOAD_ERROR(8003,"下载失败"),
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
