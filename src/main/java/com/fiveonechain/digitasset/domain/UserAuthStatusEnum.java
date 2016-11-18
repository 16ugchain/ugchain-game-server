package com.fiveonechain.digitasset.domain;

/**
 * Created by fanjl on 16/11/16.
 */
public enum UserAuthStatusEnum {
    SUCCESS(1),
    FAIL(0);
    private int id;
    UserAuthStatusEnum(int id){
        this.id = id;
    }
    public int getId(){return this.id;};
}
