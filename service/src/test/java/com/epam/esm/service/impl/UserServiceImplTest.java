package com.epam.esm.service.impl;

import com.epam.esm.error.exception.UserNotFoundException;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.RoleRepository;
import com.epam.esm.model.repository.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.service.impl.security.PermissionChecker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.model.dto.converter.ConverterDTO.toDTO;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    static User firstUser;
    static UserDTO firstUserDto;
    static User secondUser;
    static UserDTO secondUserDto;
    static List<User> userList;
    static List<UserDTO> userListDto;
    static PasswordEncoder passwordEncoder;

    UserRepository userRepository;
    RoleRepository roleRepository;
    UserService service;
    PermissionChecker permissionChecker;

    @BeforeAll
    public static void setUpData() {
        firstUser = new User("isethesal");
        firstUser.setId(1);
        firstUserDto = toDTO(firstUser);
        secondUser = new User("ileathehunter");
        secondUser.setId(2);
        secondUserDto = toDTO(secondUser);
        userList = new ArrayList<>();
        userList.add(firstUser);
        userList.add(secondUser);
        userListDto = new ArrayList<>();
        userListDto.add(firstUserDto);
        userListDto.add(secondUserDto);
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @BeforeEach
    public void setUp() {
        permissionChecker = Mockito.mock(PermissionChecker.class);
        userRepository = Mockito.mock(UserRepository.class);
        service = new UserServiceImpl(userRepository, roleRepository, null, passwordEncoder, permissionChecker);
    }

    @Test
    void findById() {
        when(permissionChecker.checkUserIdPermission(1L)).thenReturn(true);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(firstUser));
        UserDTO actual = service.findById(1);
        UserDTO expected = firstUserDto;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findByIdThrownException() {
        when(permissionChecker.checkUserIdPermission(1L)).thenReturn(true);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> service.findById(1));
    }

    @Test
    void findAll() {
        when(permissionChecker.checkAdminPermission()).thenReturn(true);
        Mockito.when(userRepository.findAll(PageRequest.of(0, 100))).thenReturn(new PageImpl<>(userList));
        List<UserDTO> actual = service.findAll(100, 1);
        List<UserDTO> expected = userListDto;
        Assertions.assertEquals(expected, actual);
    }
}