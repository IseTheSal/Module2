package com.epam.esm.service.impl;

import com.epam.esm.error.exception.RoleNotFoundException;
import com.epam.esm.error.exception.UserLoginExistException;
import com.epam.esm.error.exception.UserNotFoundException;
import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.entity.UserRole;
import com.epam.esm.model.entity.User;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findById(long id) {
        return userDao.findById(id).orElseThrow(() -> new UserNotFoundException("id=" + id));
    }

    @Override
    public List<User> findAll(int amount, int page) {
        return userDao.findAll(amount, page - 1);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserDTO create(User user) {
        String login = user.getLogin();
        findByLogin(login).ifPresent(u -> {
            throw new UserLoginExistException(login);
        });
        String roleName = user.getRole().getName();
        UserRole role = userDao.findRoleByName(roleName).orElseThrow(() -> new RoleNotFoundException(roleName));
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User created = userDao.create(user);
        return UserDTO.toDTO(created);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    @Override
    public User findByLoginOrThrow(String login) {
        return findByLogin(login).orElseThrow(() -> new UserNotFoundException("login=" + login));
    }

    @Override
    public UserDTO findByLoginAndPassword(String login, String password) {
        User user = findByLoginOrThrow(login);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UserNotFoundException();
        }
        return UserDTO.toDTO(user);
    }
}
