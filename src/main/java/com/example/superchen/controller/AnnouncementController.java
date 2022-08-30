package com.example.superchen.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.generator.config.IFileCreate;
import com.example.superchen.anno.AccessLimit;
import com.example.superchen.domain.dom.Access;
import com.example.superchen.domain.dom.Announcement;
import com.example.superchen.domain.dom.User;
import com.example.superchen.domain.ro.Result;
import com.example.superchen.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/announcement")
public class AnnouncementController extends BaseController{


    private Result result = new Result<>();git commit -m
    private  Announcement announcement =  new Announcement();

    @AccessLimit(seconds = 1, maxCount = 10)
    @ResponseBody
    @GetMapping("/getAnnouncement")
    public Result getAnnouncement(){
        LambdaQueryWrapper<Announcement> queryWrapper = new LambdaQueryWrapper<>();
        List<Announcement> list = announcementService.list(queryWrapper);
        if (list.isEmpty()){
            result.setCode(201);
            result.setMsg("暂时没有公告");
            result.setDate(DateUtils.getDate(" yyyy-MM-dd HH:mm:ss HH:mm:ss"));
            return result;
        }

        //遍历集合
        list.stream().map((item) -> {
            announcement.setContext(item.getContext());
            announcement.setCreateUser(item.getCreateUser());
            announcement.setCreateTime(item.getCreateTime());
            return announcement;
        }).collect(Collectors.toList());

        result.setCode(200);
        result.setMsg(announcement);
        result.setDate(DateUtils.getDate(" yyyy-MM-dd HH:mm:ss HH:mm:ss"));
        return result;
    }
}
