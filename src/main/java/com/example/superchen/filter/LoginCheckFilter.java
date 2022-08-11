package com.example.superchen.filter;//package com.example.superchen.filter;

import com.alibaba.fastjson.JSON;


import com.example.superchen.common.BaseContext;
import com.example.superchen.domain.dom.User;
import com.example.superchen.domain.ro.Result;
import com.example.superchen.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查用户是否已经完成登录
 */
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter{
    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    private Result result = new Result();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //1、获取本次请求的URI
        String requestURI = request.getRequestURI();// /backend/index.html

        log.info("拦截到请求：{}",requestURI);

        //定义不需要处理的请求路径
        String[] urls = new String[]{
                "/",
                "/js/**",
                "/css/**",
                "/fonts/**",
                "/sendUrl/**",
                "/img/**",
                "/layer/**",
                "/files/**",
                "/static/**",

                "/user/**",
                "/sendUrls/**",
                "/api/**",
                "/apis/**",
                "/apisTwo/**",
                "/mains/**"




        };


        //2、判断本次请求是否需要处理
        boolean check = check(urls, requestURI);

        //3、如果不需要处理，则直接放行
        if(check){
            log.info("本次请求{}不需要处理",requestURI);
            filterChain.doFilter(request,response);
            return;
        }

        //4、判断登录状态，如果已登录，则直接放行
        User user = (User) request.getSession().getAttribute("login");
        if(request.getSession().getAttribute("login") != null){
            log.info("用户已登录，用户id为：{}",user.getId());

            //放入readLocal 中
            BaseContext.setCurrentId(user.getId());

            filterChain.doFilter(request,response);
            return;
        }



        log.info("用户未登录");
        //5、如果未登录则返回未登录结果，直接返回登录
        response.setCharacterEncoding("GBK");
        result.setCode(403);
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        result.setMsg("请登录！");
        //转json
        String s = JSON.toJSONString(result);
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(s);


        return ;

    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     * @param urls
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls,String requestURI){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match){
                return true;
            }
        }
        return false;
    }
}
