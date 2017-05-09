package com.ugc.gameserver.controller;

import com.ugc.gameserver.domain.Derma;
import com.ugc.gameserver.domain.DermaListEnum;
import com.ugc.gameserver.domain.DermaOrder;
import com.ugc.gameserver.domain.UserToken;
import com.ugc.gameserver.domain.UserTokenStatusEnum;
import com.ugc.gameserver.domain.result.ErrorInfo;
import com.ugc.gameserver.service.DermaOrderService;
import com.ugc.gameserver.service.UserTokenService;
import com.ugc.gameserver.service.Web3jService;
import com.ugc.gameserver.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by fanjl on 2017/4/25.
 */
@RestController
public class UserTokenController {

    @Autowired
    private UserTokenService userTokenService;
    @Autowired
    private DermaOrderService dermaOrderService;
    @Autowired
    private Web3jService web3jService;

//    @RequestMapping(value = "/updateData/insert")
//    public String initData(@RequestParam("token") String token,
//                             @RequestParam("data") String data,
//                             @RequestParam("userName") String userName,
//                             @RequestParam(value = "callback", required = false) String callback) {
//        UserToken userToken = userTokenService.insertAndGet(userName, token, data, UserTokenStatusEnum.USEING.getId());
//        return ResultUtil.successCallBack(callback,userToken);
//    }

    @RequestMapping(value = "/insertOrder/{token}")
    public String getDerma(@RequestParam("derma") int derma,
                           @PathVariable("token") String token
            , @RequestParam(value = "callback", required = false) String callback) {
        Optional<UserToken> opt = userTokenService.getUserTokenByToken(token);
        if(!opt.isPresent()){
            return ResultUtil.buildErrorResultCallBack(ErrorInfo.TOKEN_NOTEXISTS,callback);
        }
        DermaListEnum dermaListEnum = DermaListEnum.fromValue(derma);
        Derma dermaModel = new Derma();
        dermaModel.setId(dermaListEnum.getId());
        dermaModel.setPrices(dermaListEnum.getPrices());
        dermaModel.setName(dermaListEnum.getName());
        DermaOrder dermaOrder = dermaOrderService.createOrder(token,dermaModel);
        return ResultUtil.successCallBack(callback,dermaOrder);
    }

    @RequestMapping(value = "/updateData/data")
    public String updateData(@RequestParam("token") String token,
                             @RequestParam("data") int data, @RequestParam(value = "callback", required = false) String callback) {

        return ResultUtil.successCallBack(callback,userTokenService.updateData(token, data));
    }

    @RequestMapping(value = "/updateData/{token}/onSelling")
    public String updateStatus(@PathVariable("token") String token,
                               @RequestParam("prices") BigDecimal prices,
                             @RequestParam(value = "callback", required = false) String callback) {

        return ResultUtil.successCallBack(callback,userTokenService.onSelling(web3jService.getGameId(),token,UserTokenStatusEnum.DEALING.getId(),prices));
    }

    @RequestMapping(value = "/updateOrder/{token}/derma",produces = "application/json; charset=utf-8")
    public String updateUserDerma(@RequestParam(value = "callback", required = false) String callback
            ,@PathVariable("token") String token
            ,@RequestParam("derma")int derma) {
        Optional<UserToken> userToken = userTokenService.getUserTokenByToken(token);
        if(!userToken.isPresent()){
            return ResultUtil.successCallBack(callback,ErrorInfo.TOKEN_NOTEXISTS);
        }
        List<String> dermas = userToken.get().getDerma();
        List<String> newDermas = new LinkedList<String>();
        newDermas.addAll(dermas);
        newDermas.add(String.valueOf(derma));
        boolean result = userTokenService.updateDerma(token,newDermas);
        return ResultUtil.successCallBack(callback,result);
    }
    @RequestMapping(value = "/updateData/{token}/status",produces = "application/json; charset=utf-8")
    public String updateUserStatus(@RequestParam(value = "callback", required = false) String callback
            ,@PathVariable("token") String token
            ,@RequestParam("status")int status) {
        Optional<UserToken> userToken = userTokenService.getUserTokenByToken(token);
        if(!userToken.isPresent()){
            return ResultUtil.successCallBack(callback,ErrorInfo.TOKEN_NOTEXISTS);
        }
        boolean result = userTokenService.updateStatus(token,status);
        return ResultUtil.successCallBack(callback,result);
    }

