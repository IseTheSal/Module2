package com.epam.esm.service.impl.security;

import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.entity.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class JwtUserDetails implements UserDetails {

    private long id;
    private String login;
    private String password;
    private UserRole role;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public static JwtUserDetails fromUserEntityToCustomUserDetails(UserDTO user, String password) {
        JwtUserDetails userDetails = new JwtUserDetails();
        userDetails.id = user.getId();
        userDetails.login = user.getLogin();
        userDetails.password = password;
        userDetails.role = user.getRole();
        userDetails.grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName()));
        return userDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    public long getId() {
        return id;
    }

    public UserRole getRole() {
        return role;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
