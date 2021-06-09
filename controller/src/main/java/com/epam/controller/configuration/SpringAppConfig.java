package com.epam.controller.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@Configuration
@ComponentScan("com.epam")
@EnableWebMvc
public class SpringAppConfig {

    private static final String RESOURCE_VIEWER_PREFIX = "/WEB-INF/views";
    private static final String RESOURCE_VIEWER_SUFFIX = ".jsp";

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix(RESOURCE_VIEWER_PREFIX);
        internalResourceViewResolver.setSuffix(RESOURCE_VIEWER_SUFFIX);
        return internalResourceViewResolver;
    }

    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        ArrayList<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        stringHttpMessageConverter.setSupportedMediaTypes(mediaTypes);
        stringHttpMessageConverter.setDefaultCharset(StandardCharsets.UTF_8);
        return stringHttpMessageConverter;
    }
}
