package com.example.superchen.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.superchen.domain.dom.Link;
import com.example.superchen.domain.dom.Url;
import com.example.superchen.mapper.LinkMapper;
import com.example.superchen.mapper.UrlMapper;
import com.example.superchen.service.LinkService;
import com.example.superchen.service.UrlService;
import org.springframework.stereotype.Service;

@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {
}
