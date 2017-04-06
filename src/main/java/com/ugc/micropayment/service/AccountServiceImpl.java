package com.fiveonechain.digitasset.service;

import java.math.BigDecimal;

/**
 * Created by fanjl on 2017/4/6.
 */
public class AccountServiceImpl implements AccountService {
    @Override
    public void createAccount(String address) {

    }

    @Override
    public boolean isExistsAddress(String address) {
        return false;
    }

    @Override
    public boolean isAmountEnough(String address, BigDecimal amount) {
        return false;
    }
}
