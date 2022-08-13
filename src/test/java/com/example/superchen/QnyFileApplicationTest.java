package com.example.superchen;

import com.example.superchen.utils.QiniuProvider;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@SpringBootTest
public class QnyFileApplicationTest {

    @Resource
    private QiniuProvider qiniuProvider;
    public  static void main(String[] args) {
        try {
            testGetFileUrl();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    //获取文件url
    private static void testGetFileUrl() throws UnsupportedEncodingException {
        Auth auth = Auth.create("0hekMJHr9kc8ZijWiV6Csq4KDSjZPLkncSh6TT8U", "7jqDSdfADdJMHVp2Au4yqOGzwsNPkUbZZ6EeTaoS");
        String publicUrl = String.format("%s/%s", "http://api.hh2022.cn/superchen", "LOIC.exe");
        // 过期时间，返回Url
        long expireInSeconds = 31536000;
        String s = auth.privateDownloadUrl(publicUrl, expireInSeconds);
        System.out.println(s);
    }
}
