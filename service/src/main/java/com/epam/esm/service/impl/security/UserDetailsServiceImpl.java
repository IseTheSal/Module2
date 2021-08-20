package com.epam.esm.service.impl.security;

import com.epam.esm.error.exception.RoleNotFoundException;
import com.epam.esm.error.exception.UserNotFoundException;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.entity.UserRole;
import com.epam.esm.model.repository.OrderRepository;
import com.epam.esm.model.repository.RoleRepository;
import com.epam.esm.model.repository.UserRepository;
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
    private static final String ADMIN_ROLE = "ROLE_ADMIN";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, RoleRepository roleRepository, OrderRepository orderRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.orderRepository = orderRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public JwtUserDetails loadUserByUsername(String login) {
        User user = userRepository.findByLogin(login).orElseThrow(() -> new UserNotFoundException("login=" + login));
        return JwtUserDetails.fromUserEntityToCustomUserDetails(toDTO(user), user.getPassword());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public JwtUserDetails loadOrRegisterUser(UserDTO dto) {
        User userDetail;
        User user = fromDTO(dto);
        String login = user.getLogin();
        Optional<User> optionalUser = userRepository.findByLogin(login);
        if (optionalUser.isPresent()) {
            userDetail = optionalUser.get();
        } else {
            String password = RandomStringUtils.random(PASSWORD_LENGTH, true, true);
            user.setPassword(passwordEncoder.encode(password));
            userDetail = userRepository.save(user);
        }
        return JwtUserDetails.fromUserEntityToCustomUserDetails(toDTO(userDetail), user.getPassword());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UserRole findKeycloakRoleByName(String roleName) {
        return roleRepository.findByName(roleName).orElseThrow(() -> new RoleNotFoundException(roleName));
    }
}
