package com.lsc.notebook.shiro;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 *
 * @Author: luosc
 * @Description:
 * @Date:created in 23:03 2020/3/30
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String tokenstr = request.getHeader("tokenServerKey");
        String sessionToken = (String) request.getSession().getAttribute("tokenServerKey");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "86400");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Content-Type", "text/html;charset=utf-8");
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            return false;
        }

        if (StringUtils.isEmpty(tokenstr)) {
            return true;//没有token
        }else {
            if (tokenstr.equals(sessionToken)) {
                return true;
            }
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Object user = request.getSession().getAttribute("userName");
        System.out.println("postHandle----" + user + " ::: " + request.getRequestURL());

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Object user = request.getSession().getAttribute("userName");
        //System.out.println("afterCompletion----" + user + " ::: " + request.getRequestURL());

    }
}
