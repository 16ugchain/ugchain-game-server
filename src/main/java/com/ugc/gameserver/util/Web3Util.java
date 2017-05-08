package com.ugc.gameserver.util;

import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint64;

import java.math.BigInteger;

/**
 * Created by fanjl on 2017/5/8.
 */
public class Web3Util {

    public static Uint64 toUint64(int src){
        BigInteger bigInteger = BigInteger.valueOf(new Integer(src).intValue());
        Uint64 uint64 = new Uint64(bigInteger);
        return uint64;
    }

    public static Uint256 toUint256(int src){
        BigInteger bigInteger = BigInteger.valueOf(new Integer(src).intValue());
        Uint256 uint256 = new Uint256(bigInteger);
        return uint256;
    }

    public static Bytes32 toBytes32(String src){
        return new Bytes32(hexStringToByte32(src.substring(2)));
    }

    public static byte[] hexStringToByte32(String s) {
        int len = s.length();
        byte[] data = new byte[32];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

}
