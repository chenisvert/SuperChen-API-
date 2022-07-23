package com.example.superchen.service;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.superchen.domain.dom.User;

import java.util.List;

public interface UserService extends IService<User> {

    public Boolean Login(User admin);

    public List<User> selectByUsername(String username);

    public int countRow();

    void updateTime(User user);
}
