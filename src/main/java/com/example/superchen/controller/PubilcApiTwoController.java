package com.example.superchen.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.superchen.domain.dom.Access;
import com.example.superchen.domain.dom.User;
import com.example.superchen.domain.ro.Result;
import com.example.superchen.service.AccessService;
import com.example.superchen.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.superchen.common.RedisKey.TOKEN_KEY;
import static com.example.superchen.domain.ro.ErrorCode.SERVICE_ERROR;
import static com.example.superchen.domain.ro.ErrorCode.TOKEN_ERROR;

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
    @ResponseBody
    @GetMapping("/setAccessCount/{token}")
    public Result Imgurl(@PathVariable String token) {
        log.info("入参 ,token：{}", token);

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

}
