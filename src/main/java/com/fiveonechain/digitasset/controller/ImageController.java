package com.fiveonechain.digitasset.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveonechain.digitasset.auth.UserContext;
import com.fiveonechain.digitasset.config.QiNiuConfig;
import com.fiveonechain.digitasset.domain.ImageUrl;
import com.fiveonechain.digitasset.domain.User;
import com.fiveonechain.digitasset.domain.result.ErrorInfo;
import com.fiveonechain.digitasset.domain.result.Result;
import com.fiveonechain.digitasset.exception.ImageUploadException;
import com.fiveonechain.digitasset.exception.JsonSerializableException;
import com.fiveonechain.digitasset.service.ImageUploadService;
import com.fiveonechain.digitasset.service.ImageUrlService;
import com.fiveonechain.digitasset.service.UserService;
import com.fiveonechain.digitasset.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public String upload(
            @RequestParam("imageUrl") MultipartFile[] images,
            @AuthenticationPrincipal UserContext userContext
    ) {

        User user = iUserService.getUserByUserId(userContext.getUserId());
        if (user == null) {
            Result result = ResultUtil.buildErrorResult(ErrorInfo.USER_NOT_FOUND);
            return "{'error':'error'}";
        }
        List<Integer> imageUrls = new LinkedList<>();
        for(MultipartFile image : images){
            if (image.getSize() > qiNiuConfig.getImageMaxSize()) {
                Result result = ResultUtil.buildErrorResult(ErrorInfo.IMAGE_TOO_LARGE);
                return "{'error':'error'}";
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
                return "{'error':'error'}";
            }
            imageUrls.add(imageUrl.getImageId());

        }
        String imageIdUrl = imageUrls.stream().map(Object::toString).collect(Collectors.joining(","));
        Map<String, String> map = new HashMap<>();
        map.put("error", "");
        map.put("data",imageIdUrl);
        ObjectMapper mapper = new ObjectMapper();
        String resultString = "";
        try {
            resultString = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new JsonSerializableException(e);
        }
        Result result = ResultUtil.success(imageUrls);
        return resultString;
    }
}
