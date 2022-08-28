package com.example.superchen.hander;

import lombok.extern.slf4j.Slf4j;
import org.junit.platform.commons.util.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 拦截异常处理
 * @Date: Created in 21:40 2020/5/20
 * @Author: ONESTAR
 * @QQ群: 530311074
 * @URL: https://onestar.newstar.net.cn/
 */
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {



    /**
     * @Description: 处理错误信息
     * @Auther: ONESTAR
     * @Date: 21:52 2020/5/20
     * @Param: request：访问的异常URL
     * @Param: e：异常参数
     * @Return: 返回错误信息页面
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHander(HttpServletRequest request, Exception e) throws Exception {

//        记录异常信息：请求的URL，异常信息
        log.error("Requst URL : {}，Exception : {}", request.getRequestURL(),e);

//        当标识了状态码的时候就不拦截
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

//        将记录的异常信息返回到error页面
        ModelAndView mv = new ModelAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception", e);
        mv.setViewName("error/error");
        return mv;
    }
}