package com.epam.esm.service.impl;

import com.epam.esm.error.exception.ForbiddenException;
import com.epam.esm.error.exception.GiftCertificateNotFoundException;
import com.epam.esm.error.exception.OrderNotFoundException;
import com.epam.esm.error.exception.UserNotFoundException;
import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.dto.converter.ConverterDTO;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.GiftRepository;
import com.epam.esm.model.repository.OrderRepository;
import com.epam.esm.model.repository.UserRepository;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.impl.security.PermissionChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.esm.model.dto.converter.ConverterDTO.fromDTO;
import static com.epam.esm.model.dto.converter.ConverterDTO.toDTO;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final GiftRepository giftRepository;
    private final UserRepository userRepository;
    private final PermissionChecker permissionChecker;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, GiftRepository giftRepository, UserRepository userRepository, PermissionChecker permissionChecker) {
        this.orderRepository = orderRepository;
        this.giftRepository = giftRepository;
        this.userRepository = userRepository;
        this.permissionChecker = permissionChecker;
    }

    @Override
    public OrderDTO findById(long id) {
        if (!permissionChecker.checkAdminPermission()) {
            throw new ForbiddenException();
        }
        return toDTO(orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id)));
    }

    @Override
    public List<OrderDTO> findAll(int amount, int page) {
        if (!permissionChecker.checkAdminPermission()) {
            throw new ForbiddenException();
        }
        checkPagination(amount, page);
        Pageable pageable = PageRequest.of(page - 1, amount);
        return orderRepository.findAll(pageable).stream().map(ConverterDTO::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public OrderDTO create(OrderDTO dto) {
        if (!permissionChecker.checkUserIdPermission(dto.getUser().getId())) {
            throw new ForbiddenException();
        }
        Order order = fromDTO(dto);
        Order newOrder = new Order();
        long userId = order.getUser().getId();
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("id=" + userId));
        newOrder.setUser(user);
        long certificateId = order.getCertificate().getId();
        Optional<GiftCertificate> giftCertificate = giftRepository.findById(certificateId);
        if ((!giftCertificate.isPresent())
                || (!giftCertificate.get().isForSale())) {
            throw new GiftCertificateNotFoundException(certificateId);
        }
        GiftCertificate certificate = giftCertificate.get();
        newOrder.setCertificate(certificate);
        newOrder.setPrice(certificate.getPrice());
        newOrder.setPurchaseDate(LocalDateTime.now());
        return toDTO(orderRepository.save(newOrder));
    }

    @Override
    public List<OrderDTO> findUserOrders(long userId, int amount, int page) {
        if (!permissionChecker.checkUserIdPermission(userId)) {
            throw new ForbiddenException();
        }
        checkPagination(amount, page);
        Pageable pageable = PageRequest.of(page - 1, amount);
        return orderRepository.findOrdersByUserId(userId, pageable).stream().map(ConverterDTO::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO findUserOrder(long userId, long orderId) {
        if (!permissionChecker.checkUserIdPermission(userId)) {
            throw new ForbiddenException();
        }
        return toDTO(orderRepository.findByIdAndUserId(orderId, userId).orElseThrow(() -> new OrderNotFoundException(orderId)));
    }
}
