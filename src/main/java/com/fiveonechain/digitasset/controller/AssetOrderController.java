package com.fiveonechain.digitasset.controller;

import com.fiveonechain.digitasset.auth.UserContext;
import com.fiveonechain.digitasset.domain.AssetOrder;
import com.fiveonechain.digitasset.domain.AssetOrderStatusEnum;
import com.fiveonechain.digitasset.domain.result.ErrorInfo;
import com.fiveonechain.digitasset.domain.result.Result;
import com.fiveonechain.digitasset.exception.AssetOrderException;
import com.fiveonechain.digitasset.service.AssetOrderService;
import com.fiveonechain.digitasset.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by fanjl on 16/11/21.
 */
@RestController
@RequestMapping("assetOrder")
public class AssetOrderController {
    @Autowired
    private AssetOrderService iAssetOrderService;

    @RequestMapping(value = "createOrder", method = RequestMethod.POST)
    public Result sellOrder(
            @AuthenticationPrincipal UserContext userContext,
            @RequestParam("userId") int userId,
            @RequestParam("assetId") int assetId,
            @RequestParam("amount") int amount,
            @RequestParam("cycle") int cycle,
            @RequestParam("unitPrice") double unitPrice) {

        int buyerId = userContext.getUserId();
        // TODO 检查userAsset是否有效
//        UserAsset asset = assetService.getAsset(assetId);
        LocalDateTime currentTime = LocalDateTime.now();

        Date startTime = Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant());
        Date endTime = Date.from(currentTime.plusDays(cycle).atZone(ZoneId.systemDefault()).toInstant());

        AssetOrder assetOrder = new AssetOrder();
        assetOrder.setUser_id(userId);
        assetOrder.setAsset_id(assetId);
        assetOrder.setBuyer_id(buyerId);
        assetOrder.setAmount(amount);
        assetOrder.setUnit_prices(BigDecimal.valueOf(unitPrice));
        assetOrder.setEnd_time(endTime);
        assetOrder.setStatus(AssetOrderStatusEnum.APPLY.getId());


        if(iAssetOrderService.createAssetOrder(assetOrder)!=1){
            Result result = ResultUtil.buildErrorResult(ErrorInfo.SERVER_ERROR);
            return result;
        }

        return ResultUtil.success();
    }

    @RequestMapping(value = "/{assetOrderId}", method = RequestMethod.GET)
    public Result getAssetOrderDetail(
            @AuthenticationPrincipal UserContext userContext,
            @PathVariable("assetOrderId") int assetOrderId) {

        AssetOrder assetOrder = iAssetOrderService.getAssetOrder(assetOrderId);
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
        AssetOrder assetOrder = iAssetOrderService.getAssetOrder(assetOrderId);
        if(assetOrder == null){
            return ResultUtil.buildErrorResult(ErrorInfo.ORDER_NOT_FOUND);
        }
        // TODO: 同意后需要锁死份额 
        LocalDateTime currentTime = LocalDateTime.now();
        Date startTime = Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant());
        if(assetOrder.getEnd_time().before(startTime)){
            iAssetOrderService.updateAssetOrderStatus(assetOrderId,AssetOrderStatusEnum.APPLY_OUT_TIME);
            return ResultUtil.buildErrorResult(ErrorInfo.ORDER_APPLY_OUTTIME);
        }
        if (result) {
            iAssetOrderService.updateAssetOrderStatus(assetOrderId, AssetOrderStatusEnum.OBLIGATIONS);
        } else {
            iAssetOrderService.updateAssetOrderStatus(assetOrderId, AssetOrderStatusEnum.APPLY_REJECT);
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
        AssetOrder assetOrder = iAssetOrderService.getAssetOrder(assetOrderId);
        LocalDateTime currentTime = LocalDateTime.now();
        Date startTime = Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant());
        if(assetOrder.getEnd_time().before(startTime)){
            iAssetOrderService.updateAssetOrderStatus(assetOrderId,AssetOrderStatusEnum.OBLIGATIONS_OUT_TIME);
            return ResultUtil.buildErrorResult(ErrorInfo.ORDER_OBLIGATION_OUTTIME);
        }
        if (result) {
            iAssetOrderService.updateAssetOrderStatus(assetOrderId, AssetOrderStatusEnum.OBLIGATIONS_DONE);
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
        AssetOrder assetOrder = iAssetOrderService.getAssetOrder(assetOrderId);
        LocalDateTime currentTime = LocalDateTime.now();
        Date startTime = Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant());
        if(assetOrder.getEnd_time().before(startTime)){
            iAssetOrderService.updateAssetOrderStatus(assetOrderId,AssetOrderStatusEnum.COMPLETE_OUT_TIME);
            return ResultUtil.buildErrorResult(ErrorInfo.ORDER_COMEPLETE_OUTTIME);
        }
        if (result) {
            iAssetOrderService.updateAssetOrderStatus(assetOrderId, AssetOrderStatusEnum.TRADE_SUCCESS);
        }
//        else {
//            iAssetOrderService.updateAssetOrderStatus(assetOrderId,AssetOrderStatusEnum.TRADE_FAILED);
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
            iAssetOrderService.updateAssetOrderStatus(assetOrderId, AssetOrderStatusEnum.TRADE_SUCCESS);
        }else {
            iAssetOrderService.updateAssetOrderStatus(assetOrderId,AssetOrderStatusEnum.TRADE_FAILED);
        }

        return ResultUtil.success();
    }

    @ExceptionHandler(AssetOrderException.class)
    @ResponseBody
    public Result handleOrderException() {
        return ResultUtil.buildErrorResult(ErrorInfo.ORDER_EXCEPTION);
    }

}
