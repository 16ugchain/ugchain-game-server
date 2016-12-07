package com.fiveonechain.digitasset.service;

import com.fiveonechain.digitasset.config.QiNiuConfig;
import com.fiveonechain.digitasset.exception.ImageUploadException;
import com.fiveonechain.digitasset.util.StringBuilderHolder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

/**
 * Created by fanjl on 16/11/16.
 */
@Component
public class ImageUploadService {
    @Autowired
    private QiNiuConfig qiNiuConfig;

    StringBuilderHolder stringBuilderHolder = new StringBuilderHolder(0);

    private String uploadQiNiu(byte[] image) {
        if (image == null) {
            throw new ImageUploadException();
        }
        String imageName = DigestUtils.md5DigestAsHex(image);
        String bucketName = qiNiuConfig.getBucket();
        String ak = qiNiuConfig.getAk();
        String sk = qiNiuConfig.getSk();
        //密钥配置
        Auth auth = Auth.create(ak, sk);
        Zone z = Zone.zone0();
        Configuration c = new Configuration(z);
        //创建上传对象
        UploadManager uploadManager = new UploadManager(c);
        //imageName相同允许用户修改，只需要设置上传的空间名就可以了
        String uptoken = auth.uploadToken(bucketName, imageName);
        String body = "";
        //调用put方法上传
        Response res = null;
        try {
            res = uploadManager.put(image, imageName, uptoken);
            body += res.bodyString();
        } catch (QiniuException e) {
            throw new ImageUploadException();
        }
        //打印返回的信息

        return body;
    }

    public String uploadAndGetResult(byte[] image) {
        String message = "";
        message += uploadQiNiu(image);
        JsonObject jsonObject = new Gson().fromJson(message, JsonObject.class);
        StringBuilder urlStrBuilder = stringBuilderHolder.resetAndGet();
        if (jsonObject.has("key")) {
            String domain = qiNiuConfig.getDownloadUrl();
            String domainStr = domain.replace("\"", "");
            urlStrBuilder.append(domainStr).append(jsonObject.get("key").toString().replace("\"", ""));
            System.out.println(urlStrBuilder.toString());
        }else {
            throw new ImageUploadException();
        }
        return urlStrBuilder.toString();
    }
}
