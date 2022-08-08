package com.example.superchen.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.superchen.domain.dom.IpAddress;
import com.example.superchen.domain.dom.Vipvideo;
import com.example.superchen.mapper.IpAddressMapper;
import com.example.superchen.mapper.VipvideoMapper;
import com.example.superchen.service.IpAddressService;
import com.example.superchen.service.VipvideoService;
import org.springframework.stereotype.Service;

@Service
public class IpAddressServiceImpl  extends ServiceImpl<IpAddressMapper, IpAddress> implements IpAddressService {

}
