package com.example.superchen.controller;

import com.example.superchen.domain.ro.Result;
import com.example.superchen.utils.DateUtils;
import com.example.superchen.utils.TextUtil;
import com.github.kevinsawicki.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;

@Slf4j
@Controller
public class RequestController extends BaseController {

    private Result result = new Result();

    /***
     * http 请求发送
     * @Author chen
     * @Date  16:49
     * @Param
     * @Return
     * @Since version-11

     */
    @ResponseBody
    @GetMapping("/sendUrl/{url}")
    public Result sendUrl(@PathVariable String url)  {

        Result result = new Result();
        String body = null;
        try {
            body = requestService.http(url);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("http请求错误： {}",e.getMessage());
        }
        result.setCode(200);
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        result.setMsg(body);
        return result;
    }


    /***
     * https 请求发送
     * @Author chen
     * @Date  16:49
     * @Param
     * @Return
     * @Since version-11

     */
    @ResponseBody
    @GetMapping("/sendUrls/{url}")
    public Result sendUrls(@PathVariable String url) {

        log.info("https ==================== {}", url);
        String body = null;
        try {
            body = requestService.https(url);
        } catch (Exception e) {
            log.error("http请求错误： {}",e.getMessage());
        }
        result.setCode(200);
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        result.setMsg(body);
        return result;
    }




}




