package com.epam.model.configuration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@PropertySource("classpath:database/database.properties")
@Profile("production")
public class SpringProdConfig {

    @Value("${className}")
    private String jdbcClassName;
    @Value("${url}")
    private String jdbcUrl;
    @Value("${db.username}")
    private String jdbcUsername;
    @Value("${password}")
    private String jdbcPassword;
    @Value("${maxTotal}")
    private int jdbcMaxTotal;
    @Value("${initialSize}")
    private int jdbcInitialSize;

    @Bean(name = "DataSource")
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
}
