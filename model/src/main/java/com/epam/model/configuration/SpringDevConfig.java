package com.epam.model.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
@Profile("develop")
public class SpringDevConfig {
    private static final String UTF_ENCODING = "UTF-8";

    @Bean(name = "DataSource")
    public JdbcTemplate jdbcTemplate() {
        System.out.println("dev");
        EmbeddedDatabase h2Database = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.H2)
                .addScript("database/schema.sql")
                .setScriptEncoding(UTF_ENCODING)
                .ignoreFailedDrops(true)
                .build();
        return new JdbcTemplate(h2Database);
    }
}
