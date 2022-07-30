package com.example.superchen.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.superchen.api.ImagesApi;
import com.example.superchen.api.MusicApi;
import com.example.superchen.domain.dom.Url;
import com.example.superchen.domain.dom.User;
import com.example.superchen.domain.ro.Result;
import com.example.superchen.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.example.superchen.common.RedisKey.KEY;

@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") //设置跨域
public class ApiController extends BaseController {



    private Result result = new Result();
    @PostMapping("/mp4")
    public Result mp4url(){
        User user = (User) session.getAttribute("login");
        String mp4Key = KEY +"_"+user.getUsername()+"_/api/mp4";
        if (redisTemplate.opsForValue().get(mp4Key) != null){
            result.setCode(403);
            result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
            result.setMsg("请求过于频繁，请稍后再试");
            return result;
        }

        String mp4Url = mp4Api.getMp4Url();
        result.setCode(200);
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        result.setMsg(mp4Url);
        redisTemplate.opsForValue().set(mp4Key,user.getUsername(),10, TimeUnit.SECONDS);
        return result;
    }
    @PostMapping("/image")
    public Result Imgurl(){
        User user = (User) session.getAttribute("login");
        String imgKey = KEY+"_"+user.getUsername()+"_/api/image";
        if (redisTemplate.opsForValue().get(imgKey) != null){
            result.setCode(403);
            result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
            result.setMsg("请求过于频繁，请稍后再试");
            return result;
        }

        String imageUrl = new ImagesApi().getImageUrl();
        result.setCode(200);
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        result.setMsg(imageUrl);
        redisTemplate.opsForValue().set(imgKey,user.getUsername(),2, TimeUnit.SECONDS);
        return result;
    }

    @RequestMapping("/myMp4")
    public Result myMp4() {
        Url url1 = new Url();
        User user = (User) session.getAttribute("login");
        String mp4Key = KEY + "_" + user.getUsername() + "_/api/myMp4";
        if (redisTemplate.opsForValue().get(mp4Key) != null) {
            result.setCode(403);
            result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
            result.setMsg("请求过于频繁，请稍后再试");
            return result;
        }
        int countRow = urlService.countRow();
        Random random = new Random();
        int i =random.nextInt(countRow+1);
        if (i  == 0){
            i = i+1;
        }
        System.out.println(i);
        LambdaQueryWrapper<Url> queryWrapper = new LambdaQueryWrapper<Url>();
        queryWrapper.eq(Url::getState,1);
        queryWrapper.eq(Url::getId,i);
        List<Url> list = urlService.list(queryWrapper);
        System.out.println(list);
        //遍历获取url
        list.stream().map((item) ->{
            String url = item.getUrl();
            url1.setUrl(url);
            return url1;
        }).collect(Collectors.toList());
        result.setCode(200);
        result.setDate(DateUtils.getDate(" yyyy-MM-dd HH:mm:ss HH:mm:ss"));
        result.setMsg(url1.getUrl());
        redisTemplate.opsForValue().set(mp4Key, user.getUsername(), 5, TimeUnit.SECONDS);
        return result;
    }
    //获取音乐
    @PostMapping("/getMusic")
    public Result getMusic(@RequestBody String name) {
        log.info(name);
        String url = new MusicApi().GetMusic(name);
        result.setCode(200);
        result.setDate(DateUtils.getDate(" yyyy-MM-dd HH:mm:ss HH:mm:ss"));
        result.setMsg(url);
        return result;
    }


}
