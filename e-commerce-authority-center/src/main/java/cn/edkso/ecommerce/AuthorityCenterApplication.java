package cn.edkso.ecommerce;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * <h1>授权中心启动入口</h1>
 * */
@EnableJpaAuditing  // 允许 Jpa 自动审计 (在做增删改查的时候让jpa帮助我们做时间等的自动填充)
@EnableDiscoveryClient
@SpringBootApplication
public class AuthorityCenterApplication {

    public static void main(String[] args) {

        SpringApplication.run(AuthorityCenterApplication.class, args);
    }
}
