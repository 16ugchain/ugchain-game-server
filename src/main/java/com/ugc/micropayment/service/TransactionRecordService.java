package com.ugc.micropayment.service;

import java.math.BigDecimal;

/**
 * Created by fanjl on 2017/4/5.
 */
public interface TransactionRecordService {
    int nextTransactionId();

    void recharge(String address, BigDecimal amount); //block_record 表插入充值记录;

    void transfer(String fromAddress,String toAddress,BigDecimal amount,int nonce,String signedMsg, String msg);//交易记录，需要从account获取nonce,并且验证签名消息

    void withDraw(String address,String signedMsg,BigDecimal amount,int nonce,String msg);//提现，需要验证签名消息，并获取nonce

    boolean verifySigned(String address,String signedMsg,String msg,int nonce);// 验证签名信息，客户端签名方式:address=以太坊地址&r=randomInt()&msg=交易内容 key按照字典序排列后hash,然后用以太坊
    //私钥签名。服务端调用以太坊java库验证签名，还原出明文是否一致。
}
