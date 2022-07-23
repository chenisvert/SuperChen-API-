package com.example.superchen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.superchen.domain.dom.Url;

public interface UrlService extends IService<Url> {
    int countRow();
}
