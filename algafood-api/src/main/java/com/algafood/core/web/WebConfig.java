package com.algafood.core.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
/*
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*");
        //.allowedOrigins("*")
        //.maxAge(30)
    }*/

    @Bean
    public Filter shallowEtagHeaderFilter() { //habilita Etag no cache
        return new ShallowEtagHeaderFilter();
    }

}
