package com.fiveonechain.digitasset.domain;

import java.util.Date;

/**
 * Created by yuanshichao on 2016/11/16.
 */
public class Asset {

    private int assetId;
    private int userId;
    private int guarId;

    private String name;
    private int value;
    private String description;
    private String certificate;
    private String photos;

    private Date startTime;
    private Date endTime;

    private Date createTime;
    private Date updateTime;

    private String evalConclusion;
    private int evalValue;
    private int fee;
    private int expEarnings;

    private int status;

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGuarId() {
        return guarId;
    }

    public void setGuarId(int guarId) {
        this.guarId = guarId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public String getEvalConclusion() {
        return evalConclusion;
    }

    public void setEvalConclusion(String evalConclusion) {
        this.evalConclusion = evalConclusion;
    }

    public int getEvalValue() {
        return evalValue;
    }

    public void setEvalValue(int evalValue) {
        this.evalValue = evalValue;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getExpEarnings() {
        return expEarnings;
    }

    public void setExpEarnings(int expEarnings) {
        this.expEarnings = expEarnings;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
