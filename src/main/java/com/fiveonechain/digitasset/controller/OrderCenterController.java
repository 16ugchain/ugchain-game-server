package com.fiveonechain.digitasset.controller;

import com.fiveonechain.digitasset.auth.UserContext;
import com.fiveonechain.digitasset.domain.result.OrderCenterCmd;
import com.fiveonechain.digitasset.domain.*;
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
        List<OrderCenterCmd> orderCenterCmds = orderCenterCmdPlay(assetOrders);
        List<OrderCenterCmd> orderCenterCmdsAssign = orderCenterCmdPlay(assetOrderAssign);

        model.addAttribute("orderCenterCmdsAssign",orderCenterCmdsAssign);
        model.addAttribute("orderCenterCmds",orderCenterCmds);
        return "indent-center";
    }

    public List<OrderCenterCmd> orderCenterCmdPlay(List<AssetOrder> assetOrders){
        List<OrderCenterCmd> orderCenterCmds = new LinkedList<>();
        for(AssetOrder assetOrder : assetOrders){
            OrderCenterCmd orderCenterCmd = new OrderCenterCmd();
            OrderCenterCmd orderCenterCmdAssign = new OrderCenterCmd();
            Optional<Asset> asset = assetService.getAssetOptional(assetOrder.getAssetId());
            if(!asset.isPresent()){
                continue;
            }else{
                //我发起的
                orderCenterCmd.setAssetName(asset.get().getName());
                orderCenterCmd.setAssetId(asset.get().getAssetId());
                orderCenterCmd.setApplicationShare(assetOrder.getAmount());
                orderCenterCmd.setPercent(String.valueOf(assetOrder.getAmount()*100/asset.get().getEvalValue()));
                orderCenterCmd.setEndTime(assetOrder.getEndTime());
                Optional<GuaranteeCorp> guaranteeCorp = guaranteeCorpService.getGuarOptByGuarId(asset.get().getGuarId());
                if(guaranteeCorp.isPresent()){
                    orderCenterCmd.setGuarName(guaranteeCorp.get().getCorpName());
                }
                Optional<UserInfo> userInfo = userInfoService.getUserInfoOptional(assetOrder.getBuyerId());
                if(userInfo.isPresent()){
                    orderCenterCmd.setBuyerName(userInfo.get().getRealName());
                }
                GuaranteeCorp guaranteeCorpBuyer = guaranteeCorpService.findByUserId(assetOrder.getBuyerId());
                orderCenterCmd.setBuyerCorp(guaranteeCorpBuyer.getCorpName());
                orderCenterCmd.setStartTime(assetOrder.getCreateTime());
                orderCenterCmd.setStatus(assetOrder.getStatus());
                orderCenterCmd.setStatusStr(AssetOrderStatusEnum.fromValue(assetOrder.getStatus()).getName());
                orderCenterCmd.setExpEarning(String.valueOf(asset.get().getExpEarnings()));
                getOperationByStatus(orderCenterCmd,assetOrder.getStatus());
            }
            orderCenterCmds.add(orderCenterCmd);
        }
        return orderCenterCmds;
    }

    public void getOperationByStatus(OrderCenterCmd orderCenterCmd,int orderStatus){
        if(orderStatus == AssetOrderStatusEnum.APPLY.getId()){
            orderCenterCmd.setOperation("查看");
        }else if(orderStatus == AssetOrderStatusEnum.OBLIGATIONS.getId()){
            orderCenterCmd.setOperation("支付");
        }else if(orderStatus == AssetOrderStatusEnum.COMPLETE_OUT_TIME.getId()){
            orderCenterCmd.setOperation("申诉");
        }
    }
}
