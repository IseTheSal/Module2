package com.epam.esm.controller;

import com.epam.esm.controller.security.AuthenticationBody;
import com.epam.esm.controller.security.JwtProvider;
import com.epam.esm.controller.security.RegistrationBody;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping("/registration")
    public ResponseEntity<UserDTO> registerUser(@RequestBody RegistrationBody user) {
        return new ResponseEntity<>(userService.create(user.getUserDTO(), user.getPassword()), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> auth(@RequestBody AuthenticationBody body) {
        UserDTO user = userService.findByLoginAndPassword(body.getLogin(), body.getPassword());
        return new ResponseEntity<>(jwtProvider.generateToken(user.getLogin()), HttpStatus.OK);
    }

    // FIXME: 24-Jul-21 DELETE TEST MAPPING
    @GetMapping("/test")
    @PreAuthorize("hasRole('USER')")
    public String testUser() {
        return "USER info";
    }
}
