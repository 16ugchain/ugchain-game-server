package com.fiveonechain.digitasset.domain;

import java.util.Date;

/**
 * Created by fanjl on 16/11/18.
 */
public class GuaranteeCorp {
    private int guaranteecorpId;
    private int userId;
    private String corpName;
    private String juristicPerson;
    private String mainBusiness;
    private byte[] pkcs12;
    private Date createTime;
    private Date updateTime;
    private int status;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getGuaranteecorpId() {
        return guaranteecorpId;
    }

    public void setGuaranteecorpId(int guaranteecorpId) {
        this.guaranteecorpId = guaranteecorpId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getJuristicPerson() {
        return juristicPerson;
    }

    public void setJuristicPerson(String juristicPerson) {
        this.juristicPerson = juristicPerson;
    }

    public String getMainBusiness() {
        return mainBusiness;
    }

    public void setMainBusiness(String mainBusiness) {
        this.mainBusiness = mainBusiness;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public byte[] getPkcs12() {
        return pkcs12;
    }

    public void setPkcs12(byte[] pkcs12) {
        this.pkcs12 = pkcs12;
    }
}
