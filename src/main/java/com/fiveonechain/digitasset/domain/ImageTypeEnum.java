package com.fiveonechain.digitasset.domain;

/**
 * Created by fanjl on 16/11/17.
 */
public enum ImageTypeEnum {
    IDENTITY_FRONT(1, "身份证正面照"),
    IDENTITY_BACK(2, "身份证背面照"),
    CREDITCARD_FRONT(3, "银行卡正面照"),
    CREDITCARD_BACK(4, "银行卡背面照");


    private int id;
    private String name;

    ImageTypeEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }


    public int getId() {
        return this.id;
    }

    ;

    public String getName() {
        return this.name;
    }

    ;

    public static ImageTypeEnum fromValue(int id) {
        for (ImageTypeEnum status : ImageTypeEnum.values()) {
            if (status.getId() == id) {
                return status;
            }
        }
        return null;
    }

}