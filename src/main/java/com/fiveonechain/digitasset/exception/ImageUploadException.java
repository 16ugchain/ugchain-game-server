package com.fiveonechain.digitasset.exception;

/**
 * Created by fanjl on 16/11/21.
 */
public class ImageUploadException extends RuntimeException {
    public ImageUploadException(){
        super();
    }
    public ImageUploadException(long user_id,int type){
        super("user [" + user_id + "］ upload image type [" + type + "] fail");
    }
}
