package com.riyatuition.riya_tuition.config;

import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry
            .addResourceHandler("/uploads/**")
            .addResourceLocations("file:/E:/Backend-Java/riya-tuition/uploads/")
            .setCachePeriod(3600);
    }
}




