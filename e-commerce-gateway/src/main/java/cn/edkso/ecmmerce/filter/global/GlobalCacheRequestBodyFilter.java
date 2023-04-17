package cn.edkso.ecmmerce.filter.global;

import cn.edkso.ecmmerce.config.GatewayConfig;
import cn.edkso.ecmmerce.constant.GatewayConstant;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author dingmengdi
 * @version 1.0
 * @date 2023-04-17 17:06
 * @description 缓存请求 body 的全局过滤器
 */

@Slf4j
@Component
@SuppressWarnings("all")
public class GlobalCacheRequestBodyFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        boolean isLoginOrRegister = path.contains(GatewayConstant.LOGIN_URI) || path.contains(GatewayConstant.REGISTER_URI);
        MediaType contentType = request.getHeaders().getContentType();
        if (contentType == null || !isLoginOrRegister) {
            return chain.filter(exchange);
        }


        Mono<DataBuffer> dataBufferMono = DataBufferUtils.join(request.getBody());
        Mono mono = dataBufferMono.flatMap(new Function<DataBuffer, Mono<Publisher>>() {
            @Override
            public Mono apply(DataBuffer dataBuffer) {
                //确保数据缓冲区不被释放，必须要 DataBufferUtils.retain
                DataBufferUtils.retain(dataBuffer);

                //defer、just都是去创建数据源，得到当前数据的副本
                DataBuffer newDataBuffer = dataBuffer.slice(0, dataBuffer.readableByteCount());
                Flux<DataBuffer> dataBufferFlux = Flux.just(newDataBuffer);
                Flux<DataBuffer> dataBufferFluxDefer = Flux.defer(new Supplier() {
                    @Override
                    public Publisher get() {
                        return dataBufferFlux;
                    }
                });

                //重新包装ServerHttpRequest，重写getBody方法，能够返回请求数据
                ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(request) {
                    @Override
                    public Flux<DataBuffer> getBody() {
                        return dataBufferFluxDefer;
                    }
                };

                //将包装之后的 ServerHttpRequest向下继续传递
                ServerWebExchange newExchange = exchange.mutate().request(mutatedRequest).build();
                return chain.filter(newExchange);
            }
        });

        return mono;
    }


    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE + 1;
    }
}
