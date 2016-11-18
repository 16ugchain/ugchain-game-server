package com.fiveonechain.digitasset.domain;

import java.util.Date;

/**
 * Created by fanjl on 16/11/16.
 */
public class UserAuth {
    private int id;
    private int user_id;
    private String real_name;
    private String identity;
    private byte[] identityfront;
    private byte[] identityback;
    private Date create_time;
    private Date update_time;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public byte[] getIdentityfront() {
        return identityfront;
    }

    public void setIdentityfront(byte[] identityfront) {
        this.identityfront = identityfront;
    }

    public byte[] getIdentityback() {
        return identityback;
    }

    public void setIdentityback(byte[] identityback) {
        this.identityback = identityback;
    }

    @Override
    public String toString() {
        return "UserAuth{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", real_name='" + real_name + '\'' +
                ", identity='" + identity + '\'' +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                ", status=" + status +
                '}';
    }
}
