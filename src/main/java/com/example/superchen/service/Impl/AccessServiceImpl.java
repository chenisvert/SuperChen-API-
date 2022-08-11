package com.example.superchen.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.superchen.domain.dom.Access;
import com.example.superchen.domain.dom.ImagesUrl;
import com.example.superchen.mapper.AccessMapper;
import com.example.superchen.mapper.ImagesUrlMapper;
import com.example.superchen.service.AccessService;
import com.example.superchen.service.ImagesUrlService;
import org.springframework.stereotype.Service;

@Service
public class AccessServiceImpl extends ServiceImpl<AccessMapper, Access> implements AccessService {
}
