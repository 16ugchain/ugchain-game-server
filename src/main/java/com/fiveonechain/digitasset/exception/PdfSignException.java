package com.fiveonechain.digitasset.exception;

/**
 * Created by fanjl on 2016/11/23.
 */
public class PdfSignException extends RuntimeException {
    public PdfSignException() {
    }

    public PdfSignException(String message) {
        super(String.format("pdf sign error [message: %s]", message));
    }

    public PdfSignException(String message,String filePath) {
        super(String.format("can't read file [path: %s , error: %s]", filePath,message));
    }
}
