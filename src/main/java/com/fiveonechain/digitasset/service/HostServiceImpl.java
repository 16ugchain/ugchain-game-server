package com.fiveonechain.digitasset.service;


import com.fiveonechain.digitasset.auth.UserContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Created by yuanshichao on 2016/12/2.
 */

@Component
public class HostServiceImpl implements HostService {

    @Override
    public UserContext getUserContext() {
        UserContext user = (UserContext)SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return user;
    }
}
