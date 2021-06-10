package com.epam.controller.configuration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@Configuration
@ComponentScan("com.epam")
@PropertySource("classpath:database/database.properties")
@EnableWebMvc
public class SpringAppConfig {

    private static final String RESOURCE_VIEWER_PREFIX = "/WEB-INF/views";
    private static final String RESOURCE_VIEWER_SUFFIX = ".jsp";
    private static final String UTF_ENCODING = "UTF-8";
    private static final String DATABASE_SQL = "database/schema.sql";

    @Value("${db.className}")
    private String jdbcClassName;
    @Value("${db.url}")
    private String jdbcUrl;
    @Value("${db.username}")
    private String jdbcUsername;
    @Value("${db.password}")
    private String jdbcPassword;
    @Value("${db.maxTotal}")
    private int jdbcMaxTotal;
    @Value("${db.initialSize}")
    private int jdbcInitialSize;

    @Bean(name = "DataSource")
    @Profile("production")
    public JdbcTemplate server() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(jdbcClassName);
        ds.setUrl(jdbcUrl);
        ds.setUsername(jdbcUsername);
        ds.setPassword(jdbcPassword);
        ds.setMaxTotal(jdbcMaxTotal);
        ds.setInitialSize(jdbcInitialSize);
        return new JdbcTemplate(ds);
    }

    @Bean(name = "DataSource")
    @Profile("develop")
    public JdbcTemplate jdbcTemplate() {
        EmbeddedDatabase h2Database = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.H2)
                .addScript(DATABASE_SQL)
                .setScriptEncoding(UTF_ENCODING)
                .ignoreFailedDrops(true)
                .build();
        return new JdbcTemplate(h2Database);
    }

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
