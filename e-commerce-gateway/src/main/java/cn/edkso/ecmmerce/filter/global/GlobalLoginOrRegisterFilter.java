package cn.edkso.ecmmerce.filter.global;

import cn.edkso.ecmmerce.constant.GatewayConstant;
import cn.edkso.ecommerce.constant.CommonConstant;
import cn.edkso.ecommerce.util.TokenParseUtil;
import cn.edkso.ecommerce.vo.JwtToken;
import cn.edkso.ecommerce.vo.LoginUserInfo;
import cn.edkso.ecommerce.vo.UsernameAndPassword;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * @author dingmengdi
 * @version 1.0
 * @date 2023-04-17 23:54
 * @description
 */
@Slf4j
@Component
public class GlobalLoginOrRegisterFilter implements GlobalFilter, Ordered {

    /**
     * 注册中心客户端, 可以从注册中心中获取服务实例信息
     */
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 登录、注册、鉴权
     * - 1. 如果是登录或注册, 则去授权中心拿到 Token 并返回给客户端
     * - 2. 如果是访问其他的服务, 则鉴权, 没有权限返回 401
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 1. 如果是登录
        if (request.getURI().getPath().contains(GatewayConstant.LOGIN_URI)) {
            String authorityCenterRequestUrlFormat = GatewayConstant.AUTHORITY_CENTER_TOKEN_URL_FORMAT;
            return getToken(request, response, authorityCenterRequestUrlFormat);
        }

        // 2. 如果是注册
        if (request.getURI().getPath().contains(GatewayConstant.REGISTER_URI)) {
            String authorityCenterRequestUrlFormat = GatewayConstant.AUTHORITY_CENTER_REGISTER_URL_FORMAT;
            return getToken(request, response, authorityCenterRequestUrlFormat);
        }

        //3. 其他服务
        return otherRequest(exchange, chain, request, response);
    }

    private static Mono<Void> otherRequest(ServerWebExchange exchange, GatewayFilterChain chain, ServerHttpRequest request, ServerHttpResponse response) {
        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst(CommonConstant.JWT_USER_INFO_KEY);
        LoginUserInfo loginUserInfo = null;

        try{
            loginUserInfo = TokenParseUtil.parseUserInfoFromToken(token);
        } catch (Exception e) {
            log.error("parse user info from token error:[{}]", e.getMessage(), e);
        }

        // 获取不到用户信息，返回401
        if (loginUserInfo == null) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        //解析通过则放行
        return chain.filter(exchange);
    }

    private Mono<Void> getToken(ServerHttpRequest request, ServerHttpResponse response, String authorityCenterRequestUrlFormat) {
        //去授权中心拿token
        String token = getTokenFromAuthorityCenter(request, authorityCenterRequestUrlFormat);

        // header 中不能设置null
        if (token == null) {
            token = "null";
        }
        response.getHeaders().add(CommonConstant.JWT_USER_INFO_KEY, token);
        response.setStatusCode(HttpStatus.OK);

        return response.setComplete();
    }

    private String getTokenFromAuthorityCenter(ServerHttpRequest request, String uriFormat) {

        //service id就是服务名字， 通过负载均衡的方式拿到服务实例
        ServiceInstance serviceInstance = loadBalancerClient.choose(CommonConstant.AUTHORITY_CENTER_SERVICE_ID);
        log.info("Nacos Client Info: ServiceId = [{}], InstanceId = [{}], Metadata = [{}]",
                serviceInstance.getServiceId(),
                serviceInstance.getInstanceId(),
                JSON.toJSONString(serviceInstance.getMetadata()));

        String requestUrl = String.format(uriFormat, serviceInstance.getHost(), serviceInstance.getPort());
        String bodyStr = parseBodyFromRequest(request);
        UsernameAndPassword usernameAndPassword = JSON.parseObject(bodyStr, UsernameAndPassword.class);
        log.info("login request url and body: [{}], [{}]", requestUrl, bodyStr);
        log.info("login request url and body: [{}], [{}]", requestUrl, JSON.toJSONString(bodyStr));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(JSON.toJSONString(bodyStr), headers);
        JwtToken token = restTemplate.postForObject(requestUrl, httpEntity, JwtToken.class);

        if (token != null) {
            return token.getToken();
        }

        return null;
    }


    private String parseBodyFromRequest(ServerHttpRequest request) {
        //获取请求体
        Flux<DataBuffer> body = request.getBody();
        AtomicReference<String> bodyReference = new AtomicReference<>();

        //订阅缓冲区消费请求体中的数据
        body.subscribe(new Consumer<DataBuffer>() {
            @Override
            public void accept(DataBuffer dataBuffer) {
                CharBuffer charBuffer = StandardCharsets.UTF_8.decode(dataBuffer.asByteBuffer());
                bodyReference.set(charBuffer.toString());

                //最后一定要使用DataBufferUtils.release 释放掉，否则会出现内存泄露
                DataBufferUtils.release(dataBuffer);
            }
        });

        // 返回request body  【body.subscribe是 另外一个线程异步，会导致这里提前用到没有设置好的body，所以使用原子操作】
        return bodyReference.get();
    }


    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE + 2;
    }
}
