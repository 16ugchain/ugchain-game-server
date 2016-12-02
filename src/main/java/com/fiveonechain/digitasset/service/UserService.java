package com.fiveonechain.digitasset.service;

import com.fiveonechain.digitasset.domain.User;
import com.fiveonechain.digitasset.domain.UserRoleEnum;

/**
 * Created by fanjl on 16/11/16.
 */

public interface UserService {

    public User insertAndGetUser(User user);

    boolean updateMobile(User user);

    public User getUserByUserId(int userId);

    boolean isExistsUserName(String user_name);

    boolean isExistsTelephone(String telephone);

    User getUserByUserName(String user_name);

    boolean checkUserLogin(String user_name,String password);

    boolean isUserValid(int userId, UserRoleEnum expectedRole);

}
