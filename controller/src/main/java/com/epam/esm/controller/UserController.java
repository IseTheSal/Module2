package com.epam.esm.controller;

import com.epam.esm.model.entity.User;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    /**
     * {@link User} service layer
     */
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Find {@link User} by his id
     *
     * @param id {@link User User`s} id
     * @return {@link User}
     */
    @GetMapping("/{id:^[1-9]\\d{0,18}$}")
    public ResponseEntity<User> findById(@PathVariable long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    /**
     * Find all {@link User Users}
     *
     * @return <code>List</code> of {@link User User`s}
     */
    @GetMapping("/all")
    public ResponseEntity<List<User>> findAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }
}
