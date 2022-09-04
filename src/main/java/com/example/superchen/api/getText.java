package com.example.superchen.api;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

/***
 *
 * 一言api
 * @Author chen
 * @Date  14:28
 * @Param
 * @Return
 * @Since version-11

 */
//https://api.vvhan.com/api/joke 随机笑话


public class getText {
    public String getTexts() {
        String address = "";
        try {
            //{"success":true,"id":2040,"title":"摸底考试","joke":"老师来到教室通知道：“同学们，明天我们要进行摸底考试，大家好好准备一下，这节课上自习。”一学生在下面嘀咕道：“老师，我们心里没‘底’，还是别摸了。”"}
            JSONObject resultJson = readJsonFromUrl("https://api.wpbom.com/api/index.php?type=json");
            address = resultJson.get("ishan").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(address);
        return address;
    }

    /**
     * 读取
     *
     * @param rd
     * @return
     * @throws IOException
     */
    private static String readAll(BufferedReader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    /**
     * 创建链接
     *
     * @param url
     * @return
     * @throws IOException
     * @throws JSONException
     */
    private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = JSONObject.parseObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }
}
