package com.ugc.micropayment.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

/**
 * Created by yuanshichao on 2016/12/15.
 */

@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    private BigDecimal fee;

    public BigDecimal getFee() {
        return this.fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }
}
