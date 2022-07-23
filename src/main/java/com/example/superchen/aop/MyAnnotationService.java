package com.example.superchen.aop;

import com.example.superchen.domain.dom.User;
import com.example.superchen.domain.ro.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Aspect

@Component

@Slf4j

public class MyAnnotationService {


    //切入点表达式决定了用注解方式的方法切还是针对某个路径下的所有类和方法进行切，方法必须是返回void类型

    @Pointcut("execution(public * com.example.superchen.controller.VipvideoController.*(..))")
    private void roleCheckCut() {
    }

    ;


    //定义了切面的处理逻辑。即方法上加了@MyAnnotation注解，将会进行权限校验

    @Before("roleCheckCut()")
    public void Before(JoinPoint joinPoint) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        User login = (User) request.getSession().getAttribute("login");



        //获取接口请求时header中的user_name参数，进行校验
        /*HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String userName = request.getHeader("user_name");*/

        //这里可以Apollo配置可以放行的角色

        if (login == null) {
            throw new Exception("未登录");
        }
    }
}