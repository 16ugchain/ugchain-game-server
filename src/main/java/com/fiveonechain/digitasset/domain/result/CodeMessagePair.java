package com.fiveonechain.digitasset.domain.result;

import org.springframework.util.Assert;

/**
 * Created by yuanshichao on 2016/12/7.
 */
public class CodeMessagePair {

    private final int code;
    private final String message;

    private CodeMessagePair(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static CodeMessagePair of(int code, String message) {
        Assert.notNull(message);
        return new CodeMessagePair(code, message);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
