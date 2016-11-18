package com.fiveonechain.digitasset;

import org.junit.Test;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

/**
 * Created by yuanshichao on 2016/11/17.
 */
public class PasswordTest {


    @Test
    public void test() {
        String rawPassword = "123456";
        Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder();
        String password = encoder.encode(rawPassword);
        System.out.println(password);
        // ac56dbad627c7d96493279e653365429703407efbf3d3eab0a786821bb42c24f5cbb84c32454d777
        boolean r = encoder.matches(rawPassword, password);
        System.out.println(r);
    }
}
