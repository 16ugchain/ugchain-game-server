package com.fiveonechain.digitasset.controller;

import com.fiveonechain.digitasset.auth.UserContext;
import com.fiveonechain.digitasset.domain.Asset;
import com.fiveonechain.digitasset.domain.AssetStatus;
import com.fiveonechain.digitasset.domain.result.ErrorInfo;
import com.fiveonechain.digitasset.domain.result.Result;
import com.fiveonechain.digitasset.exception.AssetNotFoundException;
import com.fiveonechain.digitasset.exception.AssetStatusTransferException;
import com.fiveonechain.digitasset.service.AssetService;
import com.fiveonechain.digitasset.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by yuanshichao on 2016/11/16.
 */

@RestController
public class AssetController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssetController.class);

    @Autowired
    private AssetService assetService;

    /**
     * 登记实物资产
     *
     */
    @RequestMapping(value = "assets", method = RequestMethod.POST)
    public Result submitAsset(
            @AuthenticationPrincipal UserContext userContext,
            @RequestParam("name") String name,
            @RequestParam("desc") String description,
            @RequestParam("cert") String certificate,
            @RequestParam("value") int value,
            @RequestParam("cycle") int cycle,
            @RequestParam(value = "photos", required = false, defaultValue = "") String photos,
            @RequestParam(value = "guarId", required = false) Integer guarId) {

        int userId = userContext.getUserId();
        int assetId = assetService.nextAssetId();

        LocalDateTime currentTime = LocalDateTime.now();

        Date startTime = Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant());
        Date endTime = Date.from(currentTime.plusDays(cycle).atZone(ZoneId.systemDefault()).toInstant());

        Asset asset = new Asset();
        asset.setAssetId(assetId);
        asset.setUserId(userId);
        asset.setName(name);
        asset.setDescription(description);
        asset.setCertificate(certificate);
        asset.setPhotos(photos);
        asset.setStartTime(startTime);
        asset.setEndTime(endTime);

        boolean needGuar = false;

        // TODO 检查guarId是否有效
        if (guarId != null) {
            needGuar = true;
            asset.setGuarId(guarId);
        }

        assetService.createAsset(asset, needGuar);

        return ResultUtil.success();
    }

    /**
     * 获取实物资产详情
     *
     * @param userContext
     * @param assetId
     */
    @RequestMapping(value = "assets/{assetId}", method = RequestMethod.GET)
    public Result getAssetDetail(
            @AuthenticationPrincipal UserContext userContext,
            @PathVariable("assetId") int assetId) {

        Asset asset = assetService.getAsset(assetId);
        if (asset == null) {
            return ResultUtil.buildErrorResult(ErrorInfo.ASSET_NOT_FOUND);
        }
        return ResultUtil.success(asset);
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
    public Result evaluateAsset(
            @AuthenticationPrincipal UserContext userContext,
            @PathVariable("assetId") int assetId,
            @RequestParam("result") boolean result,
            @RequestParam(value = "conclusion", required = false) String conclusion,
            @RequestParam(value = "value", required = false) Integer value,
            @RequestParam(value = "fee", required = false) Integer fee,
            @RequestParam(value = "earnings", required = false) Integer earnings) {

        if (result) {
            assetService.updateAssetStatusStateMachine(assetId, AssetStatus.REJECT_EVALUATE);
        } else {
            Asset asset = new Asset();
            asset.setAssetId(assetId);
            asset.setEvalConclusion(conclusion);
            asset.setEvalValue(value);
            asset.setFee(fee);
            asset.setExpEarnings(earnings);

            assetService.updateAssetEvalInfo(asset);
            assetService.updateAssetStatusStateMachine(assetId, AssetStatus.PASS_EVALUATE);
        }

        return ResultUtil.success();
    }

    /**
     * 发行资产
     *
     * @param userContext
     * @param assetId
     */
    @RequestMapping(value = "assets/{assetId}/issue", method = RequestMethod.POST)
    public Result issueAsset(
            @AuthenticationPrincipal UserContext userContext,
            @PathVariable("assetId") int assetId) {

        // TODO check pay order
        // TODO check assetId

        assetService.updateAssetStatusStateMachine(assetId, AssetStatus.ISSUE);
        return ResultUtil.success();
    }

    /**
     * 冻结资产
     *
     * @param userContext ROLE_GUAR
     * @param assetId
     */
    @RequestMapping(value = "assets/{assetId}/freeze", method = RequestMethod.POST)
    public Result freezeAsset(
            @AuthenticationPrincipal UserContext userContext,
            @PathVariable("assetId") int assetId) {

        // TODO check assetId

        assetService.updateAssetStatusStateMachine(assetId, AssetStatus.FROZEN);
        return ResultUtil.success();
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

    @ExceptionHandler(AssetStatusTransferException.class)
    @ResponseBody
    public Result handleAssetStatusTransferException() {
        return ResultUtil.buildErrorResult(ErrorInfo.ASSET_STATUS_TRANSFER_ERROR);
    }

    @ExceptionHandler(AssetNotFoundException.class)
    @ResponseBody
    public Result handleAssetNotFoundException() {
        return ResultUtil.buildErrorResult(ErrorInfo.ASSET_NOT_FOUND);
    }
}
