package cn.edkso.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author dingmengdi
 * @version 1.0
 * @date 2023-04-23 11:54
 * @description 商品微服务启动入口
 *
 *  * 启动依赖组件/中间件: Redis + MySQL + Nacos + Kafka + Zipkin
 *  * http://127.0.0.1:8001/ecommerce-goods-service/doc.html
 */

@EnableJpaAuditing
@EnableDiscoveryClient
@SpringBootApplication
public class GoodsApplication {

    public static void main(String[] args) {

        SpringApplication.run(GoodsApplication.class, args);
    }
}