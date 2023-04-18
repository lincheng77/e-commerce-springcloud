package cn.edkso.ecommerce.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author dingmengdi
 * @version 1.0
 * @date 2023-04-18 20:35
 * @description
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * Swagger实例Bean是 Docket， 所以通过配置Docket实例来配置 Swagger
     * @return
     */
    @Bean
    public Docket Docket(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.apiInfo(apiInfo());
        docket.select()
                .apis(RequestHandlerSelectors.basePackage("cn.edkso.ecommerce"))
                .paths(PathSelectors.any())
                .build();

        return docket;
    }


    /**
     * Swagger 的描述信息
     * @return
     */
    public ApiInfo apiInfo() {
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
        apiInfoBuilder.title("micro-service")
                .description("e-commerce-springcloud-service")
                .contact(new Contact(
                        "lincheng", "tech.lincheng.site", "1498047434@qq.com"
                ))
                .version("1.0")
                .build();

        return apiInfoBuilder.build();
    }
}
