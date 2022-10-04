package com.user.controller;

import com.atguigu.commonutils.Result;
import com.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class userController {

    @PostMapping("/login")
    public String isLogin(@RequestBody  User user){
        log.info(user.getUsername());
        //token
        log.info("请求成功！");
        String token = "aaaaaaaaaaaaaaaa";
        return token;
    }
}
