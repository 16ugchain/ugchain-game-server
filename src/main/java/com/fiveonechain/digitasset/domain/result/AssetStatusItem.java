package com.fiveonechain.digitasset.domain.result;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

/**
 * Created by yuanshichao on 2016/12/16.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssetStatusItem {

    private Date time;
    private String message;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
