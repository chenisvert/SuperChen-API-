package com.example.superchen.common;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * redis分布式锁
 * Created by Administrator on 2018/11/5.
 */
@Slf4j
@Component
public class RedisSynUtil {

    /**
     * 引入Redis 操作
     */
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 加锁
     * @param key  唯一标识
     * @param value 当前时间 + 超时时间
     * @return
     */
    public boolean lock(String key,String value){
        if(redisTemplate.opsForValue().setIfAbsent(key,value)){
            return true;
        }
        //从key  获取时间
        String currentTime = redisTemplate.opsForValue().get(key);
        //如果锁超时
        if(!StringUtils.isEmpty(currentTime)
                &&Long.valueOf(currentTime)<System.currentTimeMillis()){
            //获取上一个锁的时间
            String oldTime = redisTemplate.opsForValue().getAndSet(key, value);
            if(!StringUtils.isEmpty(oldTime) && oldTime.equals(currentTime)){
                return true;
            }
        }

        return false;
    }

    /**
     * 解锁
     * @param key
     * @param value
     */
    public void unlock(String key,String value){
        try{
            //获取当前值
            String curentValue = redisTemplate.opsForValue().get(key);
            if(!StringUtils.isEmpty(curentValue) && curentValue.equals(value)){
                //删除
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        }catch (Exception e){
            log.error("【redis分布式锁】解锁异常 e={}",e);
        }
    }

//    /**
//     * 删除锁
//     *
//     * @param key
//     */
//    public void delete(String key) {
//        redisTemplate.delete(LOCK_PREFIX+key);
//    }

}