package com.fiveonechain.digitasset.domain.result;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

/**
 * Created by yuanshichao on 2016/12/5.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssetDetail {

    private String name;
    private String desc;
    private int value;//资产估值
    private boolean isGuaranteed;
    private Integer guarId;
    private String guarName;
    private List<DigitalAssetCmd> digitalAssetCmds;

    private List<String> certList;
    private List<String> photoList;

    private int tradeShare;//流通份额
    private Date startTime;//发行时间
    private Date endTime;//到期时间

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

    public List<DigitalAssetCmd> getDigitalAssetCmds() {
        return digitalAssetCmds;
    }

    public void setDigitalAssetCmds(List<DigitalAssetCmd> digitalAssetCmds) {
        this.digitalAssetCmds = digitalAssetCmds;
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

    public int getTradeShare() {
        return tradeShare;
    }

    public void setTradeShare(int tradeShare) {
        this.tradeShare = tradeShare;
    }
}
