package com.fiveonechain.digitasset.controller;

import com.fiveonechain.digitasset.auth.UserContext;
import com.fiveonechain.digitasset.domain.*;
import com.fiveonechain.digitasset.domain.result.ErrorInfo;
import com.fiveonechain.digitasset.domain.result.Result;
import com.fiveonechain.digitasset.exception.AssetOrderException;
import com.fiveonechain.digitasset.service.AssetOrderService;
import com.fiveonechain.digitasset.service.AssetService;
import com.fiveonechain.digitasset.service.UserAssetService;
import com.fiveonechain.digitasset.service.UserService;
import com.fiveonechain.digitasset.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

/**
 * Created by fanjl on 16/11/21.
 */
@RestController
@RequestMapping("assetOrder")
public class AssetOrderController {

    @Autowired
    private AssetOrderService assetOrderService;

    @Autowired
    private AssetService assetService;

    @Autowired
    private UserAssetService userAssetService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "createOrder", method = RequestMethod.POST)
    public Result sellOrder(
            @AuthenticationPrincipal UserContext host,
            @RequestParam("userId") int userId,
            @RequestParam("assetId") int assetId,
            @RequestParam("amount") int amount,
            @RequestParam("unitPrice") double unitPrice) {


        if (unitPrice <= 0) {
            throw new IllegalArgumentException("每股出价异常");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("认购份额异常");
        }

        if (!host.hasRole(UserRoleEnum.USER_PUBLISHER, UserRoleEnum.USER_ASSIGNEE)) {
            return ResultUtil.buildErrorResult(ErrorInfo.USER_PERMISSION_DENIED);
        }

        Optional<Asset> assetOpt = assetService.getAssetOptional(assetId);
        if (!assetOpt.isPresent()) {
            return ResultUtil.buildErrorResult(ErrorInfo.ASSET_NOT_FOUND);
        }
        Asset asset = assetOpt.get();

        Optional<UserAsset> userAssetOpt = userAssetService.getDigitAssetOptional(userId, assetId);
        if (!userAssetOpt.isPresent()) {
            return ResultUtil.buildErrorResult(ErrorInfo.DIGIT_ASSET_NOT_FOUND);
        }
        UserAsset userAsset = userAssetOpt.get();

        if (!userAssetService.hasEnoughDigitAsset(userAsset, amount)) {
            return ResultUtil.buildErrorResult(ErrorInfo.DIGIT_ASSET_NOT_ENOUGH);
        }


        int orderId = assetOrderService.nextOrderId();

        AssetOrder assetOrder = new AssetOrder();
        assetOrder.setOrderId(orderId);
        assetOrder.setUserId(userId);
        assetOrder.setAssetId(assetId);
        assetOrder.setBuyerId(host.getUserId());
        assetOrder.setAmount(amount);
        assetOrder.setUnitPrices(BigDecimal.valueOf(unitPrice));
        assetOrder.setStatus(AssetOrderStatusEnum.APPLY.getId());

        assetOrderService.createAssetOrder(assetOrder);

        return ResultUtil.success();
    }

    @RequestMapping(value = "/{assetOrderId}", method = RequestMethod.GET)
    public Result getAssetOrderDetail(
            @AuthenticationPrincipal UserContext userContext,
            @PathVariable("assetOrderId") int assetOrderId) {

        AssetOrder assetOrder = assetOrderService.getAssetOrder(assetOrderId);
        if (assetOrder == null) {
            return ResultUtil.buildErrorResult(ErrorInfo.ORDER_NOT_FOUND);
        }
        return ResultUtil.success(assetOrder);
    }

    /**申请状态订单的驳回或者同意操作
     * @param assetOrderId
     * @param result
     * @return
     */
    @RequestMapping(value = "{assetOrderId}/apply", method = RequestMethod.POST)
    public Result ApplyOrder(
            @PathVariable("assetOrderId") int assetOrderId,
            @RequestParam("result") boolean result
            ) {
        AssetOrder assetOrder = assetOrderService.getAssetOrder(assetOrderId);
        if(assetOrder == null){
            return ResultUtil.buildErrorResult(ErrorInfo.ORDER_NOT_FOUND);
        }
        // TODO: 同意后需要锁死份额 
        LocalDateTime currentTime = LocalDateTime.now();
        Date startTime = Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant());
        if(assetOrder.getEndTime().before(startTime)){
            assetOrderService.updateAssetOrderStatus(assetOrderId,AssetOrderStatusEnum.APPLY_OUT_TIME);
            return ResultUtil.buildErrorResult(ErrorInfo.ORDER_APPLY_OUTTIME);
        }
        if (result) {
            assetOrderService.updateAssetOrderStatus(assetOrderId, AssetOrderStatusEnum.OBLIGATIONS);
        } else {
            assetOrderService.updateAssetOrderStatus(assetOrderId, AssetOrderStatusEnum.APPLY_REJECT);
        }

        return ResultUtil.success();
    }

    /**支付状态订单的状态变化
     * @param assetOrderId
     * @param result
     * @return
     */
    @RequestMapping(value = "{assetOrderId}/obligation", method = RequestMethod.POST)
    public Result obligationOrder(
            @PathVariable("assetOrderId") int assetOrderId,
            @RequestParam("result") boolean result
    ) {
        AssetOrder assetOrder = assetOrderService.getAssetOrder(assetOrderId);
        LocalDateTime currentTime = LocalDateTime.now();
        Date startTime = Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant());
        if(assetOrder.getEndTime().before(startTime)){
            assetOrderService.updateAssetOrderStatus(assetOrderId,AssetOrderStatusEnum.OBLIGATIONS_OUT_TIME);
            return ResultUtil.buildErrorResult(ErrorInfo.ORDER_OBLIGATION_OUTTIME);
        }
        if (result) {
            assetOrderService.updateAssetOrderStatus(assetOrderId, AssetOrderStatusEnum.OBLIGATIONS_DONE);
        }

        return ResultUtil.success();
    }

    /**持有人完成订单的状态变化
     * @param assetOrderId
     * @param result
     * @return
     */
    @RequestMapping(value = "{assetOrderId}/finishOrder", method = RequestMethod.POST)
    public Result finishOrder(
            @PathVariable("assetOrderId") int assetOrderId,
            @RequestParam("result") boolean result
    ) {
        AssetOrder assetOrder = assetOrderService.getAssetOrder(assetOrderId);
        LocalDateTime currentTime = LocalDateTime.now();
        Date startTime = Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant());
        if(assetOrder.getEndTime().before(startTime)){
            assetOrderService.updateAssetOrderStatus(assetOrderId,AssetOrderStatusEnum.COMPLETE_OUT_TIME);
            return ResultUtil.buildErrorResult(ErrorInfo.ORDER_COMEPLETE_OUTTIME);
        }
        if (result) {
            assetOrderService.updateAssetOrderStatus(assetOrderId, AssetOrderStatusEnum.TRADE_SUCCESS);
        }
//        else {
//            assetOrderService.updateAssetOrderStatus(assetOrderId,AssetOrderStatusEnum.TRADE_FAILED);
//        }

        return ResultUtil.success();
    }

    /**仲裁后，修改订单状态
     * @param assetOrderId
     * @param result
     * @return
     */
    @RequestMapping(value = "{assetOrderId}/tradeResult", method = RequestMethod.POST)
    public Result tradeFail(
            @PathVariable("assetOrderId") int assetOrderId,
            @RequestParam("result") boolean result
    ) {
        if (result) {
            assetOrderService.updateAssetOrderStatus(assetOrderId, AssetOrderStatusEnum.TRADE_SUCCESS);
        }else {
            assetOrderService.updateAssetOrderStatus(assetOrderId,AssetOrderStatusEnum.TRADE_FAILED);
        }

        return ResultUtil.success();
    }

    @ExceptionHandler(AssetOrderException.class)
    @ResponseBody
    public Result handleOrderException() {
        return ResultUtil.buildErrorResult(ErrorInfo.ORDER_EXCEPTION);
    }

}
