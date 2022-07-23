package com.example.superchen.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.superchen.domain.dom.ImagesUrl;
import com.example.superchen.domain.dom.Url;
import com.example.superchen.domain.dom.Vipvideo;
import com.example.superchen.mapper.ImagesUrlMapper;
import com.example.superchen.mapper.VipvideoMapper;
import com.example.superchen.service.ImagesUrlService;
import com.example.superchen.service.VipvideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class VipvideoServiceImpl extends ServiceImpl<VipvideoMapper, Vipvideo> implements VipvideoService {
    @Resource
    private VipvideoMapper vipvideoMapper;

    @Override
    public int countRow() {
        LambdaQueryWrapper<Vipvideo> queryWrapper = new LambdaQueryWrapper<>();
        Integer count = vipvideoMapper.selectCount(queryWrapper);
        return count;
    }
}
