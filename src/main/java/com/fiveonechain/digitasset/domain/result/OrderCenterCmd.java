package com.fiveonechain.digitasset.domain.result;

import com.fiveonechain.digitasset.domain.AssetOrderOperation;

import java.util.Date;
import java.util.List;

/**
 * Created by fanjl on 2016/12/9.
 */
public class OrderCenterCmd {
    /*
    * 用于订单中心，我发起的订单，以及我收到的订单，展示类。
    * */
    private String assetName;//资产名称
    private int assetId;//资产编号
    private int orderId;//订单编号
    private int ApplicationShare;//申购份额
    private String percent;//占资比例
    private Date endTime;
    private String endTimeStr;
    private String guarName;
    private String holderName;//持有人
    private Date startTime;
    private String statusStr;
    private int status;
    private List<AssetOrderOperation> operation;//订单操作
    private String expEarning;//预期收益
    private String buyerName;//申购人
    private String buyerCorp;//申购单位

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

    public int getApplicationShare() {
        return ApplicationShare;
    }

    public void setApplicationShare(int applicationShare) {
        ApplicationShare = applicationShare;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
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

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getExpEarning() {
        return expEarning;
    }

    public void setExpEarning(String expEarning) {
        this.expEarning = expEarning;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerCorp() {
        return buyerCorp;
    }

    public void setBuyerCorp(String buyerCorp) {
        this.buyerCorp = buyerCorp;
    }

    public List<AssetOrderOperation> getOperation() {
        return operation;
    }

    public void setOperation(List<AssetOrderOperation> operation) {
        this.operation = operation;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }
}
