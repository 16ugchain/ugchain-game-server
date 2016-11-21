package com.fiveonechain.digitasset.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by fanjl on 16/11/21.
 */
public class AssetOrder {
    private int order_id;
    private int asset_id;
    private int user_id;
    private int amount;
    private BigDecimal unit_prices;
    private int buyer_id;
    private Date end_time;
    private Date create_time;
    private Date update_time;
    private int status;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getAsset_id() {
        return asset_id;
    }

    public void setAsset_id(int asset_id) {
        this.asset_id = asset_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(int buyer_id) {
        this.buyer_id = buyer_id;
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

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public BigDecimal getUnit_prices() {
        return unit_prices;
    }

    public void setUnit_prices(BigDecimal unit_prices) {
        this.unit_prices = unit_prices;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
