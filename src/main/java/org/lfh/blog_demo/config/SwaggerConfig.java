package org.lfh.blog_demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("博客系统文档")
                        .version("1.0")
                        .description("API描述")
                        .contact(new Contact()
                                .name("刘方涵")
                                .url("https://github.com/liufanghan/blog_demo")
                        )
                )
                .addServersItem(new Server().url("/")); //设置服务器的基本URL。在这个例子中，"/"表示使用默认的根URL。
    }
}
