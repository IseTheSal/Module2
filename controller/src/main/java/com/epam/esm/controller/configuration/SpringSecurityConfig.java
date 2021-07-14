package com.epam.esm.controller.configuration;

import com.epam.esm.controller.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String USER_ROLE = "USER";
    private static final String ADMIN_ROLE = "ADMIN";

    private final JwtFilter jwtFilter;

    @Autowired
    public SpringSecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //fixme check if admin has permission to login
        http.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/api/v1/login").hasRole("ADMIN")
//                .antMatchers(HttpMethod.POST, "/api/v1/login").not().hasAnyRole()
//                .antMatchers(HttpMethod.PUT, "/api/v1/registration").not().hasAnyRole()
                .antMatchers(HttpMethod.GET, "/api/v1/orders").hasRole("USER")
//                .antMatchers(HttpMethod.POST, "/api/v1/orders/*").hasRole(USER_ROLE)
//                .antMatchers(HttpMethod.GET, "/api/v1/certificates/*", "/api/v1/tags/*").permitAll()
                //fixme h2 console
//                .antMatchers( "/console/**").permitAll()
//                .antMatchers("/**").hasRole(ADMIN_ROLE)
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.headers().frameOptions().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
