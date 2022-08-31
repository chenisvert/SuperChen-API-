package com.example.superchen.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.superchen.anno.AccessLimit;
import com.example.superchen.api.WeatherApi;
import com.example.superchen.api.getText;
import com.example.superchen.common.UserException;
import com.example.superchen.domain.dom.Access;
import com.example.superchen.domain.dom.User;
import com.example.superchen.domain.ro.ErrorCode;
import com.example.superchen.domain.ro.Result;
import com.example.superchen.service.AccessService;
import com.example.superchen.utils.DateUtils;
import com.example.superchen.utils.IPUtil;
import com.example.superchen.utils.IpAddressUtils;
import com.example.superchen.utils.QiniuProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.example.superchen.common.RedisKey.KEY;
import static com.example.superchen.common.RedisKey.TOKEN_KEY;
import static com.example.superchen.domain.ro.ErrorCode.*;

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
@RequestMapping("/apisTwo")
public class PubilcApiTwoController extends BaseController {

    private Result result = new Result<>();
    /***
     * 随机或者指定id获取图片
     * @Author chen
     * @Date 23:37
     * @Param
     * @Return
     * @Since version-11

     */

    @AccessLimit(seconds = 1, maxCount = 7) //1秒运行请求7次
    @ResponseBody
    @GetMapping("/setAccessCount/{token}")
    public Result setAccessCount(@PathVariable String token) {
        log.info("入参 ,token：{}", token);
        if (StringUtils.isEmpty(token)){
            result.setCode(PARAMS_ERROR.getErrCode());
            result.setMsg(PARAMS_ERROR.getErrMsg());
            result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
            return result;
        }
        Optional.ofNullable(token).orElseThrow(()->new UserException("参数为空！"));

        Access access = new Access();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getToken, token);
        List<User> userList = userService.list(queryWrapper);

        LambdaQueryWrapper<Access> queryWrapperAccess = new LambdaQueryWrapper<>();
        queryWrapperAccess.eq(Access::getToken,token);
        List<Access> listAccess = accessService.list(queryWrapperAccess);

        //认证token失败
        if (userList.isEmpty()) {
            result.setCode(TOKEN_ERROR.getErrCode());
            result.setMsg(TOKEN_ERROR.getErrMsg());
            result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
            return result;
        }
        //未开通服务
        if (listAccess.isEmpty()){
            result.setCode(SERVICE_ERROR.getErrCode());
            result.setMsg(SERVICE_ERROR.getErrMsg());
            result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
            return result;
        }
        List<Access> list = accessService.list(queryWrapperAccess);
        list.stream().map((item) ->{
            access.setId(item.getId());
            access.setCount(item.getCount());
            access.setThreshold(item.getThreshold());
            access.setCreateTime(item.getCreateTime());
            access.setToken(item.getToken());
            return access;
        }).collect(Collectors.toList());

        int count = access.getCount()+1;
        access.setCount(count);
        accessService.updateById(access);
        //返回
        result.setCode(200);
        result.setMsg(count);
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        return result;
    }

    /***
     *
     * 获取天气
     * @Author chen
     * @Date  19:12
     * @Param  day（天气日期），model（城市），token（认证）
     * @Return Result
     * @Since version-11

     */

    @AccessLimit(seconds = 1, maxCount = 5) //1秒运行请求5次
    @GetMapping("/getWeather/{day}/{model}/{token}")
    public Result getWeather( @PathVariable Integer day,@PathVariable String model,@PathVariable String token)  {

        Optional.ofNullable(day).orElseThrow(()->new UserException("日期为空！"));
        if (StringUtils.isEmpty(token) | StringUtils.isEmpty(model) ){
            result.setCode(PARAMS_ERROR.getErrCode());
            result.setMsg(PARAMS_ERROR.getErrMsg());
            result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
            return result;
        }

        String ipAddr = IPUtil.getIpAddr(request);
        String address = IpAddressUtils.getIpSource(ipAddr);
        log.info(address);
        String city = address.substring(7, 10);
        //自定义是否传城市
        if (!model.equals("null")){
            city = model;
        }

        log.info("入参  token：{}", token);
//        Object tockens = this.redisTemplate.opsForValue().get("token");
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(User::getToken, token);
        List<User> userList = userService.list(queryWrapper);
        System.out.println(userList);
        if (!userList.isEmpty()) {
            Object weatherCache = redisTemplate.opsForValue().get(ipAddr+"_"+KEY + "_getWeather_"+city+"_"+day);
            if (weatherCache != null){
                result.setCode(200);
                result.setMsg(weatherCache);
                result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
                return result;
            }

            String weather = new WeatherApi().getWeather(city, day);

            redisTemplate.opsForValue().set(ipAddr+"_"+KEY + "_getWeather_"+city+"_"+day,weather,3, TimeUnit.HOURS);
            result.setCode(200);
            result.setMsg(weather);
            result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
            return result;
        }

        //超出天数
        if (day > 2){
            result.setCode(PARAMS_ERROR.getErrCode());
            result.setMsg(PARAMS_ERROR.getErrMsg());
            result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
            return result;
        }
            log.info("token 认证错误");
            result.setCode(TOKEN_ERROR.getErrCode());
            result.setMsg(TOKEN_ERROR.getErrMsg());
            result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
            return result;
    }




}
