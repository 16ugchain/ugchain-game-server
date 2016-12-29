package com.fiveonechain.digitasset.service;

import com.fiveonechain.digitasset.domain.UserInfo;
import com.fiveonechain.digitasset.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by fanjl on 16/11/17.
 */
@Component
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Override
    public int insertAndGetUserAuth(UserInfo userInfo) {
        return userInfoMapper.insertUserAuth(userInfo);
    }

    @Override
    public boolean bindCreditCard(UserInfo userInfo) {
        return userInfoMapper.bindCreditCard(userInfo);
    }


    @Override
    public UserInfo getUserInfoByUserId(int userId) {
        return userInfoMapper.findUserInfoByUserId(userId);
    }

    @Override
    public boolean updateIcon(int imgId,int userId) {
        return userInfoMapper.updateUserIcon(imgId,userId);
    }

    @Override
    public boolean updateUserInfo(UserInfo userInfo) {
        return userInfoMapper.updateUserInfo(userInfo);
    }




    @Override
    public boolean isExistsSameID(String identity) {
        return userInfoMapper.isIdentityExists(identity);
    }

    @Override
    public Optional<UserInfo> getUserInfoOptional(int userId) {
        UserInfo userInfo = userInfoMapper.findUserInfoByUserId(userId);
        return Optional.ofNullable(userInfo);
    }

    @Override
    public boolean isExistsUserAuth(int userId) {
        return userInfoMapper.isExistsUserAuth(userId);
    }
}
