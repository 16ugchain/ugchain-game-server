package com.ugc.micropayment.service;

import java.math.BigDecimal;

/**
 * Created by fanjl on 2017/4/5.
 */
public interface AccountService {

    void createAccount(String address);   //account 表插入一条记录;

    boolean isExistsAddress(String address);//account查询账户是否存在;

    boolean isAmountEnough(String address, BigDecimal amount);//account查询是否有足够的份额；
}
