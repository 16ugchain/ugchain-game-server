package com.fiveonechain.digitasset.controller;

import com.fiveonechain.digitasset.auth.UserContext;
import com.fiveonechain.digitasset.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by fanjl on 2016/12/8.
 */
@Controller
public class TradeCenterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TradeCenterController.class);


    @Autowired
    private AssetService assetService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserAssetService userAssetService;

    @Autowired
    private GuaranteeCorpService guaranteeCorpService;

    @Autowired
    private ImageUrlService imageUrlService;


    @RequestMapping("/assetsDetail/{assetId}")
    public String assetsDetail(@AuthenticationPrincipal UserContext userContext,
                               @PathVariable("assetId") int assetId,
                               Model model) {
        model.addAttribute("assetId", assetId);
        return "asset-details";
    }


}
