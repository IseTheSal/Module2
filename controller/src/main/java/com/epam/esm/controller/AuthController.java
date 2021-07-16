package com.epam.esm.controller;

import com.epam.esm.controller.security.AuthenticationBody;
import com.epam.esm.controller.security.RegistrationBody;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.service.UserService;
import com.epam.esm.service.impl.security.JwtProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    public AuthController(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/registration")
    public ResponseEntity<UserDTO> registerUser(@RequestBody RegistrationBody user) {
        return new ResponseEntity<>(userService.create(user.getUserDTO(), user.getPassword()), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> auth(@RequestBody AuthenticationBody body) {
        UserDTO user = userService.findByLoginAndPassword(body.getLogin(), body.getPassword());
        return new ResponseEntity<>(jwtProvider.generateToken(user.getLogin()), HttpStatus.OK);
    }
}
