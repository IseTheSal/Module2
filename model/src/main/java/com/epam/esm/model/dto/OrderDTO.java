package com.epam.esm.model.dto;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDTO extends BasicDTO {

    private User user;
    private GiftCertificate certificate;
    private BigDecimal price;
    private LocalDateTime purchaseDate;

    private OrderDTO() {
    }

    public OrderDTO toDTO(Order order){
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setUser(order.getUser());
        dto.setCertificate(certificate);
        dto.setPrice(order.getPrice());
        return dto;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GiftCertificate getCertificate() {
        return certificate;
    }

    public void setCertificate(GiftCertificate certificate) {
        this.certificate = certificate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderDTO orderDTO = (OrderDTO) o;

        if (user != null ? !user.equals(orderDTO.user) : orderDTO.user != null) return false;
        if (certificate != null ? !certificate.equals(orderDTO.certificate) : orderDTO.certificate != null)
            return false;
        if (price != null ? !price.equals(orderDTO.price) : orderDTO.price != null) return false;
        return purchaseDate != null ? purchaseDate.equals(orderDTO.purchaseDate) : orderDTO.purchaseDate == null;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (certificate != null ? certificate.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (purchaseDate != null ? purchaseDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderDTO{");
        sb.append("user=").append(user);
        sb.append(", certificate=").append(certificate);
        sb.append(", price=").append(price);
        sb.append(", purchaseDate=").append(purchaseDate);
        sb.append('}');
        return sb.toString();
    }
}
