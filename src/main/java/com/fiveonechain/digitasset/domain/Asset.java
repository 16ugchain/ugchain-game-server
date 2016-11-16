package com.fiveonechain.digitasset.domain;

import java.util.Date;

/**
 * Created by yuanshichao on 2016/11/16.
 */
public class Asset {

    private int assetId;
    private int userId;
    private int guarId;

    private String name;
    private String description;
    private String certificate;
    private String photos;

    private Date startTime;
    private Date endTime;

    private String evalConclusion;
    private int evalValue;
    private int evalFee;
    private int expEarnings;

    private int status;
}
