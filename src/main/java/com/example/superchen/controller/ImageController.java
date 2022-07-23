package com.example.superchen.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.superchen.api.ImagesApi;
import com.example.superchen.domain.dom.ImagesUrl;
import com.example.superchen.domain.dom.Url;
import com.example.superchen.domain.dom.User;
import com.example.superchen.domain.ro.Result;
import com.example.superchen.service.ImagesUrlService;
import com.example.superchen.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.example.superchen.common.RedisKey.KEY;


@Slf4j
@Controller
@RequestMapping("/images")
public class ImageController  extends BaseController{
    private Result result = new Result<>();


    @ResponseBody
    @PostMapping("/save")
    public Result saveImage(@RequestBody ImagesUrl img){
        log.info("入参 ：{}",img);
        User user = (User) session.getAttribute("login");
        img.setCreateUser(user.getUsername());
        imagesUrlService.save(img);

        result.setCode(200);
        result.setMsg("提交成功");
        result.setDate(DateUtils.getDate(" yyyy-MM-dd HH:mm:ss HH:mm:ss"));
        return result;
    }

    @ResponseBody
    @PostMapping("/randomImg")
    public Result Imgurl(){
        ImagesUrl imagesUrl = new ImagesUrl();
        User user = (User) session.getAttribute("login");
        String imgKey = KEY+"_"+user.getUsername()+"_/images/randomImg";
        if (redisTemplate.opsForValue().get(imgKey) != null){
            result.setCode(403);
            result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
            result.setMsg("请求过于频繁，请稍后再试");
            return result;
        }

        int countRow = imagesUrlService.countRow();
        Random random = new Random();
        int i =random.nextInt(countRow+1);
        if (i  == 0){
            i = i+1;
        }
        LambdaQueryWrapper<ImagesUrl> queryWrapper = new LambdaQueryWrapper<ImagesUrl>();
        queryWrapper.select(ImagesUrl::getId,ImagesUrl::getUrl,ImagesUrl::getState); //查询指定字段
        queryWrapper.eq(ImagesUrl::getState,1);
        queryWrapper.eq(ImagesUrl::getId,i);
        List<ImagesUrl> list = imagesUrlService.list(queryWrapper);
        //遍历获取url
        list.stream().map((item) ->{
            String url = item.getUrl();
            imagesUrl.setUrl(url);
            return imagesUrl;
        }).collect(Collectors.toList());
        result.setCode(200);
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        result.setMsg(imagesUrl.getUrl());
        redisTemplate.opsForValue().set(imgKey, user.getUsername(), 1, TimeUnit.SECONDS);
        return result;
    }

}
