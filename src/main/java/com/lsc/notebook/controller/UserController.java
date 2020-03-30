package com.lsc.notebook.controller;

import com.lsc.notebook.entity.User;
import com.lsc.notebook.service.UserService;
import com.lsc.notebook.util.Result;
import com.lsc.notebook.util.StringUtil;
import com.lsc.notebook.util.UuidUtil;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @Author: luosc
 * @Description:
 * @Date:created in 15:04 2020/3/29
 */
@Api(description = "账号管理")
@RestController
@RequestMapping("/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Resource
    private UserService userService;

    /**
     * 查询
     * return
     * Author luosc
     * param
     * Date 2020/3/29 16:23
     */
    @ApiOperation(value = "查询详情", notes="根据ID查询数据",httpMethod = "GET")

    @RequestMapping("getUser/{userId}")
    @ResponseBody
    public Result GetUser(@PathVariable long userId){
        User user = null;
        try {
            user = userService.getById(userId);
            if (user != null) {
                user.setUserId(userId);
            }
            return Result.success(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.error(e.getMessage());
        }


    }

    @ApiOperation(value = "注册", notes="注册",httpMethod = "POST")
    @RequestMapping("/sign-in")
    public Result signIn(@RequestBody @ApiParam(name="用户对象",value="用户对象")User user) {
        try {
            String password = user.getPassword();
            String salt = UuidUtil.getUuid();//加盐
            password = StringUtil.StringInMd5(password + salt);//加密
            user.setSalt(salt);
            user.setPassword(password);
            userService.signIn(user);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    /**
     * 登录
     * return
     * Author luosc
     * param 
     * Date 2020/3/30 12:26
     */
    @ApiOperation(value = "登录", notes="登录",httpMethod = "POST")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 执行认证登陆
        try {
            subject.login(token);
        } catch (UnknownAccountException uae) {
            return "未知账户";
        } catch (IncorrectCredentialsException ice) {
            return "密码不正确";
        } catch (LockedAccountException lae) {
            return "账户已锁定";
        } catch (ExcessiveAttemptsException eae) {
            return "用户名或密码错误次数过多";
        } catch (AuthenticationException ae) {
            return "用户名或密码不正确！";
        }
        if (subject.isAuthenticated()) {
            return "登录成功";
        } else {
            token.clear();
            return "登录失败";
        }
    }
}
