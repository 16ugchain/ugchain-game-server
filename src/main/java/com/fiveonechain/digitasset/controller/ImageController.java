package com.fiveonechain.digitasset.controller;

import com.fiveonechain.digitasset.auth.UserContext;
import com.fiveonechain.digitasset.config.QiNiuConfig;
import com.fiveonechain.digitasset.domain.ImageUrl;
import com.fiveonechain.digitasset.domain.User;
import com.fiveonechain.digitasset.domain.result.ErrorInfo;
import com.fiveonechain.digitasset.domain.result.Result;
import com.fiveonechain.digitasset.exception.ImageUploadException;
import com.fiveonechain.digitasset.service.*;
import com.fiveonechain.digitasset.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by fanjl on 2016/12/7.
 */
@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private UserService iUserService;
    @Autowired
    private ImageUploadService imageUploadService;
    @Autowired
    private ImageUrlService imageUrlService;
    @Autowired
    private QiNiuConfig qiNiuConfig;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result upload(
            @RequestParam("imageUrl") MultipartFile image,
            @AuthenticationPrincipal UserContext userContext
    ) {

        User user = iUserService.getUserByUserId(userContext.getUserId());
        if (user == null) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.USER_NOT_FOUND);
            return result;
        }
        if (image.getSize() > qiNiuConfig.getImageMaxSize()) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.IMAGE_TOO_LARGE);
            return result;
        }
        String message = "";
        try {
            byte[] imageByte = image.getBytes();
            message += imageUploadService.uploadAndGetResult(imageByte);
        } catch (IOException e) {
            throw new ImageUploadException(user.getUserId());
        }
        ImageUrl imageUrl = new ImageUrl();
        imageUrl.setUserId(userContext.getUserId());
        imageUrl.setUrl(message);
        if (imageUrlService.insertImageUrl(imageUrl) != 1) {
            ErrorInfo errorInfo = ErrorInfo.SERVER_ERROR;
            Result result = ResultUtil.buildErrorResult(errorInfo);
            return result;
        }
        Result result = ResultUtil.success(imageUrl.getImageId());
        return result;
    }
}
