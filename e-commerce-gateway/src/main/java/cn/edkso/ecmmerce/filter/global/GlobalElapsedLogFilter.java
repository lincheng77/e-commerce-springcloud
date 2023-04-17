package cn.edkso.ecmmerce.filter.global;

import cn.hutool.core.date.StopWatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author dingmengdi
 * @version 1.0
 * @date 2023-04-17 16:55
 * @description 全局接口耗时日志过滤器
 */


@Slf4j
@Component
public class GlobalElapsedLogFilter  implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //前置逻辑
        StopWatch sw = new StopWatch();
        sw.start();
        String uri = exchange.getRequest().getURI().getPath();


        //后置逻辑
        Mono<Void> mono = Mono.fromRunnable(() -> {
            log.info("[{}] elapsed: [{}ms]", uri, sw.getTotalTimeMillis());
        });

        return chain.filter(exchange).then(mono);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
