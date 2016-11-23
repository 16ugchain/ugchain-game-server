package com.fiveonechain.digitasset.controller;

import com.fiveonechain.digitasset.auth.UserContext;
import com.fiveonechain.digitasset.domain.UserAsset;
import com.fiveonechain.digitasset.domain.result.ErrorInfo;
import com.fiveonechain.digitasset.domain.result.Result;
import com.fiveonechain.digitasset.exception.DigitAssetNotFoundException;
import com.fiveonechain.digitasset.service.UserAssetService;
import com.fiveonechain.digitasset.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by yuanshichao on 2016/11/18.
 */

@RestController
public class UserAssetController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAssetController.class);

    @Autowired
    private UserAssetService userAssetService;

    @RequestMapping(value = "/users/self/digitassets", method = RequestMethod.GET)
    public Result getMyDigitAssetList(
            @AuthenticationPrincipal UserContext userContext) {
        List<UserAsset> userAssetList = userAssetService.getDigitAssetListByOwner(userContext.getUserId());
        Result result = ResultUtil.success(userAssetList);
        return result;
    }

    @RequestMapping(value = "/assets/{assetId}/digitassets", method = RequestMethod.GET)
    public Result getDigitAssetListByAsset(
            @AuthenticationPrincipal UserContext userContext,
            @PathVariable("assetId") int assetId) {
        List<UserAsset> userAssetList = userAssetService.getDigitAssetListByAsset(assetId);
        Result result = ResultUtil.success(userAssetList);
        return result;
    }

    @RequestMapping(value = "/users/self/digitassets/{assetId}/tradebalance", method = RequestMethod.PUT)
    public Result setTradeBalance(
            @AuthenticationPrincipal UserContext userContext,
            @PathVariable("assetId") int assetId,
            @RequestParam("amount") int amount) {

        if (amount < 0) {
            throw new IllegalArgumentException("参数不能小于0");
        }

        userAssetService.setTradeBalance(userContext.getUserId(), assetId, amount);
        return ResultUtil.success();
    }

    @ExceptionHandler(DigitAssetNotFoundException.class)
    @ResponseBody
    public Result handleDigitAssetNotFoundException() {
        return ResultUtil.buildErrorResult(ErrorInfo.DIGIT_ASSET_NOT_FOUND);
    }

}
