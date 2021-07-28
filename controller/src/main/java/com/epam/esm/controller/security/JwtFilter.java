package com.epam.esm.controller.security;

import com.epam.esm.error.RestApplicationError;
import com.epam.esm.service.impl.security.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@Log4j2
public class JwtFilter extends GenericFilterBean {
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer";
    public static final String SEMICOLON = ";";
    public static final String CHARSET_UTF8 = "charset=UTF-8";

    private JwtProvider jwtProvider;
    private UserDetailsServiceImpl userDetailsService;
    private final ObjectMapper objectMapper;
    private final MessageSource messageSource;

    @Autowired
    public JwtFilter(ObjectMapper objectMapper, MessageSource messageSource) {
        this.objectMapper = objectMapper;
        this.messageSource = messageSource;
    }

    @Autowired
    public void setJwtProvider(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            Optional<String> token = getTokenFromRequest((HttpServletRequest) servletRequest);
            if (token.isPresent()) {
                if (jwtProvider.validateToken(token.get())) {
                    String userLogin = jwtProvider.getLoginFromToken(token.get());
                    UserDetails userDetails = userDetailsService.loadUserByUsername(userLogin);
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null,
                            userDetails.getAuthorities());
                    auth.setDetails(userDetails);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else if (jwtProvider.validateKeycloak(token.get())) {
                    UserDetails keycloakUser = jwtProvider.findOrRegisterUser(token.get());
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(keycloakUser, null,
                            keycloakUser.getAuthorities());
                    auth.setDetails(keycloakUser);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else {
                    jwtProvider.recogniseException(token.get());
                }
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (RuntimeException ex) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            RestApplicationError error = new RestApplicationError(messageSource.getMessage(ex.getMessage(), null,
                    LocaleContextHolder.getLocale()), 40401);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE + SEMICOLON + CHARSET_UTF8);
            response.getWriter().write(objectMapper.writeValueAsString(error));
        }
    }

    private Optional<String> getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearer) && bearer.startsWith(BEARER)) {
            return Optional.of(bearer.substring(7));
        }
        return Optional.empty();
    }
}
