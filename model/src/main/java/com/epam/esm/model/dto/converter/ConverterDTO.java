package com.epam.esm.model.dto.converter;

import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;

public class ConverterDTO {

    private ConverterDTO(){}

    public static GiftCertificateDTO toDTO(GiftCertificate certificate) {
        GiftCertificateDTO dto = new GiftCertificateDTO();
        dto.setId(certificate.getId());
        dto.setName(certificate.getName());
        dto.setDescription(certificate.getDescription());
        dto.setPrice(certificate.getPrice());
        dto.setDuration(certificate.getDuration());
        dto.setForSale(certificate.isForSale());
        dto.setTags(certificate.getTags());
        dto.setCreateDate(certificate.getCreateDate());
        dto.setLastUpdateDate(certificate.getLastUpdateDate());
        return dto;
    }

    public static GiftCertificate fromDTO(GiftCertificateDTO dto) {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setId(dto.getId());
        certificate.setName(dto.getName());
        certificate.setDescription(dto.getDescription());
        certificate.setPrice(dto.getPrice());
        certificate.setDuration(dto.getDuration());
        certificate.setForSale(dto.isForSale());
        dto.getTags().forEach(certificate::addTag);
        certificate.setCreateDate(dto.getCreateDate());
        certificate.setLastUpdateDate(dto.getLastUpdateDate());
        return certificate;
    }

    public static OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setUser(toDTO(order.getUser()));
        dto.setCertificate(toDTO(order.getCertificate()));
        dto.setPrice(order.getPrice());
        dto.setCreateDate(order.getCreateDate());
        dto.setLastUpdateDate(order.getLastUpdateDate());
        return dto;
    }

    public static Order fromDTO(OrderDTO dto) {
        Order order = new Order();
        order.setId(dto.getId());
        order.setUser(fromDTO(dto.getUser()));
        order.setCertificate(fromDTO(dto.getCertificate()));
        order.setPrice(dto.getPrice());
        order.setCreateDate(dto.getCreateDate());
        order.setLastUpdateDate(dto.getLastUpdateDate());
        return order;
    }

    public static TagDTO toDTO(Tag tag) {
        TagDTO dto = new TagDTO();
        dto.setId(tag.getId());
        dto.setName(tag.getName());
        dto.setCreateDate(tag.getCreateDate());
        dto.setLastUpdateDate(tag.getLastUpdateDate());
        return dto;
    }

    public static Tag fromDTO(TagDTO dto) {
        Tag tag = new Tag();
        tag.setId(dto.getId());
        tag.setName(dto.getName());
        tag.setCreateDate(dto.getCreateDate());
        tag.setLastUpdateDate(dto.getLastUpdateDate());
        return tag;
    }

    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setLogin(user.getLogin());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setRole(user.getRole());
        dto.setCreateDate(user.getCreateDate());
        dto.setLastUpdateDate(user.getLastUpdateDate());
        return dto;
    }

    public static User fromDTO(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setLogin(dto.getLogin());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setRole(dto.getRole());
        user.setCreateDate(dto.getCreateDate());
        user.setLastUpdateDate(dto.getLastUpdateDate());
        return user;
    }
}
