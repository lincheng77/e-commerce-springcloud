package cn.edkso.ecmmerce.config;

import org.springframework.beans.factory.annotation.Value;
/**
 * @author dingmengdi
 * @version 1.0
 * @date 2023-04-16 18:44
 * @description 配置类, 读取 Nacos 相关的配置项, 用于配置监听器
 */
public class GatewayConfig {

    //读取配置的超时时间
    public static final long DEFAULT_TIMEOUT = 30000;

    //Nacos 服务器地址
    @Value("${spring.cloud.nacos.discovery.server-addr}")
    public static String NACOS_SERVER_ADDR;

    //命名空间
    @Value("${spring.cloud.nacos.discovery.namespace}")
    public static String NACOS_NAMESPACE;

    //data-id
    @Value("${nacos.gateway.route.config.data-id}")
    public static String NACOS_ROUTE_DATA_ID;

    //分组 id
    @Value("${nacos.gateway.route.config.group}")
    public static String NACOS_ROUTE_GROUP;
}
