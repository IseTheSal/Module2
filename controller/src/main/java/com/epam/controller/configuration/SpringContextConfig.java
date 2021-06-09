package com.epam.controller.configuration;

import org.h2.server.web.WebServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class SpringContextConfig implements WebApplicationInitializer {

    private static final String SPRING_PROFILES_ACTIVE = "spring.profiles.active";
    private static final String APP_SERVLET_NAME = "app";
    private static final String EXCEPTION_HANDLER_NOT_FOUND_PROPERTY = "throwExceptionIfNoHandlerFound";
    private static final String EXCEPTION_HANDLER_NOT_FOUND_VALUE = "true";
    private static final String APP_SERVLET_URL_MAPPING = "/";
    private static final String HIDDEN_HTTP_METHOD_FILTER_NAME = "HiddenHttpMethodFilter";
    private static final String HIDDEN_HTTP_METHOD_FILTER_URL_MAPPING = "/";
    private static final String H2_CONSOLE_NAME = "h2-console";
    private static final String H2_CONSOLE_URL_MAPPING = "/console/*";
    // APPLICATION PROFILES
    private static final String PRODUCTION_PROFILE = "production";
    private static final String DEVELOP_PROFILE = "develop";

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(SpringAppConfig.class);
        servletContext.setInitParameter(SPRING_PROFILES_ACTIVE, DEVELOP_PROFILE);

        DispatcherServlet servlet = new DispatcherServlet(context);
        ServletRegistration.Dynamic appServletRegistration = servletContext.addServlet(APP_SERVLET_NAME, servlet);
        appServletRegistration.setInitParameter(EXCEPTION_HANDLER_NOT_FOUND_PROPERTY, EXCEPTION_HANDLER_NOT_FOUND_VALUE);
        appServletRegistration.setLoadOnStartup(1);
        appServletRegistration.addMapping(APP_SERVLET_URL_MAPPING);

        HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
        FilterRegistration.Dynamic filter = servletContext.addFilter(HIDDEN_HTTP_METHOD_FILTER_NAME, hiddenHttpMethodFilter);
        filter.addMappingForUrlPatterns(null, false, HIDDEN_HTTP_METHOD_FILTER_URL_MAPPING);

        ServletRegistration.Dynamic h2Console = servletContext.addServlet(H2_CONSOLE_NAME, new WebServlet());
        h2Console.setLoadOnStartup(1);
        h2Console.addMapping(H2_CONSOLE_URL_MAPPING);
    }
}
