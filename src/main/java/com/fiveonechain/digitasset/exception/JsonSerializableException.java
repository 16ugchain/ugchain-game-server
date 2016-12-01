package com.fiveonechain.digitasset.exception;

/**
 * Created by fanjl on 2016/12/1.
 */
public class JsonSerializableException extends RuntimeException{
    public JsonSerializableException(){
        super();
    }
    public JsonSerializableException(Exception e){
        super("json parse error , message : "+ e.getMessage());
    }
}
