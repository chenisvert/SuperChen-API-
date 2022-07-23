package com.example.superchen.service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.superchen.domain.dom.User;
import com.example.superchen.mapper.UserMapper;
import com.example.superchen.service.UserService;
import com.example.superchen.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    private HashMap<String, Object> map = new HashMap<>();


    @Override
    public Boolean Login(User admin) {
        String password = admin.getPassword();
        String md5 = MD5Util.getMD5(password);
//        log.info("password:"+md5);
        map.put("username",admin.getUsername());
        map.put("password",md5);
        List<User> admins = userMapper.selectByMap(map);
        if (admins.isEmpty()){
            return false;
        }
        return true;
    }

    @Override
    public List<User> selectByUsername(String username) {
        map.put("username",username);
        return userMapper.selectByMap(map);
    }

    @Override
    public int countRow() {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        Integer count = userMapper.selectCount(queryWrapper);
        return count;
    }

    @Override
    public void updateTime(User user) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("username",user.getUsername()).set("create_time", LocalDateTime.now());
        userMapper.update(null,updateWrapper);
    }


}
