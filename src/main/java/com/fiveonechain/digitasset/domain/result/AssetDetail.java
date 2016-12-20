package com.fiveonechain.digitasset.domain.result;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by yuanshichao on 2016/12/5.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssetDetail {
    private int assetId;
    private String name;
    private String desc;
    private int value;//资产估值
    private boolean isGuaranteed;
    private Integer guarId;
    private String guarName;

    private List<String> certList;
    private List<String> photoList;

    private int tradeShare;//流通份额
    private String startTime;//发行时间
    private String endTime;//到期时间
    private String statusStr;//状态
    private List<CodeMessagePair> operation;//操作

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Integer getGuarId() {
        return guarId;
    }

    public void setGuarId(Integer guarId) {
        this.guarId = guarId;
    }

    public String getGuarName() {
        return guarName;
    }

    public void setGuarName(String guarName) {
        this.guarName = guarName;
    }

    public List<String> getCertList() {
        return certList;
    }

    public void setCertList(List<String> certList) {
        this.certList = certList;
    }

    public List<String> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<String> photoList) {
        this.photoList = photoList;
    }

    public boolean isGuaranteed() {
        return isGuaranteed;
    }

    public void setGuaranteed(boolean guaranteed) {
        isGuaranteed = guaranteed;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getTradeShare() {
        return tradeShare;
    }

    public void setTradeShare(int tradeShare) {
        this.tradeShare = tradeShare;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public List<CodeMessagePair> getOperation() {
        return operation;
    }

    public void setOperation(List<CodeMessagePair> operation) {
        this.operation = operation;
    }

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }
}
