package com.fiveonechain.digitasset.domain;

import java.util.Date;

/**
 * Created by fanjl on 16/11/16.
 */
public class UserAuth {
    private int id;
    private int userId;
    private String realName;
    private String identity;
    private int identityType;
    private Date createTime;
    private Date updateTime;
    private String creditCardId;
    private String creditCardOwner;
    private String creditCardBank;
    private String fixedLine;
    private String email;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return userId;
    }

    public void setUser_id(int userId) {
        this.userId = userId;
    }

    public String getReal_name() {
        return realName;
    }

    public void setReal_name(String realName) {
        this.realName = realName;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public Date getCreate_time() {
        return createTime;
    }

    public void setCreate_time(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdate_time() {
        return updateTime;
    }

    public void setUpdate_time(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFixed_line() {
        return fixedLine;
    }

    public void setFixed_line(String fixedLine) {
        this.fixedLine = fixedLine;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCredit_card_id() {
        return creditCardId;
    }

    public void setCredit_card_id(String creditCardId) {
        this.creditCardId = creditCardId;
    }

    public String getCredit_card_owner() {
        return creditCardOwner;
    }

    public void setCredit_card_owner(String creditCardOwner) {
        this.creditCardOwner = creditCardOwner;
    }

    public String getCredit_card_bank() {
        return creditCardBank;
    }

    public void setCredit_card_bank(String creditCardBank) {
        this.creditCardBank = creditCardBank;
    }

    public int getIdentity_type() {
        return identityType;
    }

    public void setIdentity_type(int identityType) {
        this.identityType = identityType;
    }


    @Override
    public String toString() {
        return "UserAuth{" +
                "id=" + id +
                ", userId=" + userId +
                ", realName='" + realName + '\'' +
                ", identity='" + identity + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", status=" + status +
                '}';
    }
}
