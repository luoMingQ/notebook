package com.lsc.notebook.service;

import com.lsc.notebook.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lsc.notebook.vo.UserRoleVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author luosc
 * @since 2020-04-06
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * 查询userroleList
     * return
     * Author luosc
     * param
     * Date 2020/4/6 16:41
     */
    public List<UserRoleVo> getUserRoleVoList(UserRoleVo userRoleVo);
}
