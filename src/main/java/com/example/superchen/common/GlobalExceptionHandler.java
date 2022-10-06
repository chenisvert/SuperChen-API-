package com.example.superchen.common;

import com.example.superchen.domain.dom.User;
import com.example.superchen.domain.ro.Result;
import com.example.superchen.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

import static com.example.superchen.domain.ro.ErrorCode.SERVER_ERROR;
import static com.example.superchen.domain.ro.ErrorCode.TOKEN_ERROR;

/**
 * 全局异常处理
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class}) //配置拦截
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    private Result result = new Result();

    /**
     * 异常处理方法
     * @return
     */
    @ExceptionHandler(Throwable.class)
    public Result exceptionHandler(Throwable ex){
        log.error("出现异常！"+ex.getMessage());
        result.setCode(SERVER_ERROR.getErrCode());
        result.setMsg(SERVER_ERROR.getErrMsg());
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        return result;
    }


    /**
     * 异常处理方法
     * @return
     */
    @ExceptionHandler(UserException.class)
    public Result exceptionHandler(UserException ex){
        log.error(ex.getMessage());
        result.setCode(SERVER_ERROR.getErrCode());
        result.setMsg(ex.getMessage());
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        return result;
    }
}
