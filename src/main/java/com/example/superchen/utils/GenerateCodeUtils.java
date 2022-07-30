package com.example.superchen.utils;

import java.io.File;
import java.util.UUID;

/***
 *
 * 基于 QrCodeUtils
 * 自己封装的二维码生成（二次）工具类
 * @Author chen
 * @Date  11:54
 * @Param
 * @Return
 * @Since version-11

 */
public class GenerateCodeUtils {


    public  static String getCode(String url) throws Exception {

        //使用UUID重新生存文件名，防止覆盖
        String fileName = UUID.randomUUID().toString();

//        File dir = new File("/codeimages");
        File dir = new File("/codeimages");
        //判断目录是否存在
        if (!dir.exists()){
            //不存在 ,创建目录
            dir.mkdirs();
        }

        String logoPath = "/codeimages/logo.jpg";
        String destPath = "/codeimages/"+fileName+".jpg";
        QrCodeUtils.encode(url,logoPath,destPath,true);
        return fileName;
    }
}
