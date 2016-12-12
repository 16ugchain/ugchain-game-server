package com.fiveonechain.digitasset.controller;

import com.fiveonechain.digitasset.auth.UserContext;
import com.fiveonechain.digitasset.controller.cmd.TradeCenterCmd;
import com.fiveonechain.digitasset.domain.Asset;
import com.fiveonechain.digitasset.domain.AssetStatus;
import com.fiveonechain.digitasset.domain.GuaranteeCorp;
import com.fiveonechain.digitasset.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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

    @RequestMapping("/tradeCenter/index")
    public String index(@AuthenticationPrincipal UserContext userContext,
                        Model model) {
        List<Asset> assets = assetService.getAssetListByStatus(AssetStatus.ISSUE);
        List<TradeCenterCmd> tradeCenterCmds = new LinkedList<TradeCenterCmd>();
        for (Asset asset : assets) {
            TradeCenterCmd tradeCenterCmd = new TradeCenterCmd();
            Optional<GuaranteeCorp> guaranteeCorp = guaranteeCorpService.getGuarOptByGuarId(asset.getGuarId());
            if (guaranteeCorp.isPresent()) {
                tradeCenterCmd.setGuarName(guaranteeCorp.get().getCorpName());
            }
            tradeCenterCmd.setAssetName(asset.getName());
            tradeCenterCmd.setAssetId(asset.getAssetId());
            tradeCenterCmd.setAssetEvaluation(String.valueOf(asset.getEvalValue()));
            tradeCenterCmd.setEndTime(asset.getEndTime());
            tradeCenterCmd.setExpectExpEarning(String.valueOf(asset.getExpEarnings()));
            tradeCenterCmd.setAssetPercent(String.valueOf(asset.getFee()));
            tradeCenterCmd.setCirculationShare(asset.getFee());
            tradeCenterCmds.add(tradeCenterCmd);
        }
        model.addAttribute("tradeCenterCmds", tradeCenterCmds);
        return "deal-center";
    }

    @RequestMapping("/assetsDetail/{assetId}")
    public String assetsDetail(@AuthenticationPrincipal UserContext userContext,
                               @PathVariable("assetId") int assetId,
                               Model model) {
        model.addAttribute("assetId", assetId);
        return "asset-details";
    }


}
