package com.example.superchen.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.superchen.domain.dom.ImagesUrl;

public interface ImagesUrlService  extends IService<ImagesUrl> {
     int countRow();
}
