package com.epam.esm.controller;

import com.epam.esm.controller.security.AuthenticationBody;
import com.epam.esm.controller.security.JwtProvider;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.entity.User;
import com.epam.esm.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    public AuthController(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PutMapping("/registration")
    public String registerUser(@RequestBody User user) {
        //fixme
        userService.create(user);
        return "OK";
    }

    @PostMapping("/login")
    public String auth(@RequestBody AuthenticationBody body) {
        UserDTO user = userService.findByLoginAndPassword(body.getLogin(), body.getPassword());
        return jwtProvider.generateToken(user.getLogin());
    }
}
