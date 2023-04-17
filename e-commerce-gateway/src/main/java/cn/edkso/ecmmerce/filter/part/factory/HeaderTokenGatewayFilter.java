package cn.edkso.ecmmerce.filter.part.factory;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author dingmengdi
 * @version 1.0
 * @date 2023-04-17 16:47
 * @description HTTP 请求头部携带 Token 验证过滤器
 */
public class HeaderTokenGatewayFilter implements GatewayFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 从HTTP Header中寻找key为token， value为 edkso的键值对
        String name= exchange.getRequest().getHeaders().getFirst("token");
        if("edkso".equals(name)){
            return chain.filter(exchange);
        }

        //标记次吃请求没有权限，并结束这次请求
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE + 2;
    }
}
