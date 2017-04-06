package com.fiveonechain.digitasset.service;

import java.math.BigDecimal;

/**
 * Created by fanjl on 2017/4/6.
 */
public class TransactionRecordServiceImpl implements TransactionRecordService {
    @Override
    public void recharge(String address, BigDecimal amount) {

    }

    @Override
    public void transfer(String fromAddress, String toAddress, BigDecimal amount, int nonce, String signedMsg, String msg) {

    }

    @Override
    public void withDraw(String address, String signedMsg, BigDecimal amount, int nonce, String msg) {

    }

    @Override
    public boolean verifySigned(String address, String signedMsg, String msg, int nonce) {
        return false;
    }
}
