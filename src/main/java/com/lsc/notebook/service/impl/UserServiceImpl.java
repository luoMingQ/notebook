package com.lsc.notebook.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsc.notebook.dao.UserMapper;
import com.lsc.notebook.entity.User;
import com.lsc.notebook.service.UserService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: luosc
 * @Description:
 * @Date:created in 15:05 2020/3/29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public void signIn(User user) throws Exception{
        userMapper.signIn(user);
    }

    @Override
    public User login(String username) {
        User user = userMapper.login(username);
        return user;
    }
}
