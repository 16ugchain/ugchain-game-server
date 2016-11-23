package com.fiveonechain.digitasset.domain;

import java.util.Date;

/**
 * Created by fanjl on 16/11/18.
 */
public class GuaranteeCorp {
    private int guaranteecorp_id;
    private int user_id;
    private String corp_name;
    private String juristic_person;
    private String main_business;
    private byte[] pkcs12;
    private Date create_time;
    private Date update_time;
    private int status;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCorp_name() {
        return corp_name;
    }

    public void setCorp_name(String corp_name) {
        this.corp_name = corp_name;
    }

    public String getJuristic_person() {
        return juristic_person;
    }

    public void setJuristic_person(String juristic_person) {
        this.juristic_person = juristic_person;
    }

    public String getMain_business() {
        return main_business;
    }

    public void setMain_business(String main_business) {
        this.main_business = main_business;
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

    public int getGuaranteecorp_id() {
        return guaranteecorp_id;
    }

    public void setGuaranteecorp_id(int guaranteecorp_id) {
        this.guaranteecorp_id = guaranteecorp_id;
    }

    public byte[] getPkcs12() {
        return pkcs12;
    }

    public void setPkcs12(byte[] pkcs12) {
        this.pkcs12 = pkcs12;
    }
}
