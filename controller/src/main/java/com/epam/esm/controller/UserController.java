package com.epam.esm.controller;

import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.epam.esm.controller.hateaos.Hateoas.createOrderHateoas;
import static com.epam.esm.controller.hateaos.Hateoas.createUserHateoas;

@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    /**
     * {@link UserDTO} service layer
     */
    private final UserService userService;
    /**
     * {@link OrderDTO} service layer
     */
    private final OrderService orderService;

    @Autowired
    public UserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
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

    /**
     * Find {@link OrderDTO} by its id
     *
     * @param userId  {@link UserDTO User`s} id
     * @param orderId {@link OrderDTO Order`s} id
     * @return {@link OrderDTO}
     */
    @GetMapping("/{userId:^[1-9]\\d{0,18}$}/orders/{orderId:^[1-9]\\d{0,18}$}")
    public ResponseEntity<OrderDTO> findOrderByUserId(@PathVariable long userId, @PathVariable long orderId) {
        OrderDTO order = orderService.findUserOrder(userId, orderId);
        return new ResponseEntity<>(createOrderHateoas(order), HttpStatus.OK);
    }

    /**
     * Find user`s {@link OrderDTO Orders}
     *
     * @param id {@link UserDTO User`s} id
     * @return <code>List</code> of {@link OrderDTO Orders}
     */
    @GetMapping("/{id:^[1-9]\\d{0,18}$}/orders")
    public ResponseEntity<List<OrderDTO>> findUserOrders(@PathVariable long id,
                                                         @RequestParam(required = false, defaultValue = "1") int page,
                                                         @RequestParam(required = false, defaultValue = "10") int amount) {
        return new ResponseEntity<>(orderService.findUserOrders(id, amount, page), HttpStatus.OK);
    }
}
