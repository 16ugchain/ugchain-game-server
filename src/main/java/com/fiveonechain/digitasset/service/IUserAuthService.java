package com.fiveonechain.digitasset.service;

import com.fiveonechain.digitasset.domain.UserAuth;

/**
 * Created by fanjl on 16/11/17.
 */
public interface IUserAuthService {
    boolean isExistsSameID(String idCard);

    boolean isExistsUserAuth(int user_id);

    int insertAndGetUserAuth(UserAuth userAuth);

    boolean bindCreditCard(UserAuth userAuth);

    UserAuth getUserAuthByUserId(Long userId);
}
