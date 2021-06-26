package com.epam.esm.controller.hateaos;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.OrderController;
import com.epam.esm.controller.TagController;
import com.epam.esm.controller.UserController;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class Hateoas {

    private static final int PAGE_VALUE = 1;
    private static final int AMOUNT_VALUE = 10;
    private static final String FIND_BY_ID = "Find by id";
    private static final String FIND_ALL = "Find all";
    private static final String DELETE = "Delete";
    private static final String CREATE = "Create";
    private static final String UPDATE = "UPDATE";
    private static final String TAG_NAME = "snow";
    private static final String GIFT_VALUE = "цикл";
    private static final String FIND_BY_ATTRIBUTES = "Find by attributes";
    private static final String FIND_BY_SEVERAL_TAGS = "Find by several tags";
    private static final String ASC = "ASC";
    private static final String DESC = "DESC";
    private static final String FIND_BY_USER_ID_ORDERS = "Find by user`s orders";
    private static final String FIND_MOST_WIDELY_USED_TAG = "Find most widely used";

    public static GiftCertificate createCertificateHateoas(GiftCertificate certificate) {
        certificate.getTags().forEach(Hateoas::createTagHateoas);
        return certificate
                .add(linkTo(methodOn(GiftCertificateController.class).findById(certificate.getId())).withSelfRel()
                        .withName(FIND_BY_ID))
                .add(linkTo(methodOn(GiftCertificateController.class).findAll(PAGE_VALUE, AMOUNT_VALUE)).withSelfRel()
                        .withName(FIND_ALL))
                .add(linkTo(methodOn(GiftCertificateController.class).delete(certificate.getId())).withSelfRel()
                        .withName(DELETE))
                .add(linkTo(methodOn(GiftCertificateController.class).create(certificate)).withSelfRel()
                        .withName(CREATE))
                .add(linkTo(methodOn(GiftCertificateController.class).update(certificate)).withSelfRel()
                        .withName(UPDATE))
                .add(linkTo(methodOn(GiftCertificateController.class)
                        .findCertificates(TAG_NAME, GIFT_VALUE, ASC, DESC, PAGE_VALUE, AMOUNT_VALUE)).withSelfRel()
                        .withName(FIND_BY_ATTRIBUTES))
                .add(linkTo(methodOn(GiftCertificateController.class).findBySeveralTags(null, PAGE_VALUE, AMOUNT_VALUE))
                        .withSelfRel().withName(FIND_BY_SEVERAL_TAGS));
    }


    public static Order createOrderHateoas(Order order) {
        return order
                .add(linkTo(methodOn(OrderController.class)
                        .findUserOrders(order.getUser().getId(), PAGE_VALUE, AMOUNT_VALUE)).withSelfRel()
                        .withName(FIND_BY_USER_ID_ORDERS))
                .add(linkTo(methodOn(OrderController.class).findAll(PAGE_VALUE, AMOUNT_VALUE)).withSelfRel()
                        .withName(FIND_ALL))
                .add(linkTo(methodOn(OrderController.class).findById(order.getId())).withSelfRel()
                        .withName(FIND_BY_ID))
                .add(linkTo(methodOn(OrderController.class).create(order)).withSelfRel()
                        .withName(CREATE));
    }


    public static Tag createTagHateoas(Tag tag) {
        return tag
                .add(linkTo(methodOn(TagController.class).findTagById(tag.getId())).withSelfRel()
                        .withName(FIND_BY_ID))
                .add(linkTo(methodOn(TagController.class).findAllTags(PAGE_VALUE, AMOUNT_VALUE)).withSelfRel()
                        .withName(FIND_ALL))
                .add(linkTo(methodOn(TagController.class).deleteTagById(tag.getId())).withSelfRel()
                        .withName(DELETE))
                .add(linkTo(methodOn(TagController.class).createTag(tag)).withSelfRel()
                        .withName(CREATE))
                .add(linkTo(methodOn(TagController.class).findMostWidelyUsedTagByMaxUserPrice()).withSelfRel()
                        .withName(FIND_MOST_WIDELY_USED_TAG));
    }

    public static User createUserHateoas(User user) {
        return user
                .add(linkTo(methodOn(UserController.class).findAll(PAGE_VALUE, AMOUNT_VALUE)).withSelfRel()
                        .withName(FIND_ALL))
                .add(linkTo(methodOn(UserController.class).findById(user.getId())).withSelfRel()
                        .withName(FIND_BY_ID));
    }

}
