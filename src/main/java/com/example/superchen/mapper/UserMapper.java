package com.example.superchen.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.example.superchen.domain.dom.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper extends BaseMapper<User> {

}
