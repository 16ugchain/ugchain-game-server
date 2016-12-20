package com.fiveonechain.digitasset.controller;

import com.fiveonechain.digitasset.auth.UserContext;
import com.fiveonechain.digitasset.domain.*;
import com.fiveonechain.digitasset.domain.result.OrderCenterCmd;
import com.fiveonechain.digitasset.service.*;
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
@RequestMapping("/orderCenter")
public class OrderCenterController {

    @Autowired
    private AssetOrderService iAssetOrderService;
    @Autowired
    private AssetService assetService;


    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserAssetService userAssetService;

    @Autowired
    private GuaranteeCorpService guaranteeCorpService;


    @RequestMapping("/index")
    public String index(@AuthenticationPrincipal UserContext userContext,
                        Model model){
        List<AssetOrder> assetOrders = iAssetOrderService.getAssetOrderListByOwner(userContext.getUserId());
        List<AssetOrder> assetOrderAssign = iAssetOrderService.getAssetOrderListByBuyerId(userContext.getUserId());
        List<OrderCenterCmd> orderCenterCmdsOwner = orderCenterCmdPlay(assetOrders, false);
        List<OrderCenterCmd> orderCenterCmdsBuyer = orderCenterCmdPlay(assetOrderAssign, true);

        model.addAttribute("orderCenterCmdsAssign", orderCenterCmdsOwner);
        model.addAttribute("orderCenterCmds", orderCenterCmdsBuyer);
        return "indent-center";
    }

    public List<OrderCenterCmd> orderCenterCmdPlay(List<AssetOrder> assetOrders, boolean isBuyer){
        List<OrderCenterCmd> orderCenterCmds = new LinkedList<>();
        for(AssetOrder assetOrder : assetOrders){
            OrderCenterCmd orderCenterCmd = new OrderCenterCmd();
            Optional<Asset> asset = assetService.getAssetOptional(assetOrder.getAssetId());
            if(!asset.isPresent()){
                continue;
            }else{
                //我发起的
                orderCenterCmd.setOrderId(assetOrder.getOrderId());
                orderCenterCmd.setAssetName(asset.get().getName());
                orderCenterCmd.setAssetId(asset.get().getAssetId());
                orderCenterCmd.setApplicationShare(assetOrder.getAmount());
                orderCenterCmd.setPercent(String.valueOf(assetOrder.getAmount()*100/asset.get().getEvalValue()));
                Optional<GuaranteeCorp> guaranteeCorp = guaranteeCorpService.getGuarOptByGuarId(asset.get().getGuarId());
                if(guaranteeCorp.isPresent()){
                    orderCenterCmd.setGuarName(guaranteeCorp.get().getCorpName());
                }
                Optional<UserInfo> userInfo = userInfoService.getUserInfoOptional(assetOrder.getBuyerId());
                if(userInfo.isPresent()){
                    orderCenterCmd.setBuyerName(userInfo.get().getRealName());
                }
                Optional<GuaranteeCorp> guaranteeCorpBuyer = guaranteeCorpService.getGuarOptional(assetOrder.getBuyerId());
                if(guaranteeCorpBuyer.isPresent()){
                    orderCenterCmd.setBuyerCorp(guaranteeCorpBuyer.get().getCorpName());
                }
                orderCenterCmd.setStartTime(assetOrder.getCreateTime());
                orderCenterCmd.setStatus(assetOrder.getStatus());
                orderCenterCmd.setStatusStr(AssetOrderStatusEnum.fromValue(assetOrder.getStatus()).getName());
                orderCenterCmd.setExpEarning(String.valueOf(asset.get().getExpEarnings()));
                List<AssetOrderOperation> assetOrderOperations = iAssetOrderService.getOperationByStatusAndRole(AssetOrderStatusEnum.fromValue(assetOrder.getStatus()),userRoleEnum);
                orderCenterCmd.setOperation(assetOrderOperations);
            }
            orderCenterCmds.add(orderCenterCmd);
        }
        return orderCenterCmds;
    }

}
