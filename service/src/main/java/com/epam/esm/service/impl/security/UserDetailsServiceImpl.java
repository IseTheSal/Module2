package com.epam.esm.service.impl.security;

import com.epam.esm.error.exception.RoleNotFoundException;
import com.epam.esm.error.exception.UserNotFoundException;
import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.entity.UserRole;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.epam.esm.model.dto.converter.ConverterDTO.fromDTO;
import static com.epam.esm.model.dto.converter.ConverterDTO.toDTO;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final int PASSWORD_LENGTH = 30;

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailsServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public JwtUserDetails loadUserByUsername(String login) {
        User user = userDao.findByLogin(login).orElseThrow(() -> new UserNotFoundException("login=" + login));
        return JwtUserDetails.fromUserEntityToCustomUserDetails(toDTO(user), user.getPassword());
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public JwtUserDetails loadOrRegisterUser(UserDTO dto) {
        User userDetail;
        User user = fromDTO(dto);
        String login = user.getLogin();
        Optional<User> optionalUser = userDao.findByLogin(login);
        if(optionalUser.isPresent()){
            userDetail = optionalUser.get();
        } else {
            String password = RandomStringUtils.random(PASSWORD_LENGTH, true, true);
            user.setPassword(passwordEncoder.encode(password));
            userDetail = userDao.create(user);
        }
        return JwtUserDetails.fromUserEntityToCustomUserDetails(toDTO(userDetail), user.getPassword());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UserRole findKeycloakRoleByName(String roleName){
        return userDao.findRoleByName(roleName).orElseThrow(() -> new RoleNotFoundException(roleName));
    }
}
