package com.fiveonechain.digitasset.domain;

/**
 * Created by fanjl on 16/11/16.
 */
public enum UserRoleEnum {
    USER(1),
    ADMIN(2);

    private int id;

    UserRoleEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static UserRoleEnum fromValue(int id) {
        for (UserRoleEnum status : UserRoleEnum.values()) {
            if (status.getId() == id) {
                return status;
            }
        }
        return null;
    }



}
