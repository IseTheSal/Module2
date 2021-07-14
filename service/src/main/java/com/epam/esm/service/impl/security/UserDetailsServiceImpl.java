package com.epam.esm.service.impl.security;

import com.epam.esm.model.entity.User;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public JwtUserDetails loadUserByUsername(String username) {
        User user = userService.findByLoginOrThrow(username);
        return JwtUserDetails.fromUserEntityToCustomUserDetails(user);
    }
}
