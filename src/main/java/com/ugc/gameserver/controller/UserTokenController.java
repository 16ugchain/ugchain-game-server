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
import org.springframework.web.servlet.ModelAndView;

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
                             @RequestParam("data") String data,
                             @RequestParam("userName") String userName){
        UserToken userToken = userTokenService.insertAndGet(userName,token,data,UserTokenStatusEnum.USEING.getId());
        return ResultUtil.success(userToken);
    }

    @RequestMapping(value = "/updateData/derma")
    public Result getDerma(@RequestParam("derma") int derma,
                             @RequestParam("token") String token){
        return ResultUtil.success(userTokenService.updateDerma(token,derma));
    }

    @RequestMapping(value = "/updateData/data")
    public Result updateData(@RequestParam("token") String token,
                             @RequestParam("data") int data){

        return ResultUtil.success( userTokenService.updateData(token,data));
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
    public ModelAndView getDataList(){
        List<Integer> status = new LinkedList<Integer>();
        status.add(UserTokenStatusEnum.USEING.getId());
        List<UserToken> userTokenList = userTokenService.getUserTokenListByStatus(status);
        Result result = ResultUtil.success(userTokenList);
        ModelAndView mav = new ModelAndView();
//        mav.setViewName("result");
        mav.addObject("result", result);
        return mav;
    }
}
