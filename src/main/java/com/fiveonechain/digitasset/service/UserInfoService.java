package com.fiveonechain.digitasset.service;

import com.fiveonechain.digitasset.domain.UserInfo;

/**
 * Created by fanjl on 16/11/17.
 */
public interface UserInfoService {
    boolean isExistsSameID(String idCard);

    boolean isExistsUserAuth(int userId);

    int insertAndGetUserAuth(UserInfo userInfo);

    boolean bindCreditCard(UserInfo userInfo);

    UserInfo getUserInfoByUserId(int userId);
}
