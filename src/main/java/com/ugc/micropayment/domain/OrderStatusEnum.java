package com.ugc.micropayment.domain;

/**
 * Created by fanjl on 2017/4/7.
 */
public enum OrderStatusEnum {
    SUCCESS(1),
    PENDING(2),
    FAIL(3);

    private int id;

    OrderStatusEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static OrderStatusEnum fromValue(int id) {
        for (OrderStatusEnum status : OrderStatusEnum.values()) {
            if (status.getId() == id) {
                return status;
            }
        }
        return null;
    }

    public static OrderStatusEnum fromString(String str) {
        for (OrderStatusEnum role : OrderStatusEnum.values()) {
            if (role.name().equals(str)) {
                return role;
            }
        }
        throw new IllegalArgumentException(str);
    }

}
