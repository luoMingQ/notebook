package com.lsc.notebook.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lsc.notebook.entity.Menu;

import org.slf4j.Logger;

import java.util.List;

/**
 * controller 增删改查工具类
 *
 * @Author: luosc
 * @Description:
 * @Date:created in 12:37 2020/4/1
 */
public class ControllerUtil {

    /**
     * 新增
     * return
     * Author luosc
     * param
     * Date 2020/4/1 12:48
     */
    public static Result add(IService service, Object o, Logger logger) {
        try {
            service.save(o);
            return Result.success();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    /**
     * 根据ID删除
     * return
     * Author luosc
     * param
     * Date 2020/4/1 12:50
     */
    public static Result deleteById(IService service, long id, Logger logger) {
        try {
            service.removeById(id);
            return Result.success();
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
     * Date 2020/4/1 14:41
     */
    public static Result modify(IService service, Object o, Logger logger) {
            try {
                if (service.updateById(o)){
                    return Result.success();
                }else{
                    return Result.error("修改失败");
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                return Result.error(e.getMessage());
            }
    }

    /**
     * 根据id查询数据
     * return
     * Author luosc
     * param
     * Date 2020/4/1 15:16
     */
    public static Result findById(IService service, long id, Logger logger) {
        Object resultObj = null;
        try {
            resultObj = service.getById(id);
            return Result.success(resultObj);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }


    /**
     *
     * return
     * Author luosc
     * param
     * Date 2020/4/1 15:26
     */
    public static Result listAll(IService service, QueryWrapper queryWrapper, Logger logger) {
        try {
            List<Menu> list= service.list(queryWrapper);
            return Result.success(list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

}
