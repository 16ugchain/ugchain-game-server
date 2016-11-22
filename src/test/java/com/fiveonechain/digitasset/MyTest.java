package com.fiveonechain.digitasset;

import com.fiveonechain.digitasset.exception.NoEnoughTradeBalanceException;
import org.junit.Test;

/**
 * Created by yuanshichao on 2016/11/22.
 */
public class MyTest {

    @Test
    public void test() {
        NoEnoughTradeBalanceException e = new NoEnoughTradeBalanceException(2, 3);

        System.out.println(e.getMessage());
    }
}
