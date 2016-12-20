package com.fiveonechain.digitasset.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by fanjl on 2016/12/7.
 */
@Controller
public class IndexController {
    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }
}
