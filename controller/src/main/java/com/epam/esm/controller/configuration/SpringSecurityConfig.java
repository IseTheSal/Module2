package com.epam.esm.controller.configuration;


import com.epam.esm.controller.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String USER_ROLE = "USER";
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String REALM_ACCESS= "realm_access";
    private static final String ROLES= "roles";
    private static final String ROLE_PREFIX= "ROLE_";

    private final JwtFilter jwtFilter;

    @Autowired
    public SpringSecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/v1/certificates/**", "/api/v1/tags/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/login", "/api/v1/registration").anonymous()
                .antMatchers(HttpMethod.GET, "/api/v1/users/**").hasAnyRole(USER_ROLE, ADMIN_ROLE)
                .antMatchers(HttpMethod.POST, "/api/v1/orders/*").hasAnyRole(USER_ROLE, ADMIN_ROLE)
                .antMatchers("/api/v1/orders/**", "/api/v1/certificates/**", "/api/v1/users/**", "/api/v1/tags/**").hasRole(ADMIN_ROLE)
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2ResourceServer().jwt();
//                .jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());

    }

//    @Bean
//    public Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter());
//        return jwtAuthenticationConverter;
//    }
//
//    @Bean
//    public Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter() {
//        JwtGrantedAuthoritiesConverter delegate = new JwtGrantedAuthoritiesConverter();
//
//        return new Converter<Jwt, Collection<GrantedAuthority>>() {
//            @Override
//            public Collection<GrantedAuthority> convert(Jwt jwt) {
//                Collection<GrantedAuthority> grantedAuthorities = delegate.convert(jwt);
//                if (jwt.getClaim(REALM_ACCESS) == null) {
//                    return grantedAuthorities;
//                }
//                JSONObject realmAccess = jwt.getClaim(REALM_ACCESS);
//                if (realmAccess.get(ROLES) == null) {
//                    return grantedAuthorities;
//                }
//                JSONArray roles = (JSONArray) realmAccess.get(ROLES);
//                final List<SimpleGrantedAuthority> keycloakAuthorities = roles.stream().map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role)).collect(Collectors.toList());
//                grantedAuthorities.addAll(keycloakAuthorities);
//                return grantedAuthorities;
//            }
//        };
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
