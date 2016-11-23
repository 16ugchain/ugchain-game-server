package com.fiveonechain.digitasset.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by fanjl on 2016/11/23.
 */
@Configuration
@ConfigurationProperties(prefix = "cer.security.corp")
public class CerConfig {

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}