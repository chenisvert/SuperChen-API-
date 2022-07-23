package com.example.superchen.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.superchen.domain.dom.ImagesUrl;
import com.example.superchen.domain.ro.Result;
import com.example.superchen.service.ImagesUrlService;
import com.example.superchen.service.PubicApiService;
import com.example.superchen.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.example.superchen.common.RedisKey.KEY;

@Slf4j
@Service
public class PubicApiServiceImpl implements PubicApiService {

    private Result result = new Result<>();

    @Resource
    private ImagesUrlService imagesUrlService;
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public Result getImage(int id,String token) {
        ImagesUrl imagesUrl = new ImagesUrl();
        String imgKey = KEY + "_" + token + "_/apis/getimages";
        if (redisTemplate.opsForValue().get(imgKey) != null) {
            result.setCode(403);
            result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
            result.setMsg("请求过于频繁，请稍后再试");
            return result;
        }
        //判断是否随机
        if (id != -1) {
            //按照传入的id查询
            LambdaQueryWrapper<ImagesUrl> queryWrappersid = new LambdaQueryWrapper<ImagesUrl>();
            queryWrappersid.eq(ImagesUrl::getId, id);
            queryWrappersid.eq(ImagesUrl::getState, 1);
            List<ImagesUrl> list = imagesUrlService.list(queryWrappersid);
            if (list.isEmpty()) {
                result.setCode(201);
                result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
                result.setMsg("没有这个id");
                return result;
            }
            //遍历获取url
            list.stream().map((item) -> {
                String url = item.getUrl();
                imagesUrl.setUrl(url);
                return imagesUrl;
            }).collect(Collectors.toList());

            result.setCode(200);
            result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
            result.setMsg(imagesUrl.getUrl());
            redisTemplate.opsForValue().set(imgKey, token, 1, TimeUnit.SECONDS);
            return result;

        }
        //随机查询

        int countRow = imagesUrlService.countRow();
        Random random = new Random();
        int i = random.nextInt(countRow + 1);
        if (i == 0) {
            i = i + 1;
        }
        LambdaQueryWrapper<ImagesUrl> queryWrappers = new LambdaQueryWrapper<ImagesUrl>();
        queryWrappers.eq(ImagesUrl::getState, 1);
        queryWrappers.eq(ImagesUrl::getId, i);
        List<ImagesUrl> list = imagesUrlService.list(queryWrappers);
        //遍历获取url
        list.stream().map((item) -> {
            String url = item.getUrl();
            imagesUrl.setUrl(url);
            return imagesUrl;
        }).collect(Collectors.toList());
        result.setCode(200);
        result.setDate(DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
        result.setMsg(imagesUrl.getUrl());
        redisTemplate.opsForValue().set(imgKey, token, 1, TimeUnit.SECONDS);
        return result;
    }
}
