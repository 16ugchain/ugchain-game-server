package com.ugc.micropayment.service;

import com.ugc.micropayment.util.Keccak;
import org.junit.Test;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.Web3Sha3;
import org.web3j.protocol.ipc.UnixIpcService;

import static com.ugc.micropayment.util.HexUtil.getHex;
import static com.ugc.micropayment.util.Parameters.KECCAK_256;

/**
 * Created by fanjl on 2017/4/7.
 */
public class TransactionRecordServiceImplTest {
    @Test
    public void verifySigned() throws Exception {
        Web3j web3 = Web3j.build(new UnixIpcService("/Users/fanjl/eth/testnet/data/geth.ipc"));
        StringBuilder data = new StringBuilder();
        data.append("0xadfc0262bbed8c1f4bd24a4a763ac616803a8c54").append(4).append("123");
        Request<?, Web3Sha3> hashData = web3.web3Sha3(data.toString());
        String s = getHex("abc".getBytes());

        Keccak keccak = new Keccak();

        System.out.println("keccak-256 = " + keccak.getHash(s, KECCAK_256));

    }

}