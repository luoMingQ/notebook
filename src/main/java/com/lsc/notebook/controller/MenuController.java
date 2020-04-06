package com.lsc.notebook.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsc.notebook.entity.Menu;
import com.lsc.notebook.service.MenuService;
import com.lsc.notebook.util.ControllerUtil;
import com.lsc.notebook.util.Result;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;
import javax.annotation.Resource;
import io.swagger.annotations.Api;
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
public class MenuController extends BaseController{
    @Resource
    private MenuService menuService;


    /**
     * 新增菜单
     * return
     * Author luosc
     * param
     * Date 2020/3/30 14:38
     */
    @ApiOperation(value = "添加菜单", notes = "菜单新增", httpMethod = "POST")
    @RequestMapping(value = "/menu-add",method = RequestMethod.POST)
    @ResponseBody
    public Result menuAdd(@RequestBody @ApiParam(name = "菜单", value = "菜单") Menu menu) {
        return ControllerUtil.add( menuService,menu, logger);
    }

    /**
     * 删除
     * return
     * Author luosc
     * param
     * Date 2020/3/30 18:09
     */
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "GET")
    @RequestMapping(value = "/menu-delete/{id}")
    @ResponseBody
    public Result menuDelete(@PathVariable long id) {
        return ControllerUtil.deleteById(menuService, id, logger);
    }

    /**
     * 修改
     * return
     * Author luosc
     * param
     * Date 2020/3/30 18:09
     */
    @ApiOperation(value = "修改", notes = "修改", httpMethod = "POST")
    @RequestMapping(value = "/menu-modify",method = RequestMethod.POST)
    @ResponseBody
    public Result menuModify(@RequestBody @ApiParam Menu menu) {
        return ControllerUtil.modify(menuService, menu, logger);
    }

    @ApiOperation(value = "查询详情", notes="根据ID查询数据",httpMethod = "GET")
    @RequestMapping(value = "get-menu/{menuId}")
    @ResponseBody
    public Result getMenu(@PathVariable long menuId){
        return ControllerUtil.findById(menuService, menuId, logger);
    }

    @ApiOperation(value = "查询列表", notes="根据ID查询数据",httpMethod = "POST")
    @RequestMapping(value = "get-menus",method = RequestMethod.POST)
    @ResponseBody
    public Result list(@RequestBody @ApiParam(name="用户对象",value="用户对象")Menu menu) {
        QueryWrapper<Menu> ew = new QueryWrapper<>();
        //ew.like("name" , "318");
        //ew.eq("数据库字段名", menu.getMenuName());
        //ew.eq("menu_name", menu.getMenuName());
        if (menu != null) {
            ew.like("menu_name", menu.getMenuName());
        }
        return ControllerUtil.listAll(menuService, ew, logger);
    }

    /**
     * 根据传入的自增的ID 分页，倒序显示，查询ID小于传入参数，的10条数据，查询下一页时，传入上一页的最后一条数据ID.
     *  传入页数 "searchCount": true, "current":1, "size": 10,
     *
     *  也可以传入查询指定页数的数据 "current":1, "size": 10,
     * size 默认10，
     * return
     * Author luosc
     * param
     * Date 2020/4/2 15:32
     */
    @ApiOperation(value = "查询列表分页", notes="根据ID查询数据",httpMethod = "POST")
    @RequestMapping(value = "/menus-page",method = RequestMethod.POST)
    @ResponseBody
    public Result listPage(@RequestBody @ApiParam(name="菜单分页对象",value="菜单分页对象")Page<Menu> page) {
        Menu param =page.getRecords().get(0);
        QueryWrapper<Menu> ew = new QueryWrapper<>();
        //ew.like("name" , "318");
        //ew.eq("数据库字段名", menu.getMenuName());
        //ew.eq("menu_name", menu.getMenuName());
        if (param != null) {
            if ( param.getMenuId()!=null&&param.getMenuId() > 0) {
                ew.lt(true, "menu_id", param.getMenuId());//查询ID小于多少
            }
            page.getRecords().clear();
            ew.like("menu_name", param.getMenuName());
        }
        return ControllerUtil.pageList(menuService,page, ew, logger);

    }
}

