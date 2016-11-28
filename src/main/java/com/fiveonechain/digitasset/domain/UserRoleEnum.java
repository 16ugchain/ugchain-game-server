package com.fiveonechain.digitasset.domain;

/**
 * Created by fanjl on 16/11/16.
 */
public enum UserRoleEnum {
    USER_PUBLISHER(1),
    USER_ASSIGNEE(2),
    CORP(3),
    ADMIN(9);

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
