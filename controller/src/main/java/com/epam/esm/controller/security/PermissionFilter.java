package com.epam.esm.controller.security;

import com.epam.esm.service.impl.security.JwtUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class PermissionFilter extends GenericFilterBean {

    private static final String USERS_URI = "/api/v1/users";
    private static final String USERS_ID_URI = USERS_URI + "/[1-9]\\d{0,18}";
    private static final String USER_ALL_ORDERS_URI = USERS_ID_URI + "/orders";
    private static final String USER_ORDER_URI = USER_ALL_ORDERS_URI + "/[1-9]\\d{0,18}";
    private static final String URI_SEPARATOR = "/";
    private static final int USER_ID_INDEX = 4;
    private static final String ADMIN_ROLE = "ROLE_ADMIN";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uri = request.getRequestURI();
        boolean isUserAcceptable = uri.matches(USERS_ID_URI) || uri.matches(USER_ALL_ORDERS_URI) || uri.matches(USER_ORDER_URI);
        boolean isAdminAcceptable = uri.matches(USERS_URI) || uri.matches(USERS_URI + URI_SEPARATOR);
        if (isAdminAcceptable || isUserAcceptable) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                JwtUserDetails details = (JwtUserDetails) authentication.getDetails();
                boolean isAdmin = details.getRole().getName().equals(ADMIN_ROLE);
                if (!isAdmin && isUserAcceptable) {
                    long userId = Long.parseLong(uri.split(URI_SEPARATOR)[USER_ID_INDEX]);
                    if (!(details.getId() == userId)) {
                        ((HttpServletResponse) servletResponse).sendError(HttpStatus.FORBIDDEN.value());
                        return;
                    }
                } else if (!isAdmin) {
                    ((HttpServletResponse) servletResponse).sendError(HttpStatus.FORBIDDEN.value());
                    return;
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
