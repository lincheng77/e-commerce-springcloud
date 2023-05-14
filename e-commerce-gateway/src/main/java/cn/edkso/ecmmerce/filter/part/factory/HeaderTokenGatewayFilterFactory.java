package cn.edkso.ecmmerce.filter.part.factory;

import cn.edkso.ecmmerce.filter.part.HeaderTokenGatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * @author dingmengdi
 * @version 1.0
 * @date 2023-04-17 16:51
 * @description
 */

@Component
public class HeaderTokenGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
    @Override
    public GatewayFilter apply(Object config) {
        return new HeaderTokenGatewayFilter();
    }
}
