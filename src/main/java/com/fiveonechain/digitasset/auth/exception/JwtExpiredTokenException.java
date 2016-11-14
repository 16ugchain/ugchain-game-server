package com.fiveonechain.digitasset.auth.exception;

import com.fiveonechain.digitasset.auth.token.JwtToken;
import org.springframework.security.core.AuthenticationException;

/**
 * Created by yuanshichao on 2016/11/8.
 */
public class JwtExpiredTokenException extends AuthenticationException {

    private JwtToken token;

    public JwtExpiredTokenException(String msg) {
        super(msg);
    }

    public JwtExpiredTokenException(JwtToken token, String msg, Throwable t) {
        super(msg, t);
        this.token = token;
    }

    public String token() {
        return this.token.getToken();
    }
}
