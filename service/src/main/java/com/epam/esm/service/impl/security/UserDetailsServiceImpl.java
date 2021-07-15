package com.epam.esm.service.impl.security;

import com.epam.esm.error.exception.UserNotFoundException;
import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.entity.User;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.epam.esm.model.dto.converter.ConverterDTO.toDTO;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDao userDao;

    @Autowired
    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public JwtUserDetails loadUserByUsername(String login) {
        User user = userDao.findByLogin(login).orElseThrow(() -> new UserNotFoundException("login=" + login));
        return JwtUserDetails.fromUserEntityToCustomUserDetails(toDTO(user), user.getPassword());
    }
}
