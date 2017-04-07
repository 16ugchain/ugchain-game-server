package com.ugc.micropayment.service;

import com.ugc.micropayment.config.AppConfig;
import com.ugc.micropayment.domain.BlockRecord;
import com.ugc.micropayment.domain.OrderStatusEnum;
import com.ugc.micropayment.domain.OrderTypeEnum;
import com.ugc.micropayment.mapper.BlockRecordMapper;
import com.ugc.micropayment.mapper.InnerRecordMapper;
import com.ugc.micropayment.mapper.SequenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Created by fanjl on 2017/4/6.
 */
public class TransactionRecordServiceImpl implements TransactionRecordService {
    @Autowired
    private AccountService accountService;
    @Autowired
    private InnerRecordMapper innerRecordMapper;
    @Autowired
    private BlockRecordMapper blockRecordMapper;
    @Autowired
    private SequenceMapper sequenceMapper;
    @Autowired
    private AppConfig appConfig;

    @Override
    public int nextTransactionId() {
        return sequenceMapper.nextId(sequenceMapper.BLOCK_RECORD);
    }

    @Override
    @Transactional
    public void recharge(String address, BigDecimal amount) {
        if(accountService.isExistsAddress(address)){
            BlockRecord blockRecord = new BlockRecord();
            blockRecord.setAmount(amount);
            blockRecord.setBlockRecordId(nextTransactionId());
            blockRecord.setFee(appConfig.getFee());
            blockRecord.setTargetAddress(address);
            blockRecord.setType(OrderTypeEnum.RECHARGE.getId());
            blockRecord.setStatus(OrderStatusEnum.PENDING.getId());
            blockRecordMapper.insertBlockRecord(blockRecord);
        }else{
            accountService.createAccount(address);
        }
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
