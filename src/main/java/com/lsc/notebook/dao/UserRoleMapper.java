package com.lsc.notebook.dao;

import com.lsc.notebook.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsc.notebook.vo.UserRoleVo;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author luosc
 * @since 2020-04-06
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    List<UserRoleVo> getUserRoleVoList(UserRoleVo userRoleVo);

}
