package com.fiveonechain.digitasset.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by yuanshichao on 2016/11/7.
 */
public class User {

    private Integer id;
    private Integer userId;
    private String userName;
    @JsonIgnore
    private String password;
    private int role;
    @JsonIgnore
    private int status;
    private String telephone;
    private String createTime;
    private String updateTime;

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return userId;
    }

    public void setUser_id(Integer userId) {
        this.userId = userId;
    }

    public String getUser_name() {
        return userName;
    }

    public void setUser_name(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreate_time() {
        return createTime;
    }

    public void setCreate_time(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdate_time() {
        return updateTime;
    }

    public void setUpdate_time(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", status=" + status +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
