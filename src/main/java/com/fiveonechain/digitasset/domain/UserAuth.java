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
    private Date create_time;
    private Date update_time;
    private String credit_card_id;
    private String credit_card_owner;
    private String credit_card_bank;
    private String fixed_line;
    private String email;
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

    public String getFixed_line() {
        return fixed_line;
    }

    public void setFixed_line(String fixed_line) {
        this.fixed_line = fixed_line;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCredit_card_id() {
        return credit_card_id;
    }

    public void setCredit_card_id(String credit_card_id) {
        this.credit_card_id = credit_card_id;
    }

    public String getCredit_card_owner() {
        return credit_card_owner;
    }

    public void setCredit_card_owner(String credit_card_owner) {
        this.credit_card_owner = credit_card_owner;
    }

    public String getCredit_card_bank() {
        return credit_card_bank;
    }

    public void setCredit_card_bank(String credit_card_bank) {
        this.credit_card_bank = credit_card_bank;
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
