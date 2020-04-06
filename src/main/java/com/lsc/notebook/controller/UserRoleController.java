package com.lsc.notebook.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lsc.notebook.entity.UserRole;
import com.lsc.notebook.service.UserRoleService;
import com.lsc.notebook.util.ControllerUtil;
import com.lsc.notebook.util.Result;
import com.lsc.notebook.util.StringUtil;
import com.lsc.notebook.vo.UserRoleVo;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.misc.Request;

import java.util.List;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author luosc
 * @since 2020-04-06
 */
@Api(description = "用户角色")
@Controller
@RequestMapping("/user-role")
public class UserRoleController  extends BaseController{
    @Resource
    private UserRoleService userRoleService;

    /**
     * 新增用户角色，
     * return
     * Author luosc
     * param
     * Date 2020/4/6 15:40
     */
    @ApiOperation(value = "新增", notes="新增",httpMethod = "POST")
    @RequestMapping(value = "add-user-role",method = RequestMethod.POST)
    @ResponseBody
    public Result addUserRole(@RequestBody @ApiParam(name = "用户角色", value = "用户角色")UserRole userRole) {
       //一个user只能添加一个userRole，先根据userId去userRole中查询，如果存在则修改,如果不存在，则新增
        QueryWrapper<UserRole> ew = new QueryWrapper<>();
        if (userRole != null) {
            ew.eq("user_id", userRole.getUserId());
        }
        List<UserRole> list = userRoleService.list(ew);
        if (list != null) {
            userRole.setId(list.get(0).getId());
            return ControllerUtil.modify(userRoleService, userRole, logger);
        } else {
            return ControllerUtil.add(userRoleService, userRole, logger);
        }

    }


    /**
     * 修改用户角色
     * return
     * Author luosc
     * param
     * Date 2020/4/6 15:43
     */
    @ApiOperation(value = "修改",notes = "修改",httpMethod = "POST")
    @RequestMapping(value = "modify-user-role", method = RequestMethod.POST)
    @ResponseBody
    public Result modifyUserRole(@RequestBody @ApiParam(name = "用户角色", value = "用户角色") UserRole userRole) {
        return ControllerUtil.modify(userRoleService, userRole, logger);
    }

    /**
     * 删除用户角色
     * return
     * Author luosc
     * param
     * Date 2020/4/6 15:43
     */
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "GET")
    @RequestMapping(value = "delete-user-role/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result getMenu(@PathVariable long id){
        return ControllerUtil.deleteById(userRoleService, id, logger);
    }

    /**
     * 管理员查询用户角色列表，展示所用人的数据
     * return
     * Author luosc
     * param
     * Date 2020/4/6 16:31
     */
    @ApiOperation(value = "查看用户角色列表")
    @RequestMapping(value = "user-role-list", method = RequestMethod.POST)
    @ResponseBody
    public Result getUserRoleList(@RequestBody @ApiParam UserRoleVo userRoleVo) {
        List<UserRoleVo> resultList = userRoleService.getUserRoleVoList(userRoleVo);
        return Result.success(resultList);
        //return ControllerUtil.pageList(userRoleService,page, ew, logger);
    }
}

