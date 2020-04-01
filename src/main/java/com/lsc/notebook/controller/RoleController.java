package com.lsc.notebook.controller;


import com.lsc.notebook.entity.Role;
import com.lsc.notebook.service.RoleService;
import com.lsc.notebook.util.ControllerUtil;
import com.lsc.notebook.util.Result;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author luosc
 * @since 2020-04-01
 */
@Controller
@RequestMapping("/role")
public class RoleController  extends BaseController{
    @Resource
    private RoleService roleService;
    /**
     * 新增角色
     * return
     * Author luosc
     * param
     * Date 2020/3/30 14:38
     */
    @ApiOperation(value = "添加角色", notes = "角色新增", httpMethod = "POST")
    @RequestMapping(value = "/role-add",method = RequestMethod.POST)
    @ResponseBody
    public Result roleAdd(@RequestBody @ApiParam(name = "角色", value = "角色") Role role) {
        return ControllerUtil.add(roleService, role, logger);

    }

    @ApiOperation(value = "查询详情", notes="根据ID查询数据",httpMethod = "GET")
    @RequestMapping(value = "get-role/{roleId}")
    @ResponseBody
    public Result getMenu(HttpServletRequest request, @PathVariable long roleId){
        return ControllerUtil.findById(roleService, roleId, logger);
    }
}

