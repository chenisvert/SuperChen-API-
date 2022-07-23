package com.example.superchen.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.superchen.domain.dom.Url;
import com.example.superchen.domain.dom.User;
import com.example.superchen.mapper.UrlMapper;
import com.example.superchen.mapper.UserMapper;
import com.example.superchen.service.UrlService;
import com.example.superchen.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UrlServiceImpl extends ServiceImpl<UrlMapper, Url> implements UrlService {

    @Resource
    private UrlMapper urlMapper;

    @Override
    public int countRow() {
        LambdaQueryWrapper<Url> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Url::getState,1);
        Integer count = urlMapper.selectCount(queryWrapper);
        return count;
    }
}
