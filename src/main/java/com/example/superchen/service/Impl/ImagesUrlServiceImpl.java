package com.example.superchen.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.superchen.domain.dom.ImagesUrl;
import com.example.superchen.domain.dom.Url;
import com.example.superchen.mapper.ImagesUrlMapper;
import com.example.superchen.mapper.UrlMapper;
import com.example.superchen.service.ImagesUrlService;
import com.example.superchen.service.UrlService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ImagesUrlServiceImpl extends ServiceImpl<ImagesUrlMapper, ImagesUrl> implements ImagesUrlService {
    @Resource
    private ImagesUrlMapper imagesUrlMapper;

    @Override
    public int countRow() {
        LambdaQueryWrapper<ImagesUrl> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ImagesUrl::getState,1);
        Integer count = imagesUrlMapper.selectCount(queryWrapper);
        return count;
    }
}
