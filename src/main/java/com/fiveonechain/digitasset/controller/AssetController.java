package com.fiveonechain.digitasset.controller;

import com.fiveonechain.digitasset.auth.UserContext;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * Created by yuanshichao on 2016/11/16.
 */

@RestController
public class AssetController {


    /**
     * 登记实物资产
     *
     * @param userContext
     * @param name
     * @param description
     * @param certificate
     * @param photos
     * @param guaranteeId
     * @param guaranteeCycle
     */
    @RequestMapping(value = "assets", method = RequestMethod.POST)
    public void submitAsset(
            @AuthenticationPrincipal UserContext userContext,
            @RequestParam("name") String name,
            @RequestParam("desc") String description,
            @RequestParam("cert") String certificate,
            @RequestParam(value = "photos", required = false) String photos,
            @RequestParam(value = "guarId", required = false) Integer guaranteeId,
            @RequestParam(value = "guarCycle", required = false) Integer guaranteeCycle) {


    }

    /**
     * 获取实物资产详情
     *
     * @param userContext
     * @param assetId
     */
    @RequestMapping(value = "assets/{assetId}", method = RequestMethod.GET)
    public void getAssetDetail(
            @AuthenticationPrincipal UserContext userContext,
            @PathVariable("assetId") int assetId) {

    }

    /**
     * 评估资产
     *
     * @param userContext ROLE_GUAR
     * @param result
     * @param conclusion
     * @param value
     * @param fee
     * @param earnings
     */
    @RequestMapping(value = "assets/{assetId}/evaluate", method = RequestMethod.POST)
    public void evaluateAsset(
            @AuthenticationPrincipal UserContext userContext,
            @PathVariable("assetId") int assetId,
            @RequestParam("result") boolean result,
            @RequestParam(value = "conclusion", required = false) String conclusion,
            @RequestParam(value = "value", required = false) Integer value,
            @RequestParam(value = "fee", required = false) Integer fee,
            @RequestParam(value = "earnings", required = false) Integer earnings) {

    }

    /**
     * 发行资产
     *
     * @param userContext
     * @param assetId
     */
    @RequestMapping(value = "assets/{assetId}/issue", method = RequestMethod.POST)
    public void issueAsset(
            @AuthenticationPrincipal UserContext userContext,
            @PathVariable("assetId") int assetId) {

    }

    /**
     * 冻结资产
     *
     * @param userContext ROLE_GUAR
     * @param assetId
     */
    @RequestMapping(value = "assets/{assetId}/freeze", method = RequestMethod.POST)
    public void freezeAsset(
            @AuthenticationPrincipal UserContext userContext,
            @PathVariable("assetId") int assetId) {

    }

    /**
     * 解冻资产
     *
     * @param userContext ROLE_GUAR
     * @param assetId
     */
    @RequestMapping(value = "assets/{assetId}/unfreeze", method = RequestMethod.POST)
    public void unfreezeAsset(
        @AuthenticationPrincipal UserContext userContext,
        @PathVariable("assetId") int assetId) {

    }


}
