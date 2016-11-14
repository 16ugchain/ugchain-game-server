package com.fiveonechain.digitasset.controller;

import com.fiveonechain.digitasset.auth.UserContext;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yuanshichao on 2016/11/8.
 */

@RestController
public class AuthController {


    @RequestMapping(value = "/test_page", method = RequestMethod.GET)
    public UserContext test(@AuthenticationPrincipal UserContext user) {
        return user;
    }

    @RequestMapping(value = "/auth/login")
    public String login() {
        return "login";
    }
}
