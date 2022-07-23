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

public class ImagesApi {
    public String getImageUrl() {
        String address = "";
        try {
            JSONObject resultJson = readJsonFromUrl("https://api.apiopen.top/api/getImages?page=0&size=1");
            address = ((JSONObject) resultJson.get("result")).getString("list");
            // Json字符串转换成JsonNode对象
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(address);
            address = String.valueOf(jsonNode.findValue("url"));
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
