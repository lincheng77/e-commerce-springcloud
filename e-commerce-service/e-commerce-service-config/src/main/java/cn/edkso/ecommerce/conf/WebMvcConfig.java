package cn.edkso.ecommerce.conf;

import cn.edkso.ecommerce.interceptor.LoginUserInfoInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author dingmengdi
 * @version 1.0
 * @date 2023-04-18 13:09
 * @description Web Mvc 配置
 */

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {

        //添加用户身份统一登录拦截的拦截器
        registry.addInterceptor(new LoginUserInfoInterceptor())
                .addPathPatterns("/**")
                .order(0);
    }
}
