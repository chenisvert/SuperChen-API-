package com.example.superchen.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.superchen.anno.AccessLimit;
import com.example.superchen.common.UserException;
import com.example.superchen.domain.dom.Access;
import com.example.superchen.domain.dom.User;
import com.example.superchen.domain.ro.Result;
import com.example.superchen.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.example.superchen.domain.ro.ErrorCode.PARAMS_ERROR;
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

    /***
     * 增加访问
     * @Author chen
     * @Date  14:40
     * @Param
     * @Return
     * @Since version-11

     */
    @AccessLimit(seconds = 1, maxCount = 7)
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

    /***
     * 获取登录用户的预警阈值
     * @Author chen
     * @Date  17:06
     * @Param
     * @Return
     * @Since version-11

     */
    @ResponseBody
    @PostMapping("/checkThreshold")
    public Result checkThreshold() {

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
            access.setThreshold(access1.getThreshold());
        }
        result.setCode(200);
        result.setMsg(access.getThreshold());
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        return result;

    }


    /***
     *
     * 修改访问阈值
     * @Author chen
     * @Date  19:59
     * @Param
     * @Return
     * @Since version-11

     */

    @ResponseBody
    @PostMapping("/upDataThreshold")
    public Result upDataThreshold(@RequestBody Access accessOn) {


        if (accessOn.getCount() == null){
            throw  new UserException(PARAMS_ERROR.getErrMsg());
        }

        User user = (User) session.getAttribute("login");
        Access access = new Access();

        LambdaQueryWrapper<Access> queryWrapperAccess = new LambdaQueryWrapper<>();
        queryWrapperAccess.eq(Access::getToken,user.getToken());
        List<Access> list = accessService.list(queryWrapperAccess);
        System.out.println(list);
        //服务未开通
        if (list.isEmpty()){
            result.setCode(SERVICE_ERROR.getErrCode());
            result.setMsg(SERVICE_ERROR.getErrMsg());
            result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
            return result;
        }

        for (Access access1:list) {
            BeanUtils.copyProperties(access1,access);
        }
        if (access.getCount() > accessOn.getThreshold()){
            result.setCode(PARAMS_ERROR.getErrCode());
            result.setMsg(PARAMS_ERROR.getErrMsg());
            result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
            return result;
        }
        access.setThreshold(accessOn.getThreshold());
        accessService.updateById(access);

        result.setCode(200);
        result.setMsg("修改成功");
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        return result;

    }

    @AccessLimit(seconds = 1, maxCount = 7)
    @ResponseBody
    @GetMapping("/dirCleanState")
    public Result dirCleanState() {

        User user = (User) session.getAttribute("login");
        String token = user.getToken();
        Access access = new Access();

        LambdaQueryWrapper<Access> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Access::getToken,token);
        List<Access> list = accessService.list(queryWrapper);
        Integer cleanday = null;
        for (Access access1:list) {
            cleanday = access1.getCleanday();
        }
        result.setCode(200);
        result.setMsg(cleanday);
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        return result;
    }


    @AccessLimit(seconds = 1, maxCount = 7)
    @ResponseBody
    @GetMapping("/openClean")
    public Result openClean() {

        User user = (User) session.getAttribute("login");
        String token = user.getToken();
        Access access = new Access();

        LambdaQueryWrapper<Access> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Access::getToken,token);
        List<Access> list = accessService.list(queryWrapper);
        Integer cleanday = null;
        Long id = null;
        for (Access access1:list) {
            cleanday = access1.getCleanday();
            id = access1.getId();
        }
        //控制开关
        if (cleanday == 1){
            cleanday = 0;
        }else {
            cleanday = 1;
        }
        System.out.println(cleanday);
        access.setCleanday(cleanday);
        access.setId(id);
        accessService.updateById(access);

        result.setCode(200);
        result.setMsg(cleanday);
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        return result;
    }


}
