package com.fiveonechain.digitasset.service;

import com.fiveonechain.digitasset.domain.UserAuth;
import com.fiveonechain.digitasset.mapper.UserAuthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by fanjl on 16/11/17.
 */
@Component
public class UserAuthServiceImpl implements IUserAuthService{
    @Autowired
    private UserAuthMapper userAuthMapper;
    @Override
    public int insertAndGetUserAuth(UserAuth userAuth) {
        return userAuthMapper.insertUserAuth(userAuth);
    }

    @Override
    public UserAuth getUserAuthByUserId(Long userId) {
        return null;
    }

    @Override
    public boolean isExistsSameID(String identity) {
        return userAuthMapper.isIdentityExists(identity);
    }

    @Override
    public boolean isExistsUserAuth(int user_id) {
        return userAuthMapper.isExistsUserAuth(user_id);
    }
}
