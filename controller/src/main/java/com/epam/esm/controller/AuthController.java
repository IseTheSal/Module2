package com.epam.esm.controller;

import com.epam.esm.controller.security.AuthenticationBody;
import com.epam.esm.controller.security.JwtProvider;
import com.epam.esm.controller.security.RegistrationBody;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Rest Controller which connected with security-service layer.
 * Used to interact with {@link UserService} and {@link JwtProvider}.
 * <p>URI: <code>/api/v1/</code></p>
 *
 * @author Illia Aheyeu
 */
@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    /**
     * {@link UserService} service layer
     */
    private final UserService userService;
    /**
     * {@link JwtProvider} jwt provider layer
     */
    private final JwtProvider jwtProvider;

    public AuthController(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    /**
     * Method used to create {@link UserDTO}.
     *
     * @param user {@link UserDTO}
     * @return ResponseEntity with {@link UserDTO} object
     */
    @PostMapping("/registration")
    public ResponseEntity<UserDTO> registerUser(@RequestBody RegistrationBody user) {
        return new ResponseEntity<>(userService.create(user.getUserDTO(), user.getPassword()), HttpStatus.CREATED);
    }

    /**
     * Method used to authenticate User.
     *
     * @param body {@link AuthenticationBody}
     * @return ResponseEntity with generated String Jwt object.
     */
    @PostMapping("/login")
    public ResponseEntity<String> auth(@RequestBody AuthenticationBody body) {
        UserDTO user = userService.findByLoginAndPassword(body.getLogin(), body.getPassword());
        return new ResponseEntity<>(jwtProvider.generateToken(user.getLogin()), HttpStatus.OK);
    }

    /**
     * Method used to logout.
     *
     */
    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, null);
    }
}
