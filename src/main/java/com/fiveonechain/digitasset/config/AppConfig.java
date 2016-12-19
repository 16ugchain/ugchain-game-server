package com.fiveonechain.digitasset.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yuanshichao on 2016/12/15.
 */

@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    private Integer issueExpireTime;
    private Integer orderApplyExpireTime;
    private Integer orderPayExpireTime;
    private Integer orderPayConfirmExpireTime;

    public Integer getIssueExpireTime() {
        return issueExpireTime;
    }

    public void setIssueExpireTime(Integer issueExpireTime) {
        this.issueExpireTime = issueExpireTime;
    }

    public Integer getOrderApplyExpireTime() {
        return orderApplyExpireTime;
    }

    public void setOrderApplyExpireTime(Integer orderApplyExpireTime) {
        this.orderApplyExpireTime = orderApplyExpireTime;
    }

    public Integer getOrderPayExpireTime() {
        return orderPayExpireTime;
    }

    public void setOrderPayExpireTime(Integer orderPayExpireTime) {
        this.orderPayExpireTime = orderPayExpireTime;
    }

    public Integer getOrderPayConfirmExpireTime() {
        return orderPayConfirmExpireTime;
    }

    public void setOrderPayConfirmExpireTime(Integer orderPayConfirmExpireTime) {
        this.orderPayConfirmExpireTime = orderPayConfirmExpireTime;
    }
}
