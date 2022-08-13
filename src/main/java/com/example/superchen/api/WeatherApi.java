package com.example.superchen.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.example.superchen.api.BaiduAddressUtil.readJsonFromUrl;

public class WeatherApi {
    public String getWeather(String  city,Integer day) {
        String weathers = "";
        try {
            JSONObject resultJson = readJsonFromUrl("http://portalweather.comsys.net.cn/weather03/api/weatherService/getDailyWeather?cityName="+city);
            //resultJson 是返回结果，当前只取位置信息

//            address = ((JSONObject) resultJson.get("data")).getString("location");
            JSONArray jsonArray = resultJson.getJSONArray("results");
            System.out.println(jsonArray);
            JSONArray obs = null;
            for (int i = 0; i < jsonArray.size() ; i++) {
                JSONObject ob = (JSONObject) jsonArray.get(i);//得到json对象
                obs = ob.getJSONArray("daily");
            }
            JSONObject ob = (JSONObject) obs.get(day);//得到json对象
            String weather = ob.getString("text_night");
            String  date = ob.getString("date");
            if (city .equals("")){
                //没有获取到城市
                weathers = date+":"+weather;
            }else {
                weathers = city + "-" + date + ":" + weather;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return weathers;
    }
}
