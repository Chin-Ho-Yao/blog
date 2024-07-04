package com.yao.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by Jack Yao on 2024/6/8 7:29 PM
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/docs/**")
                .addResourceLocations("classpath:/META-INF/resources/");
    }
}