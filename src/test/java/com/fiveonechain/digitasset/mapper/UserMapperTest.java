package com.fiveonechain.digitasset.mapper;

import com.fiveonechain.digitasset.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by yuanshichao on 2016/11/7.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void findById() throws Exception {
        User user = userMapper.findById(1L);
        System.out.println(user);

    }

}