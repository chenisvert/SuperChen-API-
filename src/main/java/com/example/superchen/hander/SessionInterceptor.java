package com.example.superchen.hander;

import com.alibaba.fastjson.JSONObject;
import com.example.superchen.anno.AccessLimit;
import com.example.superchen.domain.ro.Result;
import com.example.superchen.utils.DateUtils;
import com.example.superchen.utils.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

import static com.example.superchen.domain.ro.ErrorCode.TIMEOUT_ERROR;


/***
 *
 * 接口限流拦截器
 * @Author chen
 * @Date  9:42
 * @Param
 * @Return
 * @Since version-11

 */
@Slf4j
@Component
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if (null == accessLimit) {
                return true;
            }
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();

            if (needLogin) {
                //判断是否登录
            }
            String ip = IPUtil.getIpAddr(request);
            //根据请求地址和ip 做key
            String key = request.getServletPath() + ":" + ip;
            Integer count = (Integer) redisTemplate.opsForValue().get(key);
            //判断是否在禁止访问时间内
            if (redisTemplate.opsForValue().get(key+"_time") != null){
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                Result result = new Result();
                result.setCode(TIMEOUT_ERROR.getErrCode());
                result.setMsg(TIMEOUT_ERROR.getErrMsg());
                result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
                Object obj = JSONObject.toJSON(result);
                response.getWriter().write(JSONObject.toJSONString(obj));
                return false;
            }
            //第一次访问
            if (null == count || -1 == count) {
                redisTemplate.opsForValue().set(key, 1, seconds, TimeUnit.SECONDS);
                return true;
            }

            if (count < maxCount) {
                count = count + 1;
                //重置次数
                redisTemplate.opsForValue().set(key, count, 0);
                return true;
            }

            if (count >= maxCount) {
                //接口60秒禁止访问
                redisTemplate.opsForValue().set(key+"_time", DateUtils.getDate("yyyy-MM-dd HH:mm:ss"), 60, TimeUnit.SECONDS);
                log.info("已触发-"+request.getServletPath() +"-接口限流,解除时间剩余：{}秒",redisTemplate.opsForValue().getOperations().getExpire(key+"time"));
//                response 返回 json 请求过于频繁请稍后再试
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                Result result = new Result();
                result.setCode(TIMEOUT_ERROR.getErrCode());
                result.setMsg(TIMEOUT_ERROR.getErrMsg());
                result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
                Object obj = JSONObject.toJSON(result);
                response.getWriter().write(JSONObject.toJSONString(obj));
                return false;
            }
        }

        return true;
    }
}
