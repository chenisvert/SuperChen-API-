package com.example.superchen.anno;


import com.example.superchen.domain.ro.RoleEnum;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/***
 *
 * 权限
 * @Author chen
 * @Date  9:17
 * @Param
 * @Return
 * @Since version-11

 */
@Target(ElementType.METHOD) //表示在方法上使用
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionAnnotation {

    RoleEnum[] value() default RoleEnum.USER;
}
