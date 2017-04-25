package com.ugc.gameserver.domain.result;

public enum ErrorInfo {

    TOKEN_NOTEXISTS(4001, "token不存在"),
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
