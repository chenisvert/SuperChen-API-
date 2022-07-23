package com.example.superchen.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.superchen.domain.dom.ImagesUrl;
import com.example.superchen.domain.dom.User;
import com.example.superchen.domain.ro.Result;
import com.example.superchen.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.example.superchen.common.RedisKey.KEY;
import static com.example.superchen.common.RedisKey.TOKEN_KEY;

/***
 * 开放 api
 * @Author chen
 * @Date  23:37
 * @Param
 * @Return
 * @Since version-11

 */

@Slf4j
@RestController
@RequestMapping("/apis")
public class PubicApiController extends BaseController{

    private Result result = new Result<>();

    /***
     * 随机或者指定id获取图片
     * @Author chen
     * @Date  23:37
     * @Param
     * @Return
     * @Since version-11

     */
    @ResponseBody
    @GetMapping("/getimages/{id}/{token}")
    public Result Imgurl(@PathVariable int id,@PathVariable String token){
        log.info("入参 id:{},token：{}",id,token);
        Object tockens = redisTemplate.opsForValue().get(TOKEN_KEY);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getToken,token);
        List<User> userList = userService.list(queryWrapper);
        System.out.println(userList);

        //认证临时token
        if (token.equals(tockens)){
            log.info("临时token: {}",tockens);
            Result result = pubicApiService.getImage(id, token);
            return result;
        }

        //认证token
        if (userList.size() != 0 ){
            Result result = pubicApiService.getImage(id, token);
            return result;
        }
        //临时token 和 token 都认证失败
        result.setCode(403);
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        result.setMsg("token 认证错误");
        return result;


    }
}
