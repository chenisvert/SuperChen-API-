package com.example.superchen.api;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

/*
* 百度地图api IP地址查询
* */
@Component
public class BaiduAddressUtil {

    /**
     * 百度地图申请的ak
     */
//    @Value("${baidu.ak}")
    private String AK = "LYtAh4tS4jA0GxG0qwwZK4ylQWLRch27";

    public String getAddress(String ip) {
        String address = "";
        try {
            // 这里调用百度的ip定位api服务 详见 http://api.map.baidu.com/lbsapi/cloud/ip-location-api.htm
            JSONObject resultJson = readJsonFromUrl("http://api.map.baidu.com/location/ip?ip=" + ip + "&ak=" + AK);
            //resultJson 是返回结果，当前只取位置信息
            address = ((JSONObject) resultJson.get("content")).getString("address");
        } catch (Exception e) {
            e.printStackTrace();
        }
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