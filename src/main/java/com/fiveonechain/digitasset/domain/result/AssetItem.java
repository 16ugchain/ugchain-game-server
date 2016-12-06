package com.fiveonechain.digitasset.domain.result;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

/**
 * Created by yuanshichao on 2016/12/6.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssetItem {

    private int assetId;
    private String assetName;

    private int availShare;
    private int percent;

    private int value;
    private boolean isGuaranteed;
    private Integer guarId;
    private String guraName;

    private Integer expEarnings;

    private Date endTime;

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public int getAvailShare() {
        return availShare;
    }

    public void setAvailShare(int availShare) {
        this.availShare = availShare;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isGuaranteed() {
        return isGuaranteed;
    }

    public void setGuaranteed(boolean guaranteed) {
        isGuaranteed = guaranteed;
    }

    public Integer getGuarId() {
        return guarId;
    }

    public void setGuarId(Integer guarId) {
        this.guarId = guarId;
    }

    public String getGuraName() {
        return guraName;
    }

    public void setGuraName(String guraName) {
        this.guraName = guraName;
    }

    public Integer getExpEarnings() {
        return expEarnings;
    }

    public void setExpEarnings(Integer expEarnings) {
        this.expEarnings = expEarnings;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}

