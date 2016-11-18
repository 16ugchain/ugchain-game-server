package com.fiveonechain.digitasset.service;

import com.fiveonechain.digitasset.config.QiNiuConfig;
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

    public  String uploadQiNiu(byte[] image) throws QiniuException{
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
        String uptoken = auth.uploadToken(bucketName,imageName);
        String body = "";
                //调用put方法上传
                Response res = uploadManager.put(image, imageName, uptoken);
                //打印返回的信息
                body += res.bodyString();
                System.out.println(res.bodyString());


       return body;
    }
}
