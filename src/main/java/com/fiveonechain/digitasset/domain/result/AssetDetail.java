package com.fiveonechain.digitasset.domain.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fiveonechain.digitasset.controller.cmd.DigitalAssetCmd;

import java.util.List;

/**
 * Created by yuanshichao on 2016/12/5.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssetDetail {

    private String name;
    private String desc;
    private int value;
    private boolean isGuaranteed;
    private Integer guarId;
    private String guarName;
    private List<DigitalAssetCmd> digitalAssetCmds;

    private List<String> certList;
    private List<String> photoList;

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
}
