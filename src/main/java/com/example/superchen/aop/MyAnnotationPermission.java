package com.example.superchen.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.superchen.anno.PermissionAnnotation;
import com.example.superchen.common.UserException;
import com.example.superchen.domain.ro.Result;
import com.example.superchen.domain.ro.RoleEnum;
import com.example.superchen.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Arrays;

import static com.example.superchen.domain.ro.ErrorCode.*;


@Aspect
@Component
@Slf4j
public class MyAnnotationPermission {

        private Result result = new Result<>();
        //切入点表达式决定了用注解方式的方法切还是针对某个路径下的所有类和方法进行切，方法必须是返回void类型

        @Pointcut("execution(public * com.example.superchen.controller.UserController.*(..))")
        private void roleCheckCut() {
        }

        //定义了切面的处理逻辑。即方法上加了@PermissionAnnotation注解，将会进行权限校验
        @Around("roleCheckCut()")
        public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            //获取session
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            PermissionAnnotation annotation = method.getAnnotation(PermissionAnnotation.class);
            //没有添加注解的放行
            if (annotation == null) {
                return joinPoint.proceed();
            }
            RoleEnum[] roleEnums = annotation.value();
            //session中获取用户权限
            Object userRole = session.getAttribute("permission");

            if (userRole == null) {
                throw new UserException(SESSION_ERROR.getErrMsg());
            }

            log.info("当前权限：{}", userRole.toString());
            if (Arrays.asList(roleEnums).contains(userRole)) {
                log.info("权限符合：{}", method.getName());
                return joinPoint.proceed();
            }
            //返回

            //提示权限不足
            throw new UserException(PERMISSION_ERROR.getErrMsg());


//        throw new UserException(PERMISSION_ERROR.getErrMsg());
        }

}
