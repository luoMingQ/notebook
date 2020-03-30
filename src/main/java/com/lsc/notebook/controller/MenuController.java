package com.lsc.notebook.controller;


import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.lsc.notebook.entity.Menu;
import com.lsc.notebook.service.MenuService;
import com.lsc.notebook.util.Result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.sql.Wrapper;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author luosc
 * @since 2020-03-29
 */
@Api(description = "菜单管理")
@Controller
@RequestMapping("/menu")
public class MenuController {
    private Logger logger = LoggerFactory.getLogger(MenuController.class);
    @Resource
    private MenuService menuService;
    TemplateConfig templateConfig = new TemplateConfig()
            .setEntity("Menu.java");

    /**
     * 新增菜单
     * return
     * Author luosc
     * param
     * Date 2020/3/30 14:38
     */
    @ApiOperation(value = "添加菜单", notes = "菜单新增", httpMethod = "POST")
    @RequestMapping("/menu-add")
    public Result menuAdd(@RequestBody @ApiParam(name = "菜单", value = "菜单") Menu menu) {
        try {
            menuService.save(menu);
            return Result.success();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除
     * return
     * Author luosc
     * param
     * Date 2020/3/30 18:09
     */
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "GET")
    @RequestMapping("/menu-delete/{id}")
    public Result menuDelete(@PathVariable long id) {
        try {
            if (menuService.removeById(id)) {
                return Result.success();
            } else {
                return Result.error("删除失败");
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    /**
     * 修改
     * return
     * Author luosc
     * param
     * Date 2020/3/30 18:09
     */
    @ApiOperation(value = "修改", notes = "修改", httpMethod = "GET")
    @RequestMapping("/menu-modify")
    public Result menuDelete(@RequestBody @ApiParam Menu menu) {
        try {
            if (menuService.updateById(menu)){
                return Result.success();
            }else{
                return Result.error("删除失败");
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @ApiOperation(value = "查询详情", notes="根据ID查询数据",httpMethod = "GET")
    @RequestMapping("getMenu/{menuId}")
    @ResponseBody
    public Result GetMenu(@PathVariable long menuId){
        Menu menu = null;
        try {
            menu = menuService.getById(menuId);
            if (menu != null) {
                menu.setMenuId(menuId);
            }
            return Result.success(menu);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.error(e.getMessage());
        }


    }
}

