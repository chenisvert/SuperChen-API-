package com.example.superchen;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Random;

@SpringBootTest
class SuperChenNetworkApplicationTests2 {
    public static void main(String[] args) {
        Random random = new Random();
        int a=random.nextInt(1+1);
        System.out.println(a);
    }


}
