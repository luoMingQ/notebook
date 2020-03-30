package com.lsc.notebook.shiro;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 启用拦截器 LoginHandlerInterceptor
 * @Author: luosc
 * @Description:
 * @Date:created in 23:38 2020/3/30
 */
//@EnableWebMvc
//@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    LoginHandlerInterceptor loginHandlerInterceptor() {
        return new LoginHandlerInterceptor();
    }

    /**
     * 配置拦截器
     * @author yuqingquan
     * @param registry
     */
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginHandlerInterceptor());
    }

}
