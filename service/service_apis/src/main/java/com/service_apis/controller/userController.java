package com.service_apis.controller;


import com.atguigu.commonutils.Result;
import com.service_apis.client.userService;
import com.service_apis.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/users")
public class userController {

    @Resource
    private userService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user){

        String token = userService.isLogin(user);
        if (StringUtils.isEmpty(token)){
           return Result.error();
        }
        return Result.success().data("token",token);
    }

}
