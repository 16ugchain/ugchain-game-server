package com.ugc.gameserver.config;

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

    private String gethLocation;

    private String ugAddress;

    private String walletPassword;

    private String walletPath;

    public String getWalletPassword() {
        return this.walletPassword;
    }

    public void setWalletPassword(String walletPassword) {
        this.walletPassword = walletPassword;
    }

    public String getWalletPath() {
        return this.walletPath;
    }

    public void setWalletPath(String walletPath) {
        this.walletPath = walletPath;
    }

    public String getUgAddress() {
        return this.ugAddress;
    }

    public void setUgAddress(String ugAddress) {
        this.ugAddress = ugAddress;
    }

    public String getGethLocation() {
        return this.gethLocation;
    }

    public void setGethLocation(String gethLocation) {
        this.gethLocation = gethLocation;
    }

    public BigDecimal getFee() {
        return this.fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }
}
