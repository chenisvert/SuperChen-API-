package com.example.superchen.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.superchen.domain.dom.Announcement;
import com.example.superchen.mapper.AnnouncementMapper;
import com.example.superchen.mapper.ImagesUrlMapper;
import com.example.superchen.service.AnnouncementService;
import com.example.superchen.service.ImagesUrlService;
import org.springframework.stereotype.Service;

@Service
public class AnnouncementServiceImpl  extends ServiceImpl<AnnouncementMapper, Announcement> implements AnnouncementService {
}
