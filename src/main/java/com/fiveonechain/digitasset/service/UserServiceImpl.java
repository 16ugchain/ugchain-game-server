package com.fiveonechain.digitasset.service;

import com.fiveonechain.digitasset.domain.User;
import com.fiveonechain.digitasset.mapper.SequenceMapper;
import com.fiveonechain.digitasset.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by fanjl on 16/11/16.
 */
@Component
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    private SequenceMapper sequenceMapper;
    private final String SEQUENCE_NAME="USER";

    Pbkdf2PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder();


    @Override
    @Transactional
    public User insertAndGetUser(User user) {
        int userId = sequenceMapper.nextId(SEQUENCE_NAME);
        user.setUser_id(Long.valueOf(userId));
        userMapper.insertUser(user);
        User userGet = userMapper.findByUserId(Long.valueOf(userId));
        return userGet;
    }


    @Override
    public User getUserByUserId(Long userId) {
        User user = userMapper.findByUserId(userId);
        return user;
    }

    @Override
    public boolean isExistsUserName(String user_name) {
        return userMapper.isExistsUserName(user_name);
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
}
