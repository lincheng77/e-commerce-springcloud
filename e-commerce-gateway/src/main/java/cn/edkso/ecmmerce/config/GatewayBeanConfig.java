package cn.edkso.ecmmerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author dingmengdi
 * @version 1.0
 * @date 2023-04-17 23:57
 * @description
 */

@Configuration
public class GatewayBeanConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
