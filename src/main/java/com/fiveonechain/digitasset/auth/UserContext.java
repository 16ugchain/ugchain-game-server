package com.fiveonechain.digitasset.auth;

import com.fiveonechain.digitasset.domain.UserRoleEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by yuanshichao on 2016/11/8.
 */
public class UserContext {
    private final Integer userId;
    private final String userName;
    private final List<GrantedAuthority> authorities;

    private UserContext(Integer userId, String userName, List<GrantedAuthority> authorities) {
        this.userId = userId;
        this.userName = userName;
        this.authorities = authorities;
    }

    public static UserContext create(Integer userId, String userName, List<GrantedAuthority> authorities) {
        Assert.notNull(userId);
        Assert.notNull(userName);
        Assert.notEmpty(authorities);
        return new UserContext(userId, userName, authorities);
    }

    public static UserContext create(Integer userId) {
        Assert.notNull(userId);
        return new UserContext(userId, null, null);
    }

    public int getUserId() {
        return userId;
    }

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public boolean hasRole(UserRoleEnum role) {
        for (GrantedAuthority authority : authorities) {
            if (authority.toString().equals(role.name())) {
                return true;
            }
        }
        return false;
    }

    public boolean hasRole(UserRoleEnum... roles) {
        for (UserRoleEnum role : roles) {
            if (hasRole(role)) {
                return true;
            }
        }
        return false;
    }
}
