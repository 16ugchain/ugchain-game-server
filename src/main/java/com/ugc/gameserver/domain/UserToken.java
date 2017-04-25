package com.ugc.gameserver.domain;

import java.util.Date;

/**
 * Created by fanjl on 2017/4/25.
 */
public class UserToken {
    private int userTokenId;
    private String token;
    private String data;
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
}
