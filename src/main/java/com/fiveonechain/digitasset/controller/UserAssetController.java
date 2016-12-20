package com.fiveonechain.digitasset.controller;

import com.fiveonechain.digitasset.auth.UserContext;
import com.fiveonechain.digitasset.domain.Asset;
import com.fiveonechain.digitasset.domain.User;
import com.fiveonechain.digitasset.domain.UserAsset;
import com.fiveonechain.digitasset.domain.result.DigitAssetItem;
import com.fiveonechain.digitasset.domain.result.ErrorInfo;
import com.fiveonechain.digitasset.domain.result.Result;
import com.fiveonechain.digitasset.exception.DigitAssetNotFoundException;
import com.fiveonechain.digitasset.service.AssetService;
import com.fiveonechain.digitasset.service.UserAssetService;
import com.fiveonechain.digitasset.service.UserService;
import com.fiveonechain.digitasset.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Created by yuanshichao on 2016/11/18.
 */

@RestController
public class UserAssetController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAssetController.class);

    @Autowired
    private AssetService assetService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserAssetService userAssetService;

    /*
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
    */

    @RequestMapping(value = "/assets/{assetId}/tradedigitassets", method = RequestMethod.GET)
    public Result getAvailDigitAssetListByAsset(
            @AuthenticationPrincipal UserContext userContext,
            @PathVariable("assetId") int assetId) {

        Optional<Asset> assetOpt = assetService.getAssetOptional(assetId);
        if (!assetOpt.isPresent()) {
            return ResultUtil.buildErrorResult(ErrorInfo.ASSET_NOT_FOUND);
        }
        Asset asset = assetOpt.get();

        List<UserAsset> userAssetList = userAssetService.getAvailDigitAssetListByAsset(assetId);
        if (userAssetList.isEmpty()) {
            return ResultUtil.success(Collections.emptyList());
        }
        List<DigitAssetItem> shareList = new  LinkedList<>();
        for (UserAsset userAsset : userAssetList) {
            DigitAssetItem share = new DigitAssetItem();
            share.setAssetId(assetId);
            share.setAvailShare(userAsset.getTradeBalance());
            share.setOwnerId(userAsset.getUserId());
            float num= (float)(userAsset.getTradeBalance()*100)/asset.getEvalValue();
            DecimalFormat df = new DecimalFormat("0.00");//格式化小数
            String percent = df.format(num);
            share.setPercent(percent);
            Optional<User> userOpt = userService.getUserOptional(userAsset.getUserId());
            if (!userOpt.isPresent()) {
                LOGGER.error("{} user {} NOT FOUND", ErrorInfo.SERVER_ERROR, userAsset.getUserId());
                return ResultUtil.buildErrorResult(ErrorInfo.SERVER_ERROR);
            }
            share.setOwnerName(userOpt.get().getUserName());
            shareList.add(share);
        }
        return ResultUtil.success(shareList);
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
