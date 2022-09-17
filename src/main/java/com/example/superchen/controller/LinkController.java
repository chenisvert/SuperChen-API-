package com.example.superchen.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.superchen.domain.dom.Link;
import com.example.superchen.domain.dom.User;
import com.example.superchen.domain.ro.Result;
import com.example.superchen.utils.DateUtils;
import com.example.superchen.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.example.superchen.common.RedisKey.KEY;

@Slf4j
@Controller
@RequestMapping("/link")
public class LinkController extends BaseController{

    private Result result = new Result<>();

    @ResponseBody
    @PostMapping ("/saveLink")
    public Result saveLink(@RequestBody Link link){

        String url = link.getUrl();
        String urls = "http://hh2022.cn/link/jumpLink/";
        User user = (User) session.getAttribute("login");
        System.out.println(user);
        String token = user.getToken();
        link.setToken(token);
        linkService.save(link);
        int id = link.getId();
        System.out.println("========================id:"+id);
        result.setCode(200);
        result.setMsg(urls+id);
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        return result;
    }


    @GetMapping ("/jumpLink/{id}")
    public Module jumpLink(@PathVariable Integer id) throws IOException {

        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getId,id);
        List<Link> list = linkService.list(queryWrapper);
        String url = null;
        for (Link links:list) {
             url = links.getUrl();
             log.info(url);
        }

        response.sendRedirect(url);
        return null;
    }


}


