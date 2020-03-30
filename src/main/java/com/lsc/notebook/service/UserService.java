package com.lsc.notebook.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsc.notebook.entity.Menu;
import com.lsc.notebook.entity.User;

/**
 * @Author: luosc
 * @Description:
 * @Date:created in 15:05 2020/3/29
 */
public interface UserService  extends IService<User> {

    void signIn(User user) throws Exception;

    User login(String username);


}
