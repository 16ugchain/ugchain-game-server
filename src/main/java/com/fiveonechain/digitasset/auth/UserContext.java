package com.fiveonechain.digitasset.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by yuanshichao on 2016/11/8.
 */
public class UserContext {
    private final Long userId;
    private final String userName;
    private final List<GrantedAuthority> authorities;

    private UserContext(Long userId, String userName, List<GrantedAuthority> authorities) {
        this.userId = userId;
        this.userName = userName;
        this.authorities = authorities;
    }

    public static UserContext create(Long userId, String userName, List<GrantedAuthority> authorities) {
        Assert.notNull(userId);
        Assert.notNull(userName);
        Assert.notEmpty(authorities);
        return new UserContext(userId, userName, authorities);
    }

    public Long getUserId() {
        return userId;
    }

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
