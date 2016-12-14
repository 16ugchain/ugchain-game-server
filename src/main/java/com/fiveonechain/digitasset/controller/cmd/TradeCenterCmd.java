package com.fiveonechain.digitasset.controller.cmd;

import java.util.Date;

/**
 * Created by fanjl on 2016/12/8.
 */
public class TradeCenterCmd {
    private String assetName;
    private int assetId;
    private int circulationShare;//流通份额
    private String assetPercent;//占资产比例
    private String assetEvaluation;//资产估价
    private Date endTime;
    private String guarName;//担保公司名称
    private String expectExpEarning;//预期每股回购溢价

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    public int getCirculationShare() {
        return circulationShare;
    }

    public void setCirculationShare(int circulationShare) {
        this.circulationShare = circulationShare;
    }

    public String getAssetPercent() {
        return assetPercent;
    }

    public void setAssetPercent(String assetPercent) {
        this.assetPercent = assetPercent;
    }

    public String getAssetEvaluation() {
        return assetEvaluation;
    }

    public void setAssetEvaluation(String assetEvaluation) {
        this.assetEvaluation = assetEvaluation;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getGuarName() {
        return guarName;
    }

    public void setGuarName(String guarName) {
        this.guarName = guarName;
    }

    public String getExpectExpEarning() {
        return expectExpEarning;
    }

    public void setExpectExpEarning(String expectExpEarning) {
        this.expectExpEarning = expectExpEarning;
    }
}
