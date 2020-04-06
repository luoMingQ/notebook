package com.lsc.notebook.service.impl;

import com.lsc.notebook.entity.UserRole;
import com.lsc.notebook.dao.UserRoleMapper;
import com.lsc.notebook.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsc.notebook.vo.UserRoleVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author luosc
 * @since 2020-04-06
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Resource
    UserRoleMapper userRoleMapper;
    @Override
    public List<UserRoleVo> getUserRoleVoList(UserRoleVo userRoleVo) {
        return userRoleMapper.getUserRoleVoList(userRoleVo);
    }
}
