package com.ugc.gameserver.controller;

import com.ugc.gameserver.domain.Derma;
import com.ugc.gameserver.domain.DermaListEnum;
import com.ugc.gameserver.domain.UserToken;
import com.ugc.gameserver.domain.UserTokenStatusEnum;
import com.ugc.gameserver.domain.result.ErrorInfo;
import com.ugc.gameserver.service.DermaOrderService;
import com.ugc.gameserver.service.UserTokenService;
import com.ugc.gameserver.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
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

    @RequestMapping(value = "/updateData/insert")
    public String initData(@RequestParam("token") String token,
                             @RequestParam("data") String data,
                             @RequestParam("userName") String userName,
                             @RequestParam(value = "callback", required = false) String callback) {
        UserToken userToken = userTokenService.insertAndGet(userName, token, data, UserTokenStatusEnum.USEING.getId());
        return ResultUtil.successCallBack(callback,userToken);
    }

    @RequestMapping(value = "/updateData/derma")
    public String getDerma(@RequestParam("derma") int derma,
                           @RequestParam("token") String token
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
        int orderId = dermaOrderService.createOrder(token,dermaModel);
        return ResultUtil.successCallBack(callback,orderId);
    }

    @RequestMapping(value = "/updateData/data")
    public String updateData(@RequestParam("token") String token,
                             @RequestParam("data") int data, @RequestParam(value = "callback", required = false) String callback) {

        return ResultUtil.successCallBack(callback,userTokenService.updateData(token, data));
    }

    @RequestMapping(value = "/getData/{token}",produces = "application/json; charset=utf-8")
    public String getDataByToken(@PathVariable("token") String token, @RequestParam(value = "callback", required = false) String callback
            , @RequestParam(value = "userName", required = false) String userName) {
        Optional<UserToken> op = userTokenService.getUserTokenByToken(token);
        if (op.isPresent()) {

            return ResultUtil.successCallBack(callback,op.get());
        }
        if(userName==null){
            return ResultUtil.buildErrorResultCallBack(ErrorInfo.USERNAME_NULL,callback);
        }
        UserToken userToken = userTokenService.insertAndGet(userName,token,"0",UserTokenStatusEnum.USEING.getId());
        return ResultUtil.successCallBack(callback,userToken);
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
}
