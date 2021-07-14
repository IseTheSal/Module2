package com.epam.esm.controller.hateaos;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.OrderController;
import com.epam.esm.controller.TagController;
import com.epam.esm.controller.UserController;
import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.dto.UserDTO;

import java.util.Collections;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class Hateoas {

    private static final int PAGE_VALUE = 1;
    private static final int AMOUNT_VALUE = 10;
    private static final String FIND_BY_ID = "Find by id";
    private static final String FIND_ALL = "Find all";
    private static final String DELETE_VALUE = "Delete";
    private static final String CREATE = "Create";
    private static final String UPDATE = "UPDATE";
    private static final String TAG_NAME = "snow";
    private static final String GIFT_VALUE = "цикл";
    private static final String FIND_BY_ATTRIBUTES = "Find by attributes";
    private static final String ASC = "ASC";
    private static final String DESC = "DESC";
    private static final String FIND_BY_USER_ID_ORDERS = "Find by user`s orders";
    private static final String FIND_MOST_WIDELY_USED_TAG = "Find most widely used";
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";

    public static GiftCertificateDTO createCertificateHateoas(GiftCertificateDTO certificate) {
        return certificate
                .add(linkTo(methodOn(GiftCertificateController.class).findById(certificate.getId())).withSelfRel()
                        .withName(FIND_BY_ID).withType(GET))
                .add(linkTo(methodOn(GiftCertificateController.class).delete(certificate.getId())).withSelfRel()
                        .withName(DELETE_VALUE).withType(DELETE))
                .add(linkTo(methodOn(GiftCertificateController.class).create(certificate)).withSelfRel()
                        .withName(CREATE).withType(POST))
                .add(linkTo(methodOn(GiftCertificateController.class).update(certificate)).withSelfRel()
                        .withName(UPDATE).withType(PUT))
                .add(linkTo(methodOn(GiftCertificateController.class)
                        .findCertificates(Collections.singletonList(TAG_NAME), GIFT_VALUE, ASC, DESC, PAGE_VALUE, AMOUNT_VALUE)).withSelfRel()
                        .withName(FIND_BY_ATTRIBUTES).withType(GET));
    }


    public static OrderDTO createOrderHateoas(OrderDTO order) {
        return order
                .add(linkTo(methodOn(OrderController.class)
                        .findUserOrders(order.getUser().getId(), PAGE_VALUE, AMOUNT_VALUE)).withSelfRel()
                        .withName(FIND_BY_USER_ID_ORDERS).withType(GET))
                .add(linkTo(methodOn(OrderController.class).findAll(PAGE_VALUE, AMOUNT_VALUE)).withSelfRel()
                        .withName(FIND_ALL).withType(GET))
                .add(linkTo(methodOn(OrderController.class).findById(order.getId())).withSelfRel()
                        .withName(FIND_BY_ID).withType(GET))
                .add(linkTo(methodOn(OrderController.class).create(order)).withSelfRel()
                        .withName(CREATE).withType(POST));
    }


    public static TagDTO createTagHateoas(TagDTO tag) {
        return tag
                .add(linkTo(methodOn(TagController.class).findTagById(tag.getId())).withSelfRel()
                        .withName(FIND_BY_ID).withType(GET))
                .add(linkTo(methodOn(TagController.class).findAllTags(PAGE_VALUE, AMOUNT_VALUE)).withSelfRel()
                        .withName(FIND_ALL).withType(GET))
                .add(linkTo(methodOn(TagController.class).deleteTagById(tag.getId())).withSelfRel()
                        .withName(DELETE_VALUE).withType(DELETE))
                .add(linkTo(methodOn(TagController.class).createTag(tag)).withSelfRel()
                        .withName(CREATE).withType(POST))
                .add(linkTo(methodOn(TagController.class).findMostWidelyUsedTagByMaxUserPrice()).withSelfRel()
                        .withName(FIND_MOST_WIDELY_USED_TAG).withType(GET));
    }

    public static UserDTO createUserHateoas(UserDTO user) {
        return user
                .add(linkTo(methodOn(UserController.class).findAll(PAGE_VALUE, AMOUNT_VALUE)).withSelfRel()
                        .withName(FIND_ALL).withType(GET))
                .add(linkTo(methodOn(UserController.class).findById(user.getId())).withSelfRel()
                        .withName(FIND_BY_ID).withType(GET));
    }
}
