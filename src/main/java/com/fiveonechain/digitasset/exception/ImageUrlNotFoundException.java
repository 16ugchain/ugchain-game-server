package com.fiveonechain.digitasset.exception;

/**
 * Created by yuanshichao on 2016/11/18.
 */
public class ImageUrlNotFoundException extends RuntimeException {

    public ImageUrlNotFoundException() {
    }

    public ImageUrlNotFoundException(int imageId) {
        super("ImageUrl not found [imageId: " + imageId + "]");
    }

    public ImageUrlNotFoundException(String message) {
        super(message);
    }
}
