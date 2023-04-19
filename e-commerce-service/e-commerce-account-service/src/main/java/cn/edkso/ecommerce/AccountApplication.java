package cn.edkso.ecommerce;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author dingmengdi
 * @version 1.0
 * @date 2023-04-19 20:22
 * @description 用户账户微服务启动入口
 *  127.0.0.1:8003/ecommerce-account-service/swagger-ui.html
 *  127.0.0.1:8003/ecommerce-account-service/doc.html
 */

@EnableJpaAuditing
@SpringBootApplication
@EnableDiscoveryClient
public class AccountApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }
}