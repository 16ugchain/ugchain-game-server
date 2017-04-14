package com.ugc.micropayment.service;

import com.ugc.micropayment.configuration.ConfigurationTest;
import com.ugc.micropayment.util.Keccak;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthSign;
import org.web3j.protocol.core.methods.response.Web3Sha3;
import org.web3j.protocol.ipc.UnixIpcService;
import org.web3j.protocol.parity.Parity;
import org.web3j.protocol.parity.methods.response.PersonalUnlockAccount;

import static com.ugc.micropayment.util.HexUtil.getHex;
import static com.ugc.micropayment.util.Parameters.KECCAK_256;

/**
 * Created by fanjl on 2017/4/7.
 */
@SpringBootTest(classes = {ConfigurationTest.class})
@RunWith(SpringRunner.class)
public class TransactionRecordServiceImplTest {
    private TransactionRecordServiceImpl transactionRecordService;
    @Autowired
    private AccountService accountService;


    Web3j web3j;
    Parity parity;
   /* @Before
    public void initWeb3J() throws Exception {
         web3j = Web3j.build(new UnixIpcService("/Users/fanjl/eth/testnet/data/geth.ipc"));
         parity = Parity.build(new UnixIpcService("/Users/fanjl/eth/testnet/data/geth.ipc"));
    }*/
    @Test
    public void createAccount() throws Exception {
        accountService.createAccount("adfc0262bbed8c1f4bd24a4a763ac616803a8c54");
    }

    @Test
    public void recharge() throws Exception {
    }

    @Test
    public void transfer() throws Exception {

    }

    @Test
    public void withDraw() throws Exception {

    }

    @Test
    public void verifySigned() throws Exception {
        StringBuilder data = new StringBuilder();
        data.append("0xadfc0262bbed8c1f4bd24a4a763ac616803a8c54").append(4).append("123");
        Request<?, Web3Sha3> hashData = web3j.web3Sha3(data.toString());
        String s = getHex("abc".getBytes());

        Keccak keccak = new Keccak();

        System.out.println("keccak-256 = " + keccak.getHash(s, KECCAK_256));
        Request<?,EthSign> ethSignRequest =web3j.ethSign("0xadfc0262bbed8c1f4bd24a4a763ac616803a8c54","0x"+keccak.getHash(s, KECCAK_256));
        PersonalUnlockAccount personalUnlockAccount = parity.personalUnlockAccount("0xadfc0262bbed8c1f4bd24a4a763ac616803a8c54", "123").sendAsync().get();
        if (personalUnlockAccount.accountUnlocked()) {
            // send a transaction, or use parity.personalSignAndSendTransaction() to do it all in one
            EthSign ethSign = ethSignRequest.send();
            System.out.println(ethSign.getSignature());
        }
    }

}