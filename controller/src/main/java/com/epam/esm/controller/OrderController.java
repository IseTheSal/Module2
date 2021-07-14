package com.epam.esm.controller;

import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.epam.esm.controller.hateaos.Hateoas.createOrderHateoas;

/**
 * Rest Controller which connected with service layer and provide data in JSON.
 * Used to interact with {@link OrderDTO}.
 * <p>URI: <code>/api/v1/orders</code></p>
 *
 * @author Illia Aheyeu
 */
@RestController
@RequestMapping(value = "/api/v1/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {
    /**
     * {@link OrderDTO} service layer
     */
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Find user`s {@link OrderDTO Orders}
     *
     * @param id {@link com.epam.esm.model.dto.UserDTO User`s} id
     * @return <code>List</code> of {@link OrderDTO Orders}
     */
    @GetMapping("/users/{id:^[1-9]\\d{0,18}$}")
    public ResponseEntity<List<OrderDTO>> findUserOrders(@PathVariable long id,
                                                         @RequestParam(required = false, defaultValue = "1") int page,
                                                         @RequestParam(required = false, defaultValue = "10") int amount) {
        return new ResponseEntity<>(orderService.findUserOrders(id, amount, page), HttpStatus.OK);
    }

    /**
     * Create new {@link OrderDTO}
     *
     * @param order {@link OrderDTO}
     * @return Created {@link OrderDTO}
     */
    @PostMapping
    public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO order) {
        OrderDTO createdOrder = orderService.create(order);
        return new ResponseEntity<>(createOrderHateoas(createdOrder), HttpStatus.CREATED);
    }

    /**
     * Find {@link OrderDTO} by its id
     *
     * @param id {@link OrderDTO Order`s} id
     * @return {@link OrderDTO}
     */
    @GetMapping("/{id:^[1-9]\\d{0,18}$}")
    public ResponseEntity<OrderDTO> findById(@PathVariable long id) {
        OrderDTO order = orderService.findById(id);
        return new ResponseEntity<>(createOrderHateoas(order), HttpStatus.OK);
    }

    /**
     * Find all {@link OrderDTO Orders}
     *
     * @return <code>List</code> of {@link OrderDTO orders}
     */
    @GetMapping
    public ResponseEntity<List<OrderDTO>> findAll(@RequestParam(required = false, defaultValue = "1") int page,
                                                  @RequestParam(required = false, defaultValue = "10") int amount) {
        return new ResponseEntity<>(orderService.findAll(amount, page), HttpStatus.OK);
    }
}
