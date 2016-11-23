package com.fiveonechain.digitasset.exception;

/**
 * Created by yuanshichao on 2016/11/23.
 */
public class GuaranteeCertificateException extends RuntimeException {

    public GuaranteeCertificateException() {
    }

    public GuaranteeCertificateException(String message) {
        super(message);
    }

    public GuaranteeCertificateException(String message, Throwable cause) {
        super(message, cause);
    }

    public GuaranteeCertificateException(Throwable cause) {
        super(cause);
    }
}
