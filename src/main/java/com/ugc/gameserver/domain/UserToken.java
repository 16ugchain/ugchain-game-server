package com.ugc.gameserver.domain;

import java.util.Date;

/**
 * Created by fanjl on 2017/4/25.
 */
public class UserToken {
    private int userTokenId;
    private String userName;
    private String token;
    private String data;
    private int derma;
    private Date createTime;
    private Date updateTime;
    private int status;

    public int getUserTokenId() {
        return this.userTokenId;
    }

    public void setUserTokenId(int userTokenId) {
        this.userTokenId = userTokenId;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDerma() {
        return this.derma;
    }

    public void setDerma(int derma) {
        this.derma = derma;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
