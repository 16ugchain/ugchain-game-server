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
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

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

    Pbkdf2PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder();

    @RequestMapping(value = "/index")
    public String index(@AuthenticationPrincipal UserContext userContext,
                        Model model
    ) {
        UserInfo userInfo = userInfoService.getUserInfoByUserId(userContext.getUserId());
        User user = userService.getUserByUserId(userContext.getUserId());
        List<String> imageUrls = new LinkedList<>();
        String icon = "";
        if(userInfo != null){
            if(userInfo.getImageId()!=null){
                String[] imgs = userInfo.getImageId().split(",");
                List<Integer> imgIds = new LinkedList<>();
                for(String img : imgs){
                    imgIds.add(Integer.parseInt(img));
                }
                imageUrls = imageUrlService.getUrlListByImageIds(imgIds);

            }
            List<Integer> iconId = new LinkedList<>();
            if(userInfo.getIconId()!=null){
                iconId.add(Integer.parseInt(userInfo.getIconId()));
                List<String> icons = imageUrlService.getUrlListByImageIds(iconId);
                icon += icons.get(0);
            }
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
        Optional<UserInfo> userInfo = userInfoService.getUserInfoOptional(userContext.getUserId());
        if(!userInfo.isPresent()){
//            UserInfo userInfoTmp = new UserInfo();
//            userInfoTmp.setRealName(realName);
//            userInfoTmp.setStatus(UserAuthStatusEnum.FAIL.getId());//需要实名认证上传照片后修改状态
//            userInfoService.insertAndGetUserAuth(userInfoTmp);
            return ResultUtil.buildErrorResult(ErrorInfo.USER_INFO_NOT_FOUND);
        }
        if(realName!=null && realName.trim().length()>0){
            userInfo.get().setRealName(realName);
            userInfoService.updateUserInfo(userInfo.get());
        }else{
            return ResultUtil.buildErrorResult(ErrorInfo.USER_REAL_NAME_ERROR);
        }
        Result result = ResultUtil.success();
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/updatePassword")
    public Result updatePassword(@AuthenticationPrincipal UserContext userContext,
                                 @RequestParam("oldPassword") String oldPwd,
                                 @RequestParam("newPassword") String newPwd
    ) {
        User user = userService.getUserByUserId(userContext.getUserId());
        if (!userService.checkUserLogin(user.getUserName(), oldPwd)) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.PASSWORD_ERROR);
            return result;
        }
        if(StringUtils.isEmpty(newPwd)){
            return ResultUtil.buildErrorResult(ErrorInfo.PASSWORD_ERROR);
        }
        String md5Pwd = passwordEncoder.encode(newPwd);
        userService.updatePassword(md5Pwd,userContext.getUserId());
        Result result = ResultUtil.success();
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/updateUserIcon")
    public Result updateUserIcon(@AuthenticationPrincipal UserContext userContext,
                                 @RequestParam("imgId") int imgId
    ) {
        userInfoService.updateIcon(imgId,userContext.getUserId());
        Result result = ResultUtil.success();
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/bindEmail")
    public Result bindEmail(@AuthenticationPrincipal UserContext userContext,
                                 @RequestParam("email") String email
    ) {
        Optional<UserInfo> userInfo = userInfoService.getUserInfoOptional(userContext.getUserId());
        if(!userInfo.isPresent()){
            return ResultUtil.buildErrorResult(ErrorInfo.USER_INFO_NOT_FOUND);
        }
        if(email!=null && email.trim().length()>0){
            userInfo.get().setEmail(email);
            userInfoService.updateUserInfo(userInfo.get());
        }else{
            return ResultUtil.buildErrorResult(ErrorInfo.USER_INFO_NOT_FOUND);
        }
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
