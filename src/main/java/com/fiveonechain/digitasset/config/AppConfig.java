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

    public Integer getIssueExpireTime() {
        return issueExpireTime;
    }

    public void setIssueExpireTime(Integer issueExpireTime) {
        this.issueExpireTime = issueExpireTime;
    }
}
