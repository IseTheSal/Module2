package com.epam.esm.service.impl;

import com.epam.esm.error.exception.RoleNotFoundException;
import com.epam.esm.error.exception.UserLoginExistException;
import com.epam.esm.error.exception.UserNotFoundException;
import com.epam.esm.error.exception.ValidationException;
import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.dto.converter.ConverterDTO;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.entity.UserRole;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.model.dto.converter.ConverterDTO.fromDTO;
import static com.epam.esm.model.dto.converter.ConverterDTO.toDTO;

@Service
public class UserServiceImpl implements UserService {

    private static final String USER_ROLE = "ROLE_USER";

    private final UserDao userDao;
    private final MessageSource messageSource;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, MessageSource messageSource, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.messageSource = messageSource;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO findById(long id) {
        return toDTO(userDao.findById(id).orElseThrow(() -> new UserNotFoundException("id=" + id)));
    }

    @Override
    public List<UserDTO> findAll(int amount, int page) {
        return userDao.findAll(amount, page - 1).stream().map(ConverterDTO::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserDTO create(UserDTO dto, String password) {
        checkUserValid(dto, password);
        User user = fromDTO(dto);
        user.setPassword(password);
        String login = user.getLogin();
        userDao.findByLogin(login).ifPresent(u -> {
            throw new UserLoginExistException(login);
        });
        UserRole role = userDao.findRoleByName(USER_ROLE).orElseThrow(() -> new RoleNotFoundException(USER_ROLE));
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User created = userDao.create(user);
        return toDTO(created);
    }

    private void checkUserValid(UserDTO user, String password) {
        StringBuilder exceptionValidMessage = new StringBuilder();
        String login = user.getLogin();
        if (!UserValidator.isLoginValid(login)) {
            exceptionValidMessage.append(messageSource.getMessage("error.user.validation.login",
                    new Object[]{login}, LocaleContextHolder.getLocale())).append("\n");
        }
        if (!UserValidator.isPasswordValid(password)) {
            exceptionValidMessage.append(messageSource.getMessage("error.user.validation.password",
                    new Object[]{password}, LocaleContextHolder.getLocale())).append("\n");
        }
        String firstName = user.getFirstName();
        if (!UserValidator.isFirstNameValid(firstName)) {
            exceptionValidMessage.append(messageSource.getMessage("error.user.validation.firstname",
                    new Object[]{firstName}, LocaleContextHolder.getLocale())).append("\n");
        }
        String lastName = user.getLastName();
        if (!UserValidator.isLastNameValid(lastName)) {
            exceptionValidMessage.append(messageSource.getMessage("error.user.validation.lastname",
                    new Object[]{lastName}, LocaleContextHolder.getLocale())).append("\n");
        }
        String message = exceptionValidMessage.toString();
        if (!message.isEmpty()) {
            throw new ValidationException(message);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserDTO findByLoginAndPassword(String login, String password) {
        User user = userDao.findByLogin(login).orElseThrow(() -> new UserNotFoundException("login=" + login));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UserNotFoundException();
        }
        return toDTO(user);
    }
}
