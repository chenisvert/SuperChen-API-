package com.example.superchen.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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


    /***
     *
     * 第三方接口获取
     * @Author chen
     * @Date  7:32
     * @Param
     * @Return
     * @Since version-11

     */
    /*
    *
    * {
  "status": "0",
  "t": "",
  "set_cache_time": "",
  "data": [
    {
      "ExtendedLocation": "",
      "OriginQuery": "47.106.67.99",
      "appinfo": "",
      "disp_type": 0,
      "fetchkey": "47.106.67.99",
      "location": "广东省深圳市 阿里云",
      "origip": "47.106.67.99",
      "origipquery": "47.106.67.99",
      "resourceid": "6006",
      "role_id": 0,
      "shareImage": 1,
      "showLikeShare": 1,
      "showlamp": "1",
      "titlecont": "IP地址查询",
      "tplt": "ip"
    }
  ]
}
    * */
    public String getThirdAddress(String ip) {
        String address = "";
        try {
            JSONObject resultJson = readJsonFromUrl("http://opendata.baidu.com/api.php?query="+ip+"&co=&resource_id=6006&oe=utf8");
            //resultJson 是返回结果，当前只取位置信息

//            address = ((JSONObject) resultJson.get("data")).getString("location");
            JSONArray jsonArray = resultJson.getJSONArray("data");

            for (int i = 0; i < jsonArray.size() ; i++) {
                JSONObject ob = (JSONObject) jsonArray.get(i);//得到json对象
                address = ob.getString("location");
            }
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
    static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
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