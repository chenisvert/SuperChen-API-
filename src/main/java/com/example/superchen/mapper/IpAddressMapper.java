package com.example.superchen.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.superchen.domain.dom.IpAddress;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IpAddressMapper extends BaseMapper<IpAddress> {
}
