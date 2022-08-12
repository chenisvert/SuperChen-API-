package com.example.superchen.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.superchen.domain.dom.Access;
import com.example.superchen.domain.dom.User;
import com.example.superchen.domain.ro.Result;
import com.example.superchen.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static com.example.superchen.domain.ro.ErrorCode.SERVICE_ERROR;

@Controller
@Slf4j
@RequestMapping("/access")
public class AccessController  extends BaseController{


    private Result result = new Result<>();


    /***
     * 清空访问量
     * @Author chen
     * @Date  8:02
     * @Param
     * @Return
     * @Since version-11

     */
    @ResponseBody
    @PostMapping("/resetAccess")
    public Result openAccess() {

        User user = (User) session.getAttribute("login");
        Access access = new Access();

        LambdaQueryWrapper<Access> queryWrapperAccess = new LambdaQueryWrapper<>();
        queryWrapperAccess.eq(Access::getToken,user.getToken());
        List<Access> list = accessService.list(queryWrapperAccess);
        //服务未开通
        if (list.isEmpty()){
            result.setCode(SERVICE_ERROR.getErrCode());
            result.setMsg(SERVICE_ERROR.getErrMsg());
            result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
            return result;
        }

        for (Access access1:list) {
            access.setId(access1.getId());
        }

        access.setCount(0);

        accessService.updateById(access);

        result.setCode(200);
        result.setMsg("清空成功");
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        return result;

    }

    @ResponseBody
    @PostMapping("/checkAccess")
    public Result checkAccess() {

        User user = (User) session.getAttribute("login");
        Access access = new Access();

        LambdaQueryWrapper<Access> queryWrapperAccess = new LambdaQueryWrapper<>();
        queryWrapperAccess.eq(Access::getToken,user.getToken());
        List<Access> list = accessService.list(queryWrapperAccess);
        //服务未开通
        if (list.isEmpty()){
            result.setCode(SERVICE_ERROR.getErrCode());
            result.setMsg(SERVICE_ERROR.getErrMsg());
            result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
            return result;
        }





        List<Access> list1 = accessService.list(queryWrapperAccess);
        for (Access access1:list) {
            access.setCount(access1.getCount());
        }

        result.setCode(200);
        result.setMsg(access.getCount());
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        return result;

    }


    }
