package com.epam.esm.controller;

import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.epam.esm.controller.hateaos.Hateoas.createUserHateoas;

@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    /**
     * {@link UserDTO} service layer
     */
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Find {@link UserDTO} by his id
     *
     * @param id {@link UserDTO User`s} id
     * @return {@link UserDTO}
     */
    @GetMapping("/{id:^[1-9]\\d{0,18}$}")
    public ResponseEntity<UserDTO> findById(@PathVariable long id) {
        UserDTO user = userService.findById(id);
        return new ResponseEntity<>(createUserHateoas(user), HttpStatus.OK);
    }

    /**
     * Find all {@link UserDTO Users}
     *
     * @return <code>List</code> of {@link UserDTO User`s}
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll(@RequestParam(required = false, defaultValue = "1") int page,
                                                 @RequestParam(required = false, defaultValue = "10") int amount) {
        return new ResponseEntity<>(userService.findAll(amount, page), HttpStatus.OK);
    }
}
