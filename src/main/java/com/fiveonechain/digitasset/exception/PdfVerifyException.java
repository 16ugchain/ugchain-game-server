package com.fiveonechain.digitasset.exception;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CertificateException;

/**
 * Created by fanjl on 2016/11/25.
 */
public class PdfVerifyException extends RuntimeException {
    public PdfVerifyException() {
    }

    public PdfVerifyException(String message) {
        super(String.format("pdf verify error [message: %s]", message));
    }

    public PdfVerifyException(IOException e) {
        super(String.format("IOException occured while verify contract [error: %s]",e.getMessage()));
    }

    public PdfVerifyException(CertificateException e) {
        super(String.format("CertificateException occured while verify contract [error: %s]",e.getMessage()));
    }

    public PdfVerifyException(NoSuchAlgorithmException e) {
        super(String.format("NoSuchAlgorithmException occured while verify contract [error: %s]",e.getMessage()));
    }

    public PdfVerifyException(InvalidKeyException e) {
        super(String.format("InvalidKeyException occured while verify contract [error: %s]",e.getMessage()));
    }

    public PdfVerifyException(NoSuchProviderException e) {
        super(String.format("NoSuchProviderException occured while verify contract [error: %s]",e.getMessage()));
    }

    public PdfVerifyException(SignatureException e) {
        super(String.format("SignatureException occured while verify contract [error: %s]",e.getMessage()));
    }
}
