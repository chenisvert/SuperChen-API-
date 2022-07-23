package com.example.superchen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.superchen.domain.dom.Url;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApiMapper extends BaseMapper<Url> {
}
