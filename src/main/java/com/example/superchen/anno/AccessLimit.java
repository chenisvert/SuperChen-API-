package com.example.superchen.anno;

import java.lang.annotation.*;

/***
 *
 * 接口限流
 * @Author chen
 * @Date  18:12
 * @Param
 * @Return
 * @Since version-11

 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLimit {
    int seconds();
    int maxCount();
    boolean needLogin()default true;
}