    @RequestMapping(value = "/getData/{token}",produces = "application/json; charset=utf-8")
    public String getDataByToken(@PathVariable("token") String token, @RequestParam(value = "callback", required = false) String callback
            , @RequestParam(value = "userName", required = false) String userName) {

//        int assetId = web3jService.queryAssetIdByToken(token);
//        boolean isOnSell = web3jService.isOnSell(assetId);
//        if(isOnSell){
//            return ResultUtil.buildErrorResultCallBack(ErrorInfo.ON_SELLING,callback);
//        }

        Optional<UserToken> op = userTokenService.getUserTokenByToken(token);
        if (op.isPresent()) {
//            if(op.get().getStatus()==UserTokenStatusEnum.DEALING.getId()){
//                return ResultUtil.buildErrorResultCallBack(ErrorInfo.ON_SELLING,callback);
//            }
            return ResultUtil.successCallBack(callback,op.get());
        }
        if(userName==null){
            return ResultUtil.buildErrorResultCallBack(ErrorInfo.USERNAME_NULL,callback);
        }
        UserToken userToken = userTokenService.insertAndGet(userName,token,"0",UserTokenStatusEnum.USEING.getId());
        return ResultUtil.successCallBack(callback,userToken);
    }

    @RequestMapping(value = "/getData/status",produces = "application/json; charset=utf-8")
    public String getStatusList(@RequestParam("tokens") String tokenList, @RequestParam(value = "callback", required = false) String callback
            ) {
        Map<String,Boolean> status = new LinkedHashMap<String,Boolean>();
        String[] tokens = tokenList.split(",");
        for (String token : tokens){
            int assetId = web3jService.queryAssetIdByToken(token);
            boolean isOnSell = web3jService.isOnSell(assetId);
            status.put(token,isOnSell);
        }
        return ResultUtil.successCallBack(callback,status);
    }

    @RequestMapping(value = "/getData/list",produces = "application/json; charset=utf-8")
    public String getDataList(@RequestParam(value = "callback", required = false) String callback) {
        List<Integer> status = new LinkedList<Integer>();
        status.add(UserTokenStatusEnum.USEING.getId());
        List<UserToken> userTokenList = userTokenService.getUserTokenListByStatus(status);
        return ResultUtil.successCallBack(callback,userTokenList);
    }

    @RequestMapping(value = "/getData/derma",produces = "application/json; charset=utf-8")
    public String getDermaList(@RequestParam(value = "callback", required = false) String callback) {
        List<Derma> status = new LinkedList<Derma>();
        for(DermaListEnum dermaListEnum : DermaListEnum.values()){
            Derma derma = new Derma();
            derma.setId(dermaListEnum.getId());
            derma.setName(dermaListEnum.getName());
            derma.setPrices(dermaListEnum.getPrices());
            status.add(derma);
        }
        return ResultUtil.successCallBack(callback,status);
    }

    @RequestMapping(value = "/getData/{assetId}/assetId",produces = "application/json; charset=utf-8")
    public String getUserTokenByAssetId(@PathVariable("assetId") int assetId,@RequestParam(value = "callback", required = false) String callback) {
        String token = web3jService.queryTokenByAssetId(assetId);
        Optional<UserToken> opt = userTokenService.getUserTokenByToken(token);
        if(opt.isPresent()){
            return ResultUtil.successCallBack(callback,opt.get());
        }
        return ResultUtil.buildErrorResultCallBack(ErrorInfo.TOKEN_NOTEXISTS,callback);
    }

    @RequestMapping(value = "/getOrder/{orderId}/derma",produces = "application/json; charset=utf-8")
    public String getOrderById(@RequestParam(value = "callback", required = false) String callback
                        ,@PathVariable("orderId") int orderId) {
        DermaOrder dermaOrder = dermaOrderService.getOrderById(orderId);
        return ResultUtil.successCallBack(callback,dermaOrder);
    }


}
