package com.lsc.notebook.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lsc.notebook.entity.Menu;

import org.slf4j.Logger;

import java.lang.reflect.Field;
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
            if (service.updateById(o)) {
                return Result.success();
            } else {
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


//    /**
//     * return
//     * Author luosc
//     * param
//     * Date 2020/4/1 15:26
//     */
//    public static Result listAll(IService service, QueryWrapper queryWrapper, Logger logger) {
//        try {
//            List list = service.list(queryWrapper);
//            return Result.success(list);
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            return Result.error(e.getMessage());
//        }
//    }

    /**
     * 查询条件自动封装
     * return
     * Author luosc
     * param
     * Date 2020/4/19 1:24
     */
    public static Result listAll(IService service, Object object,QueryWrapper ew, Logger logger) {
        try {
            getQueryWapper(object, ew);
            List list = service.list(ew);
            return Result.success(list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.error(e.getMessage());
        }


    }

    /**
     * return
     * Author luosc
     * param
     * Date 2020/4/1 15:26
     */
    public static Result pageList(IService service, IPage page, QueryWrapper queryWrapper, Logger logger) {
        try {
            Object object = page.getRecords().get(0);//获取参数
            getQueryWapper(object, queryWrapper);
            page = service.page(page, queryWrapper);
            return Result.success(page);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    private static void getQueryWapper(Object object, QueryWrapper ew) throws Exception {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);//允许访问私有属性
            //field.isAnnotationPresent()
            Object value = field.get(object);
            if (value != null && value != "") {
                String name = field.getName();
                String column = ChangeCharUtil.camelToUnderline(name, 0);
                if (field.isAnnotationPresent(Like.class)) {
                    ew.like(column, value.toString());
                } else if (field.isAnnotationPresent(Gt.class)) {
                    ew.gt(column, value.toString());
                } else if (field.isAnnotationPresent(Lt.class)) {
                    ew.lt(column, value.toString());
                } else {
                    //默认执行
                    ew.eq(column, value);
                }
            }
        }
    }

}
