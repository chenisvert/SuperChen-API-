package com.example.superchen.controller;



import com.example.superchen.api.Mp4Api;
import com.example.superchen.service.*;
import com.example.superchen.utils.QiniuProvider;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/*
* 统一依赖注入
*
* */
@Controller
public class BaseController {


    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;



    @Resource
    protected RedisTemplate redisTemplate;
    @Resource
    protected RequestService requestService;
    @Resource
    protected UserService userService;
    @Resource
    protected UrlService urlService;
    @Resource
    protected Mp4Api mp4Api;
    @Resource
    protected ImagesUrlService imagesUrlService;
    @Resource
    protected VipvideoService vipvideoService;
    @Resource
    protected PubicApiService pubicApiService;
    @Resource
    protected IpAddressService ipAddressService;
    @Resource
    protected AccessService accessService;
    @Resource
    protected QiniuProvider qiniuProvider;




    //@ModelAttribute 会在此controller的每个方法执行前被执行 ，如果有返回值，则自动将该返回值加入到ModelMap中。
    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession(true);
    }
}
