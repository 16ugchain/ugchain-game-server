package com.ugc.micropayment.service;

import com.ugc.micropayment.config.AppConfig;
import com.ugc.micropayment.domain.BlockRecord;
import com.ugc.micropayment.domain.OrderStatusEnum;
import com.ugc.micropayment.domain.OrderTypeEnum;
import com.ugc.micropayment.mapper.BlockRecordMapper;
import com.ugc.micropayment.mapper.InnerRecordMapper;
import com.ugc.micropayment.mapper.SequenceMapper;
import com.ugc.micropayment.util.Keccak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.ipc.UnixIpcService;

import java.math.BigDecimal;

import static com.ugc.micropayment.util.Parameters.KECCAK_256;

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

    private Web3j web3;

    private Keccak keccak = new Keccak();

    @Override
    public void initWeb3J() {
        web3 = Web3j.build(new UnixIpcService(appConfig.getGethLocation()));
    }

    @Override
    public int nextTransactionId() {
        return sequenceMapper.nextId(sequenceMapper.BLOCK_RECORD);
    }

    @Override
    public String keccakHash(String data) {
        return   keccak.getHash(data.toString(),KECCAK_256);
    }

    @Override
    @Transactional
    public void recharge(String address, String transactionId,BigDecimal amount) {
        initWeb3J();
        // TODO: 2017/4/7 监听收钱合约，为每个充值账户建立托管账户并充值。
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
    @Transactional
    public void transfer(String fromAddress, String toAddress, BigDecimal amount, int nonce, String signedMsg, String msg) {

    }

    @Override
    public void withDraw(String address, String signedMsg, BigDecimal amount, int nonce, String msg) {
        // TODO: 2017/4/7 转账交易sdk，提现即从公司账户向目标账户提现
    }

    @Override
    public boolean verifySigned(String address, String signedMsg, String msg, int nonce) {
        StringBuilder data = new StringBuilder();
        data.append(address).append(nonce).append(msg);
        String hashData = keccakHash(data.toString());
        // TODO: 2017/4/7  验证以太坊签名
        return false;
    }
}
