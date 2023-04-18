package cn.edkso.ecmmerce.config;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.BooleanSpec;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

/**
 * @author dingmengdi
 * @version 1.0
 * @date 2023-04-18 10:06
 * @description 登陆请求转发规则
 */

@Configuration
public class RouteLocatorConfig {

    /**
     * 使用代码定义路由规则，在网关层面拦截下登录和注册接口
     *
     * @param builder
     * @return
     */
    @Bean
    public RouteLocator loginRouteLocator(RouteLocatorBuilder builder) {

        // 手动定义Gateway 路由规则需要指定 id， path 和uri
        RouteLocatorBuilder.Builder routes = builder.routes();

        RouteLocator routeLocator = routes.route("e_commerce_authority", new Function<PredicateSpec, Route.AsyncBuilder>() {
            @Override
            public Route.AsyncBuilder apply(PredicateSpec predicateSpec) {
                BooleanSpec path = predicateSpec.path(
                        "/e-commerce/login",
                        "/e-commerce/register");
                Route.AsyncBuilder routeBuilder = path.uri("http://localhost:9001/");
                return routeBuilder;
            }
        }).build();
        return routeLocator;
    }

}
