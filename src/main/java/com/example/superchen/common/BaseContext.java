package com.example.superchen.common;
//readLocal解决多线程的并发问题，是Thread的局部变量，使用它维护变量，会使该变量的线程提供一个独立的副本，可以独立修改，不会影响其他线程的副本


/**
 * 基于ThreadLocal封装工具类，用户保存和获取当前登录用户id
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 设置值
     * @param id
     */
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    /**
     * 获取值
     * @return
     */
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}