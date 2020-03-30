package com.lsc.notebook.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsc.notebook.entity.User;

import org.apache.ibatis.annotations.Mapper;


/**
 * @Author: luosc
 * @Description:
 * @Date:created in 15:05 2020/3/29
 */
@Mapper
public interface UserMapper  extends BaseMapper<User> {
    User login(String username);

    void signIn(User user);


}
