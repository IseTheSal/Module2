package com.epam.esm.controller;

import com.epam.esm.model.entity.Order;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest Controller which connected with service layer and provide data in JSON.
 * Used to interact with {@link Order}.
 * <p>URI: <code>/api/v1/orders</code></p>
 *
 * @author Illia Aheyeu
 */
@RestController
@RequestMapping(value = "/api/v1/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    /**
     * {@link Order} service layer
     */
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Find user`s {@link Order Orders}
     *
     * @param id {@link com.epam.esm.model.entity.User User`s} id
     * @return <code>List</code> of {@link Order Orders}
     */
    @GetMapping("/users/{id:^[1-9]\\d{0,18}$}")
    public ResponseEntity<List<Order>> findUserOrders(@PathVariable long id) {
        return new ResponseEntity<>(orderService.findUserOrders(id), HttpStatus.OK);
    }

    /**
     * Create new {@link Order}
     *
     * @param order {@link Order}
     * @return Created {@link Order}
     */
    @PostMapping
    public ResponseEntity<Order> create(@RequestBody Order order) {
        return new ResponseEntity<>(orderService.create(order), HttpStatus.CREATED);
    }

    /**
     * Find {@link Order} by its id
     *
     * @param id {@link Order Order`s} id
     * @return {@link Order}
     */
    @GetMapping("/{id:^[1-9]\\d{0,18}$}")
    public ResponseEntity<Order> findById(@PathVariable long id) {
        return new ResponseEntity<>(orderService.findById(id), HttpStatus.OK);
    }

    /**
     * Find all {@link Order Orders}
     *
     * @return <code>List</code> of {@link Order orders}
     */
    @GetMapping("/all")
    public ResponseEntity<List<Order>> findAll() {
        return new ResponseEntity<>(orderService.findAll(), HttpStatus.OK);
    }
}
