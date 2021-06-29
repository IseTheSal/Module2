package com.epam.esm.service.impl;

import com.epam.esm.error.exception.GiftCertificateNotFoundException;
import com.epam.esm.error.exception.OrderNotFoundException;
import com.epam.esm.error.exception.UserNotFoundException;
import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.OrderDao;
import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final GiftCertificateDao giftCertificateDao;
    private final UserDao userDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, GiftCertificateDao giftCertificateDao, UserDao userDao) {
        this.orderDao = orderDao;
        this.giftCertificateDao = giftCertificateDao;
        this.userDao = userDao;
    }

    @Override
    public Order findById(long id) {
        return orderDao.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Override
    public List<Order> findAll(int amount, int page) {
        checkPagination(amount, page);
        return orderDao.findAll(amount, page - 1);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Order create(Order order) {
        Order newOrder = new Order();
        long userId = order.getUser().getId();
        User user = userDao.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        newOrder.setUser(user);
        long certificateId = order.getCertificate().getId();
        Optional<GiftCertificate> giftCertificate = giftCertificateDao.findById(certificateId);
        if ((!giftCertificate.isPresent())
                || (!giftCertificate.get().isForSale())) {
            throw new GiftCertificateNotFoundException(certificateId);
        }
        GiftCertificate certificate = giftCertificate.get();
        newOrder.setCertificate(certificate);
        newOrder.setPrice(certificate.getPrice());
        return orderDao.create(newOrder);
    }

    @Override
    public List<Order> findUserOrders(long id, int amount, int page) {
        checkPagination(amount, page);
        return orderDao.findUserOrders(id, amount, page - 1);
    }
}
