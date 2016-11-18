package com.fiveonechain.digitasset.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fanjl on 16/11/16.
 */
public enum UserRoleEnum {
    USER(1),
    ADMIN(2);

    private int id;

    UserRoleEnum(int id) {
        this.id = id;
        MapHolder.map.put(id, this);
    }

    public int getId() {
        return this.id;
    }

    private static class MapHolder {
        private final static Map<Integer, UserRoleEnum> map = new HashMap<Integer, UserRoleEnum>();
    }



    public static UserRoleEnum getUserRoleById(int id) {
        return MapHolder.map.get(id);
    }


}
