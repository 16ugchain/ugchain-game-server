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
    private UserService userService;

    @Autowired
    private GuaranteeCorpService guaranteeCorpService;


    @RequestMapping("/index")
    public String index(@AuthenticationPrincipal UserContext userContext,
                        Model model){
        List<AssetOrder> assetOrders = iAssetOrderService.getAssetOrderListByOwner(userContext.getUserId());//我收到的
        List<AssetOrder> assetOrderAssign = iAssetOrderService.getAssetOrderListByBuyerId(userContext.getUserId());//我发起的
        List<OrderCenterCmd> orderCenterCmdsOwner = orderCenterCmdPlay(assetOrders, false);//我收到的
        List<OrderCenterCmd> orderCenterCmdsBuyer = orderCenterCmdPlay(assetOrderAssign, true);//我发起的

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
                    orderCenterCmd.setBuyerInfo(userInfo.get());
                }

                Optional<User> user = userService.getUserOptional(assetOrder.getBuyerId());
                if(user.isPresent()){
                    orderCenterCmd.setBuyerTel(user.get().getTelephone());
                }

                Optional<UserInfo> holderInfo = userInfoService.getUserInfoOptional(assetOrder.getUserId());
                if(holderInfo.isPresent()){
                    orderCenterCmd.setHolderName(holderInfo.get().getRealName());
                    orderCenterCmd.setHolderInfo(holderInfo.get());
                }
                Optional<User> holder = userService.getUserOptional(assetOrder.getUserId());
                if(holder.isPresent()){
                    orderCenterCmd.setHolderTel(holder.get().getTelephone());
                }

                Optional<GuaranteeCorp> guaranteeCorpBuyer = guaranteeCorpService.getGuarOptional(assetOrder.getBuyerId());
                if(guaranteeCorpBuyer.isPresent()){
                    orderCenterCmd.setBuyerCorp(guaranteeCorpBuyer.get().getCorpName());
                }
                orderCenterCmd.setStartTime(assetOrder.getCreateTime());
                orderCenterCmd.setStatus(assetOrder.getStatus());
                orderCenterCmd.setStatusStr(AssetOrderStatusEnum.fromValue(assetOrder.getStatus()).getName());
                orderCenterCmd.setExpEarning(String.valueOf(asset.get().getExpEarnings()));
                List<AssetOrderOperation> assetOrderOperations = iAssetOrderService.getOperationByStatusAndRole(AssetOrderStatusEnum.fromValue(assetOrder.getStatus()),isBuyer);
                orderCenterCmd.setOperation(assetOrderOperations);
            }
            orderCenterCmds.add(orderCenterCmd);
        }
        return orderCenterCmds;
    }

}
