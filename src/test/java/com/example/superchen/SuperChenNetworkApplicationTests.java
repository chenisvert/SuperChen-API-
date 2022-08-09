package com.example.superchen;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

@SpringBootTest
class SuperChenNetworkApplicationTests {
    public static void main(String[] args) {
        String address = getAddress();
    }


    public static String getAddress() {
        String address = "";

        try {
            JSONObject resultJson = readJsonFromUrl("http://opendata.baidu.com/api.php?query=47.106.67.99&co=&resource_id=6006&oe=utf8");
            //resultJson 是返回结果，当前只取位置信息

//            address = ((JSONObject) resultJson.get("data")).getString("location");
            JSONArray jsonArray = resultJson.getJSONArray("data");
            System.out.println(jsonArray);
            for (int i = 0; i < jsonArray.size() ; i++) {
                JSONObject ob = (JSONObject) jsonArray.get(i);//得到json对象
                System.out.println(ob.getString("location"));
            }


            // Json字符串转换成JsonNode对象
//            ObjectMapper mapper = new ObjectMapper();
//            JsonNode jsonNode = mapper.readTree(address);
//            address = String.valueOf(jsonNode.findValue("location"));

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
