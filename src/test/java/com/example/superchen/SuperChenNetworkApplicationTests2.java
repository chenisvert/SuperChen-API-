package com.example.superchen;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.superchen.controller.BaseController;

import com.example.superchen.utils.QrCodeUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.UUID;

@SpringBootTest
class SuperChenNetworkApplicationTests2 {
    public static void main(String[] args) throws Exception {

        //使用UUID重新生存文件名，防止覆盖
        String fileName = UUID.randomUUID().toString();

        File dir = new File("/codeimages");
        //判断目录是否存在
        if (!dir.exists()){
            //不存在 ,创建目录
            dir.mkdirs();
        }
          String text = "https://blog.csdn.net/weixin_43763430";
        String logoPath = "/codeimages/logo.jpg";
        String destPath = "/codeimages/"+fileName+".jpg";
        QrCodeUtils.encode(text,logoPath,destPath,true);
    }


}
