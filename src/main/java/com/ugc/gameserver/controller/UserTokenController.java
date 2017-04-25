package com.ugc.gameserver.controller;

import com.ugc.gameserver.domain.UserToken;
import com.ugc.gameserver.domain.UserTokenStatusEnum;
import com.ugc.gameserver.domain.result.ErrorInfo;
import com.ugc.gameserver.domain.result.Result;
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

    @RequestMapping(value = "/updateData/insert")
    public Result insertData(@RequestParam("token") String token,
                             @RequestParam("data") String data){
        UserToken userToken = userTokenService.insertAndGet(token,data,UserTokenStatusEnum.USEING.getId());
        return ResultUtil.success(userToken);
    }

    @RequestMapping(value = "/getData/{token}")
    public Result getDataByToken(@PathVariable("token")String token){
        Optional<UserToken> op = userTokenService.getUserTokenByToken(token);
        if(op.isPresent()){
            return ResultUtil.success(op.get());
        }
        return ResultUtil.buildErrorResult(ErrorInfo.TOKEN_NOTEXISTS);
    }

    @RequestMapping(value = "/getData/list")
    public Result getDataList(){
        List<Integer> status = new LinkedList<Integer>();
        status.add(UserTokenStatusEnum.USEING.getId());
        List<UserToken> userTokenList = userTokenService.getUserTokenListByStatus(status);
        Result result = ResultUtil.success(userTokenList);
        return result;
    }
}
