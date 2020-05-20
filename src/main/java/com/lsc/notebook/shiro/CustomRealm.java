package com.lsc.notebook.shiro;

import com.lsc.notebook.entity.User;
import com.lsc.notebook.service.UserService;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

/**
 * @Author: luosc
 * @Description:
 * @Date:created in 19:25 2020/3/29
 */
public class CustomRealm extends AuthorizingRealm {
    @Resource
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //String username = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> stringSet = new HashSet<>();
        stringSet.add("user:show");
        stringSet.add("user:admin");
        info.setStringPermissions(stringSet);
        return info;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //System.out.println("-------身份认证方法--------");
        String userName = (String) authenticationToken.getPrincipal();
        String userPwd = new String((char[]) authenticationToken.getCredentials());

        if (userName == null) {
            throw new AccountException("用户名不正确");
        } else {
            User user = userService.login(userName);
            String dbpassword = user.getPassword();
            if (!userPwd.equals(dbpassword)) {
                throw new AccountException("密码不正确");
            }
        }
        return new SimpleAuthenticationInfo(userName, userPwd,getName());
    }
}
