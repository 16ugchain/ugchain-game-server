package com.fiveonechain.digitasset.service;

import com.fiveonechain.digitasset.auth.UserContext;
import com.fiveonechain.digitasset.domain.User;
import com.fiveonechain.digitasset.domain.UserRoleEnum;

import java.util.Optional;

/**
 * Created by fanjl on 16/11/16.
 */

public interface UserService {

    public User insertAndGetUser(User user);

    boolean updateMobile(User user);

    public User getUserByUserId(int userId);

    boolean isExistsUserName(String userName);

    boolean isExistsTelephone(String telephone);

    User getUserByUserName(String userName);

    boolean checkUserLogin(String userName,String password);

    boolean isUserValid(int userId, UserRoleEnum expectedRole);

    Optional<User> getUserOptional(int userId);

    UserContext getSystemUserContext();

    boolean isSystemUserContext(UserContext user);

    boolean isSystemUserContext(int userId);
}
