package com.fiveonechain.digitasset.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveonechain.digitasset.auth.UserContext;
import com.fiveonechain.digitasset.domain.User;
import com.fiveonechain.digitasset.domain.UserAuthStatusEnum;
import com.fiveonechain.digitasset.domain.UserInfo;
import com.fiveonechain.digitasset.domain.result.ErrorInfo;
import com.fiveonechain.digitasset.domain.result.Result;
import com.fiveonechain.digitasset.domain.result.UserInfoCmd;
import com.fiveonechain.digitasset.exception.JsonSerializableException;
import com.fiveonechain.digitasset.service.ImageUploadService;
import com.fiveonechain.digitasset.service.ImageUrlService;
import com.fiveonechain.digitasset.service.UserInfoService;
import com.fiveonechain.digitasset.service.UserService;
import com.fiveonechain.digitasset.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by fanjl on 2016/12/7.
 */
@Controller
@RequestMapping("/userInfo")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserService userService;
    @Autowired
    private ImageUploadService imageUploadService;
    @Autowired
    private ImageUrlService imageUrlService;

    @RequestMapping(value = "/index")
    public String index(@AuthenticationPrincipal UserContext userContext,
                        Model model
    ) {
        UserInfo userInfo = userInfoService.getUserInfoByUserId(userContext.getUserId());
        User user = userService.getUserByUserId(userContext.getUserId());
        String[] imgs = userInfo.getImageId().split(",");
        List<Integer> imgIds = new LinkedList<>();
        for(String img : imgs){
            imgIds.add(Integer.parseInt(img));
        }
        List<String> imageUrls = imageUrlService.getUrlListByImageIds(imgIds);
        List<Integer> iconId = new LinkedList<>();
        String icon = "";
        if(userInfo.getIconId()!=null){
            iconId.add(Integer.parseInt(userInfo.getIconId()));
            List<String> icons = imageUrlService.getUrlListByImageIds(iconId);
            icon += icons.get(0);
        }
        model.addAttribute("imageUrls",imageUrls);
        model.addAttribute("icon",icon);
        model.addAttribute("userInfo",userInfo);
        model.addAttribute("user",user);
        return "my-info";
    }
    @ResponseBody
    @RequestMapping(value = "/info")
    public Result userInfoDetail(@AuthenticationPrincipal UserContext userContext,
                        Model model
    ) {
        UserInfo userInfo = userInfoService.getUserInfoByUserId(userContext.getUserId());
        User user = userService.getUserByUserId(userContext.getUserId());
        ObjectMapper mapper = new ObjectMapper();
        List<Integer> imageIds = null;
        try {
            imageIds = mapper.readValue(userInfo.getImageId(),List.class);
        } catch (IOException e) {
            return ResultUtil.buildErrorResult(ErrorInfo.SERVER_ERROR);
        }
        List<String> imageUrls = imageUrlService.getUrlListByImageIds(imageIds);
        UserInfoCmd userInfoCmd = new UserInfoCmd();
        userInfoCmd.setUser(user);
        userInfoCmd.setUserInfo(userInfo);
        userInfoCmd.setImageUrl(imageUrls);
        return ResultUtil.success(userInfoCmd);
    }
    @ResponseBody
    @RequestMapping(value = "/bindCreditCard", method = RequestMethod.POST)
    public Result bindCreditCard(@RequestParam("creditCardId") String creditCardId,
                                 @RequestParam("creditCardOwner") String creditCardOwner,
                                 @RequestParam("creditCardBank") String creditCardBank,
                                 @AuthenticationPrincipal UserContext userContext
    ) {
        UserInfo userInfo = userInfoService.getUserInfoByUserId(userContext.getUserId());
        if (userInfo == null) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.SERVER_ERROR);
            return result;
        }
        userInfo.setCreditCardId(creditCardId);
        userInfo.setCreditCardOwner(creditCardOwner);
        userInfo.setCreditCardBank(creditCardBank);
        if (!userInfoService.bindCreditCard(userInfo)) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.SERVER_ERROR);
            return result;
        }
        Result result = ResultUtil.success();
        return result;
    }
    @ResponseBody
    @RequestMapping(value = "/updateUserInfo")
    public Result updateUserInfo(@AuthenticationPrincipal UserContext userContext,
                                 @RequestParam("real_name") String realName
    ) {
        UserInfo userInfo = userInfoService.getUserInfoByUserId(userContext.getUserId());
        // TODO: 2016/12/7 update userinfo
        Result result = ResultUtil.success();
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/updateUserIcon")
    public Result updateUserIcon(@AuthenticationPrincipal UserContext userContext,
                                 @RequestParam("imgId") int imgId
    ) {
        UserInfo userInfo = userInfoService.getUserInfoByUserId(userContext.getUserId());
        // TODO: 2016/12/7 update userinfo
        userInfoService.updateIcon(imgId,userContext.getUserId());
        Result result = ResultUtil.success();
        return result;
    }
    @ResponseBody
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public Result authenticate(@AuthenticationPrincipal UserContext userContext,
                               @RequestParam("real_name") String real_name,
                               @RequestParam("identity") String identity,
                               @RequestParam("identity_type") int type,
                               @RequestParam("image_id") String imageId,
                               @RequestParam(value = "email", required = false, defaultValue = "") String email,
                               @RequestParam(value = "fixed_line", required = false, defaultValue = "") String fixed_line
    ) {
        //验证identity后设置status
        boolean isExists = userInfoService.isExistsSameID(identity);
        if (isExists) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.IDENTITY_EXISTS);
            return result;
        }
        boolean isExistsUser = userInfoService.isExistsUserAuth(userContext.getUserId());
        if (isExistsUser) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.AUTHENTICATION);
            return result;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userContext.getUserId());
        userInfo.setIdentity(identity);
        userInfo.setIdentityType(type);
        userInfo.setRealName(real_name);
        userInfo.setEmail(email);
        userInfo.setImageId(imageId);
        userInfo.setFixedLine(fixed_line);
        userInfo.setStatus(UserAuthStatusEnum.SUCCESS.getId());
        if (userInfoService.insertAndGetUserAuth(userInfo) != 1) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.SERVER_ERROR);
            return result;
        }
        Result result = ResultUtil.success(userInfo);
        return result;
    }
    @ResponseBody
    @RequestMapping(value = "/findIdentityId", method = RequestMethod.POST)
    public String findIdentityId(@RequestParam("papersNum") String identityId) {
        boolean result = userInfoService.isExistsSameID(identityId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("valid", !result);
        ObjectMapper mapper = new ObjectMapper();
        String resultString = "";
        try {
            resultString = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new JsonSerializableException(e);
        }
        return resultString;
    }

    @ExceptionHandler(JsonSerializableException.class)
    @ResponseBody
    public Result handleJsonSerializableException() {
        return ResultUtil.buildErrorResult(ErrorInfo.SERVER_ERROR);
    }
}
