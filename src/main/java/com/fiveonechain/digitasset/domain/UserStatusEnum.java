package com.fiveonechain.digitasset.domain;

/**
 * Created by fanjl on 16/11/16.
 */
public enum UserStatusEnum {
    ACTIVE(1),
    DELETE(2);
    private int id;

    UserStatusEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
