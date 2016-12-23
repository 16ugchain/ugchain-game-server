package com.fiveonechain.digitasset.controller;

import com.fiveonechain.digitasset.auth.UserContext;
import com.fiveonechain.digitasset.domain.*;
import com.fiveonechain.digitasset.domain.result.DigitalAssetCmd;
import com.fiveonechain.digitasset.service.*;
import com.fiveonechain.digitasset.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Created by fanjl on 2016/12/8.
 */
@Controller
@RequestMapping("/digitalCenter")
public class DigitalAssetController {
    @Autowired
    private AssetService assetService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserAssetService userAssetService;

    @Autowired
    private GuaranteeCorpService guaranteeCorpService;
    @RequestMapping("/index")
    public String index(@AuthenticationPrincipal UserContext userContext,
                        Model model){
        List<UserAsset> userAssets = userAssetService.getDigitAssetListByOwner(userContext.getUserId());
        List<DigitalAssetCmd> digitalAssetCmds = new LinkedList<DigitalAssetCmd>();
        for(UserAsset userAsset : userAssets){
            DigitalAssetCmd digitalAssetCmd = new DigitalAssetCmd();
            Optional<Asset> assetOpt = assetService.getAssetOptional(userAsset.getAssetId());
            if(assetOpt.isPresent()){
                Asset asset = assetOpt.get();
                digitalAssetCmd.setTradeShare(userAsset.getTradeBalance());
                digitalAssetCmd.setAssetName(asset.getName());
                digitalAssetCmd.setEndTime(asset.getEndTime());

                digitalAssetCmd.setEndTimeStr(DateUtil.formatDate(asset.getEndTime(),DateUtil.HC_DATETIME));
                digitalAssetCmd.setStatus(asset.getStatus());
                UserInfo userInfo = userInfoService.getUserInfoByUserId(asset.getUserId());
                digitalAssetCmd.setPublishName(userInfo.getRealName());
                digitalAssetCmd.setStatusStr(AssetStatus.fromValue(asset.getStatus()).getMessage());
                Optional<GuaranteeCorp> guaranteeCorp = guaranteeCorpService.getGuarOptByGuarId(asset.getGuarId());
                if(guaranteeCorp.isPresent()){
                    digitalAssetCmd.setGuarName(guaranteeCorp.get().getCorpName());
                }

                Optional<UserInfo> issuerInfo = userInfoService.getUserInfoOptional(asset.getUserId());
                if(issuerInfo.isPresent()){
                    digitalAssetCmd.setIssuerInfo(issuerInfo.get());
                }

                Optional<User> issue = userService.getUserOptional(asset.getUserId());
                if(issue.isPresent()){
                    digitalAssetCmd.setTelephone(issue.get().getTelephone());
                }

                digitalAssetCmd.setHoldShare(userAsset.getBalance());
                digitalAssetCmd.setLockedShare(userAsset.getLockBalance());
                if (asset.getStatus() == AssetStatus.ISSUE.getCode()
                        && userContext.getUserId() != asset.getUserId()) {
                    digitalAssetCmd.setWhollyOwner(userAssetService.hasEnoughDigitAsset(userAsset, asset.getEvalValue()));
                }

            }
            digitalAssetCmd.setAssetId(userAsset.getAssetId());
            digitalAssetCmds.add(digitalAssetCmd);
        }
        model.addAttribute("digitalAssetCmds",digitalAssetCmds);
        return "digital-property";
    }
}
