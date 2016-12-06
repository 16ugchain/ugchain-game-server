package com.fiveonechain.digitasset.service;

import com.fiveonechain.digitasset.auth.UserContext;
import com.fiveonechain.digitasset.domain.User;
import com.fiveonechain.digitasset.domain.UserRoleEnum;
import com.fiveonechain.digitasset.mapper.SequenceMapper;
import com.fiveonechain.digitasset.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Optional;

/**
 * Created by fanjl on 16/11/16.
 */
@Component
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final int SYSTEM_USER_ID = 0;
    private static final UserContext SYSTEM_USER_CONTEXT = UserContext.create(SYSTEM_USER_ID);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SequenceMapper sequenceMapper;

    Pbkdf2PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder();

    @Override
    @Transactional
    public User insertAndGetUser(User user) {
        int userId = sequenceMapper.nextId(sequenceMapper.USER);
        user.setUser_id(userId);
        userMapper.insertUser(user);
        User userGet = userMapper.findByUserId(userId);
        return userGet;
    }

    @Override
    public boolean updateMobile(User user) {
        return userMapper.updateMobile(user);
    }


    @Override
    public User getUserByUserId(int userId) {
        User user = userMapper.findByUserId(userId);
        return user;
    }

    @Override
    public boolean isExistsUserName(String user_name) {
        return userMapper.isExistsUserName(user_name);
    }

    @Override
    public boolean isExistsTelephone(String telephone) {
        return userMapper.isExistsTelephone(telephone);
    }

    @Override
    public User getUserByUserName(String user_name) {
        return userMapper.getUserByUserName(user_name);
    }

    @Override
    public boolean checkUserLogin(String user_name, String password) {
        User user = getUserByUserName(user_name);
        String pwd = user.getPassword();
        if(passwordEncoder.matches(password,pwd)){
            return true;
        }
        return false;
    }

    @Override
    public boolean isUserValid(int userId, UserRoleEnum expectedRole) {
        User user = userMapper.findByUserId(userId);
        if (user != null && user.getRole() == expectedRole.getId()) {
            return true;
        }
        return false;
    }

    @Override
    public Optional<User> getUserOptional(int userId) {
        User user = userMapper.findByUserId(userId);
        return Optional.ofNullable(user);
    }

    @Override
    public UserContext getSystemUserContext() {
        return SYSTEM_USER_CONTEXT;
    }

    @Override
    public boolean isSystemUserContext(UserContext user) {
        return user == SYSTEM_USER_CONTEXT;
    }

    @Override
    public boolean isSystemUserContext(int userId) {
        return userId == SYSTEM_USER_ID;
    }
}
