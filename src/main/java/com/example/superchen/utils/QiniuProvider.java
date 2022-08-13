package com.example.superchen.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.UUID;

@Component
public class QiniuProvider {
    // 配置信息
    @Value("${qiniu.access_token}")
    private String AK;
    @Value("${qiniu.secret_token}")
    private String SK;
    @Value("${qiniu.bucket}")
    private String BUCKET;
    @Value("${qiniu.domain_of_bucket}")
    private String DOMAIN_OF_BUCKET;


    public String upload(InputStream inputStream, String fileName) throws QiniuException {
        Auth auth = Auth.create(AK, SK);
        String token = auth.uploadToken(BUCKET);
        //Region.xinjiapo() 地域 新加坡
        Configuration cfg = new Configuration(Region.xinjiapo());
        UploadManager uploadManager = new UploadManager(cfg);
        Response response = uploadManager.put(inputStream, fileName, token, null, null);
        if (response.isOK()) {
            return download(fileName);
        }
        return null;
    }

    public String download(String filename) {
        Auth auth = Auth.create(AK, SK);
        String publicUrl = String.format("%s/%s", DOMAIN_OF_BUCKET, filename);
        // 过期时间，返回Url
        long expireInSeconds = 31536000;
        return auth.privateDownloadUrl(publicUrl, expireInSeconds);
    }
}

