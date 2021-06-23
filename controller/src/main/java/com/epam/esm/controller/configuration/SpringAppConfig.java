package com.epam.esm.controller.configuration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Locale;

@Configuration
@ComponentScan("com.epam.esm")
@PropertySource("classpath:database/database.properties")
@EnableWebMvc
public class SpringAppConfig implements WebMvcConfigurer {

    private static final String RESOURCE_VIEWER_PREFIX = "/WEB-INF/views";
    private static final String RESOURCE_VIEWER_SUFFIX = ".jsp";
    private static final String UTF_ENCODING = "UTF-8";
    private static final String DATABASE_SQL = "schema.sql";

    @Value("${db.className}")
    private String jdbcClassName;
    @Value("${db.url}")
    private String jdbcUrl;
    @Value("${db.username}")
    private String jdbcUsername;
    @Value("${db.password}")
    private String jdbcPassword;
    @Value("${db.maxTotal}")
    private String jdbcMaxTotal;
    @Value("${db.initialSize}")
    private String jdbcInitialSize;

    @Bean(name = "DataSource")
    @Profile("production")
    public JdbcTemplate productionJdbc() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(jdbcClassName);
        ds.setUrl(jdbcUrl);
        ds.setUsername(jdbcUsername);
        ds.setPassword(jdbcPassword);
        ds.setMaxTotal(Integer.parseInt(jdbcMaxTotal));
        ds.setInitialSize(Integer.parseInt(jdbcInitialSize));
        return new JdbcTemplate(ds);
    }

    @Bean
    @Profile("production")
    public PlatformTransactionManager platformTransactionManagerProduction() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(productionJdbc().getDataSource());
        return dataSourceTransactionManager;
    }

    @Bean(name = "DataSource")
    @Profile("develop")
    public JdbcTemplate developJdbc() {
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
    @Profile("develop")
    public PlatformTransactionManager platformTransactionManagerDevelop() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(developJdbc().getDataSource());
        return dataSourceTransactionManager;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:language/language");
        messageSource.setDefaultEncoding(UTF_ENCODING);
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(new Locale("ru", "RU"));
        return sessionLocaleResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
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
