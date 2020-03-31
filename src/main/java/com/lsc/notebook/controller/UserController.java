package com.lsc.notebook.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lsc.notebook.entity.User;
import com.lsc.notebook.service.UserService;
import com.lsc.notebook.util.Result;
import com.lsc.notebook.util.StringUtil;
import com.lsc.notebook.util.TokenTools;
import com.lsc.notebook.util.UuidUtil;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    @ApiOperation(value = "查询详情列表", notes="查询数据",httpMethod = "POST")
    @RequestMapping(value = "/getUsers",method = RequestMethod.POST)
    @ResponseBody
    public Result testMybatisPlus(@RequestBody @ApiParam(name="用户对象",value="用户对象")User user) {
        QueryWrapper<User> ew = new QueryWrapper<>();
        //ew.like("name" , "318");
        ew.eq("name", user.getName());
        List<User> users = userService.list(ew);
        return Result.success(users);
    }

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
    public Result getUser(@PathVariable long userId){
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
    @ApiOperation(value = "登录", notes="登录",httpMethod = "GET")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public Result login(@RequestParam("username") String username, @RequestParam("password") String password,HttpServletRequest request) {
        User user = userService.login(username);
        if (user != null) {
            password = StringUtil.StringInMd5(password + user.getSalt());
        } else {
            return Result.error("未知账户");
        }
        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();

        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 执行认证登陆
        try {
            subject.login(token);
        } catch (UnknownAccountException uae) {
            return Result.error("未知账户");
        } catch (IncorrectCredentialsException ice) {
            return Result.error("密码不正确");
        } catch (LockedAccountException lae) {
            return Result.error("账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            return Result.error("用户名或密码错误次数过多");
        } catch (AuthenticationException ae) {
            return Result.error("用户名或密码不正确！");
        }
        if (subject.isAuthenticated()) {
            HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
            HttpSession session = httpServletRequest.getSession();
            //把用户信息保存到session
            session.setAttribute("user", user);
            String tokenServerKey = TokenTools.createToken(httpServletRequest,"tokenServerKey");
            Map<String, String> data = new HashMap<>();
            data.put("tokenServerKey", tokenServerKey);

            System.out.println(session.getAttribute("tokenServerKey"));
            return Result.success(data);
        } else {
            token.clear();
            return Result.error("登录失败");
        }
    }
}
