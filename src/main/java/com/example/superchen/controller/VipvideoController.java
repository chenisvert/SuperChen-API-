package com.example.superchen.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.superchen.anno.AccessLimit;
import com.example.superchen.domain.dom.ImagesUrl;
import com.example.superchen.domain.dom.User;
import com.example.superchen.domain.dom.Vipvideo;
import com.example.superchen.domain.ro.Result;
import com.example.superchen.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/vipVideo")
public class VipvideoController extends BaseController{

    private Result result = new Result<>();

    @AccessLimit(seconds = 10, maxCount = 5)
    @PostMapping("/list")
    public Result saveImage(@RequestBody Vipvideo vipvideo){
        log.info("入参 ：{}",vipvideo);
        Vipvideo vipvideos = new Vipvideo();
        LambdaQueryWrapper<Vipvideo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Vipvideo::getId,Vipvideo::getUrl); //只查询id和url字段
        queryWrapper.eq(Vipvideo::getId,vipvideo.getId());
        List<Vipvideo> list = vipvideoService.list(queryWrapper);
        System.out.println(list);
        if (session.getAttribute("login") == null){
            result.setCode(403);
            result.setMsg("请登录！");
            result.setDate(DateUtils.getDate(" yyyy-MM-dd HH:mm:ss"));
            return result;
        }
        if (list.isEmpty()){
            result.setCode(201);
            result.setMsg("线路暂未开通！");
            result.setDate(DateUtils.getDate(" yyyy-MM-dd HH:mm:ss"));
            return result;
        }
        list.stream().map((item) ->{
            String url = item.getUrl();
            vipvideos.setUrl(url);
            return vipvideos;
        }).collect(Collectors.toList());
        log.info(vipvideos.getUrl());
        result.setCode(200);
        result.setMsg(vipvideos.getUrl());
        result.setDate(DateUtils.getDate(" yyyy-MM-dd HH:mm:ss"));
        return result;
    }

    @AccessLimit(seconds = 1, maxCount = 8)
    @GetMapping("/countRow")
    public Result saveImage(){
        int count = vipvideoService.countRow();
        System.out.println(count);
        result.setCode(200);
        result.setMsg(String.valueOf(count));
        result.setDate(DateUtils.getDate(" yyyy-MM-dd HH:mm:ss"));
        return result;
    }

}
